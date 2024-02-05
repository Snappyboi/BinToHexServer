//Chad Tiffner
//02-05-2024

import java.net.*;
import java.util.*;
import java.io.*;
public class Server {
    private static final int PORT = 8080; //can be any unused port as long as this and client port are the same
    private static ServerSocket serverSocket;
    public static void main(String[] args){ //just makes the connection, everything else is handled in handleClient()
       System.out.println("Opening port.");
            try{
                serverSocket = new ServerSocket(PORT);
            }
            catch(IOException ex){
                System.out.println("Could not attach to port.");
                System.exit(1);
            }
        do{
            handleClient();
        }while(true);
    }
    public static void handleClient(){
        Socket link = null;
        try{
            link = serverSocket.accept();
            Scanner in = new Scanner(link.getInputStream()); //creates input stream from client

            PrintWriter output = new PrintWriter(link.getOutputStream(), true);  //creates output stream to client
            String bin = in.nextLine(); //takes user input from client connection
            while(!bin.equals("***CLOSE***")){
                if(isBinary(bin)){
                    String hex = binToHex(bin);
                    output.println(bin + " in hexadecimal is: " + hex); //sends converted number back to client
                    bin = in.nextLine();
                }
                else{
                    output.println("Invalid input! Enter a binary number.");
                    bin = in.nextLine();
                }
            }
            

            in.close();
        }
        catch(IOException ex) {
            System.out.println("Could not create socket.");
        }
        finally{ //ends connection
            try{
                System.out.println("Closing connection.");
                link.close();
            }
            catch(IOException ioX){
                System.out.println("Cannot disconnect!");
                System.exit(1);
            }
        }
    }

    public static String binToHex(String input){ //does the binary to hexadecimal conversion
        int decimal = Integer.parseInt(input,2);
        String hex = Integer.toString(decimal,16);
        return hex;
    }

    public static boolean isBinary(String bin){ //makes sure input is binary so the server doesn't crash
        int len = bin.length();
        for(int i = 0; i < len; i++){
            if(bin.charAt(i) != '0' && bin.charAt(i) != '1'){
                return false;
            }
        }
        return true;
    }
}
