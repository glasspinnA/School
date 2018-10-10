import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * Server klassen
 * @author Oscar
 *
 */
public class Server implements Runnable {
	private Controller controller;
    private int port;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private GUIServer guiServer;
    private ExecutorService pool = Executors.newFixedThreadPool(5); //Antal trådar i trådpoolen
    private ArrayList<ClientHandler>list = new ArrayList<ClientHandler>(); //Lista för att se ansluta klienter till servern

    /**
     * Konstruktorn
     * @param port
     * @param controller
     */
    public Server(int port, Controller controller){
        this.port = port;
        guiServer = new GUIServer();
        guiServer.Start(controller);
        openServer();
    }
    
    public GUIServer getGui(){
        return guiServer;
    }

    /**
     * Metod som startar servern på en viss port
     */
    public void openServer() {
    	try {
            serverSocket = new ServerSocket(port);
            guiServer.updateDisplay("Waiting for connections...");
            System.out.println("Server has startad");
        } catch (IOException e) {
        	System.out.println("Cant open port " + port);
        }
	}
    
    /**
     * Metod som skickar samma meddelande från servern
     * till alla klietern som finns ansluta till servern
     * @param message
     * @throws IOException
     */
    public void sendToAllClients(String message) throws IOException {        
        for(ClientHandler client : list)
            client.sendMessage(message);
    }
    

    /**
     * Thread loop som lägger till en ny Klient till till clientHandler
     * när det finns något att lägga till
     */
	@Override
    public void run() {
    	while(true){
            try {
                clientSocket = serverSocket.accept();
                list.add(new ClientHandler(clientSocket));
                pool.execute(new WorkerRunnable(clientSocket,this));
                guiServer.updateDisplay("Client connected...");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

	/**
	 * Klass som hanterar klieterna som finns ansluta 
	 * till servern
	 */
    private class ClientHandler {
        private Socket socket;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            oos = new ObjectOutputStream(socket.getOutputStream());
            
        }
        
        /**
         * Metod som skickar meddelande till klient 
         * @param message
         * @throws IOException
         */
        public void sendMessage(String message) throws IOException {
        	oos.writeUTF(message);
            oos.flush();
        }
    }


}
