import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/***
 * Klient klassen
 * @author Oscar
 *
 */

public class Client implements Runnable {
    private Socket socket;
    private String message;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private GUIClient guiClient;

    /**
     * Konstruktor
     * @param port
     * @param ip
     * @param controller
     * @throws UnknownHostException
     * @throws IOException
     */
    public Client(int port, String ip, Controller controller) throws UnknownHostException, IOException {
    	socket = new Socket(ip,port);
        guiClient = new GUIClient();
        guiClient.Start(controller,this);
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());    	
    }


    /**
     * Thread loop som lyssnar efter meddelande ifrån servern
     */
    @Override
    public void run() {
        while (true) {
	        try {
				message = ois.readUTF();
				guiClient.updateDisplay(message);
				System.out.println("Klient: " + message);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    /**
     * Metod som skickar meddelande till servern
     * från klienten
     * @param message
     */
    public void sendMessageToServer(String message) {
        try {
			oos.writeUTF(message);
	        oos.flush();
		} catch (IOException e) {
			System.out.println("Kunde inte skicka meddelande till servern");
		}
    }
}
