package com.moyamo.bfc.entities;

import com.moyamo.bfc.Constants;
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
public abstract class Player extends Entity implements InputHandle, IMovable{
	private int health;
	private int speed;
	private int basicAttackDamage;
	private int reach;	
	private long timeSinceAttack = System.currentTimeMillis();
	private double jumpspeed;
	private double battleMomentum;
	private double maxBattleMomentum;
	private double battleMomentumRegen;
	
	Player(int x, int y, int width, int height, int basicAttackDamage,
			int direction, int health, int reach) {
		super(x, y, width, height, direction);
		setHealth(health);
		this.reach             = reach;
		this.basicAttackDamage = basicAttackDamage;
	}
	
	/**
	 * Move the player. This method requires the time since last move as
	 * invoked. This is to make the animation smooth.
	 *  
	 * @param timeDiff - time passed since move was called in milliseconds
	 */
	public void move(long timeDiff){
		double offSet = 0;
		if (isMoving()){
			offSet += getSpeed() * getDirection() * timeDiff / 1000;
		}
		offSet += getXSpeed();
		int resistence = onGround() ? Constants.FRICTION : Constants.AIR_RESISTENCE;
		if (getXSpeed() > 0){
				setXSpeed(getXSpeed() - resistence);
			if (getXSpeed() < 0){
				setXSpeed(0);
			}
		} else {
				setXSpeed(getXSpeed() + resistence);
			if (getXSpeed() > 0){
				setXSpeed(0);
			}
		}
		incX(offSet);
		double yOffSet = 0;
		yOffSet = getYSpeed();
		incY(yOffSet);
		if (!onGround()) {
			setYSpeed(getYSpeed() + Constants.GRAVITY*timeDiff/1000);
		} else {
			setYSpeed(0);
			setY(Constants.GROUND-getHeight()); // Prevents players from going below ground;
		}
	}
	
	public void applyPassiveEffects(long timeDiff){
		setMomentum(battleMomentum + (double)(battleMomentumRegen*timeDiff/1000));
	}
	
	/**
	 * Causes the player to do a basic attack. If overided you should super this
	 * method, unless you send your own action event and set attacking true.
	 */
	protected void attack() {
		long currTime = System.currentTimeMillis();
		if (currTime - timeSinceAttack > getAttackDelay()){
			setMoving(false);
			setAttacking();
			if (getDirection() == 1){
				addEvent(new AttackEvent(basicAttackDamage, getX() + reach, getBasicYContact(), getDirection()));
			} else {
				addEvent(new AttackEvent(basicAttackDamage,getX() + getWidth() - reach, getY() + 60, getDirection()));
			}
			timeSinceAttack = currTime;
		}
	}

	public boolean applyAttack(AttackEvent e){
		if (getX() < e.getX() && e.getX() < getX() + getWidth()
		&& getY() < e.getY() && e.getY() < getY() + getHeight()){
			setHealth(getHealth()- e.getDamage());
			setXSpeed(getXSpeed() + e.getDamage()*e.getDirection()/2);
			setMomentum(battleMomentum + battleMomentumRegen/2);
			return true;
		}
		return false;
	}
	
	private void jump(){
		if (onGround()){
			setYSpeed(getYSpeed() - getJumpspeed());
		}
	}
	
	public boolean onGround(){
		return getY()+getHeight() >= Constants.GROUND;
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

	private void setAttacking() {
		addDrawEvent("attack");
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
			addEvent(new PlayerDeathEvent());
		}
		this.health = h;
	}

	protected abstract int getBasicYContact();

	protected double getJumpspeed() {
		return jumpspeed;
	}

	protected void setJumpspeed(double jumpspeed) {
		this.jumpspeed = jumpspeed;
	}
	
	public double getMomentum(){
		return battleMomentum;
	}
	
	protected void setMomentum(double m){
		if (m > maxBattleMomentum){
			this.battleMomentum = maxBattleMomentum;
		} else if (m < 0){
			this.battleMomentum = 0;
		} else {
			this.battleMomentum = m;
		}
	}
	protected void setMaxMomentum(int m){
		this.maxBattleMomentum = m;
	}
	
	protected void setMomentumRegen(int r){
		this.battleMomentumRegen = r;
	}
	abstract protected int getAttackDelay();
	public void onHit(AttackEvent e){}
}
