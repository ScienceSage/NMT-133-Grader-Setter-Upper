package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

public class Untar {

	public Untar(File[] files, File rubric) {
		for (File file : files) {
			/* Untar */
			String destinationName = file.toString().replace(".tar.gz", "");
			File destination = new File(destinationName);

			Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");
			try {
				archiver.extract(file, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/* Add Rubric */
			String tmp[] = file.toString().split("\\\\");
			String fileName = tmp[tmp.length - 1];
			String parts[] = fileName.split("_");
			String name = parts[1] + "_" + parts[2];
			name = name.toLowerCase();
			
			tmp = rubric.toString().split("\\\\");
			String rubricName = tmp[tmp.length - 1];
			rubricName = rubricName.replace(".odt", "");
			
			fileName = destinationName + "\\" + rubricName + "_" + name + ".odt";
			File rubricDestination = new File(fileName);
			try {
				copyFile(rubric, rubricDestination);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/* Delete Tarball */
			try {
				Files.delete(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;
	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();

	        // previous code: destination.transferFrom(source, 0, source.size());
	        // to avoid infinite loops, should be:
	        long count = 0;
	        long size = source.size();              
	        while((count += destination.transferFrom(source, count, size-count))<size);
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
	
}
