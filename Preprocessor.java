import java.util.Scanner;
import java.util.HashMap;
import java.io.*;

public class Preprocessor{
	public String preprocess(String fileLoc)throws IOException{
		int i;
		File read = new File("file.txt");	
		Scanner sn = new Scanner(read);
		String word,word2;
		HashMap<String,Boolean> common = new HashMap<String,Boolean>();
		while(sn.hasNext()){
			word=sn.next();
			common.put(word,true);
		}

				File file = new File(fileLoc);
				Scanner sb = new Scanner(file);
				BufferedWriter br = new BufferedWriter(new FileWriter("/home/asif/Mail/output.txt"));
				while(sb.hasNext()){
					word=sb.next();
					word2=word.toLowerCase();
					String array[] = word2.split("[^a-zA-Z0-9]");
					word2="";
					for(i=0;i<array.length;i++){
						word2=word2.concat(array[i]);
					}
					if(!common.containsKey(word2)){
							br.write(word2);
							br.write(" ");
					}
				}
				br.close();
				sb.close();
				return "/home/asif/Mail/output.txt";
		
				/*file = new File("output.txt");
				sb = new Scanner(file);
				br = new BufferedWriter(new FileWriter(fileLoc));
				while(sb.hasNext()){
					word=sb.next();
					br.write(word);
					br.write(" ");
				}
				br.close();
				sb.close();*/
			}
		}
	/*public static void main(String args[])throws FileNotFoundException,IOException{
		int i;
		File read = new File("file.txt");	
		Scanner sn = new Scanner(read);
		String word,word2;
		HashMap<String,Boolean> common = new HashMap<String,Boolean>();
		while(sn.hasNext()){
			word=sn.next();
			common.put(word,true);
		}
		// Removing common words
		String fileName;
		String fileLoc; 
		File folder = new File("/home/asif/Dataset/ProcessedDataset");
		for(final File fileEntry:folder.listFiles()){
			fileLoc = "/home/asif/Dataset/ProcessedDataset/"; 
			if(fileEntry.isFile()){
				fileName = fileEntry.getName();
				fileLoc = fileLoc.concat(fileName);
				System.out.println(fileLoc);
				
				File file = new File(fileLoc);
				Scanner sb = new Scanner(file);
				BufferedWriter br = new BufferedWriter(new FileWriter("output.txt"));
				while(sb.hasNext()){
					word=sb.next();
					word2=word.toLowerCase();
					String array[] = word2.split("[^a-zA-Z0-9]");
					word2="";
					for(i=0;i<array.length;i++){
						word2=word2.concat(array[i]);
					}
					if(!common.containsKey(word2)){
							br.write(word2);
							br.write(" ");
					}
				}
				br.close();
				sb.close();
		
				file = new File("output.txt");
				sb = new Scanner(file);
				br = new BufferedWriter(new FileWriter(fileLoc));
				while(sb.hasNext()){
					word=sb.next();
					br.write(word);
					br.write(" ");
				}
				br.close();
				sb.close();
			}
			fileLoc=null;
		}
	}
}*/
