package CustomBinary.CustomBinaryDev;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class CustomFileUtil {
	private static String headerSeperator = "*xxxxxjjjjjyyyyyyzzz*";
	private static String contentSeperator = "&yyyyyyjjjjjyyyyyykkk&";
	private static byte[] HEADER_SEPERATOR_SEQUENCE;
	private static byte[] CONTENT_SEPERATOR_SEQUENCE;
	private static String DEFAULT_ENCODING = "ISO-8859-1";
	
	private static boolean isInitiateOK = false;
	
	public static String HEADER = "HEADER";
	public static String TEXT_CONTENT = "TEXT_CONTENT";
	public static String IMAGE_CONTENT = "IMAGE_CONTENT";
	
	public static void initializeUtil(){
		try {
			HEADER_SEPERATOR_SEQUENCE = headerSeperator.getBytes(DEFAULT_ENCODING);
			CONTENT_SEPERATOR_SEQUENCE = contentSeperator.getBytes(DEFAULT_ENCODING);
			isInitiateOK = true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean createFile(Note note, String newFilePath){
		if(!isInitiateOK)
			return false;
		
		
		FileOutputStream outFileStream = null;
		try {
			outFileStream= new FileOutputStream(new File(newFilePath));
			outFileStream.write(createHeader(note));
			outFileStream.write(HEADER_SEPERATOR_SEQUENCE);
			for(byte[] bytes:note.getContents()){
				outFileStream.write(bytes);
				outFileStream.write(CONTENT_SEPERATOR_SEQUENCE);
			}
			outFileStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static byte[] createHeader(Note note){
		byte[] headerBytes = null;
		String headerString = new String();
		
		if(null != note.getContentTypes()){
			for(int i=0;i<note.getContentTypes().size();i++){
				String contentType = note.getContentTypes().get(i);
				headerString += contentType + ":";
			}
			try {
				headerBytes = headerString.getBytes(DEFAULT_ENCODING);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return headerBytes;
	}
	
	public static Note extractNote(String filePath){
		Note note = null;
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		byte[] inputBytes;
		List<byte[]> contents = new LinkedList<byte[]>();
		List<String> contentTypes = new LinkedList<String>();
		String[] contentTypeArray = null;
		
		try{
			FileInputStream inputStream = new FileInputStream(new File(filePath));
			note = new Note();
			int b;
			while((b = inputStream.read()) != -1){
				byteOutputStream.write(b);
			}
			inputStream.close();
			
			inputBytes = byteOutputStream.toByteArray();
			int startPos = 0;
			for(int i=0; i< inputBytes.length; i++){
				if(inputBytes[i] == HEADER_SEPERATOR_SEQUENCE[0] && isHeaderSeperator(i, inputBytes)){
					byte[] headerBytes = new byte[(i-1) - startPos];
					System.arraycopy(inputBytes, startPos, headerBytes, 0, (i-1) - startPos);
					String headerString = new String(headerBytes, DEFAULT_ENCODING);
					contentTypeArray = headerString.split(":");
					startPos = i = (i + HEADER_SEPERATOR_SEQUENCE.length); 
				}else if(inputBytes[i] == CONTENT_SEPERATOR_SEQUENCE[0] && isContentSeperator(i, inputBytes)){
						byte[] contentBytes = new byte[(i-1) - startPos];
						System.arraycopy(inputBytes, startPos, contentBytes, 0, (i-1) - startPos);
						int length = contentTypes.size();
						contentTypes.add(contentTypeArray[length]);
						contents.add(contentBytes);
						startPos = i = (i + CONTENT_SEPERATOR_SEQUENCE.length);
				}
			}
			note.setContents(contents);
			note.setContentTypes(contentTypes);
			
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return note;
	}
	
	private static boolean isHeaderSeperator(int startLocation, byte[] inputBytes){
		int matchCount = 0;
		int headerCount = 0;
		for(int i = startLocation; i <= (startLocation + (HEADER_SEPERATOR_SEQUENCE.length - 1));i++){
			if(inputBytes[i] == HEADER_SEPERATOR_SEQUENCE[headerCount]){
				headerCount++;
				matchCount++;
				if(matchCount == HEADER_SEPERATOR_SEQUENCE.length){
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isContentSeperator(int startLocation, byte[] inputBytes){
		int matchCount = 0;
		int contentCount = 0;
		for(int i = startLocation; i <= (startLocation + (CONTENT_SEPERATOR_SEQUENCE.length - 1));i++){
			if(inputBytes[i] == CONTENT_SEPERATOR_SEQUENCE[contentCount]){
				contentCount++;
				matchCount++;
				if(matchCount == CONTENT_SEPERATOR_SEQUENCE.length){
					return true;
				}
			}
		}
		return false;
	}
	
}
