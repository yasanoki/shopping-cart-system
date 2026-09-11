
public class Mouse extends Product{
    private String connectionType;
    private int numberOfButton;
    private String colour;
    
    public Mouse(String productName, String brand, String model, int quantity, double price, String connectionType,int numberOfButton,String colour){
        super(productName, brand, model, quantity, price);
        this.numberOfButton = numberOfButton;
        this.connectionType = connectionType;
        this.colour = colour;
    }
    
    public String getConnectionType(){
        return connectionType;
    }
    
    public void setConnectionType(String connectionType){
        this.connectionType = connectionType;
    }
    
    public int getNumberOfButton(){
        return numberOfButton;
    }
    
    public void setNumberOfButton(int numberOfButton){
        this.numberOfButton = numberOfButton;
    }
    
    public String getColour(){
        return colour;
    }
    
    public void setColour(String colour){
        this.colour = colour;
    }
    
    public String toString(){
        return super.toString() + 
                "Mouse" + '\n' +
                "Connection Type: " + connectionType + '\n' +
                "Number Of Button: " + numberOfButton + '\n' +
                "Colour: " + colour + '\n' +
                '\n';
    }
}
