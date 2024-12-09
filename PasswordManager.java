import java.io.*;
import java.util.*;
import java.security.SecureRandom;

public class PasswordManager {
    private static final String FILE_PATH = "passwords.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static String currentUser = null;

    public static void main(String[] args) {
        while (true) {
            if (currentUser == null) {
                System.out.println("\n=== Password Manager Login ===");
                System.out.println("1. Login");
                System.out.println("2. Create New User");
                System.out.println("3. Exit");
                
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        createUser();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }
            } else {
                showMainMenu();
            }
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== Password Manager Menu ===");
            System.out.println("1. Add Account");
            System.out.println("2. View All Categories");
            System.out.println("3. View All Accounts");
            System.out.println("4. Modify Account");
            System.out.println("5. Delete Account");
            System.out.println("6. Generate Password");
            System.out.println("7. Logout");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addAccount();
                    break;
                case 2:
                    viewCategories();
                    break;
                case 3:
                    viewAccounts();
                    break;
                case 4:
                    modifyAccount();
                    break;
                case 5:
                    deleteAccount();
                    break;
                case 6:
                    System.out.println("Generated Password: " + generatePassword());
                    break;
                case 7:
                    currentUser = null;
                    return;
            }
        }
    }

    private static void createUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write("USER|" + username + "|" + password + "\n");
            System.out.println("User created successfully!");
        } catch (IOException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals("USER") && parts[1].equals(username) && parts[2].equals(password)) {
                    currentUser = username;
                    System.out.println("Login successful!");
                    return;
                }
            }
            System.out.println("Invalid credentials!");
        } catch (IOException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }
    }

    private static void addAccount() {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter website/service: ");
        String service = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password (or type 'generate' for auto-generated): ");
        String password = scanner.nextLine();
        
        if (password.equalsIgnoreCase("generate")) {
            password = generatePassword();
        }
        
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write("ACCOUNT|" + currentUser + "|" + category + "|" + service + "|" + username + "|" + password + "\n");
            System.out.println("Account added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding account: " + e.getMessage());
        }
    }

    private static void viewCategories() {
        Set<String> categories = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals("ACCOUNT") && parts[1].equals(currentUser)) {
                    categories.add(parts[2]);
                }
            }
            System.out.println("\nCategories:");
            categories.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error viewing categories: " + e.getMessage());
        }
    }

    private static void viewAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            System.out.println("\nYour Accounts:");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals("ACCOUNT") && parts[1].equals(currentUser)) {
                    System.out.printf("Category: %s, Service: %s, Username: %s, Password: %s\n",
                            parts[2], parts[3], parts[4], parts[5]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error viewing accounts: " + e.getMessage());
        }
    }

    private static void modifyAccount() {
        viewAccounts();
        System.out.print("Enter service name to modify: ");
        String service = scanner.nextLine();
        
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals("ACCOUNT") && parts[1].equals(currentUser) && parts[3].equals(service)) {
                    System.out.print("Enter new username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter new password (or type 'generate' for auto-generated): ");
                    String password = scanner.nextLine();
                    
                    if (password.equalsIgnoreCase("generate")) {
                        password = generatePassword();
                    }
                    
                    line = String.join("|", parts[0], parts[1], parts[2], parts[3], username, password);
                    found = true;
                }
                lines.add(line);
            }
            
            if (found) {
                try (FileWriter fw = new FileWriter(FILE_PATH)) {
                    for (String l : lines) {
                        fw.write(l + "\n");
                    }
                }
                System.out.println("Account modified successfully!");
            } else {
                System.out.println("Account not found!");
            }
        } catch (IOException e) {
            System.out.println("Error modifying account: " + e.getMessage());
        }
    }

    private static void deleteAccount() {
        viewAccounts();
        System.out.print("Enter service name to delete: ");
        String service = scanner.nextLine();
        
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals("ACCOUNT") && parts[1].equals(currentUser) && parts[3].equals(service)) {
                    found = true;
                    continue;
                }
                lines.add(line);
            }
            
            if (found) {
                try (FileWriter fw = new FileWriter(FILE_PATH)) {
                    for (String l : lines) {
                        fw.write(l + "\n");
                    }
                }
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Account not found!");
            }
        } catch (IOException e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }

    private static String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < 16; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
} 