package com.moyamo.bfc.model.entities;

import com.moyamo.bfc.Constants;

public abstract class Projectile extends Entity implements IMovable{

	public Projectile(double x, double y, int width, int height, int direction) {
		super(x, y, width, height, direction);
	}

	@Override
	public void move(long timeDiff) {
		incX(getXSpeed() * timeDiff/1000.0);
		incY(getYSpeed() * timeDiff/1000.0);
		setYSpeed(getYSpeed() + Constants.GRAVITY * timeDiff/1000.0); 
	}
	
}
