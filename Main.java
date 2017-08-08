import java.util.Scanner;
import java.io.IOException;
public class Main{
	public int ch;
	public int trained=0;

	public Main(){


	}

	public int setTest(String loc)throws IOException{
		Classifier cl = new Classifier();
		int value = cl.testDataForFinal(trained,loc);
		return value;
	}

	/*

	public static void main(String args[]){
		Scanner sn = new Scanner(System.in);
		
		Classifier cl = new Classifier();
		System.out.println("Email Classifier");
		System.out.println("1. Train the classifier for Naive Bayes");
		System.out.println("2. Test the data");
		ch = sn.nextInt();
		
		switch(ch)
		{
			case 1:
				/* Calling NaiveBayes 
				try{
				trained=cl.trainClassifier();
				}
				catch(IOException e){e.printStackTrace();}
				break;
			case 2:
				try{
					cl.testData(trained);
				}catch(IOException e){e.printStackTrace();}
				break;
			default:
				System.out.println("Select a correct option");
				break;	
		}
	}
	*/
}
