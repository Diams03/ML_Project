package text_mining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

public class Indexation {
	private HashMap<String, List<String>> map;
	
	public Indexation(HashMap<String, List<String>> map){
		this.map=map;
		
	}
	
	//Compute TF 
	
	public HashMap<String, HashMap<String, Double>> TF() {
		HashMap<String, HashMap<String, Double>> Final=new HashMap<String, HashMap<String, Double>>();
		
		
		for (Map.Entry<String, List<String>> entry : this.map.entrySet()) {
			HashMap<String, Double> TF_words=new HashMap<String, Double>();
			
	        String nomFichier = entry.getKey();
	        List<String> mots = entry.getValue();
	        
	        Set<String> motsUniques = new HashSet<>(mots);
	        
	        for(String mot: motsUniques) {
	        	double tf=Collections.frequency(mots, mot);
	        	double test=tf/mots.size();
	        	TF_words.put(mot,test);	
	        } 
	        Final.put(nomFichier, TF_words);
	    }
		return Final;	
	}
	
	//Compute IDF
	
	
	public HashMap<String,HashMap<String, Double>>  IDF(){
		HashMap<String, HashMap<String, Double>> TF_w=this.TF();
		HashMap<String, HashMap<String, Double>> IDF_w=new HashMap<>();
		int total_document=TF_w.size();
		for (String nomFichier : TF_w.keySet()) {
			HashMap<String, Double> tfMap = TF_w.get(nomFichier);
			HashMap<String, Double> idfValues = new HashMap<>();
			
			for (String terme : tfMap.keySet()) {
	            double documentFrequency = 0;

	            for (HashMap<String, Double> map : TF_w.values()) {
	                if (map.containsKey(terme)) {
	                    documentFrequency++;
	                }
	            }
	            
	            double idf = Math.log(total_document / (documentFrequency + 1));

	            idfValues.put(terme, idf);  
		}
			IDF_w.put(nomFichier, idfValues);	
	}
	
	return IDF_w;
}
	
	public HashMap<String,HashMap<String, Double>> TF_IDF(){
		HashMap<String, HashMap<String, Double>> TF_w=this.TF();
		HashMap<String, HashMap<String, Double>> IDF_w=this.IDF();
		HashMap<String, HashMap<String, Double>> TF_IDF=new HashMap<>();
		double epsilon=0;
		
		for(String file_name: TF_w.keySet()) {
			HashMap<String, Double> tfMap = TF_w.get(file_name);
			HashMap<String, Double> idfMap = IDF_w.get(file_name);
			HashMap<String, Double> tF_IDF= new HashMap<>();
			for(String terme: tfMap.keySet()) {
				double tfidf = tfMap.get(terme) * idfMap.getOrDefault(terme, 0.0);
				if (tfidf >= epsilon) {
					tF_IDF.put(terme, tfidf);
	            }
			}
			if (!tF_IDF.isEmpty()) {
				TF_IDF.put(file_name, tF_IDF);
	        }
		}
		return TF_IDF;
			
		}
	}

