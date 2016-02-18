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
	int count = args.length;
    	EchoClient bc = new EchoClient(cla.get(0),cla.get(1));
	//EchoClient moveSvr2 = new EchoClient(cla.get(2),cla.get(3),server2);
    	bc.run();
        //moveSvr2.run(server2);
    }

    private Socket server;
    public EchoClient(String host, String port)throws Exception{
	   
	   int portNumber = 0;
	   try{
	        portNumber = Integer.parseInt(port);
	        if(portNumber < 1024 || portNumber > 65535){
	            System.out.println();
		    throw new IndexOutOfBoundsException("Port number must be " +
		  	 	            "between 1024 and 65535 inclusive");
	        }
	    }catch(NumberFormatException e){
	        System.out.println("\nPort number must be an Integer");
	        System.out.println("You entered: " + port);
	        System.out.println("Exiting...");
	        System.exit(0);
	    }	
	    
	    server = new Socket(host,portNumber);
    }

    public void handShake() throws Exception{
	PrintStream sout = new PrintStream(server.getOutputStream());
	Scanner sin = new Scanner(server.getInputStream());
	sout.println("HELLO");
	String response = sin.nextLine();
	System.out.println(response);
	sout.println("GAME " + playerNumber + " Player1" + " Player2");
	response = sin.nextLine();
    }

    public void run() throws Exception{
    	Scanner sin = new Scanner(server.getInputStream());
    	PrintStream sout = new PrintStream(server.getOutputStream());
    	Scanner keyboard = new Scanner(System.in);
        handShake();
    	String line = keyboard.nextLine();
    	while(true){
    	    sout.println(line);
    	    String serverLine = sin.nextLine();
    	    System.out.println(serverLine);
    	    line = keyboard.nextLine();
    	}
    }

}
