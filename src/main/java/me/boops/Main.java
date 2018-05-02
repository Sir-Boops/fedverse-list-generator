package me.boops;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import me.boops.functions.FetchRemoteContent;
import me.boops.functions.StringArrayToList;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		if(args.length <= 0) {
			System.out.println("Define a starting instance!");
			System.exit(1);
		}
		
		System.out.println("Using '" + args[0] + "' as the starting point");
		
		List<String> found_instances = new ArrayList<String>();
		List<String> to_scan = new ArrayList<String>();
		List<String> scanned = new ArrayList<String>();
		
		// Set Inital list
		to_scan.addAll(new StringArrayToList().convert(new JSONArray(new FetchRemoteContent().fetch(args[0], "/api/v1/instance/peers"))));
		
		ThreadGroup scanGroup = new ThreadGroup("scanGroup");
		while(to_scan.size() > 0) {
			
			if(scanGroup.activeCount() < 40) {
				scanned.add(to_scan.get(0));
				new Thread(scanGroup, new Runnable() {
					String scan_url = to_scan.get(0);
					public void run() {
						
						String url_to_scan = scan_url;
						List<String> new_found = new ArrayList<String>();
						
						try {
							new_found.addAll(new StringArrayToList().convert(new JSONArray(new FetchRemoteContent().fetch(url_to_scan, "/api/v1/instance/peers"))));
						} catch (Exception e) {
						}
						
						// Add newly found instances to the found_instances list
						for(int i = 0; i < new_found.size(); i++) {
							if(!listContains(found_instances, new_found.get(i))) {
								found_instances.add(new_found.get(i));
							}
							
							if(!listContains(scanned, new_found.get(i)) && !listContains(to_scan, new_found.get(i))) {
								to_scan.add(new_found.get(i));
							}
						}
					}
				}).start();
				to_scan.remove(to_scan.get(0));
				System.out.println("Known: " + found_instances.size() + ", Left to scan: " + to_scan.size());
			}
		}
		
		while(scanGroup.activeCount() > 0) {
			System.out.println("Waiting for all threads to finish");
			Thread.sleep(1000);
		}
		
		PrintWriter file = new PrintWriter(new FileWriter("instance_list.txt"));
		for(int i = 0; i < found_instances.size(); i++) {
			file.println(found_instances.get(i));
		}
		file.close();
		
	}
	
	private static boolean listContains(List<String> toSearch, String term) {
		boolean ans = false;
		for(int i = 0;  i < toSearch.size(); i++) {
			if(toSearch.get(i).equalsIgnoreCase(term)) {
				ans = true;
			}
		}
		return ans;
	}
}
