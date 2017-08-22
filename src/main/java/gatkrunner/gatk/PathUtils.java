package gatkrunner.gatk;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;

public class PathUtils {

	public static Path changeExtension(Path v, String ext) {
		String base = FilenameUtils.getBaseName(v.getFileName().toString());
		return v.resolveSibling(base+"."+ext);
	}

}
