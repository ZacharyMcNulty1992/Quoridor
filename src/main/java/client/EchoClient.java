package main.java.client;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;

public class EchoClient {
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
        playerNumber = args.length;
	ArrayList<String> cla = new ArrayList<String>();
	for(int i = 0; i < args.length; i++){
	    String [] temp = args[i].split(":");
	    cla.add(temp[0]);
	    cla.add(temp[1]);
	}
    	EchoClient bc = new EchoClient(cla);
    	bc.run();
    }

    private Socket server;
    private Socket server2;
    public EchoClient(ArrayList<String> cla)throws Exception{
	   String host = cla.get(0);
	   String host2 = cla.get(2);
	   int portNumber = 0;
	   int portNumber2 = 0;
	   try{
	        portNumber = Integer.parseInt(cla.get(1));
	        portNumber2 = Integer.parseInt(cla.get(3));
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
	    
	    server = new Socket(host,portNumber);
	    server2 = new Socket(host2,portNumber2);
    }

    public void handShake() throws Exception{
	PrintStream sout = new PrintStream(server.getOutputStream());
	PrintStream s2out = new PrintStream(server2.getOutputStream());
	Scanner sin = new Scanner(server.getInputStream());
	Scanner s2in = new Scanner(server2.getInputStream());
	sout.println("HELLO");
	s2out.println("HELLO");
	String s1response = sin.nextLine();
	String s2response = s2in.nextLine();
	System.out.println(s1response);
	System.out.println(s2response);
	sout.println("GAME " + playerNumber + " "+ s1response.substring(4) + " " + 
		     s2response.substring(4));
	s2out.println("GAME " + playerNumber + " "+ s1response.substring(4) + " " + 
		      s2response.substring(4));
	s1response = sin.nextLine();
	s2response = s2in.nextLine();
    }

    public void run() throws Exception{
    	Scanner sin = new Scanner(server.getInputStream());
	Scanner s2in = new Scanner(server2.getInputStream());
    	PrintStream sout = new PrintStream(server.getOutputStream());
	PrintStream s2out = new PrintStream(server2.getOutputStream());
    	Scanner keyboard = new Scanner(System.in);
        handShake();
    	String line = keyboard.nextLine();
    	while(true){
    	    sout.println(line);
	    s2out.println(line);
    	    String serverLine = sin.nextLine();
	    String server2Line = s2in.nextLine();
    	    System.out.println(serverLine);
	    System.out.println(server2Line);
    	    line = keyboard.nextLine();
    	}
    }

}
