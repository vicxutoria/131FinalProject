package project;

import sedgewick.StdDraw;

public class Gorilla implements GamePlayer{

	private Position position;
	private Gameboard gameboard;
	private int ownPlayerNb;
	private Banana banana;
	private Building launchBuilding;
	private String imageFile = "labs/project/gorilla.png";
	private int widthInPixel = 24;
	private int heightInPixel = 42;
	private int score;

	/**
	 * 
	 * @param gameboard
	 * @param position
	 * @param playerNb
	 */
	public Gorilla(Gameboard gameboard, Position position, int playerNb){
		this.position = position;
		this.gameboard = gameboard;
		this.ownPlayerNb = playerNb;
	}

	@Override
	public void play(){
		gameboard.setGorillaTitle(ownPlayerNb);
		double angle = 0, speed = 0;
		String title = "Gorilla on the left";
		if(ownPlayerNb == 1){
			title = "Gorilla on the right";
		}
		while(angle <= 0 || angle >= Math.PI/2)
			angle = Math.toRadians(gameboard.requestInputNumber("Angle (between 0 and 90): ", title));
		while(speed <= 0 || speed >= 400){
			speed = gameboard.requestInputNumber("Velocity (between 0 and 400): ", title);
			double vx = Math.cos(angle)*speed;
			double vy = Math.sin(angle)*speed;
			if(ownPlayerNb == 1){
				vx = -vx;
			}
			banana = new Banana(gameboard, vx, vy);
			Position newPos = new Position();
			newPos.setXpos(this.getPosition().getXpos() + this.getWidth());
			newPos.setYpos(this.getPosition().getYpos() + this.getHeight());
			banana.setPosition(newPos);
		}
	}

	public void draw(){
		double x = this.getPosition().getXpos();
		double y = this.getPosition().getYpos() + this.getHeight()/2;
		StdDraw.picture(x,  y,  this.imageFile);
	}

	public Position getPosition(){
		return position;
	}

	public double getWidth(){
		return widthInPixel/(double)gameboard.getCanvasWidth();
	}

	public double getHeight(){
		return heightInPixel/(double)gameboard.getCanvasHeight();
	}

	@Override
	public int getPlayerNumber(){
		return ownPlayerNb;
	}
	
	public Gameboard getGameboard(){
		return gameboard;
	}
	
	public void setGameboard(Gameboard gameboard){
		this.gameboard = gameboard;
	}

	public int getOwnPlayerNb(){
		return ownPlayerNb;
	}

	public void setOwnPlayerNb(int ownPlayerNb){
		this.ownPlayerNb = ownPlayerNb;
	}

	public Banana getBanana(){
		return banana;
	}

	public void setBanana(Banana banana){
		this.banana = banana;
	}

	public Building getLaunchBuilding(){
		return launchBuilding;
	}

	public void setLaunchBuilding(Building launchBuilding){
		this.launchBuilding = launchBuilding;
	}

	public void setScore(int score){
		this.score = score;
	}

	@Override
	public int getScore(){
		return score;
	}
	
	public void setPosition(Position position){
		this.position = position;
	}
}
