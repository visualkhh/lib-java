/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.http.examples.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class QuickStart2 {
	public static void printHeader(HttpMessage msg){
        Header[] header = msg.getAllHeaders();
        for (int i = 0; i < header.length; i++) {
			System.out.println(header[i].getName()+" : "+header[i].getValue());
		}
	}
    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
			
	        try {
	            HttpGet httpGet = new HttpGet("http://192.168.0.95:8080/webTest/name.jsp");
	            printHeader(httpGet);
	            httpGet.setHeader("Cookie", "JSESSIONID=BE7BE0F4B76AAB72BD94B9DC57AB5E7D");
	            System.out.println("------ss------");
	            CloseableHttpResponse response1 = httpclient.execute(httpGet);
	            printHeader(response1);
	            try {
	                System.out.println(response1.getStatusLine());
	                HttpEntity entity1 = response1.getEntity();
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                
	                
	                
	                InputStream in = entity1.getContent();
	                BufferedReader reader =   new BufferedReader(new InputStreamReader(in,"UTF-8"));
	                String line    =   "";
	                while((line = reader.readLine())!=null){
	                 System.out.println(line+"\n");
	                }
	                EntityUtils.consume(entity1);
	            } finally {
	                response1.close();
	            }
	            System.out.println("-------------------");
	        } finally {
	            //httpclient.close();
	        }
	        
	        Thread.sleep(4000);
        }

}
