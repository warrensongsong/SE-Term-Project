package md_converter;

//������ ���� ����Ʈ�� �ش�
//<ul><li></li></ul>

public class UnorderedList extends Structure{
	public UnorderedList(String content) {
		setStructureType("UnorderedList");
		setContent(content);
	}	
}
