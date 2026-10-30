package CiviliaDao.utilities;

import java.io.File;
import java.io.IOException;

public class RestrictExecutionFile {

	private static File lock = new File("RegistraMandati.txt");

	public RestrictExecutionFile() {
		// TODO Auto-generated constructor stub
	}

	public static boolean exists()
	{
	    return lock.exists();
	}

	public static void createLockFile()
	{
	  try {
	
		    lock.createNewFile();
	
		} catch (IOException e) {
	
		    // the file already exists
		    System.exit(0);}
	}
	public static void deleteLockFile()
	{
	    if(lock.exists()) lock.delete();
	}
	public static void main(String[] args) {

		if (RestrictExecutionFile.exists()){
	        System.out.println("RegistraMandati-1.0.jar è running");
	        System.exit(0);
	    }

		RestrictExecutionFile.createLockFile();

	    /*
	     * 
	     * 
	     * CODE
	     * 
	     * 
	     * 
	     */


		RestrictExecutionFile.deleteLockFile();
	    System.out.println("Program exiting normally");
	}

}
