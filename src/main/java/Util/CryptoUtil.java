package Util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class CryptoUtil {
	
	public static String bCryptEncrypt(String password, String salt) {
		return BCrypt.hashpw(password, salt);
	}
	
	public static boolean doesPasswordMatch(String candidate_password, String stored_hash) {
		return BCrypt.checkpw(candidate_password, stored_hash);
	}
	
	/**
	 * The genSalt() method takes an optional parameter (log_rounds)
	 * that determines the computational complexity of the hashing:
	 *
	 * The amount of work increases exponentially (2**log_rounds),
	 * so each increment is twice as much work. The default log_rounds is 10.
	 *
	 * @param log_rounds Determines the computational complexity of the hashing.
	 *                   (valid range is 4 to 31)
	 *
	 * @return String - Returns the generated Salt
	 */
	public static String genSalt(int log_rounds) {
		return BCrypt.gensalt(log_rounds);
	}
	
	
}
