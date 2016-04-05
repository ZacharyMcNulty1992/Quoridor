package client;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.Point;
import java.util.*;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;
import common.Parsed;

public class CMT {

    static String hostname = "localhost";
    static String names = "";
    static String move = "";
    static int playerNumber;
    static int playerCount;
    static GuiThread gt;
    private static ArrayList<Player> playerList = new ArrayList<Player>();
    private static ArrayList<String> playerNames = new ArrayList<String>();
    private static CopyOnWriteArrayList<ClientThread> threadList = 
	                                 new CopyOnWriteArrayList<ClientThread>();

    public static void main(String[] args) throws Exception{

	playerNumber = 0;
	playerCount = args.length;
        Socket clientSocket;

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
 	gt = new GuiThread();
        gt.start();
	Handshake();
	nameBuilder();	
	initGame();
	Play();
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
            playerList.add(c.getPlayer());
	}
        client.gui.Main.setPlayers(playerList);
    }

    public static void Play() throws Exception {
	String tesuji = "";
        while(true){
            for(ClientThread c: threadList){
                tesuji = c.Myoushu();
		Parsed ps = new Parsed(tesuji);
		if(ps.valid)
                    Atari(tesuji.substring(7),c.getPlayerNumber());
		else
		    Gote(c);    		
            }
        }
    }

    public static void Atari(String message, int pn) throws Exception{
	int count = 0;
	for(ClientThread c : threadList){
	    c.write("ATARI "+ pn + " " + message);
	    if(count==0)
		client.gui.Main.Atari(c.player.getCurrentPos(),message);
	}
	client.gui.Main.setPlayerCount(playerCount);
	
    }

    public static void Gote(ClientThread g) throws Exception{
	for(ClientThread c: threadList){
	   c.write("GOTE " + g.getPlayerNumber());
        }
	threadList.remove(g);
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
