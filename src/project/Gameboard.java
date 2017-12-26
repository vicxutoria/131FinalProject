package project;

import java.awt.Color;

import javax.swing.JOptionPane;

import sedgewick.StdDraw;

public class Gameboard {

	private Wind ws = new Wind();
	private Gorilla[] players = new Gorilla[2];
	private int currentPlayer = 0;
	private boolean gameOver;
	private Building[] buildings = new Building[10];
	private int canvasWidth = 1000;
	private int canvasHeight = 600;
	private int scoresToWin = 1;
	
	public Gameboard(){
		boolean changeLandscape = false;
		this.initializeBuildings();
		this.initializePlayers(true);
		boolean firstTime = true;
		while(!gameOver || currentPlayer%2 == 1){
			ws.setRandomWind();
			StdDraw.show(1);
			reset();
			this.setGorillaTitle(currentPlayer);
			int windLevel = (int)(this.ws.getWind()*10);
			displayText("Scores: " + this.players[0].getScore() + " : " + 
					this.players[1].getScore() + "\tCurrent Wind Level: " + Math.abs(windLevel) +
					(windLevel > 0? "(L to R)" : "(R to L)"), 0.85);
			StdDraw.show();
			if(firstTime){
				this.scoresToWin = (int)this.requestInputNumber("What is the winning score (default == 1)?",
						"Play to how many total points?");
				firstTime = false;
			}
			players[currentPlayer].play();
			Banana curBanana = players[currentPlayer].getBanana();
			while(!curBanana.isBananaOffScreen() && !curBanana.isHitBuilding() && !curBanana.isHitPlayer()){
				StdDraw.show(1);
				reset();
				this.setGorillaTitle(currentPlayer);
				curBanana.draw();
				StdDraw.show();
				StdDraw.pause(50);
			}
			StdDraw.show();
			if(curBanana.isBananaOffScreen()){
				displayText("It missed!", 0.9);
			}
			else if(curBanana.isHitBuilding()){
				displayText("It hit a building!", 0.9);
			}
			else{
				displayText("Gorilla on the " + (this.currentPlayer == 0? "left" : "right") + "won a point!", 0.9);
				if(this.getCurrentPlayer().getScore() == this.scoresToWin){
					gameOver = true;
				}
				this.initializeBuildings();
				this.initializePlayers(false);
				changeLandscape = true;
			}
			StdDraw.show();
			if(changeLandscape){
				StdDraw.pause(500);
			}
			else{
				StdDraw.pause(2000);
			}
			changeLandscape = false;
			currentPlayer = (currentPlayer + 1)%2;
		}
		StdDraw.show();
		if(players[0].getScore() > players[1].getScore()){
			displayText("Gorilla on the left won!", 0.7, Color.RED);
		}
		else if(players[0].getScore() < players[1].getScore()){
			displayText("Gorilla on the right won!", 0.7, Color.RED);
		}
		else{
			displayText("Tie game!", 0.7, Color.RED);
		}
		displayText("GAME OVER! Please play again.", 0.65, Color.RED);
		StdDraw.show();
		StdDraw.pause(5000);
		System.exit(0);
	}
	
	/**
	 * 
	 * @return the wind speed
	 */
	public Wind getWind(){
		return ws;
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public Gorilla getEnemyGorilla(int player){
		int enemyNb = (player + 1)%2;
		return players[enemyNb];
	}
	
	private void initializeBuildings(){
		StdDraw.setCanvasSize(canvasWidth, canvasHeight);
		double xpos = 0;
		double ypos = 0;
		double totalWidth = 0;
		for(int i = 0; i < buildings.length; i++){
			Building b = new Building();
			b.setPos(new Position(xpos, ypos));
			b.setWidth(Math.random()+0.1);
			Color c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
			b.setColor(c);
			b.setHeight(0.2 + Math.random()*0.4);
			xpos += b.getWidth();
			totalWidth += b.getWidth();
			buildings[i] = b;
		}
		xpos = 0;
		for(int i = 0; i < buildings.length; i++){
			buildings[i].getPos().setXpos(xpos);
			buildings[i].setWidth(buildings[i].getWidth()/totalWidth);
			xpos += buildings[i].getWidth();
		}
	}
	
	private void initializePlayers(boolean newGorilla){
		int b1 = (int)Math.round(4*Math.random());
		if(b1 == 0){
			b1 = 1;
		}
		int b2 = (int)Math.round(5+4*Math.random());
		if(b2 == 9){
			b2 = 8;
		}
		Position pos1 = new Position();
		pos1.setXpos(buildings[b1].getPos().getXpos() + buildings[b1].getWidth()/2);
		pos1.setYpos(buildings[b1].getHeight());
		Position pos2 = new Position();
		pos2.setXpos(buildings[b2].getPos().getXpos() + buildings[b2].getWidth()/2);
		pos2.setYpos(buildings[b2].getHeight());
		if(newGorilla){
			players[0] = new Gorilla(this, pos1, 0);
			players[1] = new Gorilla(this, pos2, 1);
		}
		else{
			players[0].setPosition(pos1);
			players[1].setPosition(pos2);
		}
		players[0].setLaunchBuilding(buildings[b1]);;
		players[1].setLaunchBuilding(buildings[b2]);
	}
	
	public void reset(){
		StdDraw.clear();
		gameOver = false;
		for(int i = 0; i < buildings.length; i++){
			buildings[i].draw();
		}
		for(int i = 0; i < players.length; i++){
			players[i].draw();
		}
	}
	 
	/**
	 * 
	 * @return
	 */
	public Gorilla getCurrentPlayer(){
		return players[this.currentPlayer];
	}
	
	/**
	 * 
	 * @param playerNumber
	 */
	public void setGorillaTitle(int playerNumber){
		if(playerNumber == 1){
			displayText("Gorilla on the right is throwing!", 0.8);
		}
		else{
			displayText("Gorilla on the left is throwing!", 0.8);
		}
	}
	
	/**
	 * 
	 * @param str text message
	 * @param ypos position of the text
	 */
	public void displayText(String str, double ypos){
		Color prevColor = StdDraw.getPenColor();
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(0.5, ypos, str);
		StdDraw.setPenColor(prevColor);
	}
	
	public void displayText(String str, double ypos, Color c){
		Color prevColor = StdDraw.getPenColor();
		StdDraw.setPenColor(c);
		StdDraw.text(0.5, ypos, str);
		StdDraw.setPenColor(prevColor);
	}
	
	/**
	 * 
	 * @return the width of canvas
	 */
	public int getCanvasWidth(){
		return canvasWidth;
	}
	
	/**
	 * 
	 * @param canvasWidth the canvas width
	 */
	public void setCanvasWidth(int canvasWidth){
		this.canvasWidth = canvasWidth;
	}
	
	/**
	 * 
	 * @return the height of canvas
	 */
	public int getCanvasHeight(){
		return canvasHeight;
	}
	
	/**
	 * 
	 * @param canvasHeight the canvas height
	 */
	public void setCanvasHeight(int canvasHeight){
		this.canvasHeight = canvasHeight;
	}
	
	/**
	 * 
	 * @return random building landscape
	 */
	public Building[] getBuildings(){
		return buildings;
	}
	
	public static void main(String[] args){
		new Gameboard();
	}
	
	/**
	 * 
	 * @param prompt
	 * @param title
	 * @return
	 */
	public double requestInputNumber(String prompt, String title){
		double number = 0;
		Boolean invalidEntry = true;
		while(invalidEntry){
			try{
				String entry = JOptionPane.showInputDialog(null, prompt, title, JOptionPane.PLAIN_MESSAGE);
				number = Double.parseDouble(entry);
				invalidEntry = false;
			}
			catch(NumberFormatException e){
				System.out.println("Entry was not valid!");
			}
			catch(NullPointerException e){
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.text(0.5, 0.5, "Quitting game");
				StdDraw.pause(2000);
				System.exit(0);
			}
		}
		return number;
	}
}
