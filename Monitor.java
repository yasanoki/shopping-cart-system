public class Monitor extends Product{
    private double screenSize;
    private String resolutionType;
    private String lightSource;
    
    public Monitor(String productName, String brand, String model, int quantity, double price, double screenSize, String resolutionType, String lightSource){
        super(productName, brand, model, quantity, price);
        this.screenSize = screenSize;
        this.resolutionType = resolutionType;
        this.lightSource = lightSource;
    }
    
    public double getScreenSize(){
        return screenSize;
    }
    
    public void setScreenSize(double screenSize){
        this.screenSize = screenSize;
    }
    
    public String getResolutionType(){
        return resolutionType;
    }
    
    public void setResolutionType(String resolutionType){
        this.resolutionType = resolutionType;
    }
    
    public String getLightSource(){
        return lightSource;
    }
    
    public void setLightSource(String lightSource){
        this.lightSource = lightSource;
    }

    public String toString(){
        return super.toString() + 
                "Monitor" + '\n' +
                "Screen Size: " + screenSize + '\n' +
                "Resolution Type: " + resolutionType + '\n' +
                "Light Source: " + lightSource + '\n' +
                '\n';
    }
}
