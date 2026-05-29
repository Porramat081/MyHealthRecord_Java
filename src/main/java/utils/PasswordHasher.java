package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {
    /* Utility class for hashing passwords using SHA-256 before storing them in the database. */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        /* Hashes the given password with a static salt using SHA-256 and returns the hex-encoded digest. */
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
