package CustomBinary.CustomBinaryDev;

import java.util.LinkedList;
import java.util.List;

public class Note {
	
	private List<String> contentTypes;
	private List<byte[]> contents;
	
	
	public List<String> getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(List<String> contentTypes) {
		this.contentTypes = contentTypes;
	}

	public List<byte[]> getContents() {
		return contents;
	}

	public void setContents(List<byte[]> contents) {
		this.contents = contents;
	}

	
	
	public Note(){
		contentTypes = new LinkedList<String>();
		contents = new LinkedList<byte[]>();
	}
	
	
	
}
