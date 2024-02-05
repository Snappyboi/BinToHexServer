import java.net.*;
import java.util.*;
import java.io.*;

public class Client {
    private static InetAddress host;
    private static final int PORT = 8080; //this random port works at the moment

    public static void main(String[] args){
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

            Scanner in = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(),true);
            Scanner userInput = new Scanner(System.in);
            String message;
            String response;
            do{
                System.out.print("Enter a binary Number: ");
                message = userInput.nextLine();
                output.println(message);
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
        finally{
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
