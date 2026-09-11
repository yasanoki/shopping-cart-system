import java.util.Scanner;

public class ProductStaff extends User {
    private InventoryManager inventoryManager;
    
    public ProductStaff(String userId, String username, String password,String phoneNumber, String email, InventoryManager inventoryManager) {
        super(userId, username, password, phoneNumber, email);
        this.inventoryManager = inventoryManager;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== PRODUCT STAFF MENU ===");
            System.out.println("1. MANAGE STOCK");
            System.out.println("2. LOG OUT");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: manageStockMenu(); 
                break;
                case 2: {System.out.println("Logging out..."); 
                return;}
                default: System.out.println("Invalid choice!");
            }
        }
    }
    
    private void manageStockMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do{
            System.out.println("\n==== MANAGE STOCK ====");
            System.out.println("1. Add Product");
            System.out.println("2. List All Products");
            System.out.println("3. Search Product");
            System.out.println("4. Add Stock");
            System.out.println("5. Update Stock");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    addProductMenu();
                    break;
                case 2:
                    listAllProducts();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    addStock();
                    break;
                case 5:
                    updateStock();
                    break;
                case 6:
                    showMenu();
                    break;
                
                default:
                    System.out.println("Invalid choice! Please enter (1-6)");
            }
        }while (choice!=6);
    }
    
    private void addProductMenu(){
        Scanner scanner = new Scanner (System.in);
        System.out.println("\n==== Add Product ====");
        System.out.println("1. Keyboard");
        System.out.println("2. Mouse");
        System.out.println("3. Monitor");
        System.out.println("4. Controller");
        System.out.print("Enter product type (1-4): ");

        int typeChoice = scanner.nextInt();
        scanner.nextLine();
        
        String pName = "";
        switch(typeChoice){
            case 1: pName = "Keyboard"; break;
            case 2: pName = "Mouse"; break;
            case 3: pName = "Monitor"; break;
            case 4: pName = "Controller"; break;
            default:
                System.out.println("Invalid type! Please enter (1-4)");
                return;
        }
        
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Price: RM");
        double price = scanner.nextDouble();
        scanner.nextLine();
        
        Product product = null;
        
        switch(typeChoice){
            case 1:
                System.out.print("Number of key: ");
                int keys = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Connection Type: ");
                String kConn = scanner.nextLine();
                System.out.print("Colour: ");
                String kColor = scanner.nextLine();
                product = new Keyboard(pName, brand, model, quantity, price, keys, kConn, kColor);
                break;
                
            case 2:
                System.out.print("Connection Type: ");
                String mConn = scanner.nextLine();
                System.out.print("Number of key: ");
                int mButton = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Colour: ");
                String mColor = scanner.nextLine();
                product = new Mouse(pName, brand, model, quantity, price,  mConn, mButton, mColor);
                break;

            case 3:
                System.out.print("Screnn Size(Inch): ");
                double size = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Resolution Type: ");
                String res = scanner.nextLine();
                System.out.print("Light Source: ");
                String light = scanner.nextLine();
                product = new Monitor(pName, brand, model, quantity, price, size, res, light);
                break;

            case 4:
                System.out.print("Controller Type: ");
                String ctrlType = scanner.nextLine();
                System.out.print("Number of button: ");
                int btnCount = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Connection Type: ");
                String connType = scanner.nextLine();
                product = new Controller(pName, brand, model, quantity, price, ctrlType, btnCount, connType);
                break;    
                
            default:
                System.out.println("Invalid input!");
                break;
        }
        if (product != null){
            inventoryManager.addProduct(product);
            inventoryManager.saveToFile("products.txt");
            System.out.println("Product added succesful!");
        }
    }
    
    private void listAllProducts(){
        System.out.println("\n==== All Products ====");
        inventoryManager.listAllProducts();
    }
    
    private void searchProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("===Search Product===\n");
        System.out.print("Enter the product type: ");
        String searchName = scanner.nextLine();
        Product found = inventoryManager.findByName(searchName);
        if (found != null){
            System.out.println("Searching product: ");
            System.out.println(found);
        }else{
             System.out.println("Dont have this product.");
        }
    }
    
    
    private void addStock(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("===Add Stock===\n");
        System.out.print("Enter the product name: ");
        String stockName = scanner.nextLine();
        System.out.print("Enter the quantity: ");
        int stockAmount = scanner.nextInt();
        scanner.nextLine();
        inventoryManager.addStock(stockName, stockAmount);
        inventoryManager.saveToFile("products.txt");
    }

    private void updateStock(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("===Update Stock===");
        System.out.print("\nEnter the product name: ");
        String updateName = scanner.nextLine();
        System.out.print("Enter the quantity: ");
        int updateAmount = scanner.nextInt();
        scanner.nextLine();
        inventoryManager.updateStock(updateName, updateAmount);
        inventoryManager.saveToFile("products.txt");
    }
}
