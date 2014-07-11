/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fleece.core.jmh.benchmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;

public class CreateJsonTestFiles {


    public static void main(String[] args)throws Exception {

        /*createUnicodeString("unicodes.txt");*/
        //create("gen_small.json", 3);
         /*create("gen_medium.json", 10);
         create("gen_big.json", 30);
         create("gen_bigger.json", 100);
         create("gen_huge.json", 1000);
         create("gen_giant.json", 100000);*/
         create("gen_monster.json", 1000000);
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

        File json = new File(path);
        FileWriterWithEncoding sb = new FileWriterWithEncoding(json, StandardCharsets.UTF_8);
    
        sb.append("{\n");

        for (int i = 0; i < count; i++) {

            sb.append("\t\"special-"+i+"\":"  +"\""  +  "\\\\f\\n\\r\\t\\uffff\udbff\udfff"      +"\",\n"); 
            sb.append("\t\"unicode-\\u0000- "+i+"\":\"\\u5656\udbff\udfff\",\n");   
            sb.append("\t\"\uffffbigdecimal"+i+"\":7817265.00000111,\n");
            sb.append("\t\"bigdecimal-2-"+i+"\":127655512123456.761009E-123,\n");
            sb.append("\t\"string-"+i+"\":\"lorem ipsum, ÄÖÜäöü.-,<!$%&/()9876543XXddddJJJJJJhhhhhhhh\udbff\udfff\",\n");
            sb.append("\t\"\uffffint"+i+"\":4561,\n");
            sb.append("\t\"\uffffints"+i+"\":0,\n");
            sb.append("\t\"\ufffffalse"+i+"\":false,\n");
            sb.append("\t\"\uffffnil"+i+"\":false,\n");
            sb.append("\t\"\uffffn"+i+"\":      null                ,\n");
            sb.append("\t\"obj"+i+"\":\n");

            sb.append("\t\t{\n");

            sb.append("\t\t\t\"special-"+i+"\":"  +"\""  +  "\\\\f\\n\\r\\t\\uffff"      +"\",\n"); 
            sb.append("\t\t\t\"unicode-\\u0000- "+i+"\":\"\\u5656\",\n");   
            sb.append(" \"bigdecimal"+i+"\":7817265.00000111,\n");
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

            sb.append("\t\t\n}\n\n\n\n                 \t\r                                                      ");
            
            if(i<count-1)
            sb.append(",\n");
            else
                sb.append("\n");
        }

    
        sb.append("\n}\n");

        sb.close();
       
        
        return json;

    }

}