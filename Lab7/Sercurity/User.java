package Lab7.Sercurity;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
    private String login;
    private String passwordHash;
    private static MessageDigest md;
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public User(String login, String passwordHash){
        this.login = login;
        this.passwordHash = passwordHash;

    }

    public static String getPasswordHash(String password){
        String pepper = "a3603Q04g3w6";
        byte[] byteHash = md.digest((pepper + password).getBytes(StandardCharsets.UTF_8));
        StringBuilder hexHash = new StringBuilder();
        for(byte b: byteHash) hexHash.append(String.format("%02x", b));
        return hexHash.toString();
    }

    public String getLogin() {
        return login;
    }
    public String getHash(){
        return passwordHash;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            User tempo = (User) obj;
            return (tempo.passwordHash.equals(this.passwordHash)) && (tempo.login.equals(this.login));
        }
        return false;
    }
}
