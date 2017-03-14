package gatkrunner.gatk;

import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;

public class PathUtils {

	public static Path changeExtension(Path v, String ext) {
		String base = FilenameUtils.getBaseName(v.getFileName().toString());
		return v.resolveSibling(base+"."+ext);
	}

}
