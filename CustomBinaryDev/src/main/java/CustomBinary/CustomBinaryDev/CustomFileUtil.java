package CustomBinary.CustomBinaryDev;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class CustomFileUtil {
	private static String textImageSeperator = "*xxxxxjjjjjyyyyyyzzz*";
	private static String imageImageSeperator = "&yyyyyyjjjjjyyyyyykkk&";
	private static byte[] TEXT_IMAGE_SEQUENCE;
	private static byte[] IMAGE_IMAGE_SEQUENCE;
	
	private static boolean isInitiateOK = false;
	
	public static void initializeUtil(){
		try {
			TEXT_IMAGE_SEQUENCE = textImageSeperator.getBytes("ISO-8859-1");
			IMAGE_IMAGE_SEQUENCE = imageImageSeperator.getBytes("ISO-8859-1");
			isInitiateOK = true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean createFile(byte[] textBytes, List<byte[]> imageBytes, String newFilePath){
		if(!isInitiateOK)
			return false;
		FileOutputStream outFileStream = null;
		try {
			outFileStream= new FileOutputStream(new File(newFilePath));
			outFileStream.write(textBytes);
			outFileStream.write(TEXT_IMAGE_SEQUENCE);
			for(byte[] bytes:imageBytes){
				outFileStream.write(bytes);
				outFileStream.write(IMAGE_IMAGE_SEQUENCE);
			}
			outFileStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
