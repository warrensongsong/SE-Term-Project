package md_converter;

//�ε�Ʈ�� �ؽ�Ʈ�� �ش�
//<blockquote></blockquote>

public class QuotedBlock extends Structure{
	public QuotedBlock(String content) {
		setStructureType("QuotedBlock");
		setContent(content);
	}
}
