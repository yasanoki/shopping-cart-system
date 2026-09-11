import java.util.ArrayList;
import java.util.List; 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class InventoryManager {
    private static InventoryManager instance;
    private List<Product> products;
    
    public InventoryManager(){
        products = new ArrayList<>();
        loadFromFile("products.txt");
    }

    public static InventoryManager getInstance(){
        if (instance == null){
            instance = new InventoryManager();
        }
        return instance;
    }
    
    public ArrayList<Product> getProducts(){
        return (ArrayList<Product>) products;
    }
    
    public void addProduct(Product product){
        products.add(product);
        System.out.println("Product finish add on!");
    }
    
    public void listAllProducts(){
        if (products.isEmpty()){
            System.out.println("Dont have any product!");
        }
        else{
            for (Product p : products){
                System.out.println(p.toString());
            }
        }
    }

    public Product findByName(String name){
        for (Product p : products){
            if (p.getProductName().equalsIgnoreCase(name)){
                return p;
            }
        }
        return null;
    }
    
    public void removeByName(String name){
        Product p = findByName(name);
        if (p != null){
            products.remove(p);
            System.out.println("Deleted product: " + name);
        }else{
            System.out.println("Dont have this product.");
        }
    }
    
    public void addStock(String name, int amount){
        Product p = findByName(name);
        if (p != null){
            p.setQuantity(p.getQuantity() + amount);
            System.out.println("Stock updated, quantity: " + p.getQuantity());
        }else{
            System.out.println("Dont have this product.");
        }
    }

    public void updateStock(String productName, int quantity){
        boolean found = false;
        
        for (Product p : products){
            if(p.getProductName().equalsIgnoreCase(productName)){
                found = true;
                if (p.getQuantity() >= quantity){
                    p.setQuantity(quantity);
                    System.out.println("Inventory reduced, remaining quantity: " + p.getQuantity());
                }else{
                    System.out.println("Out of stock, current stock: " + p.getQuantity());
                }
                break;
            }    
        }
        if (!found){
            System.out.println("Product not found: " + productName);
        }
    }
    
    public void saveToFile(String filename){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for(Product p : products){
                if(p instanceof Keyboard){
                    Keyboard k = (Keyboard) p;
                    writer.write("Keyboard\t" + k.getProductName() + "\t" + k.getBrand() + "\t" + k.getModel() + "\t" + k.getQuantity() + "\t" + k.getPrice() + "\t" + k.getNumberOfKeys() + "\t" + k.getConnectionType() + "\t" + k.getColour());
                }else if(p instanceof Mouse){
                    Mouse m = (Mouse) p;
                    writer.write("Mouse\t" + m.getProductName() + "\t" + m.getBrand() + "\t" + m.getModel() + "\t" + m.getQuantity() + "\t" + m.getPrice() + "\t" + m.getConnectionType() + "\t" + m.getNumberOfButton() + "\t" + m.getColour());
                }else if(p instanceof Monitor){
                    Monitor mo = (Monitor) p;
                    writer.write("Monitor\t" + mo.getProductName() + "\t" + mo.getBrand() + "\t" + mo.getModel() + "\t" + mo.getQuantity() + "\t" + mo.getPrice() + "\t" + mo.getScreenSize() + "\t" + mo.getResolutionType() + "\t" + mo.getLightSource());
                }else if(p instanceof Controller){
                    Controller c = (Controller)  p;
                    writer.write("Controller\t" + c.getProductName() + "\t" + c.getBrand() + "\t" + c.getModel() + "\t" + c.getQuantity() + "\t" + c.getQuantity() + "\t" + c.getControllerType() + "\t" + c.getNumberOfButtons() + "\t" + c.getConnectionType());
                }else{
                    writer.write("Product\t" + p.getProductName() + "\t" + p.getBrand() + "\t" + p.getModel() + "\t" + p.getQuantity() + "\t" + p.getPrice());
                }
                writer.newLine();
            }
            System.out.println("Saved to file: " + filename);
        }catch(IOException e){
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
    
    public void loadFromFile(String filename){
        File file = new File(filename);
        
        if(! file.exists()){
            try {
                file.createNewFile();
                System.out.println("Created new file: " + filename);
                return; // Return as the new file will be empty
            } catch (IOException e) {
                System.out.println("Error creating file: " + filename);
                return;
            }
        }
        
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split("\t");
                String type = parts[0];
                
                switch(type){
                    case "Keyboard":
                        products.add(new Keyboard(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Double.parseDouble(parts[5]), Integer.parseInt(parts[6]), parts[7], parts[8]));
                        break;
                        
                    case "Mouse":
                        products.add(new Mouse(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Double.parseDouble(parts[5]), parts[6], Integer.parseInt(parts[7]), parts[8]));
                        break;
                        
                    case "Monitor":
                        products.add(new Monitor(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Double.parseDouble(parts[5]), Double.parseDouble(parts[6]), parts[7], parts[8]));
                        break;
                        
                    case "Controller":
                        products.add(new Controller(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Double.parseDouble(parts[5]), parts[6], Integer.parseInt(parts[7]), parts[8]));
                        break;
                        
                    case "Product":
                        products.add(new Product(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Double.parseDouble(parts[5])));
                        break;
                        
                    default:
                        System.out.println("Unknown product type: " + type);
                }
            }
            System.out.println("Products loaded from file: " + filename);
        }catch(IOException e){
            System.out.println("Error loading file: " + filename);
        }
    }
    
    public List<Product> getAllProducts(){
        return new ArrayList<>(products);
    }
}
