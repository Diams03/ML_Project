package text_mining;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class Stops_Word {
	private String stop_word_file;
	
	public Stops_Word(){
		this.stop_word_file="C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\stop_words_arabic.txt";
	}
	
	public List<String> Stop_word_list(){
		File fichier=new File(this.stop_word_file);
		//words list
		List<String> mots = new ArrayList<String>();
		
		try (Scanner scanner = new Scanner(fichier)) {
            while (scanner.hasNext()) {
                mots.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
		return mots;
	}
	
	public HashMap<String, String> Delete_Stop_Words(HashMap<String, List<String>> result_map){
		HashMap<String, String> noSW_map=new HashMap<String, String>();
		List<String> stop_word=this.Stop_word_list();
		 for (Map.Entry<String, List<String>> entry : result_map.entrySet()) {
			 //get the file name who is the ower key
	            String nomFichier = entry.getKey();
	        //get the words list of the key
	            List<String> mots = entry.getValue();
	       //New stop words list
	            List<String> no_stop_words=new ArrayList<String>();
	            
	            //Start the process 
	            
	            for (String element: mots) {
	            	if(!stop_word.contains(element)) {
	            		no_stop_words.add(element);
	            	}
	            	
	            }
	            StringBuilder resultat = new StringBuilder();
	            
	            for (String mot : no_stop_words) {
	                resultat.append(mot);
	                resultat.append(" "); // Ajouter un espace entre les mots
	            }
	            
	            // Supprimer l'espace final s'il y en a un
	            if (resultat.length() > 0) {
	                resultat.deleteCharAt(resultat.length() - 1);
	            }
	            noSW_map.put(nomFichier,resultat.toString());  
	        }
		 return noSW_map;
	}
	
}
