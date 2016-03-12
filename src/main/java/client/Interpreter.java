package client;

import java.awt.Point;
import java.util.*;
import java.io.*;


public class Interpreter {


	public boolean resp;
	public String inputString;
	public GamePiece resultGamePiece;


	public Interpreter(String in){
		

		parseString(in);
		this.inputString = in;
	}
		
	public String getInString(){
	
		return inputString;
	}

	public GamePiece getPiece(){
	
		return resultGamePiece;
	}
	
	public boolean respProt(){
	
		return resp;
	}
	
	public void parseString(String in){
		
		String[] tesuji = in.split(" ", 2);
		if(!tesuji[0].equals("TESUJI")){
			resp=false;
			resultGamePiece=null;
		}
		else if(in.length()!=12&&in.length()!=17){
			resp=false;
			resultGamePiece=null;
		}
		else if(in.charAt(7)=='('&&in.charAt(11)==')'){
		
			try{
				resp=true;
				resultGamePiece=new Pawn(Integer.parseInt(String.valueOf(in.charAt(8))), Integer.parseInt(String.valueOf(in.charAt(10))));
			}catch(NumberFormatException e){
				resp=false;
				resultGamePiece=null;
			}
		}
		else if(in.charAt(7)=='['&&(in.charAt(15)=='v'||in.charAt(15)=='h')){
			try{
				resp=true;
				resultGamePiece= new Wall(Integer.parseInt(Character.toString(in.charAt(9))), Integer.parseInt(Character.toString(in.charAt(11))),in.charAt(15));
			}catch(NumberFormatException e){
				resp=false;
				resultGamePiece=null;
			}
		
		}
		else{
				resp = false;
				resultGamePiece=null;
		}
	}
	
}