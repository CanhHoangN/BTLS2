package uet.oop.bomberman.entities.tile.item;

import sound.GameSound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.Tile;
import static uet.oop.bomberman.graphics.IRender.sound;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends Tile {

    	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

    @Override
    public boolean collide(Entity e) {
        sound.getAudio(GameSound.ITEM).play();
        return false;
    }
        
}
