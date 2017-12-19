package md_converter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;


/*
MDParser
md 택스트를 한줄씩 읽어 ASTree 형태로 객체화한다 
*/

public class MDParser {
	public String wholecontent;
	public ArrayList<Structure> structureList = new ArrayList<Structure>();
	public String MDfileName;
	
	public MDParser(String MDfileName) {
		this.MDfileName = MDfileName;
	}
	
	//md택스트 파일에 모든 내용을 wholecontent 스트링 변수에 넣는다
	public String MdToString() {
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(MDfileName));
			StringBuilder contentReceiver = new StringBuilder();
			char[] buf = new char[2048];
			while(buffer.read(buf)>0) {
				contentReceiver.append(buf);
			}
			wholecontent = contentReceiver.toString();
		} catch (FileNotFoundException e) {
			System.out.println("File not exists");
		} catch(IOException e) {
			System.out.println("File cannot read");
		}
		return wholecontent;
	}
	
	
	//스트링 파일을 읽어 ASTree객체와 그 하위 객체를 구성한다 
	public ArrayList<Structure> structureParsing(String wholecontent) {
		String[] lines = wholecontent.split("\n");
		
		//한 줄씩 읽기
		for(int i=0; i<lines.length; i++) {
			
			System.out.println(i);
			
			//Header
			if(i+1<lines.length && lines[i+1].contains("====")) {
				Header bh = new Header(lines[i], "1");
				if(lines[i].contains("*")) {
					String st[] = lines[i].split("\\*");
					bh.setTextStyle("italic", st[1]);
					
				}
				structureList.add(bh);
				i++;
			}
			
			//Header
			else if(i+1<lines.length && lines[i+1].contains("----")) {
				Header lh = new Header(lines[i], "3");
				structureList.add(lh);
				i++;
			}
			
			//Horizontal Rule
			else if(lines[i].contains("- - -")) {
				Horizental hz = new Horizental(lines[i]);
				structureList.add(hz);
			}
			
			//Unordered List
			else if (lines[i].startsWith("-")) {
				UnorderedList ul = new UnorderedList(lines[i].substring(1, lines[i].length()));
				structureList.add(ul);
				
				//list가 nested 된 경우
				while(lines[i+1].contains(" -")){
					
					UnorderedList ulChild = new UnorderedList(lines[i+1].substring(4, lines[i+1].length()));
					ul.makeChild(ulChild);
					i++;
					
					//첫번째줄 아래 부분도 nested list에 포함시키기
					while(true) {
						if(i+1<lines.length && lines[i+1].startsWith(" ") && !lines[i+1].contains("-")) {
							ulChild.content += lines[i+1].substring(1, lines[i+1].length());												
							i++;
						}
						else {
							break;
						}					
					}			
				}
				
				//첫번째줄 아래 부분도 list에 포함시키기
				while(true) {
					if(i+1<lines.length && lines[i+1].startsWith("  ")) {
						structureList.get(structureList.size()-1).content += lines[i+1].substring(2, lines[i+1].length());												
						i++;
					}
					else {
						break;
					}					
				}
				
			}
			
			
			else if(lines[i].startsWith("  -") && !lines[i].contains("<") && !lines[i].contains(">")) {
				String st[] = lines[i].split("\\-");
				
				if(i!=1 && (lines[i-1].startsWith("*")||(lines[i-1].startsWith("-") ||lines[i-1].startsWith("  -")))) {					
					UnorderedList ul_child = new UnorderedList(st[1]);
					structureList.get(structureList.size()-1).makeChild(ul_child);				
				}
			}

			else if(i>=1 && structureList.get(structureList.size()-1) instanceof UnorderedList && lines[i].startsWith("   ")){			
				structureList.get(structureList.size()-1).content = lines[i];			
			}
			
			
			//*이나 +표시로 되어있는 unordered list처리
			//방식은 위와 동일
			else if (lines[i].startsWith("*")) {
				UnorderedList ul = new UnorderedList(lines[i].substring(1, lines[i].length()));
				structureList.add(ul);
				
				while(lines[i+1].contains(" +")){
					
					UnorderedList ulChild = new UnorderedList(lines[i+1].substring(4, lines[i+1].length()));
					ul.makeChild(ulChild);
					i++;
					while(true) {
						if(i+1<lines.length && lines[i+1].startsWith(" ") && !lines[i+1].contains("+")) {
							ulChild.content += lines[i+1].substring(1, lines[i+1].length());												
							i++;
						}
						else {
							break;
						}					
					}			
				}
				
				while(true) {
					if(i+1<lines.length && lines[i+1].startsWith("  ")) {
						structureList.get(structureList.size()-1).content += lines[i+1].substring(2, lines[i+1].length());												
						i++;
					}
					else {
						break;
					}					
				}
			}
			
			//header
			else if(lines[i].contains("###")) {
				Header h3 = new Header(lines[i].substring(3, lines[i].length()-4), "3");
				structureList.add(h3);
				
			}
			//header
			else if(lines[i].contains("##")) {
				Header h2 = new Header(lines[i].substring(2, lines[i].length()-3), "2");
				structureList.add(h2);
			}
			//header
			else if(lines[i].contains("#")) {
				Header h1 = new Header(lines[i].substring(1, lines[i].length()-2), "1");
				structureList.add(h1);
			}
			
			//quotedblock
			else if(lines[i].startsWith(">")) {
				QuotedBlock qb = new QuotedBlock(lines[i].substring(1, lines[i].length()));
				structureList.add(qb);
				
				//첫번째줄 밑에도 quotedblock에 포함시키기
				while(true) {
					if(i+1<lines.length && !(lines[i+1].length()<=1) && !lines[i+1].startsWith(">")) {				
						structureList.get(structureList.size()-1).content += lines[i+1];						
						i++;
					}
					else {
						break;
					}
				}			
			}
			
			else { //스타일이 적용된 택스트는 PlainContent로 구분한다
				
				//bold
				if (lines[i].contains("**")) {
					String st[] = lines[i].split("\\*\\*");
					PlainContent pc = new PlainContent(lines[i]);
					pc.setTextStyle("bold", st[1]);
					structureList.add(pc);	
				}
				
				//italic
				else if(lines[i].contains("*")) {
					String st[] = lines[i].split("\\*");
					PlainContent pc = new PlainContent(lines[i]);
					pc.setTextStyle("italic", st[1]);
					structureList.add(pc);
					
				}
				
				//code
				else if(lines[i].contains("`")) {
					
					String st[] = lines[i].split("\\`");
					PlainContent pc = new PlainContent(lines[i]);
					pc.setTextStyle("code", st[1],st[3]);
					structureList.add(pc);
				}
				
				//htmltag
				else if(lines[i].contains("\\<")){
					PlainContent pc = new PlainContent(lines[i]);
					int start_index=lines[i].indexOf("\\<");
					int end_index=lines[i].indexOf(">");
					String styledText1 =  lines[i].substring(start_index+2, end_index-1);
					String temp = lines[i].substring(end_index+1);
					start_index=temp.indexOf("\\<");
					end_index=temp.indexOf(">");
					String styledText2 = temp.substring(start_index+2, end_index-1);
					pc.setTextStyle("htmltag",styledText1,styledText2);
					structureList.add(pc);
				}
				
				//image
				else if(lines[i].contains("![")){
					PlainContent pc = new PlainContent(lines[i]);
					int start_index = lines[i].indexOf("[");
					int end_index = lines[i].indexOf("]");
					pc.setTextStyle("image", lines[i].substring(start_index+1, end_index));
					start_index = lines[i].indexOf("(");
					end_index = lines[i].indexOf(")");
					pc.setURL(lines[i].substring(start_index+1,end_index));
					structureList.add(pc);
				}
				
				//link
				else if(lines[i].startsWith("[")&&lines[i].contains(":")&&lines[i].contains("]")) {
					i++;
				}
				else if(lines[i].contains("[")&&lines[i].contains("]")) {
					if(lines[i].contains("][")) {
						int start_index = lines[i].indexOf("[");
						int end_index = lines[i].lastIndexOf("][");
						String temp1 = lines[i].substring(start_index+1, end_index);
						String temp2 = lines[i].substring(0, start_index);
						String temp3 = temp2+temp1;
						PlainContent pc = new PlainContent(temp3);
						pc.setTextStyle("underline", lines[i].substring(start_index+1, end_index));
						int index = 0;
						for(int j=0;j<lines.length;j++) {
							lines[j].startsWith("[");
							index = j;
						}
						if(lines[index-1].contains("(")) {
							start_index = lines[index-1].indexOf(":");
							end_index = lines[index-1].indexOf("(");
						}
						else {

							start_index = lines[index-1].indexOf(":");
							end_index = lines[index-1].indexOf("\"");
						}
						System.out.println(start_index+" "+end_index);
						temp1 = lines[index-1].substring(start_index+1, end_index);
						System.out.println(temp1);
						pc.setURL(temp1);
						structureList.add(pc);
						
					}
					else {
						int start_index = lines[i].indexOf("[");
						int end_index = lines[i].indexOf("]");
						String temp1 = lines[i].substring(0, start_index);
						String temp2 = lines[i].substring(start_index+1, end_index);
						String temp3 = temp1+temp2;
						
						PlainContent pc = new PlainContent(temp3);
						pc.setTextStyle("underline", lines[i].substring(start_index+1, end_index));
						
						
						if(lines[i].contains("\"")) {
							start_index = lines[i].indexOf("(");
							end_index = lines[i].lastIndexOf(" ");
						}
						else {
							start_index = lines[i].indexOf("(");
							end_index = lines[i].lastIndexOf(")");
						}
						temp1 = lines[i].substring(start_index+1, end_index);
						pc.setURL(temp1);
						System.out.println(pc.url);
						structureList.add(pc);
					}
				}
				else {
					PlainContent pc = new PlainContent(lines[i]);
					structureList.add(pc);
				}
				
				
			}	
		}
		
		//AST구성 확인용 디버깅 코드
		for(int i=0;i<structureList.size();i++) {
			
			System.out.printf("%d, %s : %s\n", i,structureList.get(i).structureType, structureList.get(i).content);

			if(structureList.get(i).hasStyle==true && structureList.get(i).textStyle.equals("underline")) {
				System.out.println("==============URL================");
				System.out.printf("Link : %s\n", structureList.get(i).styledContent);
				System.out.printf("URL : %s\n", structureList.get(i).url);
				System.out.println("==================================");
			}
			
			if(structureList.get(i).hasStyle==true) {
				System.out.println("==============STYLE================");
				System.out.printf("Style Type : %s\n", structureList.get(i).textStyle);
				System.out.printf("Styled Content : %s\n", structureList.get(i).styledContent);
				System.out.println("===================================");

			}
			
			if(structureList.get(i).hasChild==true) {
				for(int j=0;j<structureList.get(i).childStructure.size();j++) {
					System.out.println("===============CHILD===============");
					System.out.printf("Child type : %s\n", structureList.get(i).childStructure.get(j).structureType);
					System.out.printf("Child content : %s\n", structureList.get(i).childStructure.get(j).content);
					System.out.println("===================================");
				}
			}		
		}
		System.out.printf("\nstructureList size : %d\n",structureList.size());
		return structureList;
	}
}
