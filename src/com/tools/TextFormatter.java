package com.tools;

public class TextFormatter {

    public static String formatText(String input, int lineLength) {
        if (input == null || input.isEmpty() || lineLength <= 0) {
            return "";
        }

        StringBuilder formattedText = new StringBuilder();
        int currentIndex = 0;

        while (currentIndex < input.length()) {
            int endIndex = findEndIndex(input, currentIndex, lineLength);
            formattedText.append(input.substring(currentIndex, endIndex));

            if (endIndex < input.length()) {
                formattedText.append("\n");
            }

            currentIndex = endIndex;
        }

        return formattedText.toString();
    }

    private static int findEndIndex(String input, int startIndex, int lineLength) {
        int endIndex = Math.min(startIndex + lineLength, input.length());

        if (endIndex == input.length()) {
            return endIndex;
        }

        while (endIndex > startIndex && !Character.isWhitespace(input.charAt(endIndex - 1))) {
            endIndex--;
        }

        if (endIndex == startIndex) {
            endIndex = startIndex + lineLength;
        }

        return endIndex;
    }

}
