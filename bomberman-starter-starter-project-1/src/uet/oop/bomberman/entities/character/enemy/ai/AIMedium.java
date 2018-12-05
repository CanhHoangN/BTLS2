package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
	double xNow;
        double xOld;
        double yNow;
        double yOld;
	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
                this.xNow = e.getXTile();
                this.xOld = e.getXTile();
                this.yNow = -1;
                this.yOld = -1;
	}

    
        
	@Override
	public int calculateDirection() {
                current();
		if(!checkNotMove()){
                     return move();
                }
                after();
                
                return - 1;
	}
        private boolean checkNotMove(){
            return xNow == xOld && yNow == yOld;
            
        }
    
    protected int move() {
                
                if (random.nextInt(2)!=0){
                    if(_bomber.getXTile() < _e.getXTile())
                                return 3;
                        if(_bomber.getXTile() > _e.getXTile())
                                return 1;
                }
                else {
                    if(_bomber.getYTile() <= _e.getYTile())
			return 0;
                    if (_bomber.getYTile() > _e.getYTile())
			return 2;
                }
                return random.nextInt(4);
    }
    private AIMedium() {
        
    }
    private void current(){
         xNow = _e.getXTile();
         yNow = _e.getYTile();
    }
     private void after(){
         xOld = _bomber.getXTile();
         yOld = _bomber.getYTile();
    }
	

}
