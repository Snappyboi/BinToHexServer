import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class GuiClient extends JFrame implements ActionListener{
    private JTextField hostInput;
    private JTextArea display;
    private JButton hexButton;
    private JButton exitButton;
    private JPanel buttonPanel;

    private static Socket socket = null;
    private final int PORT = 8080;
    private static PrintWriter out = null;
    private static Scanner in = null;

    public static void main(String[] args){
        GuiClient frame = new GuiClient();
        frame.setSize(400,300);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event){
                disconnect();
                System.exit(0);
            }
        });
    }

    public GuiClient(){
        hostInput = new JTextField(20);
        add(hostInput, BorderLayout.NORTH);

        display = new JTextArea(10,15);
        
        display.setWrapStyleWord(true);
        display.setLineWrap(true);

        add(new JScrollPane(display), BorderLayout.CENTER);

        buttonPanel = new JPanel();

        hexButton = new JButton("Convert to hex ");
        hexButton.addActionListener(this);
        buttonPanel.add(hexButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent event){
        if(event.getSource() == exitButton){
            disconnect();
            System.exit(0);
        }
        String response;
        String binString = hostInput.getText();

        try{
            if(socket == null || socket.isClosed()){
                socket = new Socket(InetAddress.getLocalHost(), PORT);
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);
            }
            out.println(binString);
            if(in.hasNextLine()){
                response = in.nextLine();
                display.append(response + "\n");
            }
            else{
                display.append("No response from server.");
            }
            hostInput.setText("");
        }
        catch(IOException ioEx){
            display.append(ioEx.toString() + "\n");
        }
    }
    public static void disconnect(){
        try{
            if(socket != null && !socket.isClosed()){
                socket.close();
            }
        }
        catch(IOException ioEx){
            System.out.println("Unable to disconnect!");
            System.exit(1);
        }
    }
}