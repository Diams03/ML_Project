package classifieur;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import traitement.TF_IDF;

import traitement.Fichier;
import traitement.Serialisation;
import traitement.Traitement;

public class KNeighbours implements Classifieur{
	private Map<String,Map<String,Double>>Tf_idf;
	private int K;

	public KNeighbours(int K) {
		this.Tf_idf=new HashMap<>();
		this.K=K;
	}
	
	public KNeighbours(int K,String path) {
		this.K=K;
		this.Tf_idf=Serialisation.load_tfidf(path);
	}
	
	public String check_distance(Map<String,Double>file_tfid) {
		HashMap<String,Double> result=new HashMap<>();
		for(String file_name: this.Tf_idf.keySet()){
			Map<String,Double> map=this.Tf_idf.get(file_name);
			Double distance_value=TF_IDF.cosinus(file_tfid, map);
			result.put(file_name, distance_value);
		}
		Map<String, Double> newMap = result.entrySet().stream()
		        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
		        .limit(this.K)
		        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		Map<String,Integer>result_Map=new HashMap<>();
		for(String file:newMap.keySet()) {
			String class_name=file.split("__")[1].replace("_",".");
			if(result_Map.containsKey(class_name)) {
				result_Map.put(class_name, result_Map.get(class_name)+1);
			}else {
				result_Map.put(class_name,1);
			}
		}
		String className=Collections.max(result_Map.entrySet(), Map.Entry.comparingByValue()).getKey();
		return className;
	}
		
	public void Train(List<File>train_data) {
		Map<String, List<String>>File_Word=Fichier.Map_Creation(train_data);
		this.Tf_idf=traitement.TF_IDF.Tf_IDF(File_Word);	
	}
	
	public Map<String,Set<File>>predict_class(List<File>files) throws FileNotFoundException{
		Map<String,Set<File>>prediction=new HashMap<>();
		for(File file:files){
			Traitement process=new Traitement(file);
			List<String> word_list=process.file_processing();
			Map<String,Double>file_word_tfidf=TF_IDF.File_tf_idf(word_list);
			String class_predict=this.check_distance(file_word_tfidf);
			prediction.computeIfAbsent(class_predict,k->new HashSet<>()).add(file);
		}
		return prediction;	
	}
}
