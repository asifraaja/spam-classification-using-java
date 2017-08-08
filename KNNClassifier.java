import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class KNNClassifier{
	private int trained=0;
	public HashMap<String,Double> hknn = new HashMap<String,Double>();
	
	public KNNClassifier(){
		System.out.println("KNN is ready");
	}
	
	public void loadKNN(HamReader h,SpamReader s)throws IOException{
		//Calling Readers to load datasets
		if(h.readMailsForKNN(hknn)!=0){
			System.out.println("Error in loading ham DataSet");
		}
		if(s.readMailsForKNN(hknn)!=0){
			System.out.println("Error in loading spam DataSet");
		}
		System.out.println("DataSet Loaded");
		writeTheHashMap();
		System.out.println("Data entered");
		trained=1;
	}
	
	private int writeTheHashMap()throws IOException{
		BufferedWriter br = new BufferedWriter(new FileWriter("KNNMap.txt"));
		BufferedWriter br2 = new BufferedWriter(new FileWriter("/home/asif/Dataset/Ham.txt"));
		BufferedWriter br3 = new BufferedWriter(new FileWriter("/home/asif/Dataset/Ham.txt"));
		Set<Map.Entry<String, Double>> set = hknn.entrySet();
		String word;
			for(Map.Entry<String, Double> me : set) {
				word=me.getKey();
				br.write(word);
				br.write("\n");
				word=null;
				word=Double.toString(me.getValue());
				br2.write(word);
				br3.write(word);
				br2.write(" ");
				br3.write(word);
			}
		br2.close();
		br.close();
		return 0;
	}

	public void trainKNN(HamReader h,SpamReader s){
		//getAFile
		// read using a reader
		// now calculate distance between mainFile vs mainFile2
		// check for nearest distance
		if(trained==0){
			loadData();
		}
		else{
			if(h.putValuesForKNN(hknn)!=0){
				System.out.println("error in Training Hamms");
			}
			if(s.putValuesForKNN(hknn)!=0){
				System.out.println("error in Training Spams");
			}
			System.out.println("Trained");
			
		}

	}
	
	private int loadData(){
		File file = new File("KNNMap.txt");
		try{
			Scanner sn = new Scanner(file);
			String word;
			while(sn.hasNext()){
				word=sn.next();
				if(!hknn.containsKey(word)){
					hknn.put(word,0.0);
				}
			}
			sn.close();
		}catch(FileNotFoundException e){
			System.out.println("File KNNMap not found");
		}
		return 0;
	}
	
	public int testKNN(){
		File file = new File("/home/asif/Dataset/Kinput/0002.1999-12-13.farmer.ham.txt");
		try{
			Scanner sn = new Scanner(file);
			String word;
			while(sn.hasNext()){
				word=sn.next();
				hknn.put(word,hknn.get(word)+1.0);
			}
			sn.close();
			File read2=new File("/home/asif/Dataset/Ham.txt");	//This will store all differences value
			sn = new Scanner(read2);
			Set<Map.Entry<String, Double>> set = hknn.entrySet();
			Double value1,temp=0.0;
			for(Map.Entry<String, Double> me : set) {
				if(sn.hasNext()){
					value1=sn.nextDouble();	
					value1=value1-me.getValue();
					temp=temp+value1*value1;		//(x-y)^2	
				}
			}
					sn.close();
					br.close();
		}catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}
	}

	public static void main(String args[])throws IOException{
		
		KNNClassifier k = new KNNClassifier();
		HamReader h = new HamReader();
		SpamReader s = new SpamReader();
		k.loadKNN(h,s);
		k.trainKNN(h,s);
		k.testKNN();
	}
}
