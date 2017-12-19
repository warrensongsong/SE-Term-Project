package md_converter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HtmlGenerator {
	
	private ArrayList<Structure> structureList;
	private String fileName; //����� html���� �̸�
	
	//unordered list ������ Ȯ��
	private int unorderedListCount = 0;
	
	public HtmlGenerator(MDElement mdelem) {
		this.structureList = mdelem.structureList;
		this.fileName = mdelem.htmlFileName;
		
	}
	
	//HTML5 ����
	public void writeHTML(){
		
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			
			writer.write("<!DOCTYPE html>\r\n");
			writer.write("<html>\r\n");
			writer.write("<head>\r\n");
			writer.write("<title>MDtoHTML</title>\r\n");
			writer.write("</head>\r\n");
			writer.write("<body>\r\n");
			
			//ASTree Structure�� �´� HTML�±׸��� 
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
			
			//��Ÿ���� ����� �ý�Ʈ��� �� ��Ÿ�Ͽ� �µ��� HTML�±� �����
			if(structureList.get(i).hasStyle){
				HTMLStypeMapping(structureList.get(i));
			}
			
			
			//�� AST ��Ʈ���ĸ� HTML�±׷� ���� 
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
				
				// unorderedList ���ۺκ�
				if(unorderedListCount==0){ 
					structureContent+="<ul>\r\n";
				}			
				
				//list�� ũ�� ��ŭ unorderedListCount ����
				this.unorderedListCount++; 
				structureContent+="<li>"+structureList.get(i).content+"</li>\r\n";
				
				//nested list��� ����Ʈ �ȿ� ����Ʈ ����
				if(structureList.get(i).hasChild){
					structureContent+="<ul>\r\n";			
					for(Structure ul : (structureList.get(i).childStructure)){
						structureContent+="<li>"+ul.content+"</li>\r\n";
					}
					structureContent+="</ul>\r\n";
				}			
				
				//unorderedList ���κ�
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
	
	//�� ��Ÿ�Ͽ� �µ��� HTML�±� �����
	//���� �ڵ带 html�ڵ�� ��ü(replace)�ϴ� ���
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
