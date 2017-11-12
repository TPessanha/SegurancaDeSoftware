package Resources;


import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

import static Util.Constants.DATABASE_FILE_PATH;

/**
 * Created by tomas on 11/11/2017.
 */
public class Storage {
	
	
	/**
	 * @return A list of all the accounts
	 */
	public static ArrayList<Account> getAllAccounts() {
		Account acc;
		ArrayList<Account> list = new ArrayList<>();
		FileReader fr;
		BufferedReader br;
		
		try {
			fr = new FileReader(DATABASE_FILE_PATH);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				list.add(new Account(line,
						br.readLine(),
						br.readLine(),
						br.readLine(),
						br.readLine(),
						br.readLine()
						));
			}
			return list;
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
			return null;
		}
	}
	
	/**
	 * @param acc The account to add to persistent storage
	 */
	public static void addAccountToStorage(Account acc) {
		FileWriter fw;
		PrintWriter pw;
		
		try {
			fw = new FileWriter(DATABASE_FILE_PATH);
			pw = new PrintWriter(fw);
			
			pw.append(acc.getUsername()).append("\r\n")
					.append(acc.getPassword()).append("\r\n")
					.append(acc.getSalt()).append("\r\n")
					.append(acc.getRole().toString()).append("\r\n")
					.append(String.valueOf(acc.isLocked())).append("\r\n")
					.append(String .valueOf(acc.isLoggedIn())).append("\r\n");
			pw.close();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
	
	/**
	 * Saves all the accounts in persistant storage but deletes the previous ones
	 *
	 * @param book The book containing all the accounts to save
	 */
	public static void SaveChangesToStorage(AccountsBook book) {
		FileWriter fw;
		PrintWriter pw;
		
		try {
			
			fw = new FileWriter(Paths.get("") + DATABASE_FILE_PATH);
			pw = new PrintWriter(fw);
			
			pw.write("");
			for (Account acc : book.getAllAccounts()) {
				pw.append(acc.getUsername()).append("\r\n")
						.append(acc.getPassword()).append("\r\n")
						.append(acc.getSalt()).append("\r\n")
						.append(acc.getRole().toString()).append("\r\n")
						.append(String.valueOf(acc.isLocked())).append("\r\n")
						.append(String .valueOf(acc.isLoggedIn())).append("\r\n");
			}
			
			pw.close();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
}
