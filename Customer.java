import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Customer extends User {
    private static int customerCounter = loadLastCustomerCounter();
    private static final String CUSTOMER_FILE = "customers.txt";
    private ShoppingCart cart;
    private CustomerProductBrowser productBrowser;
    private List<Order> orderHistory;

    public Customer(String userId, String username, String password,String phoneNumber, String email) {
        super(userId, username, password, phoneNumber, email);
        this.cart = new ShoppingCart(this, InventoryManager.getInstance());
        this.productBrowser = new CustomerProductBrowser(InventoryManager.getInstance(), this);
        this.orderHistory = new ArrayList<>();
    }

    private static int loadLastCustomerCounter() {
        int lastCounter = 1; // Default if no file exists
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("C")) {
                    String userId = line.split(",")[0]; // Assuming format: C001,username,...
                    int counter = Integer.parseInt(userId.substring(1));
                    if (counter >= lastCounter) {
                        lastCounter = counter + 1;
                    }
                }
            }
        } 
        catch (IOException e) {
            System.err.println("Error to get last customer counter!"+ e.getMessage());
        }
        return lastCounter;
    }
    
    public static Customer signUp(Scanner scanner) {
        System.out.println("\n=== CUSTOMER SIGN UP ===");
        
        String userId = "C" + String.format("%03d", customerCounter++);
        System.out.println("User ID: " + userId);
        
        String username = validateInput(scanner, 
            "Enter Username (5-16 chars with mixed case and numbers): ",
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_+-=`|/:;'<>,.]).{5,16}$",
            "Invalid format! Must contain letters, numbers, and special symbols");
        
        String password = validateInput(scanner,
            "Enter Password (8-16 chars with mixed case and numbers): ",
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_+-=`|/:;'<>,.]).{8,16}$",
            "Invalid format! Must contain lowercase, uppercase, numbers, and special symbols");

        String phone = validateInput(scanner,
            "Enter Phone Number (+60xxxxxxxxx): ",
            "^\\+60\\d{9,10}$",
            "Invalid format! Example: +60123456789").replaceAll("\\s+", "");

        String email = validateInput(scanner,
            "Enter Email (xxx@gmail.com): ",
            "^[a-zA-Z0-9]+@gmail\\.com$",
            "Invalid format! Must end with @gmail.com");

        Customer customer = new Customer(userId, username, password, phone, email);
        saveCustomerToFile(customer);
        return customer;
    }

    private static String validateInput(Scanner scanner, String prompt, String regex, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (Pattern.matches(regex, input)) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }

    public String getPassword() {
        return this.password;
    }

    private static void saveCustomerToFile(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            String data = String.format("%s,%s,%s,%s,%s",
                customer.getUserId(),
                customer.getUsername(),
                customer.getPassword(),
                customer.getPhoneNumber(),
                customer.getEmail());
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving customer data: " + e.getMessage());
        }
    }

    public void showMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. BROWSE PRODUCTS");
            System.out.println("2. SHOPPING CART");
            System.out.println("3. VIEW ORDER HISTORY");
            System.out.println("4. LOG OUT");
            System.out.print("Enter choice (1-4): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: {
                    productBrowser.browseProducts();
                    break;
                }
                case 2: {
                    cart.showCartMenu();
                    break;
                }
                case 3: {
                    viewOrderHistory();
                    break;
                }
                case 4: {
                    System.out.println("Logging out..."); return;
                }
                default: {
                    System.out.println("Invalid choice!");
                }
            } 
        }
    }
    
    public void viewOrderHistory() throws IOException{
        List<Order> loaded = loadOrderHistory(this, InventoryManager.getInstance());
        orderHistory.clear();
        orderHistory.addAll(loaded);
        
        if (orderHistory.isEmpty()){
            System.out.println("\nNot have order record.");
            return;
        }
        
        System.out.println("==== Order History ====");
        for(int i = 0; i < orderHistory.size(); i++){
            Order order = orderHistory.get(i);
            System.out.print("[" + (i + 1) + "]");
            System.out.print(" Order ID: " + order.getOrderId());
            System.out.println("Date: " + order.getOrderDate());
            System.out.printf("Total Amount: RM%.2f\n", order.getTotalAmount());           
            System.out.println("Items: " + order.getItems().size() + " products");
            System.out.println("-----------------------");
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter order number to view order details (0 to exit)");
        int choose = scanner.nextInt();
        if(choose > 0 && choose <= orderHistory.size()){
            orderHistory.get(choose - 1).printOrderDetails();
        }
    }
    
    public void addOrder(Order order){
        orderHistory.add(order);
        order.saveToFile();
    }
    
    public static List<Order> loadOrderHistory(Customer customer, InventoryManager inventory) {
        List<Order> orders = new ArrayList<>();
        Path filePath = Paths.get("order_history.txt");

        if (!Files.exists(filePath)) {
            return orders;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Order order = parseFromFileString(line, customer, inventory);
                if (order != null) {
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            System.err.println("Error read file: " + e.getMessage());
        }

        return orders;
    }

    private static Order parseFromFileString(String fileString, Customer customer, InventoryManager inventory) {
        String[] parts = fileString.split(",", 6);
        if (parts.length != 6) return null;

        try {
            String orderId = parts[0];
            String ownerId = parts[1];
            
            if (customer != null && !ownerId.equals(customer.getUserId())) {
            return null;
            }
            
            Date orderDate = new Date(Long.parseLong(parts[2]));
            String paymentType = parts[3];
            double totalAmount = Double.parseDouble(parts[4]);

            List<OrderItem> items = new ArrayList<>();
            for (String itemStr : parts[5].split(",")) {
                String[] itemParts = itemStr.split(":");
                if (itemParts.length != 3) continue;

                String productName = itemParts[0];
                int quantity = Integer.parseInt(itemParts[1]);
                double price = Double.parseDouble(itemParts[2]);

                items.add(new OrderItem(productName, quantity, price));
            }

            PaymentMethod payment = createPayment(paymentType, totalAmount);
            return new Order(orderId, customer, orderDate, items, payment, totalAmount);
        } catch (Exception e) {
            System.err.println("Error open file" + fileString);
            return null;
        }
    }

    private static PaymentMethod createPayment(String type, double amount) {
        switch (type) {
            case "CashPayment": return new CashPayment(amount, amount);
            case "CardPayment": return new CardPayment(amount, "0000", "Name", "expDate", "Cvv");
            default: return new CashPayment(amount, amount);
        }
    }
    
    public static List<Customer> loadAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) continue;
                Customer c = new Customer(parts[0], parts[1], parts[2], parts[3], parts[4]);
                customers.add(c);
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
        return customers;
    }
}
        
