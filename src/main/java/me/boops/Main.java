package me.boops;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import me.boops.functions.FetchPeersList;

public class Main {
	
	public static String http_client = "";
	
	public static void main(String[] args) throws Exception {
		
		if(args.length <= 0) {
			System.out.println("Define a starting instance!");
			System.exit(1);
		}
		
		System.out.println("Starting fedi scan from the list located at: " + args[0]);
		
		List<Object> list = new FetchPeersList().fetch(args[0]);
		List<String> master_list = new ArrayList<String>();
		
		for(int i = 0; list.size() > i; i++) {
			master_list.add(list.get(i).toString());
		}
		
		System.out.println("Starting with " + master_list.size() + " Instances");
		
		for(int i = 0; master_list.size() > i; i++) {
			
			List<Object> new_list = new FetchPeersList().fetch(master_list.get(i));
			
			for(int i2 = 0; new_list.size()> i2; i2++) {
				if(!master_list.contains((String) new_list.get(i2))) {
					master_list.add((String) new_list.get(i2));
				}
			}
			
			System.out.println("Indexed " + new_list.size() + " From " + master_list.get(i));
			System.out.println("Current master size: " + master_list.size());
			
		}
		
		PrintWriter out = new PrintWriter("list.txt");
		for(int i = 0; master_list.size() > i; i++) {
			out.println(master_list.get(i));
		}
		out.close();
	}
}
