import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{

    //typing message box
    private JTextField userText;

    //chat display window
    private JTextArea chatWindow;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private ServerSocket server;
    private Socket connection;

    //constructor
    public Server(){
        super("Instant Messenger");
        userText= new JTextField();

        //not allowed to talk while disconnected
        userText.setEditable(false);

        userText.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    sendMessage(event.getActionCommand());
                    userText.setText("");
                }
            }
        );

        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));

        setSize(300,150);
        setVisible(true);

    }

    //set upt and un the server

    public void startRunning(){
        try{
            //port: 6789, backlog: 100 people
            server = new ServerSocket(6789, 100);
            
            //connect and have conversation
            while(true){
                try{
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                }catch(EOFException eofException){
                    showMessage("\n Server ended the connection")
                }finally{
                    closeWindow();
                }
            }
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    //wait for connection, then display connection info
    private void waitForConnection() throws IOException{
        showMessage("Waiting for someone to connect... \n");
        connection = server.accept();
        showMessage("Now connected to "+ connection.getInetAddress().getHostName());
    }

    //get stream to send and receive data
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Stream are now  setup \n");
    }

    //during the chat conversation
    private void whileChatting() throws IOException{
        String message = "You are now connected :)";
        sendMessage(message);
        abletoType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n"+ message);
            }catch(ClassNotFoundException classNotFoundException){
                showMessage("\n idk wtf that user send ");
            }
        }while(!message.equals("CLIENT - END"));
    }

    //close streams and sockets after you are done chatting
    private void closeWindow(){
        showMessage("\n Closeing connection...");
        abletoType(false);
        try{
            output.close();
            input.close();
            connection.close();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    //send message to client

    private void sendMessage(String message){
        try{
            output.writeObject("SERVER: "+ message);
            output.flush();
            showMessage("\n SERVER: "+ message);
        }catch (IOException ioException){
            chatWindow.append("\n ERROR: I cant send that message \n");
        }
    }



}