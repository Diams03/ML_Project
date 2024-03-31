package text_mining;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Fichier {
	private String chemin;
	
	public Fichier(String chemin) {
		this.chemin=chemin;
	}

	public List<String> Filelist() {
		List<String> results = new ArrayList<String>();
		
		File[] files = new File(this.chemin).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	results.add(file.getName());
		    }
		}
		return results;	
	}
	
	public HashMap<String, List<String>> Map_creation(List<String> results){
		HashMap<String, List<String>> result_map=new HashMap<String, List<String>>();
		
		for(String element: results) {
			//create a new file object
			File fichier=new File(this.chemin+"\\"+element);
			//words list
			List<String> mots = new ArrayList<>();
			
			try (Scanner scanner = new Scanner(fichier)) {
                while (scanner.hasNext()) {
                    mots.add(scanner.next());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
			result_map.put(element, mots);
			
		}
		return result_map;	
	}
}

