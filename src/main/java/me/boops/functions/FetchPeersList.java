package me.boops.functions;

import java.util.List;

import org.json.JSONArray;

public class FetchPeersList {
	
	public List<Object> fetch(String domain) {
		
		JSONArray arr = new JSONArray();
		
		try {
		
			arr = new JSONArray(new FetchRemoteContent().get("http://" + domain, "/api/v1/instance/peers"));
			
		} catch (Exception e) {
		}
		
		return arr.toList();
		
	}
}
