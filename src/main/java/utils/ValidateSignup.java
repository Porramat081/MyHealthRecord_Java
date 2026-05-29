package utils;

public class ValidateSignup {
    /* Utility class for validating user account fields on sign-up and profile edit screens. */
    public static String validateForm(String username , String password , String firstname , String lastname , String confirmedPassword){
        /*
          Validates all signup fields including username length, password strength, name lengths, and password confirmation match.
          Returns an error message or empty string if valid.
        */
        String validatePass = checkPasswordStrength(password);

        if(username.isEmpty()){
            return "Username cannot be empty.";
        } else if (username.length() < 5 || username.length() > 15) {
            return "Username must has 5 - 15 characters";
        } else if (password.isEmpty()) {
            return "Password cannot be empty.";
        } else if (!validatePass.isEmpty()) {
            return validatePass;
        } else if (firstname.isEmpty()) {
            return "FirstName cannot be empty.";
        } else if (firstname.length() < 3 || firstname.length() > 15) {
            return "FirstName must has 3-15 characters";
        } else if (lastname.isEmpty()) {
            return "LastName cannot be empty.";
        }else if (lastname.length() < 3 || lastname.length() > 15) {
            return "LastName must has 3-15 characters";
        }else if(!password.equals(confirmedPassword)){
            return "Password must be same as confirmed password";
        }else{
            return "";
        }
    }

    public static String checkPasswordStrength(String password) {
        /*
          Checks password strength against rules: min 8 chars, letter + digit, one uppercase, one special character.
          Returns an error message or empty string if the password passes.
        */
        int length = password.length();
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLetter(ch)) {
                hasLetter = true;
            } else if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if ("!@#$%^&*".contains(String.valueOf(ch))) {
                hasSpecial = true;
            }

        }

        if (length < 8){
            return "Password must has at least 8 characters.";
        } else if (!hasLetter || !hasDigit) {
            return "Password must has both letter and number.";
        } else if (!hasUpper) {
            return "Password must include at least one uppercase letter";
        } else if (!hasSpecial) {
            return  "Password must contain special character (!@#$%^&*...)";
        }else{
            return "";
        }
    }

}
