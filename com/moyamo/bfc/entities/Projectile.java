package com.moyamo.bfc.entities;

public abstract class Projectile extends Entity implements IMovable{

	public Projectile(double x, double y, int width, int height, int direction) {
		super(x, y, width, height, direction);
	}

	@Override
	public void move(long timeDiff) {
		setX(getX() + getXSpeed() * timeDiff/1000.0);
		setY(getY() + getYSpeed() * timeDiff/1000.0);
	}
	
}
