package com.tmo.gatk;

import java.nio.file.Paths;

import org.testng.annotations.Test;

public class TestFacade {

	
	private void testLarge() {
		GATKFacade gatk=new GATKFacade();
		try {
			gatk.subsampĺeVariants(StaticConf.hg19, 0.0001, 
					StaticConf.dbSNP, Paths.get("dbSNP_subsample.vcf"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testSmall() {
		GATKFacade gatk=new GATKFacade();
		try {
			gatk.subsampĺeVariants(StaticConf.hg19, 0.0001, 
					Paths.get("data/dbSNP_subsample.vcf"), Paths.get("dbSNP_small.vcf"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/*	@Test
	public void testRun1() {
		testSmall();
	}
*/
	
	
	@Test
	public void testRunInThread() throws InterruptedException {
		Runnable gatkTask = () -> {
			System.out.println("====> First Run");
			testSmall();			
			System.out.println("====> Second Run");
			testSmall();			
		};
		
		//gatkTask.run();
		
		Thread gatkThread = new Thread(gatkTask);
		gatkThread.start();
		int count = 0;
		while(gatkThread.isAlive()) {
			System.out.println("====> Main Thread is waiting: " + count);
			Thread.sleep(5000);
			count++;
		}
		
	}

}
