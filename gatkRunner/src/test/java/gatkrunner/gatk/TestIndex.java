package gatkrunner.gatk;

import java.nio.file.Paths;

import org.testng.annotations.Test;

public class TestIndex {

	@Test
	public void testCreateIndex() {
		VCFUtils.indexVCF(Paths.get("data/test.vcf"));
	}
}
