package gatkrunner.gatk;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import htsjdk.tribble.index.Index;
import htsjdk.tribble.index.IndexFactory;
import htsjdk.tribble.util.LittleEndianOutputStream;
import htsjdk.variant.vcf.VCFCodec;

public class VCFUtils {
	private static Logger logger = LoggerFactory.getLogger(VCFUtils.class);
	
	public static Stream<Path> listVCFFiles(Path variantsDir) throws IOException {
		PathMatcher vcfMatcher = FileSystems.getDefault().getPathMatcher("glob:*.{vcf,vcf.gz}");
		logger.info(String.format("Looking for variants in '%s'", variantsDir.toString()));
		Stream<Path> variants=Files.list(variantsDir)
		    	.filter((Path p) -> vcfMatcher.matches(p.getFileName()))
		        .peek( (Path p) -> logger.info(String.format("Detect variant '%s' matches='%b'", p.toString(),vcfMatcher.matches(p.getFileName()))))
		    	;
		
		return variants;
	}
	
	public static Path indexPath(Path v) {
		return PathUtils.changeExtension(v,"idx");
	}
	
	public static void indexVCF(Path v)  {
		try {
			LittleEndianOutputStream out = new LittleEndianOutputStream(new BufferedOutputStream(new FileOutputStream(indexPath(v).toFile())));
			Index index=IndexFactory.createDynamicIndex(v.toFile(), new VCFCodec());
			index.write(out);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Stream<Path> assureIndex(Stream<Path> variants) {
		return variants.filter( v -> !indexPath(v).toFile().exists()).map( v -> {
			indexVCF(v);
			return v;
		});
	}
}
