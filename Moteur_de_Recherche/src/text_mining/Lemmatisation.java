package text_mining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safar.basic.morphology.stemmer.factory.StemmerFactory;
import safar.basic.morphology.stemmer.interfaces.IStemmer;
import safar.basic.morphology.stemmer.model.WordStemmerAnalysis;
import safar.util.remover.Remover;



public class Lemmatisation {
	private HashMap<String, String > noSW_map;
	private String texte;
	
	public Lemmatisation(HashMap<String, String> noSW_map) {
		this.noSW_map=noSW_map;
	}
	
	public Lemmatisation(String texte){
		this.texte=texte;
	}
	
	public List<String> get_text_lemm(){
		IStemmer stemmer=StemmerFactory.getKhojaImplementation();
		List<String> list_mot=new ArrayList<>();
		String noStop_word=Remover.removeStopWords(this.texte);
		List<WordStemmerAnalysis> listResult = stemmer.stem(noStop_word);
		for(WordStemmerAnalysis wsa:listResult) {
        	String stem = wsa.getListStemmerAnalysis().get(0).getMorpheme();
        	list_mot.add(stem);
        }
		return list_mot;
	}
	
	public HashMap<String, List<String>> get_lemm(){
		HashMap<String, List<String>> file_lem=new HashMap<>();
		
		IStemmer stemmer=StemmerFactory.getKhojaImplementation();
		
		for (Map.Entry<String, String> entry : this.noSW_map.entrySet()) {
	        String nomFichier = entry.getKey();
	        String textes = entry.getValue();
	        List<String> list_mot=new ArrayList<>();
	        List<WordStemmerAnalysis> listResult = stemmer.stem(textes);
	        for(WordStemmerAnalysis wsa:listResult) {
	        	String stem = wsa.getListStemmerAnalysis().get(0).getMorpheme();
	        	list_mot.add(stem);
	        }
	        
	        file_lem.put(nomFichier, list_mot);
	    }
		
		
	  return file_lem;
	  
	  }
}
