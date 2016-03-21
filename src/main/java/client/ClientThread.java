package main.java.client;
import java.net.Socket;
import java.util.*;
import java.io.*;

class ClientThread extends Thread{

    Socket clientSocket;
    int clientID = -1;

    ClientThread(int i, Socket s)throws Exception {
        clientID = i;
        clientSocket = s;
    }

    public String handShake() throws Exception{
        try{
            PrintStream sout = new PrintStream(clientSocket.getOutputStream());
            Scanner sin = new Scanner(clientSocket.getInputStream());
            sout.println("HELLO");
            String response = sin.nextLine();
            System.out.println(response);
            return response.substring(4)+clientID;
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

