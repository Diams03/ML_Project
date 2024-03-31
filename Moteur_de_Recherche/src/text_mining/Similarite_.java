package text_mining;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

public class Similarite_ {
	
	public Similarite_() {

}
	public HashMap<String,Double> Query_TF_IDF(List<String> listeMots){
		HashMap<String,Double> Frequency_map=new HashMap<>();
		int taille=listeMots.size();
		for(String mot: listeMots) {
			Double occurence=Frequency_map.getOrDefault(mot, 0.0);
			Frequency_map.put(mot, occurence+1);
		}
		
		for(String mot:Frequency_map.keySet()) {
			Frequency_map.put(mot,Frequency_map.get(mot)/taille);
		}
		
		return Frequency_map;	
	}
	
	public double cosinus(HashMap<String,Double> Querry,HashMap<String,Double> Corpus_file) {
		Double norme_querry=0.0;
		Double norme_Corpus_file=0.0;
		Double scalaire=0.0;
		
		for(String mot: Querry.keySet()){
			if(Corpus_file.containsKey(mot)) {
				scalaire+=Querry.get(mot)*Corpus_file.getOrDefault(mot,0.0);		
			}
			norme_querry+=Math.pow(Querry.get(mot), 2);	
		}
		
		for(String mot: Corpus_file.keySet()) {
			norme_Corpus_file+=Math.pow(Corpus_file.get(mot), 2);
		}
		if(norme_querry==0.0 || norme_Corpus_file==0.0) {
			return 0.0;
		}
		
		return scalaire/(Math.sqrt(norme_querry)+Math.sqrt(norme_Corpus_file));
	}
	
	public HashMap<String,Double> Check_Similarity(HashMap<String,Double> Querry,HashMap<String,HashMap<String,Double>> Corpus){
		HashMap<String,Double> result=new HashMap<>();
		for(String file_name: Corpus.keySet()){
			HashMap<String,Double> map=Corpus.get(file_name);
			Double sim_value=this.cosinus(Querry, map);
			result.put(file_name, sim_value);	
		}
		
		//<=====================Sorting result=======================>//
		List<Map.Entry<String, Double>> resultList = new ArrayList<>(result.entrySet());

		    // Trier la liste en fonction des valeurs de sim_value (dans l'ordre décroissant)
		Collections.sort(resultList, (entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

		    // Créer une nouvelle HashMap pour stocker les résultats triés
		HashMap<String, Double> sortedResult = new LinkedHashMap<>();
		for (Map.Entry<String, Double> entry : resultList) {
			sortedResult.put(entry.getKey(), entry.getValue());
		}

		return sortedResult;
	}
	public void start() {
		File file=new File("C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\corpus\\SANGARE_Boubacar_Diam.xml");
		
		System.out.println("Entrer votre texte : ");
		
		Scanner sc = new Scanner(System.in);
		String  txt = sc.nextLine();
		List<String> texte = new ArrayList<>();
		
		// Stemming & Supprimer les stopwords
		Lemmatisation lm = new Lemmatisation(txt);
		texte =lm.get_text_lemm();
		
		System.out.println("Resultat : ");
		
		HashMap<String,Double> Querry = this.Query_TF_IDF(texte);
		
		if(file.exists()!=true){
			Fichier test=new Fichier("C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\نظام المعاشات العسكرية");
			Stops_Word test2=new Stops_Word();
			List<String> results=test.Filelist();
			System.out.println("Map preparation....");
			HashMap<String, List<String>> result_map=test.Map_creation(results);
			
			System.out.println("Delete stop words....");
			HashMap<String, String> nSW_map=test2.Delete_Stop_Words(result_map);
			
			System.out.println("Get word stem....");
			Lemmatisation test3=new Lemmatisation(nSW_map);
			HashMap<String, List<String>> results_lemm=test3.get_lemm();
			
			System.out.println("Compute TF_IDF....");
			Indexation test4=new Indexation(results_lemm);
			HashMap<String, HashMap<String, Double>> TF_IDF_world=test4.TF_IDF();
			
			System.out.println("<=========================Save Corpus==============================>");
			Corpus.save_corpus(TF_IDF_world);
			
			System.out.println("<=========================Processing your request==============================>");
			HashMap<String, Double> F = this.Check_Similarity(Querry,TF_IDF_world);
			int i = 0;
			
			for (Entry<String, Double> f : F.entrySet()) {
				String filee = f.getKey();
				Double cos = f.getValue();
				System.out.println(filee+" == > "+cos);
				
				i++;
				if(i == 10) {
					break;
				}
				
			}	
			
		}else {
			System.out.println("<=========================Processing your request==============================>");
			HashMap<String, HashMap<String, Double>> TF_IDF_world=Corpus.load_corpus("C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\corpus\\SANGARE_Boubacar_Diam.xml");
			
			HashMap<String, Double> F = this.Check_Similarity(Querry,TF_IDF_world);
			int i = 0;
			
			for (Entry<String, Double> f : F.entrySet()) {
				String filee = f.getKey();
				Double cos = f.getValue();
				System.out.println(filee+" == > "+cos);
				
				i++;
				if(i == 10) {
					break;
				}
				
			}	
			
		}
		
		
	}
	
	
}
