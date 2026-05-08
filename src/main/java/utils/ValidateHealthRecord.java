package utils;

public class ValidateHealthRecord {

    public static String validateEditRecord(String weight, String temperature, String upper, String lower, String note) {
        try{
            if (weight.isEmpty()) {
                return "Weight must not be empty";
            } else if (Float.parseFloat(weight) <= 0) {
                return "Weight must be greater than 0";
            } else if (temperature.isEmpty()) {
                return "Temperature must not be empty";
            } else if (Float.parseFloat(temperature) <= 0) {
                return "Temperature must be greater than 0";
            } else if (upper.isEmpty() || lower.isEmpty()) {
                return "Blood pressure must be filled out both sides";
            } else if (Integer.parseInt(upper) < 0 || Integer.parseInt(lower) < 0) {
                return "Blood pressure must not be negative number";
            } else if (note.length() > 50) {
                return "Note must be less than 50 characters";
            } else{
                return "";
            }
        }catch (NumberFormatException e){
            return "Blood pressure must be integer";
        }
        catch (Exception e){
            return e.getMessage();
        }

    }
}