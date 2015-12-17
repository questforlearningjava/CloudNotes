package CustomBinary.CustomBinaryDev;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestCustomFile {
	public static void main(String[] args){
		
		byte[] textBytes = null;
		List<byte[]> imageBytes = new ArrayList<byte[]>();
		
		ByteArrayOutputStream tempByteStream = new ByteArrayOutputStream();
		InputStream fInput = null;
		
		try{
			fInput = TestCustomFile.class.getResourceAsStream("/Note1.txt");
			writeBytes(fInput, tempByteStream);
			textBytes = tempByteStream.toByteArray();
			tempByteStream.reset();
			
			fInput = TestCustomFile.class.getResourceAsStream("/Scan1.jpg");
			writeBytes(fInput, tempByteStream);
			imageBytes.add(tempByteStream.toByteArray());
			tempByteStream.reset();
			
			fInput = TestCustomFile.class.getResourceAsStream("/Scan2.jpg");
			writeBytes(fInput, tempByteStream);
			imageBytes.add(tempByteStream.toByteArray());
			tempByteStream.reset();
			
			tempByteStream.close();
			fInput.close();
			
			CustomFileUtil.initializeUtil();
			CustomFileUtil.createFile(textBytes, imageBytes, "E:\\file.note");
			
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
