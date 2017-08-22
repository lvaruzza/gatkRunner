package gatkrunner.gatk;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;

import org.broadinstitute.gatk.utils.progressmeter.ProgressMeter;
import org.testng.annotations.Test;

import gatkrunner.gatk.GATKFacade;

public class TestFacade {

	private final GATKFacade gatk = new GATKFacade();

	@SuppressWarnings("unused")
	private void testLarge() throws Exception {
		int res=gatk.subsampĺeVariants(StaticConf.hg19, 0.0001, 
				StaticConf.dbSNP, Paths.get("dbSNP_subsample.vcf"));
		assertEquals(res, 0);
	}

	private void testSmall() {
		int res = gatk.subsampĺeVariants(StaticConf.hg19, 0.0001, 
				Paths.get("data/dbSNP_subsample.vcf"),
				Paths.get("dbSNP_small.vcf"));

		assertEquals(res, 0);
	}

	/*
	 * @Test public void testRun1() { testSmall(); }
	 */

	@Test
	public void testRunInThread() throws InterruptedException {
		Runnable gatkTask = () -> {
			System.out.println("====> Thead Started");
			testSmall();
			if (gatk.hasFailed()) {
				fail();
			}
			System.out.println("====> Thead Finished");
		};

		// gatkTask.run();

		Thread gatkThread = new Thread(gatkTask);
		gatkThread.start();
		int count = 0;
		MBeanServer mserver=ManagementFactory.getPlatformMBeanServer();
		 Set<ObjectInstance> instances = mserver.queryMBeans(null, null);
		 for(ObjectInstance x:instances) {
			 System.out.print(x.getObjectName());
		 }		
		
		
		while (gatkThread.isAlive()) {
			
			System.out.println("====> Main Thread is waiting: " + count);
			Thread.sleep(5000);
			count++;
		}

	}

}
