package Resources;

import static Util.Constants.EXPIRATION_TIME;

public class AuthToken {

    private final String username;
    private final String tokenID;
    private final long creationData;
    private final long expirationData;

    public AuthToken(String username, String token) {
        this.username = username;
        this.tokenID = token;
        this.creationData = System.currentTimeMillis();
        this.expirationData = this.creationData + EXPIRATION_TIME;
    }

    public String getUsername() {
        return username;
    }

    public String getTokenID() {
        return tokenID;
    }

    public long getCreationData() {
        return creationData;
    }

    public long getExpirationData() {
        return expirationData;
    }
}