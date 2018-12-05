package uet.oop.bomberman.entities.character;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.event.KeyEvent;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import sound.GameSound;
import static sound.GameSound.BOMB;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.level.Coordinates;


public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    Random rd = new  Random();
    
    protected int _timeBetweenPutBombs = 30;
    
   
    public Bomber(int x, int y, Board board) {
        super(x, y, board);
         
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
       
       
    }

    

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

   
    private void detectPlaceBomb() {
        
        if(_input.space  && Game.getBombRate() > 0 && _timeBetweenPutBombs < 0 )
        {
           
            int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile( _y  - _sprite.getSize()/2 );
            
            System.out.println(xt*16+": "+yt*16);
            placeBomb(xt, yt);
            sound.getAudio(GameSound.BOMB).play();
            _timeBetweenPutBombs = 30;
            Game.addBombRate(-1);           
            
            
            
             
        }		
	  
    }

    protected void placeBomb(int x, int y) {
        
            Bomb bomb = new Bomb(x, y, _board);
            _board.addBomb(bomb);
            
        
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
                if(!_alive) return;
                 _alive = false;
                 sound.getAudio(GameSound.BOMBER_DIE).play(); 
                 try {
                     Thread.sleep(200);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
		Message msg = new Message("-1 LIVE", getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
        
    }

    @Override
    protected void afterKill() {
                if (_timeAfter > 0) --_timeAfter;
                else {
                    _board.endGame();
                }
    }

    @Override
    protected void calculateMove() {
       
        
                double x = 0, y = 0;
                
		if(_input.up == true) 
                {
                    y--;
                }
		if(_input.down == true) 
                {
                    y++;
                }
		if(_input.left == true)
                {
                    x--;
                }
		if(_input.right == true)
                {
                    x++;
                }
		
		if(x != 0 || y != 0)  {
			move(x * Game.getBomberSpeed(), y * Game.getBomberSpeed());
                       
			_moving = true;
		} else {
                        
			_moving = false;
		}
             
    }

    @Override
    public boolean canMove(double x, double y) {
        
                Entity a = _board.getEntity((_x+x)/Game.TILES_SIZE, ((_y+y)-14)/Game.TILES_SIZE, this);
	               if(!a.collide(this))
				return false;                      
                       
                       a = _board.getEntity((_x+x+9)/Game.TILES_SIZE, (_y+y-14)/Game.TILES_SIZE, this); 
                       if(!a.collide(this))                                                     
			return false;
                                            
                       a = _board.getEntity((_x+x)/Game.TILES_SIZE, (_y+y-1)/Game.TILES_SIZE, this); 
                       if(!a.collide(this))
				return false;
                       
                       a = _board.getEntity((_x+x+9)/Game.TILES_SIZE, (_y+y-1)/Game.TILES_SIZE, this); 
                       if(!a.collide(this))
				return false;
                       
                return true;
    }

    @Override
    public void move(double xa, double ya) {
               
                if(xa > 0) 
                    _direction = 1;
		if(xa < 0) 
                    _direction = 3;
		if(ya > 0) 
                    _direction = 2;
		if(ya < 0) 
                    _direction = 0;
		
                
		if(canMove(0, ya)) {
			_y += ya;
                       
                       // _steps -= 1 + rest;
		}
                
                           
		
		if(canMove(xa, 0)) {
			_x += xa;
                        
		}
                
		/*Entity a = _board.getEntity((_x+2)/Game.TILES_SIZE, (_y-1)/Game.TILES_SIZE, this); 
                       if(!a.collide(this))
                       {
                           _x+=0.5;
                           _y+=0.5;
                       }*/
			
                       		
                
                
    }

    @Override
    public boolean collide(Entity e) {
       
                if(e instanceof Flame) {
                        
                        kill();                       
			return false;
		}	
                
		if(e instanceof Enemy) {
                    
                        
                            kill();
                            
                                           
			return false;
		}
                
                if(e instanceof Item) 
                {
                        (e).remove();
                }
		return false;
        
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
