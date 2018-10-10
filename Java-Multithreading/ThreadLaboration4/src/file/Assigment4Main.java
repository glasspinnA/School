package file;



public class Assigment4Main {
    public static void main(String[] args) {
        GUIMonitor gui = new GUIMonitor();
        Controller controller = new Controller(gui);
        gui.Start(controller);

    }
}
