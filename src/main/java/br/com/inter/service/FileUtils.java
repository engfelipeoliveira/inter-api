package br.com.inter.service;

import static java.nio.file.Files.move;
import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileUtils {

	public static FilenameFilter filterFile(String... exts) {
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				for (String ext : exts) {
		             if (name.toUpperCase().endsWith(ext)) {
		                 return true;
		             }
		        }
				return false;
			}
		};
		return filter;
	}
	
	public static void moveFile(String dirTarget, File file) throws IOException {
		if(!isBlank(dirTarget)) {					
			move(get(file.getAbsolutePath()), get(new File(dirTarget + file.getName()).getAbsolutePath())); 					
		}
	}
	
}
