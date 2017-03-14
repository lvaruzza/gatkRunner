package gatkrunner.gatk;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import utils.reference.GenomeRef;
import utils.reference.SimpleGenomeRef;

public class StaticConf {
	public static GenomeRef hg19 = new SimpleGenomeRef(Paths.get("data/hg19.fasta"));
	public static Path dbSNP = Paths.get("/data/dbSNP/human/00-All.chr.vcf.gz");
	public static Path variantsDir = Paths.get("data/variants");
}
