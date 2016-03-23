package main.java.client;
import java.net.Socket;
import java.util.*;
import java.io.*;

class ClientThread extends Thread{

    Socket clientSocket;
    //int clientID = -1;
    String playerName = "";
    ClientThread(Socket s)throws Exception {
      //  clientID = i;
        clientSocket = s;
    }

    public String handShake() throws Exception{
        try{
            PrintStream sout = new PrintStream(clientSocket.getOutputStream());
            Scanner sin = new Scanner(clientSocket.getInputStream());
            sout.println("HELLO");
            String response = sin.nextLine();
            System.out.println(response);
	    playerName = response.substring(4);
            return playerName;
        }catch(IOException e){
            System.out.println(e);
        }
        return "Player name failure";
    }

    public void write(String message) throws Exception{
        try{
            Scanner sin = new Scanner(clientSocket.getInputStream());
            PrintStream sout = new PrintStream(clientSocket.getOutputStream());
            sout.println(message);
            String serverLine = sin.nextLine();
            System.out.println(serverLine);
        }catch(IOException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public String Myoushu(){
        try{
            Scanner sin = new Scanner(clientSocket.getInputStream());
            PrintStream sout = new PrintStream(clientSocket.getOutputStream());
            sout.println("MYOUSHU");
            String serverLine = sin.nextLine();
	    return serverLine;
        }catch(IOException e){
            System.out.println(e);
        }catch(Exception e){
            System.out.println(e);
        }
        return "Failure to retrieve message from server";
    }

    public String getPlayerName(){
	return playerName;
    }

    public void run(){
        try{
            Scanner sin = new Scanner(clientSocket.getInputStream());
            PrintStream sout = new PrintStream(clientSocket.getOutputStream());
            Scanner keyboard = new Scanner(System.in);
        }catch(IOException e){
            System.out.println(e);
        }catch(Exception j){
            System.out.println(j);
        }
    }
}

