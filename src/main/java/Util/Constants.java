package Util;

public class Constants {
	//Path para o base de dados
	public final static String DATABASE_FILE_PATH = "src/main/resources/Database.db";
	//Se o path para a base de dados é absulote ou relativo ao projeto
	public final static Boolean USE_ABSOLUTE_PATH = false;

	//Time for capability to expire
	public final static long CAPABILITY_EXPIRE_TIME = 60 * 60 * 3; //3 Horas
	
	//Score minimo para uma password poder ser usada (0-4)
	public final static int MIN_PASSWORD_SECURITY_SCORE = 4;
	
	//Strength of the salts used in password (4-31)
	public final static int SALT_STRENGHT = 14;
	
	//Maximum delayed for repeated logins
	public final static int LVL1_LOGIN_DELAY = 1; //Minutos
	public final static int LVL2_LOGIN_DELAY = 3; //Minutos
	public final static int LVL3_LOGIN_DELAY = 10; //Minutos
	public final static int MAX_LOGIN_DELAY = 30; //Minutos
	
	//O numero de vezes que o user pode introduzir a password errada antes de começar a fazer throttling
	public final static int NUMBER_OF_ALLOWED_WRONG_PASSWORD = 3;
	
	//Messages
	public final static String UNKNOWN_ERROR_MSG = "Unknown error";
	
	//FROM HERE DONT CHANMGE ANYTHING really stop it
	/*
    
    
    
    
    
    No
    
    
    
    
    
    What are u doing stop go back up
    
    
    
    
    
    U are not following my instructions
     
     
     
     
     
     Ok i get it this is my fault Im not helping, maybe u are lost, up is that way /\
     
     
     
     
     
     
     I give up u just want to watch the world burn
     
     
     */
	
	
	//Helpers
	public static final String USER_COOKIE = "USER";
	public static final String ROLE_COOKIE = "ROLE";
}
