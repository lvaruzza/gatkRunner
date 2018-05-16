package gatkrunner.gatk;

import org.broadinstitute.gatk.engine.walkers.Walker;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.testng.annotations.Test;

import java.util.Set;

public class TestHelp {

    private final GATKFacade gatk = new GATKFacade();

    @Test
    public void showHelp() {
        //gatk.showHelp();

        Reflections reflections = new Reflections((new ConfigurationBuilder()).setUrls(ClasspathHelper.forPackage("org.broadinstitute.gatk")).setScanners(new Scanner[]{new SubTypesScanner()}));

        Set<Class<? extends Walker>> allTypes = reflections.getSubTypesOf(Walker.class);

        for(Class<? extends Walker> klass:allTypes) {
            System.out.println(klass.getName());
        }


        EmbeddedCommand instance = new EmbeddedCommand();
        try  {
            EmbeddedCommand.start(instance, new String[]{"-T","CombineVariants"});
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
