import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Klass som sköter all logik mellan klasserna för programmet
 * @author Oscar
 *
 */
public class Controller {
    private Server server;
    private Thread serverThread, client1Thread, client2Thread, client3Thread, client4Thread, client5Thread;
    private Client client1, client2, client3, client4, client5;
    
    /**
     * Konstruktor
     * @throws UnknownHostException
     * @throws IOException
     */
    public Controller() throws UnknownHostException, IOException{
    	startServer();
    	startClient();
    }
    
    /**
     * Metod som tar emot meddelande ifrån Server GUI
     * och skickar viadare till Server
     * @param message
     * @throws IOException
     */
    public void messageFromServerGUI(String message) throws IOException {
        server.sendToAllClients(message);
    }
    
    /**
     * Metod som start server
     */
    public void startServer(){
        server = new Server(9000,this);
        serverThread = new Thread(server);
        serverThread.start();
    }
    
    /**
     * Metod som startar klienter
     * @throws UnknownHostException
     * @throws IOException
     */
    public void startClient() throws UnknownHostException, IOException{
        client1 = new Client(9000,"localhost",this);
        client2 = new Client(9000,"localhost",this);
        client3 = new Client(9000,"localhost",this);
        client4 = new Client(9000,"localhost",this);
        client5 = new Client(9000,"localhost",this);
        
        client1Thread = new Thread(client1);
        client1Thread.start();
        
        client2Thread = new Thread(client2);
        client2Thread.start();        
        
        client3Thread = new Thread(client3);
        client3Thread.start();
        
        client4Thread = new Thread(client4);
        client4Thread.start();
        
        client5Thread = new Thread(client5);
        client5Thread.start();
    }
    
    /**
     * Metod som tar emot meddelande ifrån Klient GUI och skickar
     * vidare till Client klasssen
     * @param message
     * @param client
     * @throws IOException
     */
    public void messageFromClientGUI(String message, Client client) throws IOException {
        client.sendMessageToServer(message);
    }
    

}
