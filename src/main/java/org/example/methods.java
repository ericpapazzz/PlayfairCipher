package org.example;

public class methods {
    // This method is used to get the next unused character from the alphabet, excluding 'j'
    static char alphabetFiller(char[][] matrix) {
        char nextChar = 'a';
        while (isCharUsed(matrix, nextChar)) {
            nextChar++;
            if (nextChar=='j'){
                nextChar++;
            }
        }
        return nextChar;
    }

    // This method checks if a character is already used in the matrix
    static boolean isCharUsed(char[][] matrix, char ch) {
        for (char[] row : matrix) {
            for (char c : row) {
                if (c == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    // This method gets the position of a char in the matrix
    static int[] findCharPos(char[][] matrix, char ch) {
        int[] position = new int[2];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == ch) {
                    position[0] = i; // row
                    position[1] = j; // column
                    return position;
                }
            }
        }

        return null; // Character not found in the matrix
    }

    // Remove letters from the keyword
    static String fixKey(String key){
        StringBuilder fixedKey = new StringBuilder();
        char keyChar;
        for (int i = 0; i < key.length(); i++) {
            keyChar = key.charAt(i);
            // Check if the character is not already in the result
            if ((fixedKey.indexOf(String.valueOf(keyChar)) == -1)) {
                // Replace 'j' with 'i'
                if (key.charAt(i) == 'j') {
                    fixedKey.append('i');
                } else {
                    fixedKey.append(keyChar);
                }
            }
        }
        return fixedKey.toString();
    }
}
