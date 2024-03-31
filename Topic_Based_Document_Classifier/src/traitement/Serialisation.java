package traitement;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public abstract class Serialisation {
	
	public static void save_corpus(Map<String,Map<String,Double>> probability,String path) {
    	try {
	    	FileOutputStream fileOut = new FileOutputStream(path);
	    	XMLEncoder out = new XMLEncoder(fileOut);
            out.writeObject(probability);
            out.close();
            fileOut.close();
            System.out.println("The Map has been serialized and saved in SANGARE_Boubacar_Diam_Proba.xml");
    	}catch (IOException e) {
            e.printStackTrace();
            }
        }
        
    public static Map<String,Map<String,Double>> load_corpus(String filename){
    	Map<String,Map<String,Double>> probability=new HashMap<>();
    	try {
    		FileInputStream filein=new FileInputStream(filename);
    		XMLDecoder in=new XMLDecoder(filein);
    		probability=(Map<String,Map<String,Double>>) in.readObject();
            in.close();
            filein.close(); 
            System.out.println("The corpus has been successfully recovered");
        } catch (IOException e) {
        	e.printStackTrace();  
        }
    	return probability;
    }
    
    public static void save_class_probability(Map<String,Double> class_probability,String path) {
    	try {
	    	FileOutputStream fileOut = new FileOutputStream(path);
	    	XMLEncoder out = new XMLEncoder(fileOut);
            out.writeObject(class_probability);
            out.close();
            fileOut.close();
            System.out.println("The Map has been serialized");
    	}catch (IOException e) {
            e.printStackTrace();
            }
        }
    
    public static Map<String,Double> load_prob_class(String filename){
    	Map<String,Double> probability_class=new HashMap<>();
    	try {
    		FileInputStream filein=new FileInputStream(filename);
    		XMLDecoder in=new XMLDecoder(filein);
    		probability_class=(Map<String,Double>) in.readObject();
            in.close();
            filein.close(); 
            System.out.println("The class probability has been successfully recovered");
        } catch (IOException e) {
        	e.printStackTrace();  
        }
    	return probability_class;
    }
    
//    public static void save_tfidf(Map<File,Map<String,Double>> tf_idf,String path) {
//    	try {
//	    	FileOutputStream fileOut = new FileOutputStream(path);
//	    	XMLEncoder out = new XMLEncoder(fileOut);
//            out.writeObject(tf_idf);
//            out.close();
//            fileOut.close();
//            System.out.println("The Map has been serialized");
//    	}catch (IOException e) {
//            e.printStackTrace();
//            }
//      }
    public static void save_tfidf(Map<String, Map<String, Double>> tf_idf, String path) {
        try (FileOutputStream fileOut = new FileOutputStream(path);
             XMLEncoder out = new XMLEncoder(fileOut)) {
            out.writeObject(tf_idf);
            System.out.println("The Map has been serialized");
        } catch (IOException e) {
            System.err.println("Error saving the Map to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static Map<String,Map<String,Double>> load_tfidf(String filename){
    	Map<String,Map<String,Double>> tf_idf=new HashMap<>();
    	try {
    		FileInputStream filein=new FileInputStream(filename);
    		XMLDecoder in=new XMLDecoder(filein);
    		tf_idf=(Map<String,Map<String,Double>>) in.readObject();
            in.close();
            filein.close(); 
            System.out.println("The corpus has been successfully recovered");
        } catch (IOException e) {
        	e.printStackTrace();  
        }
    	return tf_idf;
    }
}
