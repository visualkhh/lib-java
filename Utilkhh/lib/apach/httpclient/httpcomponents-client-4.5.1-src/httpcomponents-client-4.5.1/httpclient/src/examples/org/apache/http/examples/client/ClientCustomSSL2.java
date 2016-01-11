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

import java.io.File;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL2 {

    public final static void main(String[] args) throws Exception {
    	   SSLContext sslContext = SSLContext.getInstance("TLS"); 
           // 모든 인증서를 허용한다  
           TrustManager tm = new X509TrustManager()  
           {  
                @Override  
                public X509Certificate[] getAcceptedIssuers()  
                {  
                     return null;  
                }  
                @Override  
                public void checkServerTrusted(X509Certificate[] cetificates, String authType) throws CertificateException  
                {  
                }  
                @Override  
                public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException  
                {  
                }  
           };  
           sslContext.init(null, new TrustManager[]{ tm }, new SecureRandom()); 
           SSLSocketFactory sf = new SSLSocketFactory(sslContext);

           // https scheme을 등록하면서, 위에서 생성한 SSLSocketFactory를 객체를 사용한다         
           SchemeRegistry schemeRegistry = new SchemeRegistry();  
           schemeRegistry.register(new Scheme("https", 443, sf));
  
           // GCM에 POST 요청을 보낸다
           ClientConnectionManager cm = new BasicClientConnectionManager();  
           HttpClient httpClient = new DefaultHttpClient(cm);  
           HttpPost post = new HttpPost("https://android.googleapis.com/gcm/send");  
           post.addHeader("Content-Type", "application/json");  
           post.addHeader("Authorization", "key=A");  
           post.setEntity(new StringEntity("{ \"registration_ids\": [ \"42\" ] }"));  
           HttpResponse response = httpClient.execute(post);  
           HttpEntity entity = response.getEntity();  
           BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));  
           String line = null;  
           while ((line = br.readLine()) != null) {  
                System.out.println(line);  
           }  
           br.close();  
    }

}
