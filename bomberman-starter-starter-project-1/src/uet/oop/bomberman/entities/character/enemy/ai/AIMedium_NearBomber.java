/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

/**
 *
 * @author hoan
 */
public class AIMedium_NearBomber extends AIMedium{

    public AIMedium_NearBomber(Bomber bomber, Enemy e) {
		super(bomber,e);
    }
    public int calculateDirection() {
        if(Math.abs(_bomber.getXTile() - _e.getXTile())< 4||Math.abs(_bomber.getYTile() - _e.getYTile()) < 5)
            return super.calculateDirection(); 
        return random.nextInt(4);
            
    }
        
}
