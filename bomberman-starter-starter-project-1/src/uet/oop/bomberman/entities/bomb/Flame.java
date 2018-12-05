package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];

	
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments();
	}

	
	private void createFlameSegments() {
		
                _flameSegments = new FlameSegment[calculatePermitedDistance()];
                boolean last = false;
                double x = _x;
                double y = _y;
                for(int i = 0 ; i < _flameSegments.length ; i++)
                {
                    if(i == _flameSegments.length-1)
                    {
                        last = true;
                    }
                    switch(_direction){
                        case 0: y--; break;
                        case 1: x++; break;
                        case 2: y++; break;
                        case 3: x--; break;
                    }
                    
                    _flameSegments[i] = new FlameSegment((int)x,(int) y, _direction, last);
                }
                           
	}

	
	private int calculatePermitedDistance() {
		
                int radius = 0;
                double x = _x;
                double y = _y;
                while(radius < _radius)
                {
                    switch(_direction){
                        
                        case 0: y--; break;
                        case 1: x++; break;
                        case 2: y++; break;
                        case 3: x--; break;
                        
                    }
                    
                    Entity e = _board.getEntity(x, y, null);
	           
                    if(e.collide(this) == false)
                        break;
                    
                    radius++;
                }
                
                return radius;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for(int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for(int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		if(e instanceof Brick)
                {
                    ((Brick) e).destroy();
                }
                if(e instanceof Bomber)
                {
                    ((Bomber)e).kill();
                    
                }
		return true;
	}
}
