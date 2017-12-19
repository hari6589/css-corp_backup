package samples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.attribute.FileAttribute;

public class FileManipulation {

	public static void main(String[] args) {
		String fileName = "testFile";
//		try {
//			File file = Files.createTempFile(fileName, "txt", new FileAttribute[0]).toFile();
//			System.out.println("File Path : " + file.getPath());
//	    	System.out.println("File Name : " + file.getName());
//	    	FileWriter fw = new FileWriter(file.getAbsoluteFile());
//	    	BufferedWriter bw = new BufferedWriter(fw);
//	    	
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            bw.write("Data!");
//	    	
//	    	bw.close();
//	    	fw.close();
//            file.delete();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
