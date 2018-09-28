package me.boops.functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.boops.Main;

public class FetchRemoteContent {
	
	public String get(String domain, String path) {
		
		String ans = "";
		
		try {
			
			URL url = new URL(domain + path);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setReadTimeout(10 * 1000);
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", Main.http_client);
			
			conn.connect();
			
			// If redirection happens
			if(conn.getResponseCode() == 301 || conn.getResponseCode() == 302) {		
				conn = follow_redir(conn.getHeaderField("Location"));
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			StringBuilder sb = new StringBuilder();
			String inByte;
			while ((inByte = in.readLine()) != null) {
				sb.append(inByte);
			}
			
			ans = sb.toString();
			
		} catch (Exception e) {
		}
		return ans;
		
	}
	
	public HttpURLConnection follow_redir(String url) {
		
		HttpURLConnection ans = null;
		
		try {
			URL URL = new URL(url);
			ans = (HttpURLConnection) URL.openConnection();
		} catch (Exception e) {
		}
		
		return ans;
		
	}
	
}
