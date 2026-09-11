import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class ShoppingCart {
    private final Customer currentCustomer;
    private final List<CartItem> cartItems;
    private final InventoryManager inventoryManager;
    private final Scanner scanner;
    
    public ShoppingCart(Customer customer, InventoryManager inventoryManager) {
        this.currentCustomer = customer;
        this.cartItems = new ArrayList<>();
        this.inventoryManager = inventoryManager;
        this.scanner = new Scanner(System.in);
    }
    
    public void showCartMenu() {
        int choice;
        do {
            System.out.println("\n==== Shopping Cart ====");
            System.out.println("1. View cart");
            System.out.println("2. Add product to cart");
            System.out.println("3. Remove product from cart");
            System.out.println("4. Update product quantity");
            System.out.println("5. Checkout");
            System.out.println("6. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> viewCart();
                case 2 -> addToCartMenu();
                case 3 -> removeFromCartMenu();
                case 4 -> updateQuantityMenu();
                case 5 -> checkout(currentCustomer);
                case 6 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }
    
    private void addToCartMenu() {
        System.out.println("\n==== Add to Cart ====");
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        Product product = inventoryManager.findByName(productName);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        
        System.out.println("Available quantity: " + product.getQuantity());
        System.out.print("Enter quantity to add: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        
        addToCart(product, quantity);
    }
    
    private void removeFromCartMenu() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }
        
        viewCart();
        System.out.print("\nEnter product name to remove: ");
        String productName = scanner.nextLine();
        Product product = inventoryManager.findByName(productName);
        
        if (product != null) {
            removeFromCart(product);
        } else {
            System.out.println("Product not found!");
        }
    }
    
    private void updateQuantityMenu() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }
        
        viewCart();
        System.out.print("\nEnter product name to update: ");
        String productName = scanner.nextLine();
        Product product = inventoryManager.findByName(productName);
        
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        
        CartItem cartItem = findCartItemByProduct(product);
        if (cartItem == null) {
            System.out.println("Product not in cart!");
            return;
        }
        
        System.out.print("Enter new quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();
        
        if (newQuantity <= 0) {
            removeFromCart(product);
        } else {
            if (newQuantity > cartItem.getProduct().getQuantity()) {
                System.out.println("Not enough stock! Available: " + cartItem.getProduct().getQuantity());
                return;
            }
            cartItem.setQuantity(newQuantity);
            System.out.println("Quantity updated.");
        }
    }
    
    public void addToCart(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            System.out.println("Invalid product or quantity!");
            return;
        }
        
        CartItem existingItem = findCartItemByProduct(product);
        int alreadyInCart = (existingItem != null) ? existingItem.getQuantity() : 0;
    
        if (quantity + alreadyInCart > product.getQuantity()) {
            System.out.println("Not enough stock! Available: " + product.getQuantity() + 
                            ", already in cart: " + alreadyInCart);
            return;
        }
    
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            cartItems.add(new CartItem(product, quantity));
        }
        System.out.printf("Added %d x %s to cart.\n", quantity, product.getProductName());
    }
    
    public void removeFromCart(Product product) {
        CartItem itemToRemove = findCartItemByProduct(product);
        if (itemToRemove != null) {
            cartItems.remove(itemToRemove);
            System.out.println("Removed " + product.getProductName() + " from cart.");
        } else {
            System.out.println("Product not in cart.");
        }
    }
    
    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }
        
        System.out.println("\n==== Your Shopping Cart ====");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s %-10s %-10s %-10s\n", 
                        "Product", "Quantity", "Price", "Total");
        System.out.println("--------------------------------------------------");
        
        for (CartItem item : cartItems) {
            Product p = item.getProduct();
            System.out.printf("%-20s %-10d RM%-9.2f RM%-9.2f\n",
                            p.getProductName(),
                            item.getQuantity(),
                            p.getPrice(),
                            item.getTotalPrice());
        }
        
        System.out.println("--------------------------------------------------");
        System.out.printf("%42s RM%.2f\n", "Total:", getTotal());
    }
    
    private CartItem findCartItemByProduct(Product product) {
        return cartItems.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);
    }
    
    public double getTotal() {
        return cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
    
    public void checkout(Customer customer) {
        if (cartItems.isEmpty()) {
            System.out.println("\nShopping cart is empty!");
            return;
        }
        
        System.out.println("\n==== Check Out ====");
        System.out.println("1. Cash Payment");
        System.out.println("2. Credit Card Payment");
        System.out.println("3. Bank Transfer");
        System.out.print("Choose payment method (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        PaymentMethod payment = null;
        switch (choice) {
            case 1 -> {
                System.out.print("Enter paid amount: RM");
                double paidAmount = scanner.nextDouble();
                payment = new CashPayment(getTotal(), paidAmount);
            }
            case 2 -> {
                System.out.print("Card number: ");
                String cardNo = scanner.nextLine();
                System.out.print("Cardholder name: ");
                String name = scanner.nextLine();
                System.out.print("Expiry date: ");
                String expDate = scanner.nextLine();
                System.out.print("CVV: ");
                String cvv = scanner.nextLine();
                payment = new CardPayment(getTotal(), cardNo, name, expDate, cvv);
            }
            case 3 -> {
                System.out.print("Bank name: ");
                String bankName = scanner.nextLine();
                System.out.print("Account number: ");
                int accNum = scanner.nextInt();
                payment = new BankTransfer(getTotal(), bankName, accNum);
            }
            default -> System.out.println("Invalid choice!");
        }
        
        if (payment != null) {
            CheckoutService checkout = new CheckoutService();
            Order order = checkout.processCheckout(customer, this, payment);
            customer.addOrder(order);
            for (CartItem item : cartItems){
                Product p = item.getProduct();
                int proQuantity = p.getQuantity();
                int itemQuantity = item.getQuantity();
                int quantity = proQuantity - itemQuantity;
                inventoryManager.updateStock(p.getProductName(), quantity);                
            }
            inventoryManager.saveToFile("products.txt");
            cartItems.clear();

        }
    }
    
    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }
}