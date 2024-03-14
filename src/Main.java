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
            map.put((char) ('a' + i), key.charAt(i));
        }

        // Mã hóa từng ký tự trong văn bản gốc
        for (char ch : plaintext.toCharArray()) {
            // Kiểm tra xem ký tự có trong bảng chữ cái không
            if (Character.isLetter(ch)) {
                ciphertext.append(map.get(ch));
            } else {
                // Giữ nguyên các ký tự không phải chữ cái
                ciphertext.append(ch);
            }
        }
        return ciphertext.toString();
    }



    //// Playfair



    ///Mật mã Hoán vị (Rail Fence)
    public static String railFenceCipherEncrypt(String plaintext, int key) {
        // Tạo mảng 2D để lưu trữ văn bản theo dạng mật mã hoán vị
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
        //...


        //Mật mã Hoán vị (Rail Fence)

        String plaintext = "meetmeafterthetogaparty";
        int key = 2;

        // Mã hóa văn bản gốc
        String ciphertext = railFenceCipherEncrypt(plaintext, key);

        // In ra kết quả mã hóa
        System.out.println("Ciphertext: " + ciphertext);

    }
}