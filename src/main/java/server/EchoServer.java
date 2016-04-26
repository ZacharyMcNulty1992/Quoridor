package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;
import java.net.UnknownHostException;
import common.Parsed;

public class EchoServer {

    public final static int DEFAULT_PORT_NUMBER = 3939;
    public final static String DEFAULT_MACHINE_NAME = "localhost";
    
    public final static String ARG_PORT = "--port";
    public final static String ARG_DELAY = "--delay";
    public final static String MSG_HELLO = "HELLO";
    static String hostname = "localhost";

    private static int playerNumber;
    private int portNumber;
    private int delay;
    private AI ai;
    private static EchoServer fc;

    public EchoServer(int portNumber, int delay) {
        this.portNumber = portNumber;
	this.delay = delay;
    }

    public void handshake(String clientMessage, PrintStream cout){
        cout.println("IAM 4tr:" + hostname);
        System.out.format("Server saw \"%s\"\n", clientMessage); 
    }

    public void myoushu(PrintStream cout){
        System.out.println("Please make your move or place a wall");
        cout.println(ai.getMove());
    }

    public void gameInit(String clientMessage){
        try{
            String[] message = clientMessage.split(" ");
            int pn = Integer.parseInt(clientMessage.substring(5,6));
            playerNumber = pn;
            System.out.println("Testing message.legnth: " + message.length);
            if(message.length == 4)
                ai = new AI(pn, 2, delay);
            else
                ai = new AI(pn, 4, delay);
        }catch(NumberFormatException e){
            System.out.println(e);
        }
        System.out.format("Server saw \"%s\"\n", clientMessage);

    }

    public void updateAI(String clientMessage){
        try{
            int pn = Integer.parseInt(clientMessage.substring(6,7));
            Parsed parsed = new Parsed(clientMessage);
            if(parsed.isWall){
                ai.placeWalls(parsed.c, parsed.r, parsed.wallPos);
             }else{
                ai.updatePlayerPosition(parsed.c , parsed.r , pn);
             }
        }catch(NumberFormatException e){
             System.out.println(e);
        }
        System.out.format("Server saw \"%s\"\n", clientMessage);
    }

    public void handleGOTE(String clientMessage, Scanner cin, PrintStream cout,
			   ServerSocket server) throws IOException{
        try{
            String[] message = clientMessage.split(" ");
            int pn = Integer.parseInt(clientMessage.substring(5,6));
            if(pn == playerNumber){
                resetConnection(cin,cout,server,clientMessage);
            }else{
                System.out.format("Server saw \"%s\"\n", clientMessage);
            }
        }catch(NumberFormatException e){
            System.out.println(e);
        }
    }

    public void resetConnection(Scanner cin, PrintStream cout,
                ServerSocket server, String clientMessage) throws IOException{

        System.out.format("Server saw \"%s\"\n", clientMessage);
        cout.close();
        cin.close();
        server.close();
        fc.run();
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(portNumber);
            System.out.format("Server now accepting connections on port %d\n",
                              portNumber);
            Socket client;

            while ((client = server.accept()) != null) {
                System.out.format("Connection from %s\n", client);

                Scanner cin = new Scanner(client.getInputStream());
                PrintStream cout = new PrintStream(client.getOutputStream());
                String clientMessage = "";

                    while (cin.hasNextLine()) {
		        clientMessage = cin.nextLine();
                        if(clientMessage.equals(MSG_HELLO))
                            handshake(clientMessage, cout);
			else if(clientMessage.equals("MYOUSHU"))   
                            myoushu(cout);                            
                        else if(clientMessage.substring(0,4).equals("GAME"))
                            gameInit(clientMessage);
			else if(clientMessage.substring(0,5).equals("ATARI"))
                            updateAI(clientMessage);
                        else if(clientMessage.substring(0,4).equals("GOTE"))
                            handleGOTE(clientMessage, cin, cout, server);
			else if(clientMessage.substring(0,7).equals("KIKASHI"))
                            resetConnection(cin,cout,server,clientMessage);
			else
			    System.out.format("Server saw \"%s\"\n",clientMessage);
                    }

                if (!clientMessage.isEmpty()) {
                    System.out.format("Server saw \"%s\" and is exiting.\n",
              		              clientMessage);
                }
                resetConnection(cin,cout,server,clientMessage);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    private static void usage() {
        System.err.print("usage: java FirstServer [options]\n" +
            "       where options:\n" + "       --port port\n" +
	    "--delay (delay in ms)");
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT_NUMBER;
        int delay = 1;
        /* Parsing parameters. argNdx will move forward across the
         * indices; remember for arguments that have their own parameters, you
         * must advance past the value for the argument too.
         */
        try{
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }catch (UnknownHostException ex){
            System.out.println("Hostname can not be resolved");
        }
        int argNdx = 0;

        while (argNdx < args.length) {
             String curr = args[argNdx];

             if (curr.equals(ARG_PORT)) {
                 ++argNdx;
                 String numberStr = args[argNdx];
                 port = Integer.parseInt(numberStr);
	     }else if(curr.equals(ARG_DELAY)) {
	         ++argNdx;
		 String delayStr = args[argNdx];
                 delay = Integer.parseInt(delayStr);
             } else {
                 System.err.println("Unknown parameter \"" + curr + "\"");
                 usage();
                 System.exit(1);
             }

             ++argNdx;
        }

        fc = new EchoServer(port,delay);
        fc.run();
    }
}
