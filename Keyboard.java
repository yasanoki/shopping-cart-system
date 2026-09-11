public class Keyboard extends Product{
    private int numberOfKeys;
    private String connectionType;
    private String colour;
    
    public Keyboard(String productName, String brand, String model, int quantity, double price, int numberOfKeys, String connectionType, String colour){
        super(productName, brand, model, quantity, price);
        this.numberOfKeys = numberOfKeys;
        this.connectionType = connectionType;
        this.colour = colour;
    }
    
    public int getNumberOfKeys(){
        return numberOfKeys;
    }
    
    public void setNumberOfKeys(int numberOfKeys){
        this.numberOfKeys = numberOfKeys;
    }
    
    public String getConnectionType(){
        return connectionType;
    }
    
    public void setConnectionType(String connectionType){
        this.connectionType = connectionType;
    }
    
    public String getColour(){
        return colour;
    }
    
    public void setColour(String colour){
        this.colour = colour;
    }
    
    public String toString(){
        return super.toString() + 
                "Keyboard" + '\n' +
                "Number Of Keys: " + numberOfKeys + '\n' +
                "Connection Type: " + connectionType + '\n' +
                "Colour: " + colour + '\n' +
                '\n';
    }
}
