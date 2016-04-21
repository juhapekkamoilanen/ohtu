package com.ihasama.ohtu.util;

public class StringUtils {

    public static String toBibFormat(String string) {
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i< string.length(); i++) {
            char c = string.charAt(i);

            switch (c) {
                case 'ä': newString.append("{\\\"a}"); break;
                case 'ö': newString.append("{\\\"o}"); break;
                case 'Ä': newString.append("{\\\"A}"); break;
                case 'Ö': newString.append("{\\\"O}"); break;
                case 'Å': newString.append("{\\\"AA}"); break;
                case 'å': newString.append("{\\\"aa}"); break;
                default: newString.append(c);
            }
        }

        return newString.toString();
    }

    public static String toNormalFormat(String string) {
        return string
                .replace("{\\\"a}", "ä")
                .replace("{\\\"A}", "Ä")
                .replace("{\\\"o}", "ö")
                .replace("{\\\"O}", "Ö")
                .replace("{\\\"AA}", "Å")
                .replace("{\\\"aa}", "å");
    }
}
