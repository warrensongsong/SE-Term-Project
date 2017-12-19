package md_converter;

//순서가 없는 리스트에 해당
//<ul><li></li></ul>

public class UnorderedList extends Structure{
	public UnorderedList(String content) {
		setStructureType("UnorderedList");
		setContent(content);
	}	
}
