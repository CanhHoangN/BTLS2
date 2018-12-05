/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.destroyable.DestroyableTile;
import uet.oop.bomberman.entities.tile.item.Item;

/**
 *
 * @author hoan
 */
public class AIMedium_Pontan extends AIMedium {

    protected int X;
    Board _board;
    protected int Y;
    int xE, yE, xB, yB;

    protected int arrStep[][];

    public AIMedium_Pontan(Bomber bomber, Enemy e, Board board) {
        super(bomber, e);
        _board = board;
        X = _board.getHeight();
        Y = _board.getWidth();
        arrStep = new int[X + 1][Y + 1];
    }

    protected void loadStep() {
        xE = _e.getXTile();
        yE = _e.getYTile();
        xB = _bomber.getXTile();
        yB = _bomber.getYTile();
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                Entity e = _board.getEntity(j, i, _e);
                if (e instanceof Grass || e instanceof Item || e instanceof Bomber || e instanceof Enemy || e instanceof Portal) {
                    arrStep[i][j] = -1;
                } else {
                    arrStep[i][j] = 0;
                }
                if (e instanceof LayeredEntity) {
                    Entity eL = ((LayeredEntity) e).getTopEntity();
                    if (eL instanceof Brick || e instanceof Item) {
                        arrStep[i][j] = -1;
                    }
                }

            }
        }
        arrStep[yB][xB] = 1;
        find();

    }

    void find() {
        boolean stop = false;
        boolean change = true;
        int count = 1;
        while (!stop && change) {
            change = false;
            for (int i = 1; i < X - 1; i++) {
                for (int j = 1; j < Y - 1; j++) {
                    if (arrStep[i][j] == count) {
                        if (arrStep[i + 1][j] < 0) {
                            arrStep[i + 1][j] = arrStep[i][j] + 1;
                            change = true;
                            if (i + 1 == yE && j == xE) {
                                stop = true;
                            }
                        }
                        if (arrStep[i - 1][j] < 0 && !stop) {
                            arrStep[i - 1][j] = arrStep[i][j] + 1;
                            change = true;
                            if (i - 1 == yE && j == xE) {
                                stop = true;
                            }
                        }

                        if (arrStep[i][j + 1] < 0 && !stop) {
                            arrStep[i][j + 1] = arrStep[i][j] + 1;
                            change = true;
                            if (i == yE && j + 1 == xE) {
                                stop = true;
                            }
                        }

                        if (arrStep[i][j - 1] < 0 && !stop) {
                            arrStep[i][j - 1] = arrStep[i][j] + 1;
                            change = true;
                            if (i == yE && j - 1 == xE) {
                                stop = true;
                            }
                        }

                    }
                }
            }
            count++;
        }
    }

    @Override
    public int calculateDirection() {
        loadStep();
        if (arrStep[yE + 1][xE] == arrStep[yE][xE] - 1) {
            return 2;
        }
        if (arrStep[yE - 1][xE] == arrStep[yE][xE] - 1) {
            return 0;
        }
        if (arrStep[yE][xE + 1] == arrStep[yE][xE] - 1) {
            return 1;
        }
        return 3;

    }

}
