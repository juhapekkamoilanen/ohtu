package com.ihasama.ohtu.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtils {

    private static Map<String, Character> conversionsFromBibToNormal = new HashMap<String, Character>(){
        {
            put("(\\{\\\\\"a\\})|(\\\\\"\\{a\\})", 'ä');
            put("(\\{\\\\\"A\\})|(\\\\\"\\{A\\})", 'Ä');
            put("(\\{\\\\\"o\\})|(\\\\\"\\{o\\})", 'ö');
            put("(\\{\\\\\"O\\})|(\\\\\"\\{O\\})", 'Ö');
            put("(\\{\\\\\"aa\\})|(\\\\\"\\{aa\\})", 'å');
            put("(\\{\\\\\"AA\\})|(\\\\\"\\{AA\\})", 'Å');
        }
    };

    private static Map<Character, String> conversionsFromNormalToBib = new HashMap<Character, String>(){
        {
            put('ä', "{\\\"a}");
            put('Ä', "{\\\"A}");
            put('ö', "{\\\"o}");
            put('Ö', "{\\\"O}");
            put('å', "{\\\"aa}");
            put('Å', "{\\\"AA}");
        }
    };

    public static String toBibFormat(String string) {
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (conversionsFromNormalToBib.containsKey(c)) {
                newString.append(conversionsFromNormalToBib.get(c));
            } else {
                newString.append(c);
            }
        }

        return newString.toString();
    }

    public static String toNormalFormat(String string) {
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (c == '{' || c == '\\') {
                StringBuilder stringToMatch = new StringBuilder();

                for (; i < string.length(); i++) {
                    stringToMatch.append(string.charAt(i));

                    if (string.charAt(i) == '}') {
                        Character match = findMatch(stringToMatch);

                        if (match != null) {
                            newString.append(match);
                            break;
                        }
                    }
                }
            } else {
                newString.append(c);
            }
        }

        return newString.toString();
    }

    private static Character findMatch(StringBuilder stringToMatch) {
        for (String key : conversionsFromBibToNormal.keySet()) {
            if (Pattern.matches(key, stringToMatch.toString())) {
                return conversionsFromBibToNormal.get(key);
            }
        }

        return null;
    }
}
