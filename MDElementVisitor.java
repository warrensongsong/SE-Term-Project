package md_converter;

/*
사용자 인풋으로 받은 파일 이름과 옵션이 저장된다
MDElement.accept() 메소드의 아규먼트로 사용된다
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
