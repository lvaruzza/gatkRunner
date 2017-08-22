package gatkrunner.gatk;

import java.io.File;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TestIndex {
	private File idxFile = new File("data/test.idx"); 
			
	@Test
	public void testCreateIndex() {
		idxFile.delete();
		VCFUtils.indexVCF(Paths.get("data/test.vcf"));
		assertTrue(idxFile.exists());
	}
}
