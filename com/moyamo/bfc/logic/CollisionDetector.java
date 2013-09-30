package com.moyamo.bfc.logic;

import java.util.ArrayList;
import java.util.List;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.debug.Out;
import com.moyamo.bfc.entities.Bullet;
import com.moyamo.bfc.entities.Player;
import com.moyamo.bfc.events.AttackEvent;

public class CollisionDetector {
	private List<Integer> bullets = new ArrayList<Integer>();
	private Player players[];
	
	void checkCollisions(){
		players = EntityStore.self().getCombatants();
		for (int I = 0; I < bullets.size(); I++){
			Integer i = bullets.get(I);
			Bullet b = (Bullet) EntityStore.self().getEntity(i.intValue());
			if (b.getY() > Constants.GROUND){
				delBullet(i);
				b.destroy();
			}
			for(Player p: players){
				boolean hit = p.applyAttack(new AttackEvent(b.getDamage(), b.getX(), b.getY(), b.getDirection()));
				if (hit){
					Out.print("Bullet " + i.intValue() + "Collision");
					delBullet(i.intValue());
					b.destroy();
				}
			}
		}
	}
	
	void addBullet(int id){
		bullets.add(new Integer(id));
	}
	private void delBullet(int id){
		bullets.remove(new Integer(id));
	}
}
