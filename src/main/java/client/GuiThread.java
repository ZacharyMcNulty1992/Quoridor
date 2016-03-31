package client;

public class GuiThread extends Thread{
    
    GuiThread(){
    }
    public void run(){
        String [] args = new String [0];
        client.gui.Main.main(args);
    }

}
