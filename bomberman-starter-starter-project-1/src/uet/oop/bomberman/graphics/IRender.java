package uet.oop.bomberman.graphics;

import sound.GameSound;

public interface IRender {

	void update();
	public GameSound sound = new GameSound();
	void render(Screen screen);
}
