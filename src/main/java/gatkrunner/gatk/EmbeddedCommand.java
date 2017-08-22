package gatkrunner.gatk;

import org.broadinstitute.gatk.engine.CommandLineExecutable;
import org.broadinstitute.gatk.engine.arguments.GATKArgumentCollection;
import org.broadinstitute.gatk.utils.commandline.Argument;
import org.broadinstitute.gatk.utils.commandline.ArgumentCollection;
import org.broadinstitute.gatk.utils.progressmeter.ProgressMeter;

public class EmbeddedCommand extends CommandLineExecutable {

	@Argument(fullName = "analysis_type", shortName = "T", doc = "Name of the tool to run")
	private String analysisName = null;
    
    @ArgumentCollection
	private GATKArgumentCollection args = new GATKArgumentCollection();
		
	@Override
	public String getAnalysisName() {
		
		return analysisName;
	}

	@Override
	protected GATKArgumentCollection getArgumentCollection() {
		return args;
	}

	
	public ProgressMeter getProgressMeter() {
		return engine.getProgressMeter();
	}
	
}
