package CustomBinary.CustomBinaryDev;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestCustomFile {
	public static void main(String[] args){
		
		
		ByteArrayOutputStream tempByteStream = new ByteArrayOutputStream();
		InputStream fInput = null;
		Note note = new Note();		
		try{
			fInput = TestCustomFile.class.getResourceAsStream("/Note1.txt");
			writeBytes(fInput, tempByteStream);
			note.getContentTypes().add(CustomFileUtil.TEXT_CONTENT);
			note.getContents().add(tempByteStream.toByteArray());
			tempByteStream.reset();
			
			fInput = TestCustomFile.class.getResourceAsStream("/Scan1.jpg");
			writeBytes(fInput, tempByteStream);
			note.getContentTypes().add(CustomFileUtil.IMAGE_CONTENT);
			note.getContents().add(tempByteStream.toByteArray());
			tempByteStream.reset();
			
			fInput = TestCustomFile.class.getResourceAsStream("/Scan2.jpg");
			writeBytes(fInput, tempByteStream);
			note.getContentTypes().add(CustomFileUtil.IMAGE_CONTENT);
			note.getContents().add(tempByteStream.toByteArray());
			tempByteStream.reset();
			
			tempByteStream.close();
			fInput.close();
			
			CustomFileUtil.initializeUtil();
			CustomFileUtil.createFile(note, "/home/linux/Documents/TempOut/file.note");
			Note extractedNote = CustomFileUtil.extractNote("/home/linux/Documents/TempOut/file.note");
			
			FileOutputStream fileOutputImage = new FileOutputStream(new File("/home/linux/Documents/TempOut/image.jpg"));
			fileOutputImage.write(extractedNote.getContents().get(1));
			fileOutputImage.close();
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private static void writeBytes(InputStream input, ByteArrayOutputStream ouput){
		int readByte;
		try {
			while( (readByte = input.read()) != -1){
				ouput.write(readByte);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
