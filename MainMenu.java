import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class MainMenu {
    private static ArrayList<User> users = new ArrayList<>();
    private static InventoryManager inventoryManager = InventoryManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // Create staff accounts (not stored in file)
        users.add(new ProductStaff("PS001", "prod_staff", "Prod123!", "+60111234567", "prod@store.com", inventoryManager));
        users.addAll(Customer.loadAllCustomers());
        
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. CUSTOMER");
            System.out.println("2. PRODUCT STAFF");
            System.out.println("3. EXIT");
            System.out.print("Enter choice: ");
                    
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: customerMenu(); break;
                case 2: loginStaff("ProductStaff"); break;
                case 3: 
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default: System.out.println("Invalid choice! Please enter (1-3)");
            }
        }
    }

    private static void customerMenu() throws IOException {
        while (true) {
            System.out.println("\n=== CUSTOMER ===");
            System.out.println("1. SIGN UP");
            System.out.println("2. LOGIN");
            System.out.println("3. BACK TO MAIN MENU");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: 
                    Customer newCustomer = Customer.signUp(scanner);
                    users.add(newCustomer);
                    break;
                case 2: loginCustomer(); break;
                case 3: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void loginCustomer() throws IOException {
        System.out.println("\n=== CUSTOMER LOGIN ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        for (User user : users) {
            if (user instanceof Customer && user.authenticate(userId, password)) {
                System.out.println("Login successful!");
                ((Customer)user).showMenu();
                return;
            }
        }
        System.out.println("Invalid User ID or password!");
    }

    private static void loginStaff(String staffType) {
        System.out.println("\n=== " + staffType.toUpperCase() + " LOGIN ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        for (User user : users) {
            boolean isCorrectType = (staffType.equals("ProductStaff") && user instanceof ProductStaff);
            
            if (isCorrectType && user.authenticate(userId, password)) {
                System.out.println("Login successful!");
                if (user instanceof ProductStaff) {
                    ((ProductStaff)user).showMenu();
                }
                return;
            }
        }
        System.out.println("Invalid User ID or password!");
    }
}

