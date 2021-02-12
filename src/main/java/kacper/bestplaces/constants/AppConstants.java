package kacper.bestplaces.constants;


public class AppConstants {

	public static final String hereApiKey = "f0fvPzMKTkmZhlNjS_Ot9QCdpP8PyR49dIdHMnBk2bY";

	public static final String hereURL = "https://places.sit.ls.hereapi.com/places/v1/discover/"
			+ "explore?languages=pl-PL&cat=sights-museums,natural-geographical&in=";

	public static final String hereURL2 = "https://places.demo.api.here.com/places/v1/places/";

	public static final String hereContext = ";context="
			+ "Zmxvdy1pZD0yNWEwOTkyOS0wNDIzLTU3NTUtYWIwYS1mNzkyNDZjNmRkM2VfMTU5MDE1ODcxMjE1N18wXzE1OTkmc2l6ZT01JlgtRldELUFQUC1JRD1EZW1vQXBwSWQwMTA4MjAxM0dBTCZYLU5MUC1UZXN0aW5nPTE"
			+ "?app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg";

	public static final String emailPattern = "^[a-zA-z0-9]+[\\._a-zA-Z0-9]*@[a-zA-Z0-9]+{2,}\\.[a-zA-Z]{2,}[\\.a-zA-Z0-9]*$";
	
	public static final String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\*])(?!.*\\s).{8,12}$";
}
