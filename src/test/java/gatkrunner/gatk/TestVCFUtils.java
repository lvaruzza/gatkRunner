package gatkrunner.gatk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.testng.Assert.assertEquals;


public class TestVCFUtils {

    private static Logger log = LoggerFactory.getLogger(TestVCFUtils.class);

    @Test
    public void testListFiles() throws IOException {
        long count=VCFUtils.listVCFFiles(Paths.get("data/list")).map( p -> { log.info(p.toString()); return 1;}).count();
        assertEquals(6,count);
    }

    @Test
    public void testGetSampleName() {
        assertEquals("Exome",VCFUtils.getSampleName(Paths.get("data/test.vcf")));
    }

    @Test
    public void testMakeSamplesDictionary() throws IOException {
        Map<String,String> m=VCFUtils.makeSamplesDictionary(VCFUtils.listVCFFiles(Paths.get("data/list")));
        for(Map.Entry<String,String> e:m.entrySet()) {
            System.out.println(e.getKey() +"\t"+e.getValue());
        }
    }
}
