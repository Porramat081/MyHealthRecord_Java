package utils;

public class ValidateHealthRecord {
    /* Utility class for validating health record fields before saving or updating a record. */
    public static String validateEditRecord(String weight, String temperature, String upper, String lower, String note) {
        /*
           Validates weight, temperature, blood pressure, and note fields.
           Returns an error message string, or empty string if all fields are valid.
        */
        try{
            // Weight
            if (weight.isEmpty()) {
                return "Weight must not be empty";
            }

            float weightValue;

            try {
                weightValue = Float.parseFloat(weight);
            } catch (NumberFormatException e) {
                return "Weight must be a valid number";
            }

            if (weightValue <= 0) {
                return "Weight must be greater than 0";
            }

            // Temperature
            if (temperature.isEmpty()) {
                return "Temperature must not be empty";
            }

            float temperatureValue;

            try {
                temperatureValue = Float.parseFloat(temperature);
            } catch (NumberFormatException e) {
                return "Temperature must be a valid number";
            }

            if (temperatureValue <= 0) {
                return "Temperature must be greater than 0";
            }

            // Blood pressure
            if (upper.isEmpty() || lower.isEmpty()) {
                return "Blood pressure must be filled out both sides";
            }

            int upperValue;
            int lowerValue;

            try {
                upperValue = Integer.parseInt(upper);
            } catch (NumberFormatException e) {
                return "Upper blood pressure must be a valid integer";
            }

            try {
                lowerValue = Integer.parseInt(lower);
            } catch (NumberFormatException e) {
                return "Lower blood pressure must be a valid integer";
            }

            if (upperValue < 0 || lowerValue < 0) {
                return "Blood pressure must not be negative";
            }

            // Note
            if (note.length() > 50) {
                return "Note must not exceed 50 words";
            }

            return "";

        }
        catch (Exception e){
            return e.getMessage();
        }

    }
}