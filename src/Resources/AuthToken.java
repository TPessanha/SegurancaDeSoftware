package Resources;

/**
 * Created by tomas on 12/11/2017.
 */
public class AuthToken {
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; //2h

    public String username;
    public String tokenID;
    public long creationData;
    public long expirationData;

    public AuthToken(String username, String token) {
        this.username = username;
        this.tokenID = token;
        this.creationData = System.currentTimeMillis();
        this.expirationData = this.creationData + AuthToken.EXPIRATION_TIME;
    }
}