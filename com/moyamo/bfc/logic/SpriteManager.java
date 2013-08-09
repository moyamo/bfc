package com.moyamo.bfc.logic;

import java.util.ArrayList;
import java.util.List;

import com.moyamo.bfc.desktop.gui.sprites.IDrawable;

public class SpriteManager {
	private List<IDrawable> spriteArray = new ArrayList<IDrawable>();
	private static SpriteManager instance;
	
	private SpriteManager() {}
	
	public static SpriteManager self(){
		if (instance == null) {
			instance = new SpriteManager();
		}
		return instance;
	}
	
	public List<IDrawable> getSprites(){
		for (int i = 0; i < spriteArray.size(); i++){
			if (spriteArray.get(i).destroyed()){
				spriteArray.remove(i);
			}
		}
		return spriteArray;
	}
	
	public void addSprite(IDrawable sprite){
		spriteArray.add(sprite);
	}
}
