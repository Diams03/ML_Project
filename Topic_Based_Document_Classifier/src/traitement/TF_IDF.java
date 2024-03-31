package traitement;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;

@SuppressWarnings("serial")
public class TF_IDF implements Serializable{
	public static Map<String,Double> File_tf_idf(List<String>listeMots){
		Map<String,Double> Occurence=new HashMap<>();
		Set<String> motsUniques = new HashSet<>(listeMots);
		for(String mot: motsUniques) {
			int word_occ=Collections.frequency(listeMots, mot);
			Occurence.put(mot,(double)word_occ/listeMots.size());
		}
		return Occurence;
	}
	
	public static Map<String,Map<String, Double>> TF(Map<String,List<String>>data) {
		Map<String, Map<String, Double>> Final=new HashMap<>();
		for (Map.Entry<String, List<String>> entry :data.entrySet()) {
			Map<String, Double> TF_words=new HashMap<>();
	        String nomFichier = entry.getKey();
	        List<String> mots = entry.getValue();
	        Set<String> motsUniques = new HashSet<>(mots);  
	        for(String mot: motsUniques) {
	        	double tf=Collections.frequency(mots, mot);
	        	double ratio=tf/mots.size();
	        	TF_words.put(mot,ratio);	
	        } 
	        Final.put(nomFichier, TF_words);
	    }
		return Final;	
	}
	
	public static  Map<String,Map<String, Double>>  IDF(Map<String,List<String>>data){
		Map<String, Map<String, Double>> TF_w=TF_IDF.TF(data);
		Map<String, Map<String, Double>> IDF_w=new HashMap<>();
		int total_document=TF_w.size();
		for (String nomFichier : TF_w.keySet()) {
			Map<String, Double> tfMap = TF_w.get(nomFichier);
			Map<String, Double> idf_values= new HashMap<>();
			for (String terme : tfMap.keySet()) {
	            double documentFrequency = 0;
	            for (Map<String, Double> map : TF_w.values()) {
	                if (map.containsKey(terme)) {
	                    documentFrequency++;
	                }
	            }
	            double idf = Math.log(total_document / (documentFrequency + 1));
	            idf_values.put(terme, idf);  
		}
			IDF_w.put(nomFichier, idf_values);	
	}
	
	return IDF_w;
}
	public static Map<String,Map<String, Double>> Tf_IDF(Map<String,List<String>>data){
		Map<String, Map<String, Double>> TF_w=TF_IDF.TF(data);
		Map<String, Map<String, Double>> IDF_w=TF_IDF.IDF(data);
		Map<String, Map<String, Double>> Tf_IDF=new HashMap<>();
		double epsilon=0;
		for(String file_name: TF_w.keySet()) {
			Map<String, Double> tfMap = TF_w.get(file_name);
			Map<String, Double> idfMap = IDF_w.get(file_name);
			Map<String, Double> tF_IDF= new HashMap<>();
			for(String terme: tfMap.keySet()) {
				double tfidf = tfMap.get(terme) * idfMap.getOrDefault(terme, 0.0);
				if (tfidf >= epsilon) {
					tF_IDF.put(terme, tfidf);
	            }
			}
			if (!tF_IDF.isEmpty()) {
				Tf_IDF.put(file_name, tF_IDF);
	        }
		}
		return Tf_IDF;		
}
	
	public static double cosinus(Map<String,Double> file,Map<String,Double> Corpus_file) {
		Double norme_querry=0.0;
		Double norme_Corpus_file=0.0;
		Double scalaire=0.0;
		for(String mot: file.keySet()){
			if(Corpus_file.containsKey(mot)) {
				scalaire+=file.get(mot)*Corpus_file.getOrDefault(mot,0.0);		
			}
			norme_querry+=Math.pow(file.get(mot), 2);	
		}
		for(String mot: Corpus_file.keySet()) {
			norme_Corpus_file+=Math.pow(Corpus_file.get(mot), 2);
		}
		if(norme_querry==0.0 || norme_Corpus_file==0.0) {
			return 0.0;
		}
		return scalaire/(Math.sqrt(norme_querry)+Math.sqrt(norme_Corpus_file));
	}
}
