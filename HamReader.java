/*
Algorithm:
Name: Ham Reader
Function: This will read the ham mails and count the ham values

ReadMail()
1.) Open a file
2.) Using scanner read word by word
3.) Get a word from the file and it into hashmap of ham
4.) Alternatively add the word in Positive and positive map
5.) Repeat from step 3 untill EOF.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class HamReader{
	private HashMap<String,Double> hmap = new HashMap<String,Double>();
	public int positive;
	public int vocabulary;
	private double value;
	private int cnt;
	
	public HamReader(){
		System.out.println("Reading the ham mail dataset");
		positive=0;vocabulary=0;value=0;cnt=0;
		
	}
	
	public int ReadMail(HashMap<String,Double> pmap,HashMap<String,Double> nmap)throws FileNotFoundException{
		String word;
		String fileName;
		String fileLoc = "/home/asif/Dataset/ham/";
		File folder = new File("/home/asif/Dataset/ham");
		
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
						positive++;
						if(hmap.containsKey(word))
						{
							value = hmap.get(word);
							hmap.put(word,value+1.0);
						}
						else
						{
							hmap.put(word,1.0);
							vocabulary++;
						        pmap.put(word,0.0);
						        nmap.put(word,0.0);
						        cnt++;
						}
					}
					fileLoc=null;
					fileLoc = "/home/asif/Dataset/ham/";
					in.close();
			}
		}
		return 0;
	}
	
	public int createLookMatrix(HashMap<String,Double> pmap,int totalCount){
		Set<Map.Entry<String, Double>> set = pmap.entrySet();
			for(Map.Entry<String,Double> me:set){
                                String key=me.getKey();
                                if(hmap.containsKey(key))
                                {
                                    value=hmap.get(key)+1.0;   
                                }
                                else
                                {
                                    value=1.0;
                                }
                                value=value/(positive+totalCount);
                                pmap.put(key,value);
			}
			return 0;
	}
}
