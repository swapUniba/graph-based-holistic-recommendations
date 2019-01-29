package Jung.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadCSV {

	public static HashMap<String,String[]> getPlacesNew() throws IOException
    
    {
		  File file = new File("businesses_torino.csv"); 
		  
		  BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		  String st; 
		  String[] all;
		  HashMap<String,String[]> dict = new HashMap<String,String[]>();
		  br.readLine();
		  while ((st = br.readLine()) != null) {
			  all=st.split(",");
			  String[] cats=all[6].replaceAll("'", "").split(";");
			  dict.put(all[1].replaceAll("'", ""), cats);
		  }
		  
		  br.close();
		  return dict;

	} 
	
	
	public static ArrayList<ArrayList<String>> getPlaces() throws IOException
    
    {
		  File file = new File("businesses_torino.csv"); 
		  
		  BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		  String st; 
		  String[] all;
		  ArrayList<String> nomi = new ArrayList<String>();
		  ArrayList<String> categorie = new ArrayList<String>();
		  ArrayList<ArrayList<String>> toRet = new ArrayList<ArrayList<String>>();
		  br.readLine();
		  while ((st = br.readLine()) != null) {
			  all=st.split(",");
			  nomi.add(all[1]);  
			  categorie.add(all[6]);  
		  }
		  
		  br.close();
		  toRet.add(nomi);
		  toRet.add(categorie);
		  return toRet;

	} 

	
	public static ArrayList<String> getContesti() throws IOException{
		
		  File file = new File("contesti.csv"); 
		  
		  BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		  String st; 
		  String[] all;
		  ArrayList<String> contesti = new ArrayList<String>();

		  while ((st = br.readLine()) != null) {
			  all=st.split("=");
			  contesti.add(all[1]);	  
		  }
		  br.close();
		return contesti;
	}
	
	public static int getNumCategorie(HashMap<String, String[]> dict) {
		Object[] nomi = dict.keySet().toArray();
		ArrayList<String> categorie = new ArrayList<String>();
		for (int i = 0; i < nomi.length; i++) {
			String[] cats = dict.get(nomi[i]);
			for (int y = 0; y < cats.length; y++) {
				if (!categorie.contains(cats[y])) {
					categorie.add(cats[y]);
				}

			}
		}

		return categorie.size();
	}
	
}
