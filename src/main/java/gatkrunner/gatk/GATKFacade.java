package gatkrunner.gatk;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.WriterAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.reference.GenomeRef;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;


public class GATKFacade {
	private static Logger logger = LoggerFactory.getLogger(GATKFacade.class);

	public static String logFileName="logs/gatk.log";

	
	private ByteArrayOutputStream log4jout = new ByteArrayOutputStream();

	private boolean useProcess = true;
	
	public boolean useProcess() {
		return useProcess;
	}
	
	public void useProcess(boolean flag) {
		this.useProcess =flag;
	}
	public void writeLog(OutputStream out) throws IOException {
		log4jout.writeTo(out);
	}

	public String getLog() throws IOException {
		return log4jout.toString();
	}

	
	public int run(String command,GenomeRef genome,Object... args) {
		Stream<String> sargs=Arrays.stream(args).map( (Object x) -> x.toString());
		String[] strArgs = Stream.concat(
				Stream.of("-T",command,"-R",genome.fastaFile().toString()),
				sargs)
				.toArray(String[]::new);
		
		return runThread(strArgs);
	}
	
	private Optional<Exception> gatkException = Optional.empty();
	public Exception getException() {
		return gatkException.get();
	}
	
	@SuppressWarnings("static-access")
	public int runThread(String... args) {
		log4jout.reset();
		gatkException = Optional.empty();
		org.apache.log4j.Logger.getRootLogger().getLoggerRepository().resetConfiguration();
		WriterAppender appender = new WriterAppender(new org.apache.log4j.SimpleLayout(), log4jout);
		org.apache.log4j.Logger.getRootLogger().addAppender(appender);


		logger.info(String.format("Running gatk. args=[%s]", StringUtils.join(args, ' ')));
		EmbeddedCommand instance = new EmbeddedCommand();
		try {
			EmbeddedCommand.start(instance, args);
		} catch(Exception e) {
			logger.error("Analsys failed: " + e.getMessage());
			e.printStackTrace();
			gatkException = Optional.of(e);
			throw new RuntimeException(e);
		}
		return instance.result;
	}

	public int selectVariants(GenomeRef reference,Path variants, Path bedFile, Path output) {
		return run("SelectVariants",reference, 
				"-U","ALL","-S","LENIENT",
				"-l","INFO","-log",logFileName,
				"-V", variants.toAbsolutePath(), 
				"-L", bedFile.toAbsolutePath(), 
				"-o", output.toString());
	}
	
	public int combineVariants(GenomeRef reference,Stream<Path> variants,Path output) {
		Stream<String> varLst = variants.map((Path p) ->Stream.of("-V",p.toString())).flatMap(x -> x);
		Stream<String> argLst = Stream.concat(varLst,Stream.of("-o",output.toString(),
											   "--excludeNonVariants",
											   "-genotypeMergeOptions","UNIQUIFY"));
		//System.out.println(varLst.collect(Collectors.joining(" ")));
		return run("CombineVariants",reference,argLst.toArray());
	}
	
	
	public int subsampĺeVariants(GenomeRef reference,double fraction, Path variants,Path output) {
		return run("SelectVariants",reference,
				"-fraction",fraction,
				"-V",variants.toAbsolutePath(),
				"-o",output.toAbsolutePath());
				
	}

	public boolean hasFailed() {
		return gatkException.isPresent();
	}


	public void combineVariants(GenomeRef reference, Path variantsDir, Path output) throws IOException {
		combineVariants(reference,VCFUtils.listVCFFiles(variantsDir),output);
	}
}
