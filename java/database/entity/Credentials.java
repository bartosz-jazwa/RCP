package database.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Credentials implements Serializable{
    @Column(name = "user_ID")
    private String userId;
    @Column(name = "hashed_password")
    private String hashedPassword;

    public Credentials(String username, String password) {
        this.userId = username;
        //PBKDF2Hasher hasher = new PBKDF2Hasher();
        this.hashedPassword = password;//hasher.hash(password.toCharArray());
    }

    public Credentials() {
    }

    public String getUsername() {
        return userId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }


}
