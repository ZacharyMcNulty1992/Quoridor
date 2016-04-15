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
    public final static String ARG_MACHINE = "--machine";
    public final static String MSG_GOODBYE = "Goodbye";
    public final static String MSG_HELLO = "HELLO";
    static String hostname = "localhost";

    private int portNumber;
    private AI ai;

    public EchoServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void run() {
	Scanner keyboard = new Scanner(System.in);
	String move = "";
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

                    while (cin.hasNextLine() &&
                    (!(clientMessage = cin.nextLine()).equals(MSG_GOODBYE))) {

                        if(clientMessage.equals(MSG_HELLO)) {
                            cout.println("IAM 4tr:" + hostname);
                            System.out.format("Server saw \"%s\"\n", 
				              clientMessage);
			}else if(clientMessage.equals("MYOUSHU")){   
			    //Determine move choice
			    //Send move string
		            //String move = ...
			    //cout.println("TESUJI " + move)
			    System.out.println("Please make your move" + 
					       " or place your wall");
	
			    //This is to obtain move from the AI.
			    //It is commented out for testing purposes & until
			    //error's associated with it are fixed.
			    move = ai.getMove();
			
			    //move = keyboard.nextLine();
			    //cout.println("TESUJI " + move);
                            cout.println(move);
                            
                        }else if(clientMessage.substring(0,4).equals("GAME")){
			    try{
			        int pn = Integer.parseInt(clientMessage.substring(5,6));
				ai = new AI(pn);
			    }catch(NumberFormatException e){
			        System.out.println(e);
			    }
			    System.out.format("Server saw \"%s\"\n",
                                              clientMessage);
                            cout.format(hostname +" Server saw \"%s\"\n",
                                        clientMessage);
			}else if(clientMessage.substring(0,5).equals("ATARI")){
			    try{
			        int pn = Integer.parseInt(clientMessage.substring(6,7));
				//x , y temporary
				System.out.println("Testing parse PN: " + pn);
				System.out.println("Testing substring: " + clientMessage.substring(8,13));
				Parsed parsed = new Parsed(clientMessage.substring(8,13));
				ai.updatePlayerPostion(parsed.c , parsed.r , pn);
			    }catch(NumberFormatException e){
				System.out.println(e);
			    }
			    System.out.format("Server saw \"%s\"\n",
                                              clientMessage);
			    cout.format(hostname +" Server saw \"%s\"\n",
                                        clientMessage);

			}else{
                            System.out.format("Server saw \"%s\"\n",
				              clientMessage);
                            cout.format(hostname +" Server saw \"%s\"\n",
					clientMessage);
                        } 
                    }

                if (!clientMessage.isEmpty()) {
                    System.out.format("Server saw \"%s\" and is exiting.\n",
              		              clientMessage);
                }

                cout.close();
                cin.close();
            }
        } catch (IOException ioe) {

            // there was a standard input/output error (lower-level from uhe)
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    private static void usage() {
        System.err.print("usage: java FirstServer [options]\n" +
            "       where options:\n" + "       --port port\n");
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT_NUMBER;

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
             } else {
                 System.err.println("Unknown parameter \"" + curr + "\"");
                 usage();
                 System.exit(1);
             }

             ++argNdx;
        }

        EchoServer fc = new EchoServer(port);
        fc.run();
    }
}
