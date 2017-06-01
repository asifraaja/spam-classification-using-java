/*
  Classifier.Java
    This module is responsible for classification of mails.
    It does both training and testing.
    
    Training Algorithm:
    0.) Preprocessing the files
    1.) Calling spamReader() to read spam mails and store data in pmap,nmap
    2.) Calling hamReader() to read hpam mails and store data in pmap,nmap
    3.) Creating a lookalike matrix
    4.) Store in separate file
    
    Testing Algorithm:
    0.) Read and store the values into pmap and nmap from respective files
    1.) Read a sentence
    2.) convert into array of strings
    3.) for each word calculating 
    		a.) probability of ham
    		b.) probability of spam
    4.) if ham == spam
    		Calling KNN classifier
    	else if ham > spam
    		It is Ham
    	else 
    		It is spam
*/

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Classifier{
	public HashMap<String,Double> pmap = new HashMap<String,Double>();	// stores words of ham mails
	public HashMap<String,Double> nmap = new HashMap<String,Double>();	// stores words of spam mails
	private int vocabulary;
	
	public Classifier(){
		System.out.println("Classifier is ready");
		vocabulary=0;
	}
	
	public int trainClassifier()throws IOException{
		try{
			
			SpamReader sr = new SpamReader();
			sr.ReadMail(pmap,nmap);
			
			HamReader hr = new HamReader();
			hr.ReadMail(pmap,nmap);
			vocabulary=vocabulary+hr.vocabulary+sr.vocabulary;;
			
			sr.createLookMatrix(nmap,vocabulary);
			hr.createLookMatrix(pmap,vocabulary);
			
			testData();
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}
		return 0;
	}
	
	public int testData()throws IOException{
		int ham=0,spam=0;
		  try{
		  	    File folder = new File("/home/asif/Dataset/input");
		  	    String fileLoc;
		  	    for(final File fileEntry:folder.listFiles()){
		  	    	if(fileEntry.isFile()){
		  	    		fileLoc = "/home/asif/Dataset/input/";
		  	    		fileLoc = fileLoc.concat(fileEntry.getName());
		  	    		
		  	    		File input = new File(fileLoc);
				        BufferedReader br = new BufferedReader(new FileReader(input));
				        String comments;
				        double probOfHam = 1.0,probOfSpam = 1.0;  
		  	    		
		  	    		while((comments=br.readLine())!=null){                            
				                String words[]=comments.split(" ");
				                for(int i=0;i<words.length;i++)
				                {
				                    if(pmap.containsKey(words[i])){probOfHam=probOfHam*pmap.get(words[i]);}
				                    if(nmap.containsKey(words[i])){probOfSpam=probOfSpam*nmap.get(words[i]);}
				                }
                              		}
				        System.out.println("Yes = "+probOfHam);
				        System.out.println("No = "+probOfSpam);
				        if(probOfHam >= probOfSpam){
				              System.out.println(fileEntry.getName()+" Ham Mail");
				              ham++;
				        }
				        else{
				            System.out.println(fileEntry.getName()+" Spam Mail");
				            spam++;
				        }				     
				        fileLoc=null;    
		  	    	}   	
		  	    } 
		  	    System.out.println("Ham = "+ham+" Spam ="+spam);                       
                        }
                        catch(FileNotFoundException e){
                        	System.out.println("File for testing not found");
                        }
                        return 0;
	}
}
