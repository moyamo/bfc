package com.moyamo.bfc.entities;

import java.util.LinkedList;
import java.util.Queue;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.debug.Out;
import com.moyamo.bfc.events.AttackEvent;
import com.moyamo.bfc.events.InputEvent;
import com.moyamo.bfc.events.InputHandle;
import com.moyamo.bfc.events.PlayerDeathEvent;

/**
 * This is an entity that specifically represents a player.
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.2
 */
public abstract class Player extends Entity implements InputHandle{
	private int health;
	private int speed;
	private int basicAttackDamage;
	private boolean attacking;
	private int reach;	
	private static final int ATTACK_DELAY = 100;
	private long timeSinceAttack = System.currentTimeMillis();
	private Queue<Object> events;
	private int jumpspeed;
	
	Player(int x, int y, int width, int height, int basicAttackDamage,
			byte direction, int health, int reach) {
		super(x, y, width, height, direction);
		setHealth(health);
		this.reach             = reach;
		this.basicAttackDamage = basicAttackDamage;
		this.events = new LinkedList<Object>();
	}
	
	/**
	 * Move the player. This method requires the time since last move as
	 * invoked. This is to make the animation smooth.
	 *  
	 * @param timeDiff - time passed since move was called in milliseconds
	 */
	public void move(long timeDiff){
		int offSet = 0;
		if (isMoving()){
			offSet += getSpeed() * getDirection() * timeDiff / 1000;
		}
		offSet += getXSpeed();
		if (getXSpeed() > 0){
			setXSpeed(getXSpeed() - Constants.FRICTION);
			if (getXSpeed() < 0){
				setXSpeed(0);
			}
		} else {
			setXSpeed(getXSpeed() + Constants.FRICTION);
			if (getXSpeed() > 0){
				setXSpeed(0);
			}
		}
		setX(getX() + (int)offSet);
		int yOffSet = 0;
		yOffSet = getYSpeed();
		setY(getY() + yOffSet);
		if (!onGround()) {
			setYSpeed((int)(getYSpeed() + Constants.GRAVITY*timeDiff/1000));
		} else {
			setYSpeed(0);
		}
	}
	
	/**
	 * Causes the player to do a basic attack. If overided you should super this
	 * method, unless you send your own action event and set attacking true.
	 */
	protected void attack() {
		long currTime = System.currentTimeMillis();
		if (currTime - timeSinceAttack > ATTACK_DELAY){
			setMoving(false);
			setAttacking(true);
			if (getDirection() == 1){
				events.add(new AttackEvent(basicAttackDamage, getX() + reach, getBasicYContact(), getDirection()));
			} else {
				events.add(new AttackEvent(basicAttackDamage,getX() + getWidth() - reach, getY() + 60, getDirection()));
			}
			timeSinceAttack = currTime;
		}
	}

	public void applyAttack(AttackEvent e){
		if (getX() < e.getX() && e.getX() < getX() + getWidth()
		&& getY() < e.getY() && e.getY() < getY() + getHeight()){
			setHealth(getHealth()- e.getDamage());
			setXSpeed(getXSpeed() + e.getDamage()*e.getDirection());
		}
	}
	
	private void jump(){
		if (onGround()){
			setYSpeed(getYSpeed() - getJumpspeed());
		}
	}
	
	private boolean onGround(){
		return getY()+getHeight() >= Constants.GROUND;
	}
	
	/**
	 * Causes the player to stop a basic attack. If overided you should super 
	 * this method, unless you set attacking false.
	 */
	protected void notAttack() { 
		//setAttacking(false);
	}
	
	public void pressEvent(InputEvent e) {
		if (e.getInputString() == InputEvent.LEFT) {
			setDirection((byte) -1);
			setMoving(true);
		}else if (e.getInputString() == InputEvent.RIGHT) {
			setDirection((byte) 1);
			setMoving(true);
		}else if (e.getInputString() == InputEvent.ATTACK1){
			attack();
		}else if (e.getInputString() == InputEvent.UP) {
			jump();
		}
	}
	
	public void releaseEvent(InputEvent e){
		if (e.getInputString() == InputEvent.LEFT ||
			e.getInputString() == InputEvent.RIGHT) {
			setMoving(false);
		}else if (e.getInputString() == InputEvent.ATTACK1){
			notAttack();
		}
	}
	public int getHealth() {
		return health;
	}
	
	/**
	 * Get the speed of the player in pixels/second
	 * 
	 * @return - players speed
	 */
	protected int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the speed of the player in pixels/second
	 * 
	 * @param speed - player speed
	 */
	protected void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public int getReach() {
		return this.reach;
	}

	protected void setReach(int reach) {
		this.reach = reach;
	}

	public int getBasicAttackDamage() {
		return basicAttackDamage;
	}

	private void setBasicAttackDamage(int basicAttackDamage) {
		this.basicAttackDamage = basicAttackDamage;
	}
	
	protected void setHealth(int h){
		if (h <= 0){
			events.add(new PlayerDeathEvent());
		}
		this.health = h;
	}
	
	public Object nextEvent(){
		return events.poll();
	}
	protected abstract int getBasicYContact();

	protected int getJumpspeed() {
		return jumpspeed;
	}

	protected void setJumpspeed(int jumpspeed) {
		this.jumpspeed = jumpspeed;
	}
}
