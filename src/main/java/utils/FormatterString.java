package utils;

import java.util.HashMap;

public class FormatterString {
    public static String combineBP(int upperBP , int lowerBP){
        return upperBP+" / "+lowerBP;
    }

    public static HashMap<String ,Integer> splitBP(String originalBP){
      HashMap<String,Integer> output = new HashMap<>();
      String[] strArr = originalBP.split("/");

      Integer upper = Integer.parseInt(strArr[0].trim());
      Integer lower = Integer.parseInt(strArr[1].trim());

      output.put("upper" , upper);
      output.put("lower" , lower);

      return output;
    }

    public static String generatePasswordRule(){
        String rule1 = "·password must has at least 8 characters\n";
        String rule2 = "·password must has both letter and number.\n";
        String rule3 = "·password must include at least one uppercase letter\n";
        String rule4 = "·password must contain speacial character (!@#$%^&*...)\n";
        return rule1+rule2+rule3+rule4;
    }
}
