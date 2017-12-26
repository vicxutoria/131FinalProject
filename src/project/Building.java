package project;

import java.awt.Color;

import sedgewick.StdDraw;

public class Building implements GameItem{

	private Position pos;
	private double width;
	private double height;
	private Color color;
	
	public Building(){	
	}
	
	public void draw(){
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(pos.getXpos() + width/2, pos.getYpos() + height/2, width/2, height/2);
	}
	
	public Position getPos(){
		return pos;
	}
	
	public void setPos(Position pos){
		this.pos = pos;
	}
	
	public double getWidth(){
		return width;
	}
	
	public void setWidth(double width){
		this.width = width;
	}
	
	public double getHeight(){
		return height;
	}
	
	public void setHeight(double height){
		this.height = height;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	@Override
	public Position getPosition(){
		return pos;
	}
	
}
