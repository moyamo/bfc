package com.moyamo.bfc.entities;

import java.net.URI;
import java.util.LinkedList;
import java.util.Queue;

import com.moyamo.bfc.logic.GameEngine;

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
	private int x;
	private int y;
	private int xSpeed;
	private int ySpeed;
	private int width;
	private int height;
	private int direction;
	private boolean moving;

	public Entity (int x, int y, int width, int height, int direction) {
		this.x         = x;
		this.y         = y;
		this.height    = height;
		this.width     = width;
		this.direction = direction;
		this.events = new LinkedList<Object>();
		this.drawEvents = new LinkedList<String>();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	protected int getHeight() {
		return height;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
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
	protected void setXSpeed(int x){
		this.xSpeed = x;
	}
	protected void setYSpeed(int y){
		this.ySpeed = y;
	}
	
	protected int getXSpeed(){
		return xSpeed;
	}
	
	protected int getYSpeed(){
		return ySpeed;
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
