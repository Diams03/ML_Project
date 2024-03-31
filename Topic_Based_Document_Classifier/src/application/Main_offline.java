package application;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import traitement.Bayesian_probability;
import traitement.Fichier;
import traitement.Serialisation;
import traitement.TF_IDF;

public class Main_offline {

	public static void main(String[] args) {
		String file_path="C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\DataProcessing";
		String tfidf_path="C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\corpus\\SANGARE_Boubacar_Diam_tfidf.xml";
		String propability_path="C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\corpus\\SANGARE_Boubacar_Diamt_Proba.xml";
		String class_propability_path="C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\corpus\\SANGARE_Boubacar_Diamt_ProbaClass.xml";
		
		System.out.println("Taking Files....");
		List<File>data=Arrays.asList(new File(file_path).listFiles());
		
		System.out.println("Map Creation...");
		Map<String, List<String>>File_Word=Fichier.Map_Creation(data);
		System.out.println("TF_IDF Computing....");
		Map<String,Map<String,Double>>Tf_idf=TF_IDF.Tf_IDF(File_Word);
		System.out.println("TF_IDF computing finish");
		Serialisation.save_tfidf(Tf_idf,tfidf_path);
		
		System.out.println("Take word by class...");
		Map<String,List<String>>word_by_Class=Fichier.word_by_class(data);
		System.out.println("Compute word probability...");
		Map<String,Map<String,Double>>probability=new Bayesian_probability(word_by_Class).probability();
		System.out.println("Compute class probability...");
		Map<String,Double>class_probability=Bayesian_probability.probability_per_class(data);
		Serialisation.save_corpus(probability,propability_path);
		Serialisation.save_class_probability(class_probability, class_propability_path);
	}
}
