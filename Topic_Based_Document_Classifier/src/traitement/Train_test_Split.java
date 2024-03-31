package traitement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Train_test_Split {
	public static void main(String args[]) {
		String path="C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\DataProcessing";
		String train_path="C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\Train\\";
		String test_path="C:\\Users\\Y.STORE\\Documents\\MasterS3\\TextMining\\Test\\";
		double test_fraction=0.2;
		List<File>data=Arrays.asList(new File(path).listFiles());
		int test_size=(int)(test_fraction*data.size());
		Collections.shuffle(data);
		try {
			for(File file : data.subList(0, test_size)) {
			    Files.copy(file.toPath(),
			        (new File(test_path + file.getName())).toPath(),
			        StandardCopyOption.REPLACE_EXISTING);
			}
			System.out.println("Test file finish...");
			for(File file : data.subList(test_size, data.size())) {
			    Files.copy(file.toPath(),
			        (new File(train_path + file.getName())).toPath(),
			        StandardCopyOption.REPLACE_EXISTING);
			}
			System.out.println("Train file finish...");
		}catch(IOException e){
			e.printStackTrace();	
		}
		
		
	}
}
