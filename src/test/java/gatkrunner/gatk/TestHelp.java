package gatkrunner.gatk;

import org.testng.annotations.Test;

public class TestHelp {

    private final GATKFacade gatk = new GATKFacade();

    @Test
    public void showHelp() {
        //gatk.showHelp();

        EmbeddedCommand instance = new EmbeddedCommand();
        try  {
            EmbeddedCommand.start(instance, new String[]{"-T","CombineVariants"});
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
