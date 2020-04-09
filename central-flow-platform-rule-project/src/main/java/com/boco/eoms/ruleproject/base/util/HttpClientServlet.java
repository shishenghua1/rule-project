package com.boco.eoms.ruleproject.base.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

public class HttpClientServlet {
	
	@Autowired
	private Logger logger = LoggerFactory.getLogger(HttpClientServlet.class);
	
	/** 
     * 发送 http put 请求，参数以原生字符串进行提交 
     * @param url 
     * @param encode 
     * @return 
     */  
    public static String httpPutRaw(String url,String stringJson,Map<String,String> headers, String encode){  
        if(encode == null){  
            encode = "utf-8";  
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();   
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();   
        HttpPut httpput = new HttpPut(url);

        //设置header
        httpput.setHeader("Content-type", "application/json");    
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
            	if(!"content-length".equals(entry.getKey())){
            		httpput.setHeader(entry.getKey(),entry.getValue());
            	}
            }
        }
        
        //组织请求参数  
        StringEntity stringEntity = new StringEntity(stringJson, encode);  
        httpput.setEntity(stringEntity);  
        String content = null;  
        CloseableHttpResponse  httpResponse = null;  
        try {  
            //响应信息
        	httpResponse = closeableHttpClient.execute(httpput);  
            HttpEntity entity = httpResponse.getEntity();  
            content = EntityUtils.toString(entity, encode);  
            LoggerFactory.getLogger(HttpClientServlet.class).info( "http put requert result = " + content);
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
            try { 
                httpResponse.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } 
        }  
        try {  
            closeableHttpClient.close();  //关闭连接、释放资源  
        } catch (IOException e) {  
            e.printStackTrace();
        }
        return content;  
    }

    
    
    
    /**
     * get 请求rest接口  权限查询
     * @param url
     * @return
     */
    public static JSONObject httpGetRaw(String url){  
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();   
        CloseableHttpClient httpclient  = HttpClients.createDefault();   
        JSONObject jsonauthority = new JSONObject();
        String js = null;
        try {
        	HttpGet httpget = new HttpGet(url);
        	LoggerFactory.getLogger(HttpClientServlet.class).debug("executing request " + httpget.getURI());
            //设置header
            httpget.setHeader("Content-type", "application/json");    
            // 执行get请求.  
            CloseableHttpResponse response = httpclient.execute(httpget); 
            try {  
                // 获取响应实体  
                HttpEntity entity = response.getEntity();  
                // 打印响应状态  
//                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度  
//                    System.out.println("Response content length: "  
//                            + entity.getContentLength());  
                    // 打印响应内容  
                    js = EntityUtils.toString(entity, "UTF-8");
                    LoggerFactory.getLogger(HttpClientServlet.class).debug("executing result " + js);
                    jsonauthority = JSONObject.parseObject(js);
                    return jsonauthority;
                }  
            } finally {  
                response.close();  
            }  
		}catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		return null;  
    }
	
    public static JSONObject httpPost(String url){  
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();   
        CloseableHttpClient httpclient  = HttpClients.createDefault();   
        JSONObject jsonauthority = new JSONObject();
        String js = null;
        try {
        	HttpPost httpPost = new HttpPost(url);
        	LoggerFactory.getLogger(HttpClientServlet.class).debug("executing request " + httpPost.getURI());
            //设置header
            httpPost.setHeader("Content-type", "application/json");    
            CloseableHttpResponse response = httpclient.execute(httpPost); 
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    js = EntityUtils.toString(entity, "UTF-8");
                    jsonauthority = JSONObject.parseObject(js);
                    return jsonauthority;
                }  
            } finally {  
                response.close();  
            }  
		}catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		return null;  
    }
    /**
     * post 请求rest接口
     * @param url
     * @return
     */
    public static String httpPostRaw(String url,String stringJson,Map<String,String> headers, String encode){
    	if(encode == null || "".equals(encode)){
            encode = "utf-8";  
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();   
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();   
        HttpPost httpput = new HttpPost(url);

        //设置header
        httpput.setHeader("Content-type", "application/json");    
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
            	if(!"content-length".equals(entry.getKey())){
            		httpput.setHeader(entry.getKey(),entry.getValue());
            	}
            }
        }
        //组织请求参数  
        StringEntity stringEntity = new StringEntity(stringJson, encode);  
        httpput.setEntity(stringEntity);  
        String content = null;  
        CloseableHttpResponse  httpResponse = null;  
        try {
            //响应信息
        	httpResponse = closeableHttpClient.execute(httpput);  
            HttpEntity entity = httpResponse.getEntity();  
            content = EntityUtils.toString(entity, encode);
            LoggerFactory.getLogger(HttpClientServlet.class).info("http post requert result = " + content);
            return content;
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                httpResponse.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        try {
            closeableHttpClient.close();  //关闭连接、释放资源  
        } catch (IOException e) {  
            e.printStackTrace();  
        }    
        return null;  
    }
	
}
