package text_mining;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public abstract class Corpus {
	
	    public static void save_corpus(HashMap<String,HashMap<String,Double>> TF_IDF) {
	    	try {
		    	FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\corpus\\SANGARE_Boubacar_Diam.xml");
		    	XMLEncoder out = new XMLEncoder(fileOut);
	            out.writeObject(TF_IDF);
	            out.close();
	            fileOut.close();
	            System.out.println("The HashMap has been serialized and saved in TFID.xml");
	    	}catch (IOException e) {
	            e.printStackTrace();
	            }
            }
	        
	    public static HashMap<String, HashMap<String, Double>> load_corpus(String filename){
	    	HashMap<String,HashMap<String,Double>> TF_IDF=new HashMap<>();
	    	try {
	    		FileInputStream filein=new FileInputStream(filename);
	    		XMLDecoder in=new XMLDecoder(filein);
	    		TF_IDF=(HashMap<String,HashMap<String,Double>>) in.readObject();
	            in.close();
	            filein.close(); 
	            System.out.println("The corpus has been successfully recovered");
	        } catch (IOException e) {
	        	e.printStackTrace();  
	        }
	    	return TF_IDF;
	    }
}
