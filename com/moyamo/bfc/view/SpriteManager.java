package com.moyamo.bfc.view;

import java.util.ArrayList;
import java.util.List;

import com.moyamo.bfc.view.desktop.sprites.IDrawable;

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
			if (spriteArray.get(i) != null && spriteArray.get(i).destroyed()){
				spriteArray.set(i, null);
			}
		}
		return spriteArray;
	}
	
	public IDrawable getSprite(int i){
		return spriteArray.get(i);
	}
	
	public int addSprite(IDrawable sprite){
		spriteArray.add(sprite);
		return spriteArray.size() - 1;
	}
}
