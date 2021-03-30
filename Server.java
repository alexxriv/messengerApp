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

}