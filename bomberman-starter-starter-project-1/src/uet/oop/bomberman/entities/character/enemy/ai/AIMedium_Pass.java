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
import uet.oop.bomberman.entities.character.enemy.Pass;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.level.Coordinates;

/**
 *
 * @author hoan
 */
public class AIMedium_Pass extends AIMedium_Pontan {

    int stepSafe[][];
    Pos posSafe = new Pos(-1, -1);

    public AIMedium_Pass(Bomber bomber, Enemy e, Board board) {
        super(bomber, e, board);
        stepSafe = new int[X][Y];
    }

    @Override
    protected void loadStep() {
        xE = _e.getXTile();
        yE = _e.getYTile();
        xB = _bomber.getXTile();
        yB = _bomber.getYTile();
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                Entity e = _board.getEntity(j, i, _e);
                if (e instanceof Grass  || e instanceof Bomber || e instanceof Enemy) {
                    arrStep[i][j] = -1;
                } else {
                    arrStep[i][j] = 0;
                }
                if (e instanceof LayeredEntity) {
                    Entity eL = ((LayeredEntity) e).getTopEntity();
                    if (eL instanceof Grass ) {
                        arrStep[i][j] = -1;
                    }
                }

            }
        }

        checkBomb();
        arrStep[yB][xB] = 1;

        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                stepSafe[i][j] = arrStep[i][j];
            }
        }
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (arrStep[i][j] == -2) {
                    arrStep[i][j] = 0;
                }
            }
        }
        find();

    }

    void checkBomb() {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                Entity e = _board.getEntity(j, i, _e);
                boolean left, right, up, down;
                left = right = up = down = true;
                if (e instanceof Bomb) {
                    stepSafe[i][j] = 0;
                    arrStep[i][j] = 0;
                    for (int x = 1; x <= Game.getBombRadius(); x++) {
                        if (i + x < X && down) {
                            if (arrStep[i + x][j] == 0 || i + x == yB && j == xB) {
                                down = false;
                            }
                        }
                        if (i - x > 0 && up) {
                            if (arrStep[i - x][j] == 0 || i - x == yB && xB == j) {
                                up = false;
                            }
                        }
                        if (j + x < Y && right) {
                            if (arrStep[i][j + x] == 0 || j + x == xB && i == yB) {
                                right = false;
                            }
                        }
                        if (j - x > 0 && left) {
                            if (arrStep[i][j - x] == 0 || j - x == xB && i == yB  ) {
                                left = false;
                            }
                        }
                        if (i + x < X && down) {

                            arrStep[i + x][j] = -2;

                        }
                        if (j + x < Y && right) {

                            arrStep[i][j + x] = -2;

                        }
                        if (i - x > 0 && up) {

                            arrStep[i - x][j] = -2;

                        }

                        if (j - x > 0 && left) {

                            arrStep[i][j - x] = -2;

                        }
                    }
                }
            }
        }
    }

    void findPosSafe() {
        boolean stop = false;
        boolean change = true;
        boolean isFind = false;
        stepSafe[yB][xB] = -1;
        stepSafe[yE][xE] = 1;

        int count = 1;
        while (!stop && change) {
            change = false;
            for (int i = 1; i < X - 1; i++) {
                for (int j = 1; j < Y - 1; j++) {
                    if (stepSafe[i][j] == count) {
                        if (stepSafe[i + 1][j] < 0) {
                            stepSafe[i + 1][j] = stepSafe[i][j] + 1;
                            change = true;
                            if (arrStep[i + 1][j] == -1) {
                                stop = true;
                                posSafe.setX(i + 1);
                                posSafe.setY(j);
                                isFind = true;
                                break;
                            }
                        }
                        if (stepSafe[i - 1][j] < 0 && !stop) {
                            stepSafe[i - 1][j] = stepSafe[i][j] + 1;
                            change = true;
                            if (arrStep[i - 1][j] == -1) {
                                stop = true;
                                posSafe.setX(i - 1);
                                posSafe.setY(j);
                                isFind = true;
                                break;
                            }
                        }

                        if (stepSafe[i][j + 1] < 0 && !stop) {
                            stepSafe[i][j + 1] = stepSafe[i][j] + 1;
                            change = true;
                            if (arrStep[i][j + 1] == -1) {
                                stop = true;
                                posSafe.setX(i);
                                posSafe.setY(1 + j);
                                isFind = true;
                                break;
                            }
                        }

                        if (stepSafe[i][j - 1] < 0 && !stop) {
                            stepSafe[i][j - 1] = stepSafe[i][j] + 1;
                            change = true;
                            if (arrStep[i][j - 1] == -1) {
                                stop = true;
                                posSafe.setX(i);
                                posSafe.setY(j - 1);
                                isFind = true;
                                break;
                            }
                        }

                    }
                }
            }

            count++;
        }
        if (isFind) {
            return;
        }
        posSafe.setX(-1);
        posSafe.setY(-1);
    }

    int moveSafe() {
        stepSafe[yE][xE] = 1;
        arrStep[yB][xB] = -1;
        findPosSafe();

        findStepSafe();
        if (posSafe.getX() == - 1) {
            return -1;
        }
        if (stepSafe[yE + 1][xE] == stepSafe[yE][xE] - 1) {
            return 2;
        }
        if (stepSafe[yE - 1][xE] == stepSafe[yE][xE] - 1) {
            return 0;
        }
        if (stepSafe[yE][xE + 1] == stepSafe[yE][xE] - 1) {
            return 1;
        }
        return 3;
    }

    @Override
    public int calculateDirection() {
        loadStep();
        if(arrStep[yE][xE] == -1){
           ((Pass)_e).setX(Coordinates.tileToPixel(xE));
            ((Pass)_e).setY(Coordinates.tileToPixel(yE) + Game.TILES_SIZE);
        }
        if (stepSafe[yE][xE] == -2) {
            return moveSafe();
        }
        if (arrStep[yE][xE] == -1) {
            return -1;
        }
        if (arrStep[yE + 1][xE] == arrStep[yE][xE] - 1) {
            if (stepSafe[yE + 1][xE] != -2) {

                return 2;
            } else {
                return -1;
            }
        }
        if (arrStep[yE - 1][xE] == arrStep[yE][xE] - 1) {
            if (stepSafe[yE - 1][xE] != -2) {

                return 0;
            } else {
                return -1;
            }
        }
        if (arrStep[yE][xE + 1] == arrStep[yE][xE] - 1) {
            if (stepSafe[yE][xE + 1] != -2) {

                return 1;
            } else {
                return -1;
            }
        }
        if (stepSafe[yE][xE - 1] == -2) {
            return -1;
        }
        return 3;
    }

    void findStepSafe() {
        if (posSafe.getX() == -1) {
            return;
        }
        stepSafe[yB][xB] = -1;
        stepSafe[yE][xE] = -1;
        stepSafe[posSafe.getX()][posSafe.getY()] = 1;
        boolean stop = false;
        boolean change = true;
        int count = 1;
        while (!stop && change) {
            change = false;
            for (int i = 1; i < X - 1; i++) {
                for (int j = 1; j < Y - 1; j++) {
                    if (stepSafe[i][j] == count) {
                        if (stepSafe[i + 1][j] < 0) {
                            stepSafe[i + 1][j] = stepSafe[i][j] + 1;
                            change = true;
                            if (i + 1 == yE && j == xE) {
                                stop = true;
                            }
                        }
                        if (stepSafe[i - 1][j] < 0 && !stop) {
                            stepSafe[i - 1][j] = stepSafe[i][j] + 1;
                            change = true;
                            if (i - 1 == yE && j == xE) {
                                stop = true;
                            }
                        }

                        if (stepSafe[i][j + 1] < 0 && !stop) {
                            stepSafe[i][j + 1] = stepSafe[i][j] + 1;
                            change = true;
                            if (i == yE && j + 1 == xE) {
                                stop = true;
                            }
                        }

                        if (stepSafe[i][j - 1] < 0 && !stop) {
                            stepSafe[i][j - 1] = stepSafe[i][j] + 1;
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

    class Pos {

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        int x, y;
    }
}
