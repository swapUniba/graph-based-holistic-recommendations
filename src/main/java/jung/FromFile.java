package jung;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FromFile {

	public static HashMap<String, String[]> getPlacesNew() throws IOException

	{
		File file = new File("businesses_torino.csv");

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		String[] all;
		HashMap<String, String[]> dict = new HashMap<String, String[]>();
		br.readLine();
		while ((st = br.readLine()) != null) {
			all = st.split(",");
			String[] cats = all[6].replaceAll("'", "").trim().split("\\s*;\\s*");
			dict.put(all[1].replaceAll("'", "").trim(), cats);
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
			all = st.split(",");
			nomi.add(all[1]);
			categorie.add(all[6]);
		}

		br.close();
		toRet.add(nomi);
		toRet.add(categorie);
		return toRet;

	}

	public static ArrayList<String> getContesti() throws IOException {

		File file = new File("contesti.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		String[] all;
		ArrayList<String> contesti = new ArrayList<String>();

		while ((st = br.readLine()) != null) {
			all = st.split("=");
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

	public static ArrayList<String> getCategorie() throws IOException {

		File file = new File("categorie.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		ArrayList<String> categ = new ArrayList<String>();

		while ((st = br.readLine()) != null) {
			categ.add(st);
		}
		br.close();
		return categ;
	}

	public static HashMap<String, List<Object>> getContestiCategorizzati() throws IOException {

		JSONObject obj = parseJSONFile("contesti_categorie.json");
		JSONArray names = obj.names();
		HashMap<String, List<Object>> mappa = new HashMap<String, List<Object>>();
		
		for (int i = 0; i < names.length(); i++) {
			JSONObject obj1 = obj.getJSONObject(names.get(i).toString());
			JSONArray names1 = obj1.names();
			
			for (int y = 0; y < names1.length(); y++) {
				JSONArray obj2 = obj1.getJSONArray(names1.get(y).toString());
				mappa.put(names1.getString(y), obj2.toList());
			}
		}
		return mappa;
	}
	
	public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
		String content = new String(Files.readAllBytes(Paths.get(filename)));
		return new JSONObject(content);
	}

	

}
