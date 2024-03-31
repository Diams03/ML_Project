package distance;
import java.util.HashMap;
import java.util.Map;

public class Distance {
	public static double distance(Map<String,Double> vecteur1,Map<String,Double> vecteur2) {
		Double norme_querry=0.0;
		Double norme_Corpus_file=0.0;
		Double scalaire=0.0;
		for(String mot: vecteur1.keySet()){
			if(vecteur2.containsKey(mot)) {
				scalaire+=vecteur1.get(mot)*vecteur2.getOrDefault(mot,0.0);		
			}
			norme_querry+=Math.pow(vecteur1.get(mot), 2);	
		}
		for(String mot: vecteur2.keySet()) {
			norme_Corpus_file+=Math.pow(vecteur2.get(mot), 2);
		}
		if(norme_querry==0.0 || norme_Corpus_file==0.0) {
			return 0.0;
		}
		return scalaire/(Math.sqrt(norme_querry)+Math.sqrt(norme_Corpus_file));
	}
	
	public static double norme(Map<String,Double>vecteur1,Map<String,Double>vecteur2){
		double norme=0;
		for(String word:vecteur1.keySet()) {
			if(vecteur2.containsKey(word)) {
				norme+=Math.pow((vecteur1.get(word)-vecteur2.get(word)),2);
			}
		}
		return Math.sqrt(norme);	
	}
	
	public static double Single_Linkage(Map<String,Map<String,Double>>cluster1,Map<String,Map<String,Double>>cluster2){
		double min_distance=Double.POSITIVE_INFINITY;
		for(String Point1:cluster1.keySet()) {
			for(String Point2:cluster2.keySet()){
				double distance=Distance.distance(cluster1.get(Point1),cluster2.get(Point2));
				if(distance<min_distance) {
					min_distance=distance;
				}
			}
		}
		return min_distance;
	}
	
	public static double Complete_Linkage(Map<String,Map<String,Double>>cluster1,Map<String,Map<String,Double>>cluster2){
		double max_distance=Double.NEGATIVE_INFINITY;
		for(String Point1:cluster1.keySet()) {
			for(String Point2:cluster2.keySet()){
				double distance=Distance.distance(cluster1.get(Point1),cluster2.get(Point2));
				if(distance>max_distance) {
					max_distance=distance;
				}
			}
		}
		return max_distance;
	}
	
	public static double Centroid_Linkage(Map<String,Map<String,Double>>cluster1,Map<String,Map<String,Double>>cluster2) {
		return Distance.distance(Distance.Centre_gravite(cluster1),Distance.Centre_gravite(cluster2));	
}
	
	public static Map<String,Double>Centre_gravite(Map<String,Map<String,Double>>cluster_documents) {
		int dim=cluster_documents.size();
		Map<String,Double>centre_gravite=new HashMap<>();
		for(String document:cluster_documents.keySet()) {
			for(String word:cluster_documents.get(document).keySet()) {
				centre_gravite.put(word,cluster_documents.get(document).get(word)+centre_gravite.getOrDefault(word, 0.0));
			}
		}
		
		for(String word:centre_gravite.keySet()) {
			centre_gravite.put(word, centre_gravite.get(word)/dim);
		}
		return centre_gravite;
	}
}
