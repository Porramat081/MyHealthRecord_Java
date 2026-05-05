package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {
    // Generate salt
    public static String generateSalt(){
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash a password
    public static String hashPassword(String password , String salt) throws NoSuchAlgorithmException {
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

    // Verify password
    public static boolean verifyPassword(String inputPassword , String storedSalt ,String storedHash) throws NoSuchAlgorithmException{
        String inputHash = hashPassword(inputPassword , storedSalt);
        return inputHash.equals(storedHash);
    }
}
