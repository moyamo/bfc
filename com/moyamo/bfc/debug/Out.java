package com.moyamo.bfc.debug;

/**
 * This class is used to print output meant for debugging.
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.1
 */
public class Out {
	private static boolean debug;
	
	public static void setDebug(boolean debug){
		Out.debug = debug;
	}
	
	/**
	 * If debug is true, will print to standard output.
	 * 
	 * @param string - output
	 */
	public static void print(String string){
		if(debug){
			System.out.println(string);
		}
	}
}
