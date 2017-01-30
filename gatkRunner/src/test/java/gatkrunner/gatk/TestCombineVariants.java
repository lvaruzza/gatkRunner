package gatkrunner.gatk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCombineVariants {

	private final GATKFacade gatk = new GATKFacade();

	@Test
	public void testCombineVarantes() {
		try {
			Path output = Paths.get("combined.vcf");
			gatk.combineVariants(StaticConf.hg19, StaticConf.variantsDir, output);
			Assert.assertTrue(Files.exists(output));
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
