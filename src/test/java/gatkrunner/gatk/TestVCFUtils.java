package gatkrunner.gatk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;


public class TestVCFUtils {

    private static Logger log = LoggerFactory.getLogger(TestVCFUtils.class);

    @Test
    public void testListFiles() throws IOException {
        long count=VCFUtils.listVCFFiles(Paths.get("data/list")).map( p -> { log.info(p.toString()); return 1;}).count();
        assertEquals(6,count);
    }
}
