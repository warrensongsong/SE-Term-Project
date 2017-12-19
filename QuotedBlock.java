package md_converter;

//인덴트된 텍스트에 해당
//<blockquote></blockquote>

public class QuotedBlock extends Structure{
	public QuotedBlock(String content) {
		setStructureType("QuotedBlock");
		setContent(content);
	}
}
