package me.simon.commands.util;

public class TextFormatter {
    public String formatString(String text){
        String output = "";
        char[] textArray = text.toCharArray();

        int[] markers = new int[text.replaceAll("[^&]+", "").length() + 2];

        int j = 1;
        for(int i = 0; i < textArray.length; i++){
            if(textArray[i] == '&'){
                if((i == 0 || textArray[i -1] != '\\') && textArray.length > i + 1){
                    markers[j] = i;
                }
                j++;
            }
        }
        markers[0] = 0;
        markers[markers.length - 1] = text.length();
        for(int i = 0; i < markers.length - 1; i++){
            output = output.concat(getFormattedPart(text, markers[i], markers[i + 1]));
        }


        return output;
    }

    private String getFormattedPart(String text, int firstIndex, int lastIndex){
        String outputString = text.substring(firstIndex, lastIndex);
        return setParagraphs(outputString);
    }

    private String setParagraphs(String string){
        StringBuilder builder = new StringBuilder(string);
        for(int i = 0; i < builder.length(); i++){
            if(builder.charAt(i) == '&'){
                if(i == 0 || builder.charAt(i-1) != '\\'){
                    if(i != builder.length() - 1 && matches(builder.charAt(i+1))){
                        builder.setCharAt(i, '§');
                    }
                }
            }
        }
        return builder.toString();
    }

    private boolean matches(Character c){
        return "b0931825467adcfeklmnor".contains(c.toString());
    }

    public static String tablistChars(String string){
       String output;
       output = string.replaceAll("#TPS", Long.toString(TickCounter.getTps()));
       return output;
    }
}
