package md_converter;
import java.util.ArrayList;

/*

ASTree 객체
md택스트와 html택스트의 중간단계
여러개의 스트럭쳐로 구성되어있다

 */


public class MDElement {
	
	ArrayList<Structure> structureList;
	String htmlFileName;
	
	public void accept(MDElementVisitor mdev) {
		this.htmlFileName = mdev.html_name;
		MDParser parser = new MDParser(mdev.md_name);
		String wholecontent = parser.MdToString();
		structureList = parser.structureParsing(wholecontent);
		
	}
}
