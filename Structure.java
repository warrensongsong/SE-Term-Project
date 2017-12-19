package md_converter;
import java.util.ArrayList;

/*
Header, Horizontal, PlainContent, QuotedBlock, UnorderedList Ŭ������
Structure Ŭ������ ����Ͽ� ��������
*/


abstract public class Structure {
	
	
	//structureType : Ŭ���� �̸��� ��õǾ� ������ ���� ������ ���ؼ� ��Ʈ�� ������ �־���//
	public String structureType;
	
	//content : �ش� ��Ʈ��ó�� ����� ���ڿ��� ���� ����//
	public String content;
	
	//hasChild : UnorderedList ó�� nested�Ǵ� ��쿡 haschild�� true��
	//nested �Ǿ� �ִٴ� ���̴�. �׷��� �ȿ� ��Ʈ��ó�� Ȯ���ؾߵȴ�//
	public boolean hasChild = false;
	
	
	// textStyle : bold, italic ���� �������ٰ� ������ �ȿ� ������ �����ȴ� //
	// styledConetn : bold, italic ���� string�κ��� ����ȴ� //
	public String textStyle;
	public boolean hasStyle = false;
	public String styledContent;
	public String styledContent2;
	
	
	//childStructure : nested�� ��쿡 ������ �Ѱ� Ȥ�� �������� child structure//
	public ArrayList<Structure> childStructure = new ArrayList<>();
	
	public String url;
	
	public void setStructureType(String st) {
		this.structureType = st;
	}
	public String getStructureType() {
		return structureType;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void makeChild(Structure childStructure) {
		hasChild = true;
		this.childStructure.add(childStructure);
	}
	
	//����//
	// 1. bold //
	// 2. italic //
	// 3. underline //
	// 4. image //
	public void setTextStyle(String textStyle, String styledContent) {
		this.textStyle = textStyle;
		this.hasStyle = true;
		this.styledContent = styledContent;
	}
	
	//�����ε�
	public void setTextStyle(String textStyle, String styledContent, String styleContent2) {
		this.textStyle = textStyle;
		this.hasStyle = true;
		this.styledContent = styledContent;
		this.styledContent2 = styleContent2;
	}
	
	//��ũ�� �̹����� ��� �ش� ������ Ȥ�� ������ url�� �����Ѵ�
	public void setURL(String url) {
		this.url = url;
	}
}
