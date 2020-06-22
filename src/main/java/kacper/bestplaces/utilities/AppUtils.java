package kacper.bestplaces.utilities;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
	
	public static boolean checkEmailOrPassword(String pattern, String pStr) {
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(pStr);
		return m.matches();
	}

	public static String randomStringGenerator()
	{
		String randomString="";
		String signs="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random rnd=new Random();
		for(int i=0;i<32;i++)
		{
			int liczba=rnd.nextInt(signs.length());
			randomString+=signs.charAt(liczba);
		}
		return randomString;
	}
	
	public static String randomPasswordGenerator()
	{
		String pass="";
		String big="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String signs="abcdefghijklmnopqrstuvwxyz1234567890";
		String special="!@#$";
		Random rnd=new Random();
		int l=rnd.nextInt(big.length());
				pass+=big.charAt(l);
		for(int i=1;i<7;i++)
		{
			l=rnd.nextInt(signs.length());
			pass+=signs.charAt(l);
		}
		l=rnd.nextInt(special.length());
		pass+=special.charAt(l);
		return pass;
	}
	
}
