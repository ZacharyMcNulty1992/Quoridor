package main.java.client;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;

public class CMT {

    static String hostname = "localhost";
    static int playerNumber;
    static int playerCount;
    private static ArrayList<ClientThread> threadList = 
	                                 new ArrayList<ClientThread>();

    public static void main(String[] args) throws Exception{
        try{
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }catch(UnknownHostException ex){
            System.out.println("Hostname can not be resolved");
        }

        playerNumber = 0;
        int portNumber = 0;
        playerCount = args.length;
        Socket clientSocket;
    
        for(int i = 0; i < args.length; i++){
            String [] temp = args[i].split(":");
            try{
                portNumber = Integer.parseInt(temp[1]);
                hostname = temp[0];
                if(portNumber < 1024 || portNumber > 65535){
                    System.out.println();
                    throw new IndexOutOfBoundsException("Port number must be " +
                                             "between 1024 and 65535 inclusive");
                }
            }catch(NumberFormatException e){
                System.out.println("\nPort number must be an Integer");
                System.out.println("You entered: " + temp[0]);
                System.out.println("Exiting...");
                System.exit(0);
            }
            clientSocket = new Socket(hostname,portNumber);
            ClientThread client = new ClientThread(++playerNumber,clientSocket);
            threadList.add(client);
            client.start();
        }
 
        ArrayList<String> playerNames = new ArrayList<String>();
        for(ClientThread c: threadList){
	    playerNames.add(c.handShake());
        }

        for(ClientThread c: threadList){
	    c.write("GAME " + playerCount + " " + playerNames);
        }

        Scanner keyboard = new Scanner(System.in);
        String line = keyboard.nextLine();
        //IDEA FOR LATER: ADD MYOUSHU, TESUJI(SERVERSIDE) AND ATARI METHODS.
        //                CALL EACH METHOD PER THREAD IN LIST                
        while(true){
	    for(ClientThread c : threadList)
	        c.write(line);
            line = keyboard.nextLine();
        }
    }
}  

  class ClientThread extends Thread{

    Socket clientSocket;
    int clientID = -1;
 
    ClientThread(int i, Socket s)throws Exception {
        clientID = i;
	clientSocket = s;
  }

  public String handShake() throws Exception{
    try{
        PrintStream sout = new PrintStream(clientSocket.getOutputStream());
        Scanner sin = new Scanner(clientSocket.getInputStream());
        sout.println("HELLO");
        String response = sin.nextLine();
        System.out.println(response);
	return response.substring(4)+clientID;
    }catch(IOException e){
	System.out.println(e);
    }
    return "Player name failure";
  }

  public void write(String message) throws Exception{
	try{
	    Scanner sin = new Scanner(clientSocket.getInputStream());
	    PrintStream sout = new PrintStream(clientSocket.getOutputStream());
	    sout.println(message);
  	    String serverLine = sin.nextLine();
	    System.out.println(serverLine);
	}catch(IOException e){
	    System.out.println(e);
	}catch(Exception e){
	    System.out.println(e);
	}
  }

  public void run(){
    try{
        Scanner sin = new Scanner(clientSocket.getInputStream());
        PrintStream sout = new PrintStream(clientSocket.getOutputStream());
        Scanner keyboard = new Scanner(System.in);
    }catch(IOException e){
	System.out.println(e);
    }catch(Exception j){
	System.out.println(j);
    }
  }

}
