package com.tmo.gatk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.WriterAppender;
import org.broadinstitute.gatk.engine.CommandLineGATK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmo.reference.GenomeRef;


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

	public int run(String command,GenomeRef genome,Object... args) throws Exception {
		Stream<String> sargs=Arrays.stream(args).map( (Object x) -> x.toString());
		String[] strArgs = Stream.concat(
				Stream.of("-T",command,"-R",genome.fastaFile().toString()),
				sargs)
				.toArray(String[]::new);
		
		return runThread(strArgs);
	}
	
	public int runThread(String... args) throws Exception {
		log4jout.reset();
		org.apache.log4j.Logger.getRootLogger().getLoggerRepository().resetConfiguration();
		WriterAppender appender = new WriterAppender(new org.apache.log4j.SimpleLayout(), log4jout);
		org.apache.log4j.Logger.getRootLogger().addAppender(appender);

		Set<Thread> threadSetBefore = Thread.getAllStackTraces().keySet();
		threadSetBefore.forEach(t-> {
			System.out.println("==> Thread: "  + t.getName() + " g:" + t.getThreadGroup().getName());
		});

		logger.info(String.format("Running gatk. args=[%s]", StringUtils.join(args, ' ')));
		CommandLineGATK instance = new CommandLineGATK();
		CommandLineGATK.start(instance, args);
		//CommandLineGATK.main(args);
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		threadSet.forEach(t-> {
			System.out.println("==> Thread: "  + t.getName() + " g:" + t.getThreadGroup().getName());
		});
		logger.info("GATK task finished");
		return 0;
	}

	
	public int selectVariants(GenomeRef reference,Path variants, Path bedFile, Path output) throws Exception {
		return run("SelectVariants",reference, 
				"-U","ALL","-S","LENIENT",
				"-l","INFO","-log",logFileName,
				"-V", variants.toAbsolutePath(), 
				"-L", bedFile.toAbsolutePath(), 
				"-o", output.toString());
	}
	
	public int combineVariants(GenomeRef reference,List<Path> variants) throws Exception {
		return run("CombineVariants",reference);
	}
	
	
	public int subsampĺeVariants(GenomeRef reference,double fraction, Path variants,Path output) throws Exception {
		return run("SelectVariants",reference,
				"-fraction",fraction,
				"-V",variants.toAbsolutePath(),
				"-o",output.toAbsolutePath());
				
	}
}
