package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Dall;
import uet.oop.bomberman.entities.character.enemy.KonDoria;
import uet.oop.bomberman.entities.character.enemy.Minvo;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.character.enemy.Ovape;
import uet.oop.bomberman.entities.character.enemy.Pass;
import uet.oop.bomberman.entities.character.enemy.Pontan;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	private static char[][] _map;

	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	public FileLevelLoader() {
		
	}
	@Override
	public void loadLevel(int level) {
                int i = 0 ;
		String path = "levels"+"/"+"Level" + Integer.toString(level)+".txt"; 
                ClassLoader classLoader = getClass().getClassLoader();
	        File file = new File(classLoader.getResource(path).getFile());
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    _level = level;  
                    String[] line = br.readLine().split(" ", 3);
                    
                    _height = Integer.parseInt(line[1]);
                    _width = Integer.parseInt(line[2]);
                    
                    
                    String get = null;
                    _map = new char[_height][_width];
                    while((get = br.readLine()) != null)
                    { 
                         
                       for(int c = 0 ; c < get.length() ; c++)
                        {
                            
                         _map[i][c] = get.charAt(c);
                            
                        }
                        i++;
                     
                        
                    }
                    
                    for(int x = 0 ; x < _height ; x++)
                    {
                        for(int y = 0 ; y < _width; y++)
                        {
                            System.out.print(_map[x][y]);
                        }
                        System.out.println("");
                    }
                          
                     
                  br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
	}

	@Override
	public void createEntities() {
		
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
                switch (_map[y][x]){
                
          		// thêm Wall 
                case '#':               
                      _board.addEntity(x + y * _width,
                        new LayeredEntity(x, y,
                            new Grass(x, y, Sprite.grass),
                            new Wall(x, y, Sprite.wall)
                            )
                    );
                    
                    break;
                // thêm Bomber
                case 'p':
                    
                    _board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
                    Screen.setOffset(0, 0);
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    break;
                // thêm Enemy
                 case '1':
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    _board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    break;
                case '5':
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    _board.addCharacter( new Dall(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    break;
                case '3':
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    _board.addCharacter( new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    break;
                case '4':
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    _board.addCharacter( new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    break;
                case '2':
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    _board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    break;
                case '6':
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    _board.addCharacter( new Ovape(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    break;
                case '7':
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    _board.addCharacter( new Pontan(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    break;
                case '8':
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    _board.addCharacter( new Pass(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                    break;
           		// thêm Brick
                case '*':
                    _board.addEntity(x + y * _width,
                        new LayeredEntity(x, y,
                            new Grass(x, y, Sprite.grass),
                            new Brick(x, y, Sprite.brick)
                            )
                    );
                    break;
                // thêm portal
                case 'x':
                    _board.addEntity(x + y * _width,
                        new LayeredEntity(x, y,
                            new Grass(x ,y, Sprite.grass),
                            new Portal(x, y, Sprite.portal),
                            new Brick(x, y, Sprite.brick)
                        )
                    );
                    break;
                // thêm Item kèm Brick che ph? ? trên
                case 'f':
                    _board.addEntity(x + y * _width,
                        new LayeredEntity(x, y,
                            new Grass(x ,y, Sprite.grass),
                            new FlameItem(x, y, Sprite.powerup_flames),
                            new Brick(x, y, Sprite.brick)
                        )
                    );
                    break;
               case 'b':
                    _board.addEntity(x + y * _width,
                        new LayeredEntity(x, y,
                            new Grass(x ,y, Sprite.grass),
                            new BombItem(x, y, Sprite.powerup_bombs),
                            new Brick(x, y, Sprite.brick)
                        )
                    );
                    break;
                case 's':
                    _board.addEntity(x + y * _width,
                        new LayeredEntity(x, y,
                            new Grass(x ,y, Sprite.grass),
                            new SpeedItem(x, y, Sprite.powerup_speed),
                            new Brick(x, y, Sprite.brick)
                        )
                    );
                    break;
                //thêm grass
                default:
                    _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                    break;

                }
			}
		}
        }
	
        
        public static void main(String args[])
        {
            FileLevelLoader f = new FileLevelLoader();
            f.loadLevel(1);
            f.createEntities();
        }

}
