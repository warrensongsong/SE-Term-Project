package md_converter;
import java.util.ArrayList;

/*

ASTree ��ü
md�ý�Ʈ�� html�ý�Ʈ�� �߰��ܰ�
�������� ��Ʈ���ķ� �����Ǿ��ִ�

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
