package md_converter;

//큰 글씨에 해당
//<h1></h1>

public class Header extends Structure{
	//header type//
	// 1. h1 : "=====" //
	// 2. h3 : "----" //
	// 3. h1 : "#" //
	// 4. h2 : "##" //
	// 5. h3 : "###" //
	private String headerType; 
	
	public Header(String content, String headerType) {
		setStructureType("Header");
		setContent(content);
		this.headerType = headerType;
	}
	
	public String getHeaderType(){
		return this.headerType;
	}
}
