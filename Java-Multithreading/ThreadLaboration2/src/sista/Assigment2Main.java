package sista;
import java.io.IOException;


public class Assigment2Main {
    public static void main(String[] args) throws IOException
    {
        GUIMutex gui = new GUIMutex();
        Controller controller = new Controller(gui);
        gui.Start(controller);  
    }
}
