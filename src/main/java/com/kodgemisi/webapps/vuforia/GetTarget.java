package com.kodgemisi.webapps.vuforia;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.kodgemisi.webapps.vuforia.SignatureBuilder;

/*
 * 17.08.03 정다은 추가 
 * vuforia cloud recognization java example 참조
 * Reference: https://developer.vuforia.com/resources/dev-guide/adding-target-cloud-database-api
 */
public class GetTarget {//target id에 맞는 target을 갖고 오기 위한 클래스

	//Server Keys, 현재 우리 프로젝트에만 해당 
	private String accessKey = "a9e8e2bf5ed866ffa198a12743bc5dff0d60f1b8";
	private String secretKey = "b2665507a1e9543e192fdc9c29703e3ad9e67579";
		
	private String targetId = "[ target id ]";
	private String url = "https://vws.vuforia.com";

	//Constructor
	public GetTarget(String id){
		targetId=id;
	}
	
	//얘 불러야 해요!!
	public void findTarget(){
		try{
			getTarget();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    //target id에 맞는 target을 갖고 온다.  
	private void getTarget() throws URISyntaxException, ClientProtocolException, IOException {
		HttpGet getRequest = new HttpGet();
		HttpClient client = new DefaultHttpClient();
		getRequest.setURI(new URI(url + "/targets/" + targetId));
		setHeaders(getRequest);
		
		HttpResponse response = client.execute(getRequest);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
	
	private void setHeaders(HttpUriRequest request) {
		SignatureBuilder sb = new SignatureBuilder();
		request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));
		request.setHeader("Authorization", "VWS " + accessKey + ":" + sb.tmsSignature(request, secretKey));
	}
}