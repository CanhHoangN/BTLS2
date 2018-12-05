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
public class AIMedium_Ovape extends AIMedium{
    public AIMedium_Ovape(Bomber bomber, Enemy e) {
		super(bomber,e);
    }
    public int calculateDirection() {
        if(random.nextInt(2) == 0)
            return super.calculateDirection(); 
        return random.nextInt(4);
            
    }
}
