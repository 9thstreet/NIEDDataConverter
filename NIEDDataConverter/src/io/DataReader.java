package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataReader {
	public String[] readTextData(String filename){
		String[] strings = null;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename),"UTF-8"));
			
			String line = br.readLine();
			int maxno = Integer.parseInt(line);
			strings = new String[maxno];
			for(int i = 0;i<maxno;i++){
			strings[i] = br.readLine();
	
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return strings;
		
		
	}
	public String[][] readTabbedTextData(String filename){
		String[][] strings;
		String[] splitted;
		int maxcol = 0;
		ArrayList<String[]> str = new ArrayList<String[]>();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename),"UTF-8"));
			
			String line = br.readLine();
			while(line != null){
				splitted = line.split("\t");
				str.add(splitted);
				if(splitted.length >maxcol) maxcol = splitted.length;
				line = br.readLine();
				
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		strings = str.toArray(new String[str.size()][maxcol]);
		return strings;
		
		
	}
	public String[] readTextData(File file){
		String[] strings = null;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			
			String line = br.readLine();
			int maxno = Integer.parseInt(line);
			strings = new String[maxno];
			for(int i = 0;i<maxno;i++){
			strings[i] = br.readLine();
			
	
		}

			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return strings;
		
		
	}

}
