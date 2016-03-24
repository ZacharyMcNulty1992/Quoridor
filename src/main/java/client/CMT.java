package client;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;

public class CMT {

    static String hostname = "localhost";
    static String names = "";
    static int playerNumber;
    static int playerCount;
    private static ArrayList<String> playerNames = new ArrayList<String>();
    private static ArrayList<ClientThread> threadList = 
	                                 new ArrayList<ClientThread>();

    public static void main(String[] args) throws Exception{

	playerNumber = 0;
	String tesuji = "";
	playerCount = args.length;
        Socket clientSocket;
	Scanner keyboard = new Scanner(System.in);

        try{
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }catch(UnknownHostException ex){
            System.out.println("Hostname can not be resolved");
        }
    
        for(int i = 0; i < args.length; i++){
            String [] temp = args[i].split(":");
	    clientSocket = errorCheck(temp);
            ClientThread client = new ClientThread(clientSocket);
            threadList.add(client);
            client.start();
        }
 
	Handshake();
	nameBuilder();	
	initGame();
	//Interpreter GM = new Interpreter();	
	/**Could create new Interpreter here and then call 
	   GM.parseString(tesuji), Need way of converting tesuji to point
	   for the isValidMove method. */
	while(true){
	    for(ClientThread c: threadList){
	        tesuji = c.Myoushu();
	        //validateMove(tesuji);
		Interpreter GM = new Interpreter(tesuji);
		//boolean vm = c.player.isValidMove(GM.getInString());
	        System.out.println("Testing Interpreter: " + GM.getInString());
	        Atari(tesuji.substring(7));
	    } 
	}
        /*String line = keyboard.nextLine();
        while(true){
	    for(ClientThread c : threadList)
	        c.write(line);
            line = keyboard.nextLine();
        } */
    }

    public static void nameBuilder(){
	for(int i = 0; i < playerNames.size(); i++) 
            names += playerNames.get(i) + " ";
    }

    public static void Handshake() throws Exception{
	int counter = 1;
	for(ClientThread c: threadList){
            playerNames.add(c.handShake() + counter);
            counter++;
        }
    }

    public static void initGame() throws Exception{
	for(ClientThread c: threadList){
            c.write("GAME " + ++playerNumber + " " + names);
	    c.setPlayerNumber(playerNumber);
	    c.createPlayer();
	}
    }

    public static void Atari(String message) throws Exception{
	for(ClientThread c : threadList)
	    c.write("ATARI "+ c.getPlayerName() + " " + message);
    }

    public static Socket errorCheck(String [] temp){
	Socket ec = new Socket();
	int portNumber = 0;
	try{
            portNumber = Integer.parseInt(temp[1]);
            hostname = temp[0];
            if(portNumber < 1024 || portNumber > 65535){
               System.out.println();
               throw new IndexOutOfBoundsException("Port number must be " +
                                         "between 1024 and 65535 inclusive");
            }
	    ec = new Socket(hostname,portNumber);
        }catch(NumberFormatException e){
            System.out.println("\nPort number must be an Integer");
            System.out.println("You entered: " + temp[0]);
            System.out.println("Exiting...");
            System.exit(0);
        }catch(IOException e){
	    System.out.println(e);
	    System.exit(0);
	}
	return ec;	
    } 

}  
