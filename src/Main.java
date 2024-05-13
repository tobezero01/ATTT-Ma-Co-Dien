import java.util.HashMap;
import java.util.Map;

public class Main {
    // MẬT MÃ CAESAR
    public static String caesarCipherEncrypt(String plaintext, int key) {
        // Khai báo một StringBuilder để xây dựng chuỗi kết quả
        StringBuilder ciphertext = new StringBuilder();
        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) { // kiểm tra chữ cái
                char tempChar = (char) (ch + key); // Thực hiện dịch chuyển ký tự theo khóa
                if (Character.isLowerCase(ch)) { // Xử lý trường hợp chữ cái là chữ thường
                    // Đảm bảo ký tự mới nằm trong phạm vi 'a' đến 'z'
                    if (tempChar > 'z') tempChar -= 26;
                    else if (tempChar < 'a') tempChar += 26;
                }
                else if (Character.isUpperCase(ch)) { // Xử lý trường hợp chữ cái là chữ hoa
                    // Đảm bảo ký tự mới nằm trong phạm vi 'A' đến 'Z'
                    if (tempChar > 'Z') tempChar -= 26;
                    else if (tempChar < 'A') tempChar += 26;
                }
                ciphertext.append(tempChar);
            } else {
                ciphertext.append(ch);
            }
        }
        // Trả về chuỗi kết quả sau khi đã mã hóa
        return ciphertext.toString();
    }
    public static String caesarCipherDecrypt(String ciphertext, int key) {
        StringBuilder plaintext = new StringBuilder();
        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {
                char tempChar = (char) (ch - key); // Thực hiện dịch chuyển ngược lại ký tự theo khóa
                if (Character.isLowerCase(ch)) {
                    if (tempChar < 'a') tempChar += 26;
                    else if (tempChar > 'z') tempChar -= 26;
                }
                else if (Character.isUpperCase(ch)) {
                    if (tempChar < 'A') tempChar += 26;
                    else if (tempChar > 'Z') tempChar -= 26;
                }
                plaintext.append(tempChar);
            } else {
                plaintext.append(ch);
            }
        }
        // Trả về chuỗi kết quả sau khi đã giải mã
        return plaintext.toString();
    }


    //MẬT MÃ VIGENERE – LẶP KHÓA
    public static String vigenereCipher(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        int keyLength = key.length();
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % keyLength); // Lặp lại chuỗi khóa khi cần thiết

            int shift = keyChar - 'A'; // Xác định số lượng dịch chuyển dựa trên ký tự trong chuỗi khóa
            char encryptedChar = (char) (((plainChar - 'A' + shift) % 26) + 'A'); //  chuyển đổi
            ciphertext.append(encryptedChar);
        }
        return ciphertext.toString();
    }

    // MẬT MÃ VIGENERE – LẶP KHÓA - Giải mã
    public static String vigenereDecipher(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        int keyLength = key.length();
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % keyLength); // Lặp lại chuỗi khóa khi cần thiết

            int shift = keyChar - 'A'; // Xác định số lượng dịch chuyển dựa trên ký tự trong chuỗi khóa
            char decryptedChar = (char) (((cipherChar - 'A' - shift + 26) % 26) + 'A'); // Giải mã
            plaintext.append(decryptedChar);
        }
        return plaintext.toString();
    }


    //MẬT MÃ VIGENERE – AUTOKEY
    public static String vigenereCipherAutokey(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        int keyLength = key.length();
        StringBuilder fullKey = new StringBuilder(key);

        // Tạo khóa đầy đủ bằng cách nối khóa đã cho với văn bản gốc
        while (fullKey.length() < plaintext.length()) {
            fullKey.append(plaintext.charAt(fullKey.length() - keyLength));
        }
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = fullKey.charAt(i);

            int shift = keyChar - 'a'; // Xác định số lượng dịch chuyển dựa trên ký tự trong chuỗi khóa
            char encryptedChar = (char) (((plainChar - 'a' + shift) % 26) + 'a'); // Thực hiện phép dịch chuyển
            ciphertext.append(encryptedChar);
        }
        return ciphertext.toString();
    }

    // MẬT MÃ VIGENERE – AUTOKEY - Giải mã
    public static String vigenereDecipherAutokey(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        int keyLength = key.length();
        StringBuilder fullKey = new StringBuilder(key);

        // Tạo khóa đầy đủ bằng cách nối khóa đã cho với văn bản đã giải mã
        while (fullKey.length() < ciphertext.length()) {
            fullKey.append(ciphertext.charAt(fullKey.length() - keyLength));
        }
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = fullKey.charAt(i);

            int shift = keyChar - 'a'; // Xác định số lượng dịch chuyển dựa trên ký tự trong chuỗi khóa
            char decryptedChar = (char) (((cipherChar - 'a' - shift + 26) % 26) + 'a'); // Giải mã
            plaintext.append(decryptedChar);
        }
        return plaintext.toString();
    }


    // Mật mã Monoalphabetic -- Mã đơn bảng chữ
    public static String monoalphabeticCipher(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        HashMap<Character, Character> map = new HashMap<>();

        // Tạo bảng ánh xạ từ các ký tự trong bảng chữ cái tiếng Anh sang các ký tự trong khóa
        //A -> K, B -> G, C -> O, D -> X, E -> P, F -> M, G -> U, H -> H, I -> C, J -> A, K -> Y,
        //L -> T, M -> J, N -> Q, O -> W, P -> Z, Q -> R, R -> I, S -> V, T -> E, U -> S, V -> F,
        //W -> L, X -> D, Y -> N, Z -> B
        for (int i = 0; i < 26; i++) {
            map.put((char) ('A' + i), key.charAt(i));
            //map.put((char) ('a' + i), key.charAt(i));
        }

        // Mã hóa từng ký tự trong văn bản gốc
        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) {
                ciphertext.append(map.get(ch));
            } else {
                ciphertext.append(ch);
            }
        }
        return ciphertext.toString();
    }

    // Mật mã Monoalphabetic - Giải mã
    public static String monoalphabeticDecipher(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        HashMap<Character, Character> map = new HashMap<>();

        // Tạo bảng ánh xạ từ các ký tự trong khóa sang các ký tự trong bảng chữ cái tiếng Anh
        for (int i = 0; i < 26; i++) {
            map.put(key.charAt(i), (char) ('A' + i));
            //map.put(Character.toLowerCase(key.charAt(i)), (char) ('a' + i));
        }

        // Giải mã từng ký tự trong văn bản mã hóa
        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {
                plaintext.append(map.get(ch));
            } else {
                plaintext.append(ch);
            }
        }
        return plaintext.toString();
    }



    //// Playfair
    public static char[][] initializeKeyMatrix(String key) {
        char[][] keyMatrix = new char[5][5];
        String keyWithoutDuplicates = removeDuplicates(key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        int i = 0, j = 0;
        for (char c : keyWithoutDuplicates.toCharArray()) {
            if (j == 5) {
                j = 0;
                i++;
            }
            if (i == 5)
                break;
            if (c == 'J') // In Playfair, I and J are typically treated as the same letter
                continue;
            keyMatrix[i][j] = c;
            j++;
        }
        return keyMatrix;
    }

    public static String removeDuplicates(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (!result.toString().contains(String.valueOf(str.charAt(i)))) {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    public static String encrypt(String plaintext, char[][] keyMatrix) {
        StringBuilder ciphertext = new StringBuilder();
        String[] digraphs = generateDigraphs(plaintext.toUpperCase());
        for (String digraph : digraphs) {
            int[] pos1 = findPosition(digraph.charAt(0), keyMatrix);
            int[] pos2 = findPosition(digraph.charAt(1), keyMatrix);
            char encryptedChar1, encryptedChar2;
            if (pos1[0] == pos2[0]) { // gióng hàng
                encryptedChar1 = keyMatrix[pos1[0]][(pos1[1] + 1) % 5];
                encryptedChar2 = keyMatrix[pos2[0]][(pos2[1] + 1) % 5];
            } else if (pos1[1] == pos2[1]) { // cột
                encryptedChar1 = keyMatrix[(pos1[0] + 1) % 5][pos1[1]];
                encryptedChar2 = keyMatrix[(pos2[0] + 1) % 5][pos2[1]];
            } else {
                encryptedChar1 = keyMatrix[pos1[0]][pos2[1]];
                encryptedChar2 = keyMatrix[pos2[0]][pos1[1]];
            }
            ciphertext.append(encryptedChar1).append(encryptedChar2);
        }
        return ciphertext.toString();
    }

    public static String[] generateDigraphs(String input) {
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length() - 1; i += 2) {
            if (sb.charAt(i) == sb.charAt(i + 1)) {
                sb.insert(i + 1, 'X');
            }
        }
        if (sb.length() % 2 != 0) {
            sb.append('X');
        }
        String[] digraphs = new String[sb.length() / 2];
        for (int i = 0; i < sb.length(); i += 2) {
            digraphs[i / 2] = sb.substring(i, i + 2);
        }
        return digraphs;
    }
    public static int[] findPosition(char c, char[][] keyMatrix) {
        int[] position = new int[2];
        for (int i = 0; i < keyMatrix.length; i++) {
            for (int j = 0; j < keyMatrix[i].length; j++) {
                if (keyMatrix[i][j] == c) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    public static void printMatrix(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    ///Mật mã Hoán vị (Rail Fence)
    public static String railFenceCipherEncrypt(String plaintext, int key) {
        char[][] rail = new char[key][plaintext.length()];

        // Khởi tạo các ô trong mảng với dấu cách
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < plaintext.length(); j++) {
                rail[i][j] = ' ';
            }
        }

        // Biến xác định hướng di chuyển của dây ray
        boolean down = false;
        int row = 0, col = 0;

        // Điền văn bản vào mảng theo dạng mật mã hoán vị
        for (int i = 0; i < plaintext.length(); i++) {
            if (row == 0 || row == key - 1) {
                down = !down;
            }

            rail[row][col] = plaintext.charAt(i); col++;

            row += (down) ? 1 : -1;

        }

        // Kết hợp các ký tự từ mảng thành chuỗi mã hóa
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < plaintext.length(); j++) {
                if (rail[i][j] != ' ') {
                    ciphertext.append(rail[i][j]);
                }
            }
        }

        return ciphertext.toString();
    }


    public static void main(String[] args) {

        // caesar
        String ciphertextCaesar = caesarCipherEncrypt("STILLWATERSRUNDE", 25);

        System.out.println("Ciphertext Caesar: " + ciphertextCaesar);

        //MẬT MÃ VIGENERE – LẶP KHÓA

        String ciphertextVigenere = vigenereCipher("PRACTICEMAKESP", "TWOCANP");
        System.out.println("ciphertextVigenere : " + ciphertextVigenere);

        //MẬT MÃ VIGENERE – AUTOKEY
//        String plaintext = "wearediscoveredsaveyourself";
//        String key = "deceptive";
//        String ciphertext = vigenereCipherAutokey(plaintext, key);
//        System.out.println("Ciphertext: " + ciphertext);




        //// Mật mã Monoalphabetic -- Mã đơn bảng chữ
//        String plaintext = "TIMEISMONEYTIMEI";
//        String key = "KGOXPMUHCAYTJQWZRIVESFLDNB";
//
//        // Mã hóa văn bản gốc
//        String ciphertext = monoalphabeticCipher(plaintext, key);
//
//        // In ra kết quả mã hóa
//        System.out.println("Ciphertext: " + ciphertext);


        // Playfair
        char[][] matrix = initializeKeyMatrix("beautyis".toUpperCase());
        printMatrix(matrix);
        int [] x = findPosition('L', matrix);
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
        String ciphertext = encrypt("ALLWORKANDNOP", matrix);
        System.out.println(ciphertext);
        //...


        //Mật mã Hoán vị (Rail Fence)





    }
}