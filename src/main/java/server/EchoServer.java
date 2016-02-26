package main.java.server;

import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * A simple, single-threaded echo server with sequential servicing of
 * clients. That is, when a client connects, it is serviced to
 * completion (until it closes the connection or sends MSG_GOODBYE)
 * before another client connection is accepted. The server cannot be
 * stopped gracefully: use Ctrl-C to break the running program in a
 * terminal.
 *
 * "Echo server" means that it listens for a connection (on a
 * user-specifiable port), reads in the input on the wire, modifies
 * the information, and writes the modified information back to the
 * client, echoing the input it was given.
 */
public class EchoServer {

  /** Default port number; used if none is provided. */
  public final static int DEFAULT_PORT_NUMBER = 3939;

  /** Default machine name is the local machine; used if none provided. */
  public final static String DEFAULT_MACHINE_NAME = "localhost";

  /** Command-line switches */
  public final static String ARG_PORT = "--port";
  public final static String ARG_MACHINE = "--machine";

  /** Message op-codes */
  public final static String MSG_HELLO = "HELLO";
  public final static String MSG_GOODBYE = "Goodbye";
  static String hostname = "localhost";

  /** Port number of distant machine */
  private int portNumber;

  /**
   * Creates a new <code>FirstServer</code> instance. FirstServer is
   * a listening echo server (it responds with a slightly modified
   * version of the same message it was sent).
   *
   * @param portNumber required port number where the server will
   * listen.
   */
  public EchoServer(int portNumber) {
    this.portNumber = portNumber;
  }

  /**
   * Primary method of the server: Opens a listening socket on the
   * given port number (specified when the object was
   * constructed). It then loops forever, accepting connections from
   * clients.
   *
   * When a client connects, it is assumed to be sending messages, one per line. The server will process
   */
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
	//String serverMessage = "";

        while (cin.hasNextLine() &&
              (!(clientMessage = cin.nextLine()).equals(MSG_GOODBYE))) {
	    
          if(clientMessage.equals(MSG_HELLO)) {
            cout.println("IAM 4tr:" + hostname);
            System.out.format("Server saw \"%s\"\n", clientMessage);
          } else {
            System.out.format("Server saw \"%s\"\n",clientMessage);
	    cout.format(hostname +" Server saw \"%s\"\n",clientMessage);
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

  /**
   * Print the usage message for the program on standard error stream.
   */
  private static void usage() {
    System.err.print("usage: java FirstServer [options]\n" +
      "       where options:\n" + "       --port port\n");
  }

  /**
   * Processes the command-line parameters and then create and run
   * the FirstClient object.
   *
   * @param args a <code>String</code> value
   */
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

        // if there is an unknown parameter, give usage and quit
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
