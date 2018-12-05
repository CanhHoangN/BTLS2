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
import java.awt.Color;
import java.util.Random;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium_Pass;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Pass extends Enemy{
        
	public Pass(int x, int y, Board board) {
		super(x, y, board, Sprite.pass_dead, 1 , 4000);
		
		_sprite = Sprite.pass_left1;
		
		_ai = new AIMedium_Pass(_board.getBomber(), this, board);
		_direction  = _ai.calculateDirection();
                
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.pass_right1, Sprite.pass_right2, Sprite.pass_right3, _animate, 60);
				else
					_sprite = Sprite.pass_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.pass_left1, Sprite.pass_left2, Sprite.pass_left3, _animate, 60);
				else
					_sprite = Sprite.pass_left1;
				break;
		}
	}
        public void kill() {
            
                 super.kill();
                int x = this.getXTile();
                int y = this.getYTile();
                Random rand = new Random();
                switch(rand.nextInt(3)){
                    case 0:
                        _board.addEntity(x + y * _board.getWidth(),
                            new LayeredEntity(x, y,
                                new Grass(x ,y, Sprite.grass),
                                new SpeedItem(x, y, Sprite.powerup_speed)
                            )
                        );
                        break;
                    case 1:
                        _board.addEntity(x + y * _board.getWidth(),
                            new LayeredEntity(x, y,
                                new Grass(x ,y, Sprite.grass),
                                new BombItem(x, y, Sprite.powerup_bombs)
                            )
                        );
                        break;
                    default:
                        _board.addEntity(x + y * _board.getWidth(),
                            new LayeredEntity(x, y,
                                new Grass(x ,y, Sprite.grass),
                                new FlameItem(x, y, Sprite.powerup_flames)
                            )
                        );
                        
                
               
            }
	}

    public Board getBoard() {
        return _board;
    }

    public double getX() {
        return _x;
    }

    public void setX(double _x) {
        this._x = _x;
    }

    public double getY() {
        return _y;
    }

    public void setY(double _y) {
        this._y = _y;
    }
    
    
        
}