package com.moyamo.bfc.model.entities;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;

/**
 * An Entity is an object in the game. It is a sprite without a picture. The 
 * reason for having this class is to make it easier to port this program to
 * different systems.
 *  
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.2
 *
 */
public class Entity {
	private Queue<Object> events;
	private Queue<String> drawEvents;
	private Point2D position;
	private Point2D speed;
	private int width;
	private int height;
	private int direction;
	private boolean moving;

	public Entity (double x, double y, int width, int height, int direction) {
		position = new Point2D.Double(x, y);
		speed = new Point2D.Double();
		this.height    = height;
		this.width     = width;
		this.direction = direction;
		this.events = new LinkedList<Object>();
		this.drawEvents = new LinkedList<String>();
	}
	
	public int getX(){
		return (int) position.getX();
	}
	
	public int getY(){
		return (int) position.getY();
	}
	
	public int getWidth() {
		return width;
	}
	
	protected int getHeight() {
		return height;
	}
	
	protected void setX(double x){
		this.position.setLocation(x, position.getY());
	}
	
	protected void setY(double y) {
		this.position.setLocation(position.getX(), y);
	}
	
	protected void incX(double x){
		this.position.setLocation(position.getX() + x, position.getY());
	}
	
	protected void incY(double y){
		this.position.setLocation(position.getX(), position.getY() + y);
	
	}
	public int getDirection() {
		return direction;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	protected void setWidth(int width) {
		this.width = width;
	}
	
	protected void setHeight(int height) {
		this.height = height;
	}

	protected void setDirection(byte direction) {
		this.direction = direction;
	}
	
	protected void setMoving(boolean moving) {
		this.moving = moving;
	}
	protected void setXSpeed(double x){
		this.speed.setLocation(x, speed.getY());
	}
	protected void setYSpeed(double y){
		this.speed.setLocation(speed.getX(), y);
	}
	
	protected double getXSpeed(){
		return speed.getX();
	}
	
	protected double getYSpeed(){
		return speed.getY();
	}
	protected void addEvent(Object e){
		events.add(e);
	}
	public String nextDrawEvent(){
		return drawEvents.poll();
	}
	
	protected void addDrawEvent(String e){
		drawEvents.add(e);
	}
	
	public Object nextEvent(){
		return events.poll();
	}
}
