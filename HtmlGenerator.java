package md_converter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HtmlGenerator {
	
	private ArrayList<Structure> structureList;
	private String fileName; //출력할 html파일 이름
	
	//unordered list 사이즈 확인
	private int unorderedListCount = 0;
	
	public HtmlGenerator(MDElement mdelem) {
		this.structureList = mdelem.structureList;
		this.fileName = mdelem.htmlFileName;
		
	}
	
	//HTML5 기준
	public void writeHTML(){
		
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			
			writer.write("<!DOCTYPE html>\r\n");
			writer.write("<html>\r\n");
			writer.write("<head>\r\n");
			writer.write("<title>MDtoHTML</title>\r\n");
			writer.write("</head>\r\n");
			writer.write("<body>\r\n");
			
			//ASTree Structure에 맞는 HTML태그매핑 
			writer.write(HTMLTagMapping());
			
			writer.write("</body>\r\n");
			writer.write("<html>");
		
			writer.close();
		} catch (IOException e) {
			
		}
		
	}
	
	private String HTMLTagMapping(){
		String structureContent = "";
		
		
		for(int i=0;i<structureList.size();i++) {
			
			//스타일이 적용된 택스트라면 각 스타일에 맞도록 HTML태그 씌우기
			if(structureList.get(i).hasStyle){
				HTMLStypeMapping(structureList.get(i));
			}
			
			
			//각 AST 스트럭쳐를 HTML태그로 매핑 
			if(structureList.get(i) instanceof PlainContent){
				structureContent+=structureList.get(i).content;
				structureContent+="<br>";
				structureContent+="\r\n";
			}
			else if(structureList.get(i) instanceof Header){
				structureContent+="<h"+((Header)structureList.get(i)).getHeaderType()+">";
				structureContent+=structureList.get(i).content;
				structureContent+="</h"+((Header)structureList.get(i)).getHeaderType()+">";
				structureContent+="\r\n";
			}
			else if(structureList.get(i) instanceof UnorderedList){
				
				// unorderedList 시작부분
				if(unorderedListCount==0){ 
					structureContent+="<ul>\r\n";
				}			
				
				//list의 크기 만큼 unorderedListCount 증가
				this.unorderedListCount++; 
				structureContent+="<li>"+structureList.get(i).content+"</li>\r\n";
				
				//nested list라면 리스트 안에 리스트 생성
				if(structureList.get(i).hasChild){
					structureContent+="<ul>\r\n";			
					for(Structure ul : (structureList.get(i).childStructure)){
						structureContent+="<li>"+ul.content+"</li>\r\n";
					}
					structureContent+="</ul>\r\n";
				}			
				
				//unorderedList 끝부분
				if(!(structureList.get(i+1) instanceof UnorderedList)){ 
					structureContent+="</ul>\r\n";
					this.unorderedListCount=0;
				}
				
			}
			else if(structureList.get(i) instanceof QuotedBlock){
				structureContent += "<blockquote>";
				structureContent += structureList.get(i).content;
				structureContent += "</blockquote>";
			}
			else if(structureList.get(i) instanceof Horizental){
				structureContent+="<hr>\r\n";
			}
			else{
				System.out.println("undefined type: "+structureList.get(i));
			}
		}
		return structureContent;
	}
	
	//각 스타일에 맞도록 HTML태그 씌우기
	//기존 코드를 html코드로 대체(replace)하는 방식
	private void HTMLStypeMapping(Structure structure){		
		if(structure.textStyle=="bold"){		
			structure.content = structure.content.replace("**"+structure.styledContent+"**", "<strong>"+structure.styledContent+"</strong>");			
		}
		else if(structure.textStyle=="italic"){
			structure.content = structure.content.replace("*"+structure.styledContent+"*", "<em>"+structure.styledContent+"</em>");
		}
		else if(structure.textStyle=="underline"){
			structure.content = structure.content.replaceFirst(structure.styledContent,"<a href='"+structure.url+"'>"+structure.styledContent+"</a>");
		}
		else if(structure.textStyle=="code"){
			structure.content = structure.content.replace("`"+structure.styledContent+"`", "<code>"+structure.styledContent+"</code>");
			structure.content = structure.content.replace("`"+structure.styledContent2+"`", "<code>"+structure.styledContent2+"</code>");
		}
		else if(structure.textStyle=="image"){
			structure.content = structure.content.replace("!["+structure.styledContent+"]("+structure.url+")", "<img src='"+structure.url+"' alt='"+structure.styledContent+"'>");
		}
		else if(structure.textStyle=="htmltag"){
			structure.content = structure.content.replace("\\<"+structure.styledContent+"\\>","&lt"+structure.styledContent+"&gt");
			structure.content = structure.content.replace("\\<"+structure.styledContent2+"\\>","&lt"+structure.styledContent2+"&gt");
		}
	}
}
