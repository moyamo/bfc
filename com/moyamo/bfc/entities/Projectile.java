package com.moyamo.bfc.entities;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.moyamo.bfc.debug.Out;

public abstract class Projectile extends Entity implements IMovable{

	public Projectile(int x, int y, int width, int height, int direction) {
		super(x, y, width, height, direction);
	}

	@Override
	public void move(long timeDiff) {
		setX((int)(getX() + getXSpeed() * timeDiff/1000.0));
		setY((int)(getY() + getYSpeed() * timeDiff/1000.0));
	}
	
}
