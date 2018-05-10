package gatkrunner.gatk;

import org.testng.annotations.Test;

public class TestHelp {

    private final GATKFacade gatk = new GATKFacade();

    @Test
    public void showHelp() {
        gatk.showHelp();
    }
}
