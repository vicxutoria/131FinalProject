package project;

public class Position {

	private double xpos;
	private double ypos;
	
	public Position(){
		xpos = 0.0;
		ypos = 0.0;
	}
	
	/**
	 * 
	 * @param x the xposition
	 * @param y the y position
	 */
	public Position(double x, double y){
		xpos = x;
		ypos = y;
	}
	
	/**
	 * 
	 * @return the x position
	 */
	public double getXpos(){
		return xpos;
	}
	
	/**
	 * 
	 * @param xpos x position
	 */
	public void setXpos(double xpos){
		this.xpos = xpos;
	}
	
	/**
	 * 
	 * @return the y position
	 */
	public double getYpos(){
		return ypos;
	}
	
	/**
	 * 
	 * @param ypos y position
	 */
	public void setYpos(double ypos){
		this.ypos = ypos;
	}
	
}
