package utils;

public class ValidateSignup {
    public static String validateForm(String username , String password , String firstname , String lastname){
        String validatePass = checkPasswordStrength(password);

        if(username.isEmpty()){
            return "Username cannot be empty.";
        } else if (password.isEmpty()) {
            return "Password cannot be empty.";
        } else if (!validatePass.isEmpty()) {
            return validatePass;
        } else if (firstname.isEmpty()) {
            return "firstName cannot be empty.";
        } else if (lastname.isEmpty()) {
            return "lastName cannot be empty.";
        }else{
            return "";
        }
    }

    public static String checkPasswordStrength(String password) {
        int length = password.length();
        boolean hasLetter = false, hasDigit = false, hasSpecial = false, hasUpper = false;

        for (char ch : password.toCharArray()) {
            if (Character.isLetter(ch)) hasLetter = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if (Character.isUpperCase(ch)) hasUpper = true;
            else if ("!@#$%^&*".contains(String.valueOf(ch))) hasSpecial = true;
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
