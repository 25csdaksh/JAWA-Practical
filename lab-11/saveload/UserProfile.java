package saveload;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private String username;
    private String email;
    // Transient field: will NOT be saved during serialization
    private transient String sessionToken;

    public UserProfile(int userId, String username, String email, String sessionToken) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.sessionToken = sessionToken;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return String.format("UserProfile[ID=%d, Username='%s', Email='%s', SessionToken='%s']",
                userId, username, email, (sessionToken != null ? sessionToken : "<NULL / TRANSIENT NOT RESTORED>"));
    }
}
