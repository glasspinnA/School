package assignment3;
import java.io.IOException;


public class Assigment3Main {
    public static void main(String[] args) throws IOException
    {
        GUIFoodSupply gui = new GUIFoodSupply();
        Controller controller = new Controller(gui);
        gui.Start(controller);  
    }
}
