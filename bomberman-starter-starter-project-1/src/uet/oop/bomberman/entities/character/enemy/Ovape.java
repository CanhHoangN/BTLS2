/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.entities.character.enemy;

/**
 *
 * @author hoan
 */
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium_Ovape;
import uet.oop.bomberman.graphics.Sprite;

public class Ovape extends Enemy {
	
	public Ovape(int x, int y, Board board) {
		super(x, y, board, Sprite.ovape_dead, 1.0, 1000);
		
		_sprite = Sprite.ovape_left1;
		
		_ai = new AIMedium_Ovape(_board.getBomber(), this);
		_direction  = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.ovape_right1, Sprite.ovape_right2, Sprite.ovape_right3, _animate, 60);
				else
					_sprite = Sprite.ovape_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.ovape_left1, Sprite.ovape_left2, Sprite.ovape_left3, _animate, 60);
				else
					_sprite = Sprite.ovape_left1;
				break;
		}
	}
}