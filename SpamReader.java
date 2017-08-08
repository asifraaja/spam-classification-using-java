/*
Algorithm:
Name: Spam Reader
Function: This will read the spam mails and count the spam values

ReadMail()
1.) Open a file
2.) Using scanner read word by word
3.) Get a word from the file and it into hashmap of Spam
4.) Alternatively add the word in Positive and Negative map
5.) Repeat from step 3 untill EOF.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class SpamReader{
	private HashMap<String,Double> smap = new HashMap<String,Double>();
	public int negative;
	public int vocabulary;
	private double value;
	private int cnt;	

	public SpamReader(){
		System.out.println("Reading the spam mail dataset");
		negative=0;vocabulary=0;value=0;cnt=0;
		
	}
	
	public int ReadMail(HashMap<String,Double> pmap,HashMap<String,Double> nmap)throws FileNotFoundException{
		String word;
		String fileName;
		String fileLoc = "/home/asif/Dataset/spam/";
		File folder = new File("/home/asif/Dataset/spam");
		for(final File fileEntry:folder.listFiles()){
			if(fileEntry.isFile()){
				fileName = fileEntry.getName();
				fileLoc = fileLoc.concat(fileName);
				System.out.println(fileLoc);
				File read = new File(fileLoc);
				Scanner in = new Scanner(read);
					while(in.hasNext())
					{
						word=in.next();
						negative++;
						if(smap.containsKey(word))
						{
							value = smap.get(word);
							smap.put(word,value+1.0);
						}
						else
						{
							smap.put(word,1.0);
							vocabulary++;
						        pmap.put(word,0.0);
						        nmap.put(word,0.0);
						        cnt++;
						}
					}
					fileLoc=null;
					fileLoc = "/home/asif/Dataset/spam/";					
					in.close();
			}
		}
		return 0;
	}
	
	public int createLookMatrix(HashMap<String,Double> nmap,int totalCount){
		Set<Map.Entry<String, Double>> set = nmap.entrySet();
			for(Map.Entry<String,Double> me:set){
                                String key=me.getKey();
                                if(smap.containsKey(key))
                                {
                                    value=smap.get(key)+1.0;   
                                }
                                else
                                {
                                    value=1.0;
                                }
                                value=value/(negative+totalCount);
                                nmap.put(key,value);
			}
			return 0;
	}
	
	public int readMailsForKNN(HashMap<String,Double> sknn){
		String word;
		String fileName;
		String fileLoc;
		File folder = new File("/home/asif/Dataset/Kspam");
		for(final File fileEntry:folder.listFiles()){
			if(fileEntry.isFile()){
				fileLoc = "/home/asif/Dataset/Kspam/";
				fileName = fileEntry.getName();
				fileLoc = fileLoc.concat(fileName);
				System.out.println("Reading the file :"+fileLoc);
				try{
					File read = new File(fileLoc);
					Scanner sn = new Scanner(read);
					while(sn.hasNext()){
						word=sn.next();
						if(!sknn.containsKey(word)){
							sknn.put(word,0.0);
						}
					}
					sn.close();
				}catch(FileNotFoundException e){
					System.out.println("File Not Found");
				}
				fileLoc=null;
			}
		}
		return 0;
	}
	
	public int putValuesForKNN(HashMap<String,Double> hknn){
		String word;
		String fileName;
		String fileLoc;
		Double centroid=0.0;
		File folder = new File("/home/asif/Dataset/Kspam");
		for(final File fileEntry:folder.listFiles()){
			if(fileEntry.isFile()){
				fileLoc = "/home/asif/Dataset/Kspam/";
				fileName = fileEntry.getName();
				fileLoc = fileLoc.concat(fileName);
				System.out.println("Reading the file "+fileLoc);
				try{
					File read = new File(fileLoc);
					Scanner sn = new Scanner(read);
					while(sn.hasNext()){
						word=sn.next();
						value=hknn.get(word);
						hknn.put(word,value+1.0);
					}
					sn.close();
					//Calculating the Euclidean distance file by file
					File read2=new File("/home/asif/Dataset/Spam.txt");	//This will store all differences value
					sn = new Scanner(read2);
					fileLoc="/home/asif/Dataset/temp.txt";
					BufferedWriter br = new BufferedWriter(new FileWriter(fileLoc));
					Set<Map.Entry<String, Double>> set = hknn.entrySet();
					Double value1,temp=0.0;
					for(Map.Entry<String, Double> me : set) {
						if(sn.hasNext()){
							value1=sn.nextDouble();	
							value1=value1-me.getValue();
							temp=temp+value1*value1;		//(x-y)^2
							if(value1<0){value1=-value1;}	
							String write = Double.toString(value1);
							br.write(write);
							br.write(" ");
						}
					}
					sn.close();
					br.close();
					File f = new File("/home/asif/Dataset/temp.txt");
					f.renameTo(read2);
					centroid=temp;
					System.out.println("The centroid is: "+centroid);
					for(Map.Entry<String, Double> me : set) {
						hknn.put(me.getKey(),0.0);
					}
				}catch(FileNotFoundException e){
					System.out.println("File Not Found");
				}catch(IOException e){
					System.out.println("Input Output Error.");
				}
				fileLoc=null;
			}
		}
		System.out.println("Centroid="+centroid);
		return 0;
	}
}
