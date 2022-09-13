package classes;

import java.io.File;

public class FindJDK {


	public static String getPathForWindows(){
		
		String jdk = FindJDKByDirectory("C:\\Program Files");
		jdk = jdk.length()==0?FindJDKByDirectory("C:\\Program Files (x86)"):jdk;
		jdk = jdk.length()==0?jdk:jdk.concat( System.getProperty("file.separator").concat("bin").concat(System.getProperty("file.separator")).concat("javac"));
		return jdk;
	}

	public static String getPath(){
		
		String jdk = FindJDKByDirectory("C:\\Program Files");
		jdk = jdk.length()==0?FindJDKByDirectory("C:\\Program Files (x86)"):jdk;
		return jdk;
	}
	private static String FindJDKByDirectory(String directory) {
		File file  = new File(directory);
		if(file.isDirectory()){
			for(File children : file.listFiles()){
				if(children.getName().equalsIgnoreCase("Java")){
					if(children.isDirectory()){
						for(File jdk : children.listFiles()){
							if(jdk.getName().toString().toLowerCase().contains("jdk")){
								return jdk.getAbsolutePath();
							}
						}
					}
				}
			}
		}
		return "";
	}
}
