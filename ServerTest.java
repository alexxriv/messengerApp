import javax.swing.JFrame;

public class ServerTest {
    public static void main(String[] args){
        Server theServer = new Server();
        theServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theServer.startRunning();
    }
}