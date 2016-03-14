package server;

import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

  public EchoServer(int portNumber) {
    this.portNumber = portNumber;
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
