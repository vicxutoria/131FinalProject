package project;

import sedgewick.StdDraw;

public class Banana implements GameItem{

	private Gameboard gameboard;
	private double vx, vy;
	private Position curPos;
	private final double speedFactor = 0.3;
	private String imageFile = "labs/project/banana.png";
	private int time = 0;
	
	/**
	 * 
	 * @param gameboard
	 * @param vx the velocity in the x direction
	 * @param vy the velocity in the y direction
	 */
	public Banana(Gameboard gameboard, double vx, double vy){
		this.gameboard = gameboard;
		this.vx = vx;
		this.vy = vy;
	}
	
	@Override
	public void draw(){
		time++;
		vy = vy - 9.81*(0.5*time)*speedFactor;
		vx = vx + gameboard.getWind().getWind()*speedFactor;
		curPos.setXpos(curPos.getXpos() + vx*speedFactor/(double)gameboard.getCanvasWidth());
		curPos.setYpos(curPos.getYpos() + vy*speedFactor/(double)gameboard.getCanvasHeight());
		Building b = this.HitAnyBuilding();
		if(b != null){
			if(gameboard.getCurrentPlayer().getOwnPlayerNb() == 0){
				curPos.setXpos(b.getPosition().getXpos());
			}
			else{
				curPos.setXpos(b.getPosition().getXpos() + b.getWidth());
			}
		}
		StdDraw.picture(curPos.getXpos(), curPos.getYpos(), imageFile);
	}
	
	/**
	 * 
	 * @return whether or not the banana got thrown off screen
	 */
	public boolean isBananaOffScreen(){
		return curPos.getXpos() < 0 || curPos.getXpos() > 1 || curPos.getYpos() < 0 || curPos.getYpos() > 1;
	}
	
	/**
	 * 
	 * @return whether or not the banana hit the player
	 */
	public boolean isHitPlayer(){
		Gorilla enemy = gameboard.getEnemyGorilla(gameboard.getCurrentPlayer().getPlayerNumber());
		boolean hit = isHitSomething(enemy);
		if(hit){
			gameboard.getCurrentPlayer().setScore(gameboard.getCurrentPlayer().getScore() + 1);
		}
		return hit;
	}
	
	/**
	 * 
	 * @return whether or not the banana hit the building
	 */
	public boolean isHitBuilding(){
		Building[] buildings = gameboard.getBuildings();
		for(int i = 0; i < buildings.length; i++){
			if(isHitSomething(buildings[i])){
				return true;
			}
		}
		return false;
	}
	
	public Building HitAnyBuilding(){
		Building[] buildings = gameboard.getBuildings();
		for(int i = 0; i < buildings.length; i++){
			if(isHitSomething(buildings[i])){
				return buildings[i];
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param item
	 * @return whether or not the banana hit something
	 */
	public boolean isHitSomething(GameItem item){
		return curPos.getXpos() >= item.getPosition().getXpos() && 
				curPos.getXpos() <= item.getPosition().getXpos() + item.getWidth()
				&& curPos.getYpos() >= item.getPosition().getYpos() &&
				curPos.getYpos() <= item.getPosition().getYpos() + item.getHeight();
	}
	
	@Override
	public Position getPosition(){
		return this.curPos;
	}
	
	/**
	 * 
	 * @param curPos the banana's current position
	 */
	public void setPosition(Position curPos){
		this.curPos = curPos;
	}
	
	@Override
	public double getWidth(){
		return 0;
	}
	
	@Override
	public double getHeight(){
		return 0;
	}
	
}
