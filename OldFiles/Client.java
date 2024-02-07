package OldFiles; //This is a barebones client, the gui one will be nicer
//Chad Tiffner
//02-05-2024
import java.net.*;
import java.util.*;
import java.io.*;

public class Client {
    private static InetAddress host;
    private static final int PORT = 8080; //can be any unused port as long as this and server port are the same

    public static void main(String[] args){ //really just makes the connection, most of the program is handled in accessServer()
        try{
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException uhEx){
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer(){
        Socket link = null;
        try{
            link = new Socket(host, PORT);

            Scanner in = new Scanner(link.getInputStream());//creates inputStream from server
            PrintWriter output = new PrintWriter(link.getOutputStream(),true); //creates output stream to send String to server
            Scanner userInput = new Scanner(System.in);
            String message;
            String response;
            do{
                System.out.print("Enter a binary Number: ");
                message = userInput.nextLine();
                output.println(message); //sends user input to server
                if(in.hasNextLine()){
                    response = in.nextLine();
                System.out.println("\nSERVER> " + response);
                }
                else
                System.out.println("No response from server.");
                
            }
            while(!message.equals("***CLOSE***"));
            in.close();
            userInput.close();

        }
        catch(IOException ioEx){
            ioEx.printStackTrace();
        }
        finally{ //closes connection
            try{
                System.out.println("Closing connection...");
                link.close();
            }
            catch(IOException ioEx){
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}
