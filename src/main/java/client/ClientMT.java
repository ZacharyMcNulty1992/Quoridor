package main.java.client;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;

public class ClientMT {
  static String hostname = "localhost";
  static int playerNumber;

  public static void main(String[] args) throws Exception{
    try{
      InetAddress addr;
      addr = InetAddress.getLocalHost();
      hostname = addr.getHostName();
    }catch(UnknownHostException ex){
      System.out.println("Hostname can not be resolved");
    }
    playerNumber = 0;
    ArrayList<String> cla = new ArrayList<String>();
    
    for(int i = 0; i < args.length; i++){
      String [] temp = args[i].split(":");
      cla.add(temp[0]);
      cla.add(temp[1]);
      ClientThread client = new ClientThread(++playerNumber,cla,temp);
      client.start();
    }
  }
}  

  class ClientThread extends Thread{

    Socket clientSocket;
    int clientID = -1;
    int portNumber = 3939;
    String [] temp = new String [2];
    ArrayList<String> cla = new ArrayList<String>();
    static String hostname =  "localhost";
 
    ClientThread(int i, ArrayList<String> a, String [] t)throws Exception {
        clientID = i;
	cla = a;
        temp = t.clone();
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
            System.out.println("You entered: " + cla);
            System.out.println("Exiting...");
            System.exit(0);
       }	
       clientSocket = new Socket(hostname,portNumber);
  }

  public void handShake() throws Exception{
    try{
        PrintStream sout = new PrintStream(clientSocket.getOutputStream());
        Scanner sin = new Scanner(clientSocket.getInputStream());
        sout.println("HELLO");
        String s1response = sin.nextLine();
        System.out.println(s1response);
        sout.println("GAME " + clientID + " "+ s1response.substring(4));
        s1response = sin.nextLine();

    }catch(IOException e){
	System.out.println(e);
    }
  }

  public void run(){
    try{
        Scanner sin = new Scanner(clientSocket.getInputStream());
        PrintStream sout = new PrintStream(clientSocket.getOutputStream());
        Scanner keyboard = new Scanner(System.in);
        handShake();
        String line = keyboard.nextLine();
        while(true){
            sout.println(line);
            String serverLine = sin.nextLine();
            System.out.println(serverLine);
            line = keyboard.nextLine();
        }
    }catch(IOException e){
	System.out.println(e);
    }catch(Exception j){
	System.out.println(j);
    }
  }
}
