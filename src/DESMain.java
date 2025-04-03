public class DESMain {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Mã hóa DES ===");
            System.out.println("1. Mã hóa DES");
            System.out.println("2. Giải mã DES");
            System.out.println("3. Tạo khóa DES");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại.");
                continue;
            }

            switch (choice) {
                case 1:
                    showEncryptionMenu(scanner);
                    break;
                case 2:
                    showDecryptionMenu(scanner);
                    break;
                case 3:
                    generateKey(scanner);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại.");
            }
        }
        scanner.close();
    }
    
    private static void showEncryptionMenu(java.util.Scanner scanner) {
        System.out.println("\n=== MÃ HÓA DES ===");
        String message = getInput(scanner, "Nhập M (16 ký tự hex): ", 16);
        String key = getInput(scanner, "Nhập K (16 ký tự hex): ", 16);

        if (message == null || key == null) return;
        System.out.println("1. Hiển thị chi tiết từng bước");
        System.out.println("2. Chỉ hiển thị kết quả cuối cùng");
        System.out.println("0. Quay lại");
        System.out.print("Lựa chọn của bạn: ");
        
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Lựa chọn không hợp lệ.");
            return;
        }
        
        switch (choice) {
            case 1:
                DES.encryptWithDetails(message, key);
                break;
            case 2:
                String result = DES.encrypt(message, key);
                System.out.println("\nKết quả mã hóa:");
                System.out.println("C = " + result);
                break;
            case 0:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
        }
    }
    
    private static void showDecryptionMenu(java.util.Scanner scanner) {
        System.out.println("\n=== GIẢI MÃ DES ===");
        String ciphertext = getInput(scanner, "Nhập C (16 ký tự hex): ", 16);
        String key = getInput(scanner, "Nhập K (16 ký tự hex): ", 16);

        System.out.println("1. Hiển thị chi tiết từng bước");
        System.out.println("2. Chỉ hiển thị kết quả cuối cùng");
        System.out.println("0. Quay lại");
        System.out.print("Lựa chọn của bạn: ");
        
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Lựa chọn không hợp lệ.");
            return;
        }
        
        switch (choice) {
            case 1:
                DES.decryptWithDetails(ciphertext, key);
                break;
            case 2:
                String result = DES.decrypt(ciphertext, key);
                System.out.println("\nKết quả giải mã:");
                System.out.println("M = " + result);
                break;
            case 0:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
        }
    }

    
    private static void generateKey(java.util.Scanner scanner) {
        String masterKey = getInput(scanner, "Nhập khóa K (16 ký tự hex): ", 16);
        
        if (masterKey == null) return;
        
        System.out.println("\n=== KHÓA PHỤ ĐÃ ĐƯỢC TẠO ===");
        DESKey desKey = new DESKey();
        String[] subkeys = desKey.generateSubkeys(masterKey);
        
        System.out.println("Khóa chính K = " + masterKey);
        for (int i = 1; i <= 16; i++) {
            System.out.println("K" + i + " = " + binaryToHex(subkeys[i]));
        }
    }

    private static String getInput(java.util.Scanner scanner, String prompt, int expectedLength) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        if (input.length() != expectedLength) {
            System.out.println("Lỗi: Dữ liệu phải có độ dài " + expectedLength + " ký tự hex");
            return null;
        }

        if (!input.matches("[0-9A-Fa-f]+")) {
            System.out.println("Lỗi: Dữ liệu phải ở dạng hex (0-9, A-F)");
            return null;
        }
        
        return input;
    }
    
    private static String binaryToHex(String binary) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 4) {
            int decimal = Integer.parseInt(binary.substring(i, Math.min(i + 4, binary.length())), 2);
            hex.append(Integer.toHexString(decimal).toUpperCase());
        }
        return hex.toString();
    }
}
