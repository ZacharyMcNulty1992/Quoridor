package client;
import java.net.Socket;
import java.util.*;
import java.io.*;

public class EchoClient {
    public final static String DEFAULT_MACHINE_NAME = "localhost";
    
    public static void main(String[] args) throws Exception{
    	EchoClient bc = new EchoClient("localhost",3939);
    	bc.run();
    }

    private Socket server;
    public EchoClient(String host, int port)throws Exception{
	   server = new Socket(host,port);
    }

    public void run() throws Exception{
    	Scanner sin = new Scanner(server.getInputStream());
    	PrintStream sout = new PrintStream(server.getOutputStream());
    	Scanner keyboard = new Scanner(System.in);
    	String line = keyboard.nextLine();
    	while(true){
    	    sout.println(line);
    	    String serverLine = sin.nextLine();
    	    System.out.println(serverLine);
    	    line = keyboard.nextLine();
    	}
    }

}
