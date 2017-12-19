package md_converter;
import java.util.ArrayList;
import java.util.Scanner;


/*

프로그램 첫 시작부분
커멘드라인 유저인터페이스

*/


public class md_converter {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		String inFile;
		String outFile;
		String style;
		ArrayList<MDElementVisitor> visitorList = new ArrayList<MDElementVisitor>();
		ArrayList<MDElement> elementList = new ArrayList<MDElement>();
		
		
		while(true) {
			System.out.println("=========================================================================");
			System.out.println("If you want to quit putting md file, input 'q'");
			System.out.println("If you need information about input, input '--help' in command line");
			System.out.println("=========================================================================");
			System.out.println("MD file for input : ");
			inFile = sc.nextLine();
			if(inFile.equals("--help")) { //도움말
				System.out.println("Input format example : 'README.md'");
				System.out.println("Output format example : 'handong.html'");
				System.out.println("Style format example : 'plain(default)', 'stylish', 'slide'.");
				System.out.println("1. You can convert one or several md file. If you want convert more than one md file, input another upper three format after filling all current format.");
				System.out.println("2. If you don't specify Style format and leave it blank, plain style is chosen as default.");
				continue;
			}
			else if(inFile.equals("q")) { //입력 종료
				break;
			}
			else if(!inFile.contains(".md")) { //입력 파일 형식 확인
				System.out.println("!!!Warning!!! Input file format should be 'md', please input again");
				continue;
			}
			
			System.out.println("HTML file name for output:");
			outFile = sc.nextLine();
			if(outFile.equals("--help")) {
				System.out.println("Input format example : 'README.md'");
				System.out.println("Output format example : 'handong.html'");
				System.out.println("Style format example : 'plain(default)', 'stylish', 'slide'.");
				System.out.println("1. You can convert one or several md file. If you want convert more than one md file, input another upper three format after filling all current format.");
				System.out.println("2. If you don't specify Style format and leave it blank, plain style is chosen as default.");
				continue;
			}
			else if(inFile.equals("q")) {
				break;
			}
			else if(!outFile.contains(".html")) { //출력 파일 형식 확인
				System.out.println("!!!Warning!!! Output file format should be '.html'. please input again");
				continue;
			}
			System.out.println("Style option - plain(default), stylish, slide :");
			style = sc.nextLine();
			if(style.equals("--help")) {
				System.out.println("Input format example : 'README.md'");
				System.out.println("Output format example : 'handong.html'");
				System.out.println("Style format example : 'plain(default)', 'stylish', 'slide'.");
				System.out.println("1. You can convert one or several md file. If you want convert more than one md file, input another upper three format after filling all current format.");
				System.out.println("2. If you don't specify Style format and leave it blank, plain style is chosen as default.");
				continue;
			}
			else if(inFile.equals("q")) { //입력종료
				break;
			}
			
			else if(style.equals("")) { //plain이 default값
				style = "plain";
			}
			else if(!(style.equals("plain")||style.equals("stylish")||style.equals("slide"))) {
				System.out.println("!!!Warning!!! Style format should be plain(you can leave it as blank), stylish or slide");
				continue;
			}
			System.out.println("=========================================================================");
			
			MDElementVisitor mdev = new MDElementVisitor();
			mdev.genHtmlName(outFile);
			mdev.visitDocument(inFile);
			mdev.visitStructure(style);
			visitorList.add(mdev);
		}
		
		
		
		
		//test
		//샘플파일 4개 한번에 변환
		/*
		MDElementVisitor mdev1 = new MDElementVisitor();
		mdev1.genHtmlName("doc1.html");
		mdev1.visitDocument("doc1.md");
		mdev1.visitStructure("plain");
		visitorList.add(mdev1);
		
		MDElementVisitor mdev2 = new MDElementVisitor();
		mdev2.genHtmlName("doc2.html");
		mdev2.visitDocument("doc2.md");
		mdev2.visitStructure("plain");
		visitorList.add(mdev2);
		
		MDElementVisitor mdev3 = new MDElementVisitor();
		mdev3.genHtmlName("doc3.html");
		mdev3.visitDocument("doc3.md");
		mdev3.visitStructure("plain");
		visitorList.add(mdev3);
		
		
		MDElementVisitor mdev4 = new MDElementVisitor();
		mdev4.genHtmlName("doc4.html");
		mdev4.visitDocument("doc4.md");
		mdev4.visitStructure("plain");
		visitorList.add(mdev4);
		*/
		
		
		
		//MDElementVisitor를 이용해 MDElement(ASTree) 생성 
		for(MDElementVisitor mdev:visitorList){			
			MDElement mdelem = new MDElement();
			mdelem.accept(mdev);
			elementList.add(mdelem);
		}
		
		//ASTree를 Html코드로 변환
		for(MDElement mdelem : elementList){
			HtmlGenerator htmlGenerator = new HtmlGenerator(mdelem);
			htmlGenerator.writeHTML();
		}
	}
}

