package md_converter;
import java.util.ArrayList;

/*
Header, Horizontal, PlainContent, QuotedBlock, UnorderedList 클래스는
Structure 클래스를 상속하여 쓰여진다
*/


abstract public class Structure {
	
	
	//structureType : 클래스 이름에 명시되어 있지만 쉬운 구분을 위해서 스트링 변수에 넣었다//
	public String structureType;
	
	//content : 해당 스트럭처가 적용될 문자열을 담은 변수//
	public String content;
	
	//hasChild : UnorderedList 처럼 nested되는 경우에 haschild가 true면
	//nested 되어 있다는 뜻이다. 그래서 안에 스트럭처를 확인해야된다//
	public boolean hasChild = false;
	
	
	// textStyle : bold, italic 등을 지정해줄게 컨텐츠 안에 있으면 지정된다 //
	// styledConetn : bold, italic 해줄 string부분이 저장된다 //
	public String textStyle;
	public boolean hasStyle = false;
	public String styledContent;
	public String styledContent2;
	
	
	//childStructure : nested된 경우에 지정될 한개 혹은 여러개의 child structure//
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
	
	//종류//
	// 1. bold //
	// 2. italic //
	// 3. underline //
	// 4. image //
	public void setTextStyle(String textStyle, String styledContent) {
		this.textStyle = textStyle;
		this.hasStyle = true;
		this.styledContent = styledContent;
	}
	
	//오버로딩
	public void setTextStyle(String textStyle, String styledContent, String styleContent2) {
		this.textStyle = textStyle;
		this.hasStyle = true;
		this.styledContent = styledContent;
		this.styledContent2 = styleContent2;
	}
	
	//링크나 이미지의 경우 해당 페이지 혹은 파일의 url을 저장한다
	public void setURL(String url) {
		this.url = url;
	}
}
