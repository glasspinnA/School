import java.io.*;
import java.net.Socket;

/***
 * Klass som 
 * @author Oscar
 *
 */
public class WorkerRunnable implements Runnable{
    private Server server;
    private Socket clientSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String message;
    private GUIServer guiServer;
    
    /**
     * Konstruktor 
     * @param clientSocket
     * @param server
     */
    public WorkerRunnable(Socket clientSocket, Server server){
        this.clientSocket = clientSocket;
        this.server = server;
    }

    /**
     * Thread loop som tar emot meddelande ifrån klient 
     * och skickar vidare meddelandet till GUI för servern
     */
    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
//            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            while(true) {
                message = ois.readUTF();
                guiServer = server.getGui();
                guiServer.updateDisplay(message);
                System.out.println("Server:" + message);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}