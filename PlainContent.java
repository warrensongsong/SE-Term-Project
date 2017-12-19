package md_converter;

//일반 텍스트에 해당

public class PlainContent extends Structure{
	public PlainContent(String content) {
		setStructureType("PlainContent");
		setContent(content);
	}

}
