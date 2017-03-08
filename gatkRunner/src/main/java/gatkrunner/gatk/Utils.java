package gatkrunner.gatk;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static Stream<Path> listVCFFiles(Path variantsDir) throws IOException {
		PathMatcher vcfMatcher = FileSystems.getDefault().getPathMatcher("glob:*.{vcf,vcf.gz}");
		logger.info(String.format("Looking for variants in '%s'", variantsDir.toString()));
		Stream<Path> variants=Files.list(variantsDir)
		    	.filter((Path p) -> vcfMatcher.matches(p.getFileName()))
		        .peek( (Path p) -> logger.info(String.format("Detect variant '%s' matches='%b'", p.toString(),vcfMatcher.matches(p.getFileName()))))
		    	;
		
		return variants;
	}
}
