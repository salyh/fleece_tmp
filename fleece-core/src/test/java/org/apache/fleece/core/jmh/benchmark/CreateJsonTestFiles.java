package org.apache.fleece.core.jmh.benchmark;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

public class CreateJsonTestFiles {

	
	public static void main(String[] args)throws Exception {
		 
		createUnicodeString("unicodes.txt");
		create("gen_small.json", 3);
		 create("gen_medium.json", 10);
		 create("gen_big.json", 30);
		 create("gen_bigger.json", 100);
		 create("gen_huge.json", 1000);
		 create("gen_giant.json", 100000);
		 //create("gen_monster.json", 1000000);
		
	}
	
	
	public static File createUnicodeString(String path) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		for(char c=0; c< '\u0a14';c++)
			sb.append(c);
		
		for(int i=0;i<8;i++)
			sb.append(sb);
			
		File file = new File(path);
		FileUtils.write(file, sb.toString(), Charset.forName("UTF-8"));
		return file;
	}

	
	public static File create(String path, int count) throws Exception {

		
		if(count < 0 || path == null || path.length()==0) throw new IllegalArgumentException();
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		
		for (int i = 0; i < count; i++) {
			
			sb.append("\t\"special-"+i+"\":"  +"\""  +  "\\\\f\\n\\r\\t\\uffff"      +"\",\n");	
			sb.append("\t\"unicode-\\u0000- "+i+"\":\"\\u5656\",\n");	
			sb.append("\t\"bigdecimal"+i+"\":7817265.00000111,\n");
			sb.append("\t\"bigdecimal-2-"+i+"\":127655512123456.761009E-123,\n");
			sb.append("\t\"string-"+i+"\":\"lorem ipsum, ÄÖÜäöü.-,<!$%&/()9876543XXddddJJJJJJhhhhhhhh\",\n");
			sb.append("\t\"int"+i+"\":4561,\n");
			sb.append("\t\"ints"+i+"\":0,\n");
			sb.append("\t\"false"+i+"\":false,\n");
			sb.append("\t\"nil"+i+"\":false,\n");
			sb.append("\t\"n"+i+"\":      null                ,\n");
			sb.append("\t\"obj"+i+"\":\n");
			
			sb.append("\t\t{\n");
				
			sb.append("\t\t\t\"special-"+i+"\":"  +"\""  +  "\\\\f\\n\\r\\t\\uffff"      +"\",\n");	
			sb.append("\t\t\t\"unicode-\\u0000- "+i+"\":\"\\u5656\",\n");	
			sb.append("	\"bigdecimal"+i+"\":7817265.00000111,\n");
			sb.append("\t\t\t\"bigdecimal-2-"+i+"\":127655512123456.761009E-123,\n");
			sb.append("\t\t\t\"string-"+i+"\":\"lorem ipsum, ÄÖÜäöü.-,<!$%&/()9876543XXddddJJJJJJhhhhhhhh\",\n");
			sb.append("\t\t\t\"int"+i+"\":4561,\n");
			sb.append("\t\t\t\"ints"+i+"\":0,\n");
			sb.append("\t\t\t\"false"+i+"\":false,\n");
			sb.append("\t\t\t\"nil"+i+"\":false,\n");
			sb.append("\t\t\t\"obj"+i+"\":      null                ,\n");
			sb.append("\t\t\t\"obj"+i+"\":\n");	
				sb.append("\t\t\t\t[    true    ,\n");
				
					sb.append("\t\t\t\t{\n");
					
					sb.append("\t\t\t\t\"special-"+i+"\":"  +"\""  +  "\\\\f\\n\\r\\t\\uffff"      +"\",\n");	
					sb.append("\t\t\t\t\"unicode-\\u0000- "+i+"\":\"\\u5656\",\n");	
					sb.append("\t\t\t\t\"bigdecimal"+i+"\":7817265.00000111,\n");
					sb.append("\t\t\t\t\"bigdecimal-2-"+i+"\":127655512123456.761009E-123,\n");
					sb.append("\t\t\t\t\"string-"+i+"\":\"lorem ipsum, ÄÖÜäöü.-,<!$%&/()9876543XXddddJJJJJJhhhhhhhh\",\n");
					sb.append("\t\t\t\t\"int"+i+"\":4561,\n");
					sb.append("\t\t\t\t\"ints"+i+"\":0,\n");
					sb.append("\t\t\t\t\"false"+i+"\":false,\n");
					sb.append("\t\t\t\t\"nil"+i+"\":false,\n");
					sb.append("\t\t\t\t\"obj"+i+"\":      null                \n");
					sb.append("\t\t\t\t\n}\n");
				
				
				sb.append("\t\t\t]\n");
			
			sb.append("\t\t\n}\n\n\n\n                 \t\r                                                      ,\n");
		}
		
		if(count > 0)
		sb.deleteCharAt(sb.length()-2);
		
		sb.append("\n}\n");

		File json = new File(path);
		FileUtils.write(json, sb.toString(), Charset.forName("UTF-8"));
		return json;

	}

}
