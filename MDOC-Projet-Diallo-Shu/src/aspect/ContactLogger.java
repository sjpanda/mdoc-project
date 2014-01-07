package aspect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;

public class ContactLogger {
	public void log(String firstname, String lastname) throws Throwable{
		File file;
		FileWriter output;
		BufferedWriter writer;

		try {
			String logFolder = "contactLogs";
			if(! (new File(logFolder)).exists()){
				if(! (new File(logFolder).mkdirs())){
					System.err.println("Cannot create the folder " + logFolder);
					return;
				}
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			file = new File(logFolder + "/contactLog-" + dateFormat.format(cal.getTime()));
			if(! file.exists()){
				file.createNewFile();
			}
			
			output = new FileWriter(file.getAbsoluteFile(), true);
			writer = new BufferedWriter(output);

			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			writer.append(dateFormat.format(cal.getTime()) + 
					" try to add the contact(firstname, lastname) : " + firstname + " " + lastname);
			writer.newLine();
			System.out.println("Logger's location : " + file.getAbsolutePath());
			
			writer.close();			
		} catch (Exception | Error e) {
			e.printStackTrace();
		}
	}
}