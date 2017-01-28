package com.tmo.gatk;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.tmo.reference.GenomeRef;
import com.tmo.reference.SimpleGenomeRef;

public class StaticConf {
	public static GenomeRef hg19 = new SimpleGenomeRef(Paths.get("/data/ref/hg19/hg19.fasta"));
	public static Path dbSNP = Paths.get("/data/dbSNP/human/00-All.chr.vcf.gz");
}
