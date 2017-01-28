package com.tmo.reference;

import java.nio.file.Path;

public class SimpleGenomeRef implements GenomeRef {
	private Path file;
	
	public SimpleGenomeRef(Path refFile) {
		this.file=refFile.toAbsolutePath();
	}
	@Override
	public Path fastaFile() {
		return file;
	}

}
