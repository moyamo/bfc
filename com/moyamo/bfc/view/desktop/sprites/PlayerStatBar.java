package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.nio.ByteBuffer;

import com.moyamo.bfc.view.SpriteManager;

public class PlayerStatBar implements IDrawable{
	private int playerID, x, y;
	private double scale = 0.3;
	
	public PlayerStatBar(int playerID){
		this.playerID = playerID;
		if (playerID == 1) {
			x = 5;
			y = 5;
		} else {
			x = 400;
			y = 5;
		}
	}
	
	@Override
	public void draw(Graphics g, long timeDiff) {
		IDrawablePlayer p = (IDrawablePlayer) SpriteManager.self().getSprites().get(playerID);
		int health = p.getHealth();
		int momentum = p.getMomentum();
		g.setColor(Color.RED);
		g.drawString("Player " + playerID, x, y + 7);
		g.fillRect(x, y + 10, (int) (health*scale), 5);
		g.setColor(Color.BLUE);
		g.fillRect(x, y + 17, (int)(momentum*scale), 5);
	}

	@Override
	public boolean destroyed() {
		return false;
	}

	@Override
	public void update(ByteBuffer buffer) {}

}
