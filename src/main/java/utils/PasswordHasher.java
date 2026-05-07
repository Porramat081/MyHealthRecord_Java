package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    // Hash a password
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        String salt = "this is salt";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashBytes = md.digest(password.getBytes());

        // convert byte array to string
        StringBuilder sb = new StringBuilder();
        for(byte b : hashBytes){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }

}
