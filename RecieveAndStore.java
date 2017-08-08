import java.util.regex.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.time.*;
import java.text.*;
import java.time.format.*;

public class ReceiveAndStore{
	public String loc = "/home/asif/Mail/allMails";
	public String To;
	public String From;
	public String Subject;
	public String F[] = new String[4];
	public String URL;
	public String U;
	public String P;
	public String username;
	public String password;
	public int mydates;

	public ReceiveAndStore(String user, String pass){
		username = user;
		password = pass;
	}

	public ReceiveAndStore(){
		URL = null;
		U = null;
		P = null;
	}

	public void ReInitialize(){
		/* Actually getting the mails from my Gmail Account.
		 * And then storing it into a common place called "/home/asif/Mail/allMails"
		 * Then reading each mail from allMails and classifing based on the reciever of mail
		 * In the same way checking if mail is Spam or Not
		 * All extracting features of the mail
		 */
		To = null;
		From = null;
		Subject = null;
		for(int i=0;i<4;i++){
			F[i] = null;
		}
		username = null;
		password = null;
		mydates = 0;
		System.out.println("Initializing the data"+loc);

	}


	public void getMainInbox(){
		/*
		 *	Reading mails
		 *	converting maildata into text data
		 *	storing it in /home/asif/Mail/allMails directory
		 *  calling readMailsAndStoreInDatabase() method
		*/

		/*
		* RecieveMail rm = new RecieveMail();
		* rm.sendData(username,password);
		*/
	}

	private void deleteMailIfDate(){
		/*
		 * check if  spam then
		 * 		if mail read and currDate exceeds all the features date then
		 *			delete it
		 * 		if mail not read and currDate exceeds all the features date then
		 *			addOneFeatureDate += 2;
		 * if mail read and currDate exceeds all the features date then
		 * 		delete it
		 * if no features found then 
		 * 		do not delete the mail
		 * 
		 */
	} 

	private String addDeletingDate(String temp){
		/*
		 * This will add deleting date
		 */

		int i = 0;
		String deleteDate = null;
		for(;i<2;i++){
			if(F[i]!=null && F[i+1]!=null){
				if(F[i].compareTo(F[i+1])<0){
					deleteDate = F[i+1];
				}else{
					deleteDate = F[i];
				}
			}else if(F[i]!=null && F[i+1]!=null){
				if(F[i].compareTo(deleteDate)>0){
					deleteDate = F[i];
				}
			}
		}

		if(F[3]!=null){
			// extract date and add with F[3]

		}


		return deleteDate;
	}

	private String reverseDateMonthYear(String date){
		String currDate = null;
		String arr[] = date.split('/');
		StringBuffer s;
		for(int i=0;i<arr.length;i++){
			s = new StringBuffer(arr[0]);
			currDate = currDate.concat(s.reverse());
			if(i<2){currDate = currDate.concat("/");}
		}
		return currDate;
	}
	private void readMailsAndStoreInDatabase()throws IOException{
		/*
		 * After getting main Inbox
		 * Now read mail from main Inbox
		 * Extract to,from,subject,features
		 * Check if mail is a spam
		 * add the mail data into database
		 * also place the mail into appropriate user Inbox or spam box
		 */
		BufferedReader br;
		File dir = new File(loc);
		for(final File file:dir.listFiles()){
			ReInitialize();
			int i=0;
			if(file.isFile()){
				//This Stub of code will extract features, To , From and Subject.
				/************************************************************/
				String fileName = file.getName();
				loc = loc.concat("/");
				loc = loc.concat(fileName);
				br = new BufferedReader(new FileReader(loc));
				Pattern to = Pattern.compile("^To:[ ]*[.a-zA-Z0-9]+[@][a-z]+.com$");
				Pattern subject = Pattern.compile("^Subject:[ ]*[ .,a-zA-Z0-9]+");
				Pattern from = Pattern.compile("From:[ ]*[.a-zA-Z0-9]+[@][a-z]+.com");
				Pattern dates = Pattern.compile("[0-9]+/[0-9]+/[0-9]+");	// 25/2/2016
				//Pattern dates1 = Pattern.compile("[0-9]+[ ]*[a-zA-Z]+[,][ ]*[0-9]+");	// 25 June, 2016
				Pattern dates2 = Pattern.compile("[0-9]+[ ]*days");	// 7 days
				Matcher mat;
				String word;
				while((word=br.readLine())!=null){
					mat = to.matcher(word);
					if(mat.find()){
						To = mat.group();
						To = To.substring(4,To.length());
						System.out.println("To:"+To);
					}
					mat = from.matcher(word);
					if(mat.find()){
						From = mat.group();
						From = From.substring(6,From.length());
						System.out.println("From:"+From);
					}
					mat = subject.matcher(word);
					if(mat.find()){
						Subject = mat.group();
						Subject = Subject.substring(10,Subject.length());
						System.out.println("Subject:"+Subject);
					}
					mat = dates.matcher(word);
					if(mat.find()){
						if(i<3){
						F[i]=mat.group();
						i++;
						System.out.println("Dates:"+F[i-1]);
						}
					}
					/*mat = dates1.matcher(word);
					if(mat.find()){
						F[i]=mat.group();
						i++;
						System.out.println("Dates1:"+F[i-1]);
					}*/
					mat = dates2.matcher(word);
					if(mat.find()){
						F[3]=mat.group();
						Pattern days = Pattern.compile("[0-9]+");
						mat = days.matcher(word);
						if(mat.find()){
							F[3] = null;
							F[3] = mat.group();
							mydates = Integer.parseInt(F[3]);
						}
						System.out.println("Dates2:"+F[3]);
					}
				}
				br.close();
				To = "mohamad.asifraaja@gmail.com";
				/************************************************************/
				// Check whether the mail is spam or not
				/************************************************************/
				Main m = new Main();
				int spam = m.setTest(loc);
				System.out.println("Spam = "+spam);
				/************************************************************/
				//This stub will put the mail into correct username and into their Inbox or Spam Box
				/*************************************************************/
				String newLoc = "/home/asif/Mail/";
				newLoc = newLoc.concat(To);
				newLoc = newLoc.concat("/");
				if(spam==0){newLoc = newLoc.concat("Inbox/");}
				else{newLoc = newLoc.concat("Spam/");}
				newLoc = newLoc.concat(fileName);
				File newFile = new File(loc);
				File dest = new File(newLoc);
				newFile.renameTo(dest);
				System.out.println("Sent");
				/************************************************************/
				// This Stub will store the data into the database
				/************************************************************/
				URL="jdbc:mysql://localhost/mailDb";
				U="root";
				P="";
				int readAlready=0,count;

				Connection con = null;
				PreparedStatement ps = null;
				Statement stmt = null;
				try{
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection(URL,U,P);
					stmt=con.createStatement();
					String sql;

					sql = "insert into Inbox values (?,?,?,?,?,?,?,?,?,?,?,?)";

					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					
					java.sql.Date D0=null,D1=null,D2=null;
					if(F[0]!=null){
						Date d0 = format.parse(F[0]);
						D0 = new java.sql.Date(d0.getTime());
						System.out.println(D0);
					}
					
					if(F[1]!=null){
						Date d1 = format.parse(F[1]);
						D1 = new java.sql.Date(d1.getTime());
					}

					if(F[2]!=null){
						Date d2 = format.parse(F[2]);
						D2 = new java.sql.Date(d2.getTime());
					}

					LocalDate localdate = LocalDate.now();
					String temp = localdate.toString();
					String temp2 = temp.replace('-','/');
					temp = null;
					temp = reverseDateMonthYear(temp2);
					Date ldate = format.parse(temp);
					java.sql.Date date = new java.sql.Date(ldate.getTime()); 
					String deleteDate = addDeletingDate(temp);

					ps=con.prepareStatement(sql);

					ps.setString(1,fileName);ps.setString(2,To);ps.setString(3,From);ps.setString(4,Subject);
					ps.setInt(5,readAlready);ps.setInt(6,spam);ps.setDate(7,date);ps.setString(8,newLoc);ps.setDate(9,D0);
					ps.setDate(10,D1);ps.setDate(11,D2);ps.setInt(12,mydates);

					count=ps.executeUpdate();
					ps.close();
					stmt.close();
					con.close();
				}catch(SQLException e){
					e.printStackTrace();
				}catch(ClassNotFoundException e){
					e.printStackTrace();
				}catch(ParseException e){
					e.printStackTrace();
				}

				loc = null;
				loc = "/home/asif/Mail/allMails";
				System.out.println("*****************************************************************************");
			}
		}
	}

	public static void main(String args[])throws IOException{
		ReceiveAndStore r = new ReceiveAndStore();
		r.readMailsAndStoreInDatabase();
	}
}
