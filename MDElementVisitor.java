package md_converter;

/*
����� ��ǲ���� ���� ���� �̸��� �ɼ��� ����ȴ�
MDElement.accept() �޼ҵ��� �ƱԸ�Ʈ�� ���ȴ�
*/

public class MDElementVisitor {
	public String md_name;
	public String conv_option;
	public String html_name;


	public void visitDocument(String md_name) {	
		this.md_name = md_name;
	}
	
	public void visitStructure(String conv_option) {
		this.conv_option = conv_option;
	}
	
	public void genHtmlName(String html_name) {
		this.html_name = html_name;
	}
}
