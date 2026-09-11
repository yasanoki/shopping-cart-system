public class Controller extends Product{
    private String controllerType;
    private int numberOfButtons;
    private String connectionType;
    
    public Controller(String productName, String brand, String model, int quantity, double price, String controllerType, int numberOfButtons, String connectionType){
        super(productName, brand, model, quantity, price);
        this.controllerType = controllerType;
        this.numberOfButtons = numberOfButtons;
        this.connectionType = connectionType;
    }
    
    public String getControllerType(){
        return controllerType;
    }
    
    public void setControllerType(String controllerType){
        this.connectionType = controllerType;
    }
    
    public int getNumberOfButtons(){
        return numberOfButtons;
    }
    
    public void setNumberOfButtons(int numberOfButtons){
        this.numberOfButtons = numberOfButtons;
    }
    
    public String getConnectionType(){
        return connectionType;
    }
    
    public void setConnectionType(String connectionType){
        this.connectionType = connectionType;
    }

    public String toString(){
        return super.toString() +
                "Controller" + '\n' +
                "Controller Type: " + controllerType + '\n' +
                "Number Of Buttons: " + numberOfButtons + '\n' +
                "Connection Type: " + connectionType + '\n' +
                '\n';
    }
}
