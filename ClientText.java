import javax.swing.JFrame;

public class ClientText {
    public static void main(String[] args){
        Client theClient;
        theClient = new Client("127.0.0.1");
        theClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theClient.startRunning();
        
    }    
}
