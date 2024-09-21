package org.example;
import javax.swing.JOptionPane;

public class Main {
    // Methods

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
        int[] position = {-1, -1}; // Initialize default values in case character is not found(invalid)
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == ch) {
                    position[0] = i; // row
                    position[1] = j; // column
                    return position;
                }
            }
        }

        // If the character is not found, return the default position indicating not found
        return position;
    }

    // Remove letters from the keyword
    static String fixKey(String key){
        StringBuilder fixedKey = new StringBuilder();
        key=key.toLowerCase();
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

    static void matriz(char[][] matrix, String fixedKey){

        // Populate the matrix with characters from the key
        int k = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (k < fixedKey.length()) {
                    matrix[i][j] = fixedKey.charAt(k++);
                } else {
                    matrix[i][j] = alphabetFiller(matrix);
                }
            }
        }
    }

    public static void main(String[] args) {
        boolean quit=true;

        while (quit){

            String[] botones = {"Encrypt", "Decrypt", "Exit"};
            int ventana = JOptionPane.showOptionDialog(null,
                    "What do you want to do?",
                    "Playfair Cipher",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    botones, botones[0]);


            char[][] matrix = new char[5][5];
            boolean valid=true;

            switch (ventana){


                case 0:
                    // Recive data
                    String message = JOptionPane.showInputDialog(null, "Insert the message (only alphabetical values, ecxept 'ñ')");
                    String key = JOptionPane.showInputDialog(null, "Insert the keyword (only alphabetical values, ecxept 'ñ')");

                    String fixedKey = fixKey(key);
                // Remove spaces from message
                message = message.replaceAll("\\s", "");

                // Make everything lowercase
                message = message.toLowerCase();

                // If a letter appears twice side by side, append "x" between them
                boolean twice = false;
                for (int c = 0; c < message.length() - 1; c += 2) { // Iterates the message looking for repeated letters
                    if (message.charAt(c) == message.charAt(c + 1)) {
                        twice = true;
                        break;
                    }
                }
                if (twice) {
                    char[] charMessage = new char[message.length() * 2]; // Create an array big enough for extra letters
                    int index = 0;
                    for (int c = 0; c < message.length() - 1; c++) { // Iterates the message looking for the repeated letters
                        charMessage[index] = message.charAt(c);
                        index++;

                        if (message.charAt(c) == message.charAt(c + 1)) { //  Append x between the letters
                            charMessage[index] = 'x';
                            index++;
                        }
                    }
                    charMessage[index] = message.charAt(message.length() - 1); // Add the last character
                    message = new String(charMessage, 0, index + 1); // Create a string using only the valid part of the array
                }

                // If message odd, append "x"
                if (!(message.length() % 2 == 0)) {
                    message = message + "x";
                }
                //System.out.println(message);

                // Populate the matrix
                matriz(matrix,fixedKey);

                /*
                // Display the matrix
                for (char[] chars : matrix) {
                    for (int j = 0; j < chars.length; j++) {
                        System.out.print(chars[j] + "\t");
                    }
                    System.out.println();
                }
                */


                // Encryption logic
                char encryptyedCharOne;
                char encryptyedCharTwo;
                int firstRow;
                int secRow;
                int firstCol;
                int secCol;
                StringBuilder encryptedMessage = new StringBuilder();

                for (int c = 0; c < message.length(); c += 2) {

                    char firstChar = message.charAt(c);
                    char secChar = message.charAt(c + 1);

                    // Replace 'j' with 'i'
                    if (firstChar == 'j') {
                        firstChar = 'i';
                    }
                    if (secChar == 'j') {
                        secChar = 'i';
                    }

                    // Find the positions of the characters in the matrix
                    int[] firstCharPos = findCharPos(matrix, firstChar);
                    int[] secCharPos = findCharPos(matrix, secChar);


                    if (firstCharPos[0] == -1 || secCharPos[0] == -1) {
                        JOptionPane.showMessageDialog(null, "You inserted an invaliad character you stoopid or wat?");
                        valid=false;
                        break;
                    } else {
                        // Your existing code for accessing firstCharPos[1] and secCharPos[1]


                        // Same row case
                        if (firstCharPos[0] == secCharPos[0]) {
                            // Replace each letter with the letter to its right
                            firstCol = (firstCharPos[1] + 1) % 5;
                            secCol = (secCharPos[1] + 1) % 5;
                            encryptyedCharOne = matrix[firstCharPos[0]][firstCol];
                            encryptyedCharTwo = matrix[secCharPos[0]][secCol];

                            // Same column case
                        } else if (firstCharPos[1] == secCharPos[1]) {
                            // Replace each letter with the letter below
                            firstRow = (firstCharPos[0] + 1) % 5;
                            secRow = (secCharPos[0] + 1) % 5;
                            encryptyedCharOne = matrix[firstRow][firstCharPos[1]];
                            encryptyedCharTwo = matrix[secRow][secCharPos[1]];
                        } else {
                            // "Square" case
                            encryptyedCharOne = matrix[firstCharPos[0]][secCharPos[1]];
                            encryptyedCharTwo = matrix[secCharPos[0]][firstCharPos[1]];
                        }
                        encryptedMessage.append(encryptyedCharOne);
                        encryptedMessage.append(encryptyedCharTwo);
                    }
                }
                if (valid) {
                    System.out.println(encryptedMessage);
                    JOptionPane.showMessageDialog(null, "Your encrypted message is: " + encryptedMessage);
                }
                break;

                // Decryption
             case 1:
                 // Recive data
                  message = JOptionPane.showInputDialog(null, "Insert the message (only alphabetical values, ecxept 'ñ')");
                  key = JOptionPane.showInputDialog(null, "Insert the keyword (only alphabetical values, ecxept 'ñ')");
                  fixedKey = fixKey(key);

                // Decryption logic
                char decryptyedCharOne;
                char decryptyedCharTwo;
                StringBuilder decryptedMessage = new StringBuilder();

                //populate the matrix
                matriz(matrix,fixedKey);

                for (int c = 0; c < message.length(); c += 2) {

                    // Find the positions of the characters in the matrix
                    int[] firstCharPos = findCharPos(matrix, message.charAt(c));
                    int[] secCharPos = findCharPos(matrix, message.charAt(c + 1));

                    // Handle the case where a character is not found in the matrix
                    if (firstCharPos[0] == -1 || secCharPos[0] == -1) {
                        JOptionPane.showMessageDialog(null, "You inserted an invaliad character you stoopid or wat?");
                        valid=false;
                        break;
                    } else {

                        if (firstCharPos[0] == secCharPos[0]) {
                            // Replace each letter with the letter to its right
                            firstCol = (firstCharPos[1] - 1 + 5) % 5; // Adding 5 before applying modulo operator
                            secCol = (secCharPos[1] - 1 + 5) % 5; // Adding 5 before applying modulo operator
                            decryptyedCharOne = matrix[firstCharPos[0]][firstCol];
                            decryptyedCharTwo = matrix[secCharPos[0]][secCol];

                        } else if (firstCharPos[1] == secCharPos[1]) {
                            // Replace each letter with the letter below
                            firstRow = (firstCharPos[0] - 1 + 5) % 5; // Adding 5 before applying modulo operator
                            secRow = (secCharPos[0] - 1 + 5) % 5; // Adding 5 before applying modulo operator
                            decryptyedCharOne = matrix[firstRow][firstCharPos[1]];
                            decryptyedCharTwo = matrix[secRow][secCharPos[1]];

                        } else {
                            // "Square" case
                            decryptyedCharOne = matrix[firstCharPos[0]][secCharPos[1]];
                            decryptyedCharTwo = matrix[secCharPos[0]][firstCharPos[1]];
                        }

                        decryptedMessage.append(decryptyedCharOne);
                        decryptedMessage.append(decryptyedCharTwo);
                    }
                    if (decryptedMessage.charAt(decryptedMessage.length() - 1) == 'x') {
                        decryptedMessage.deleteCharAt(decryptedMessage.length() - 1);
                    }
                }
                if (valid) {
                    System.out.println(decryptedMessage);
                    JOptionPane.showMessageDialog(null, "Your encrypted message is: " + decryptedMessage);
                }
                break;

            case 2:
                quit=false;
                break;
            }
        }

    }
}