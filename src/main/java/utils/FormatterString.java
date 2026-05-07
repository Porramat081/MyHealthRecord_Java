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
}
