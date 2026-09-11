import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class CustomerProductBrowser {
    private InventoryManager inventoryManager;
    private Scanner scanner;
    private Customer currentCustomer;
    
    public CustomerProductBrowser(InventoryManager inventoryManager, Customer customer){
        this.currentCustomer = customer;
        this.inventoryManager = inventoryManager;
        this.scanner = new Scanner (System.in);
    }
    
    public void browseProducts() throws IOException{
        int choice;
        
        do{
            System.out.println("\n===== Product Browsing =====");
            System.out.println("1. View all products");
            System.out.println("2. Search product by name");
            System.out.println("3. Search by brand");
            System.out.println("4. Search by model");
            System.out.println("5. Search by price range");
            System.out.println("6. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch(choice){
                case 1:
                    System.out.println("\n=== All Products ===");
                    inventoryManager.listAllProducts();
                    break;
                
                case 2:
                    searchByName();
                    break;
                    
                case 3:
                    searchByBrand();
                    break;
                    
                case 4:
                    searchByModel();
                    break;
                    
                case 5:
                    searchByPriceRange();
                    break;
                    
                case 6:
                    System.out.println("Returning to customer menu...");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
                
            }
        }while (choice != 6);
    }
    
    private void searchByName(){
        System.out.print("\nEnter product name to search: ");
        String searchName = scanner.nextLine();
        Product found = inventoryManager.findByName(searchName);
        
        if (found != null){
            System.out.println("\nProduct found:");
            System.out.println(found);
        } else{
            System.out.println("Product not found.");
        }
    }
    
    private void searchByBrand(){
        System.out.print("\nEnter brand name to search: ");
        String brand = scanner.nextLine();
        List<Product> results = new ArrayList<>();
        
        for (Product p : inventoryManager.getAllProducts()){
            if (p.getBrand().equalsIgnoreCase(brand)){
                results.add(p);
            }
        }
        displaySearchResults(results, " brand " + brand);
    }
    
    private void searchByModel(){
        System.out.print("\nEnter model to search: ");
        String model = scanner.nextLine();
        List<Product> results = new ArrayList<>();
        
        for (Product p : inventoryManager.getAllProducts()){
            if (p.getModel().equalsIgnoreCase(model)){
                results.add(p);
            }
        }
        
        displaySearchResults(results, " model " + model);
    }
    
    private void searchByPriceRange(){
        System.out.print("\nEnter minimum price: RM ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: RM ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine();
        
        if (minPrice > maxPrice) {
            System.out.println("Minimum price cannot be greater than maximum price!");
            return;
        }
        
        List<Product> results = new ArrayList<>();
        
        for (Product p : inventoryManager.getAllProducts()){
            if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice){
                results.add(p);
            }
        }
        displaySearchResults(results, String.format(" price range RM%.2f to RM%.2f", minPrice, maxPrice));
    }
    
    private void displaySearchResults(List<Product> results, String searchCriteria){
        if (results.isEmpty()){
            System.out.println("No products found with " + searchCriteria + ".");
        } else {
            System.out.println("\nFound " + results.size() + " products(s) with " + searchCriteria + ".");
        }
            for (Product p : results){
                System.out.println(p);
        }
    }
}
