package me.boops.functions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class StringArrayToList {
	
	public List<String> convert(JSONArray arr){
		
		List<String> ans = new ArrayList<String>();
		
		for(int i = 0; i < arr.length(); i++) {
			ans.add(arr.getString(i));
		}
		
		return ans;
	}
}
