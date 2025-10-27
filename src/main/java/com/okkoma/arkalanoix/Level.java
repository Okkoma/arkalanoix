package com.okkoma.arkalanoix;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Level {
	
	int levelId_;
	Brick[] bricks_;
	GameScene gameScene_;
	
    private static Map<Integer, LevelData> levelPatterns;

    static {
        try {
            levelPatterns = LevelLoader.loadLevels();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public Level(GameScene gameScene) {
		set(1);
		gameScene_ = gameScene;
	}

	public void set(int level) {
		levelId_ = level;
		
		createBricks();
	}
	
	public void reset() {
		
        for (Brick brick : bricks_) {
            if (brick != null) {
                brick.reset();
            }
        }
	}
	
	public void next() {
		set(levelId_+1);
	}

	private LevelData generateRandomLevel( int cols, int rows) {
		
		Random random = new Random();
		
		if (cols == 0)
			cols = (int) (6 + Math.floor(random.nextDouble() * 3.));		
		if (rows == 0)
			rows = (int) (5 + Math.floor(random.nextDouble() * 5.));
		
		int[][] patterns = new int[rows][cols];	    
	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            double rand = random.nextDouble();
	            if (rand < 0.1) {
	            	patterns[i][j] = 0; // Vide
	            } else if (rand < 0.7) {
	            	patterns[i][j] = 1; // Normale
	            } else if (rand < 0.9) {
	            	patterns[i][j] = 2; // Résistante
	            } else {
	            	patterns[i][j] = 3; // Bonus
	            }
	        }
	    }
	    
	    LevelData levelData = new LevelData();
		levelData.setRows(rows);
		levelData.setCols(cols);
		levelData.setPattern(patterns);
		
	    return levelData;
	}
	
	private void createBricks() {
		
	    LevelData levelData = levelPatterns.get(levelId_);
        if (levelData == null)
            levelData = generateRandomLevel(0 , 0);
        
	    final int rows = levelData.getRows();
	    final int cols = levelData.getCols();
	    final int spacex = 5;
	    final int spacey = 5;
	    final int brickWidth = 80;
	    final int brickHeight = 20;
	    final int bricksWidth = cols * brickWidth + (cols-1) * spacex;
	    final int borderx = (GameScene.screenWidth - bricksWidth) / 2;
	    final int bordery = 50;
	    
	    bricks_ = new Brick[rows * cols];

	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            int brickType = levelData.getPattern()[i][j];
	            if (brickType > 0) {
	                Color color = getBrickColor(i, j, brickType);
	                bricks_[i * cols + j] = new Brick(
	                    j * (brickWidth + spacex) + borderx,
	                    i * (brickHeight + spacey) + bordery,
	                    brickWidth,
	                    brickHeight,
	                    color,
	                    brickType
	                );
	            }
	        }
	    }
	}
		
	private Color getBrickColor(int row, int col, int type) {
		if (type == 1 || type == 3) 
			return Color.rgb((row * 25) % 256, (90 + col * 20) % 256, 200);
		else if (type == 2) {
			int gradGray = Math.clamp(100 + row * col, 0, 255);
			return Color.rgb(gradGray, gradGray, gradGray);
		} else 
			return Color.GRAY;
	}
	
	public boolean update(Score score, ArrayList<Ball> balls) {
		
		int numDestroyedBricks = 0;
		boolean intersectFound = false;
		Random random = new Random();
		
		for (Brick brick : bricks_) {
			
			if (brick == null || brick.isDestroyed())
				numDestroyedBricks++;
			
			else if (!intersectFound) {
				
				for (Ball ball : balls) {
		
					if (brick.intersects(ball)) {
						brick.hit();
						if (ball.getSteel() == 0)
							ball.bounce(brick.getCollisionDirection(ball));

						intersectFound = true;

			            // Si la brique est de type bonus (3), créer un bonus
						if (brick.isDestroyed()) {
							score.increase(100);
							numDestroyedBricks++;
							if (brick.getType() == 3 || brick.getType() == 4) {
								gameScene_.addBonus(brick.getRect().x, brick.getRect().y, random.nextInt(0, Bonus.BonusType.Max));
							}
			            }
					}
				}
			}
		}

		return bricks_.length == numDestroyedBricks;
	}

	public void draw(GraphicsContext gc) {
		
		for (Brick brick : bricks_) {
			if (brick != null && !brick.isDestroyed()) {
				brick.draw(gc);
			}
		}
		
    	gc.setTextAlign(TextAlignment.LEFT);
    	gc.setFont(Font.font("Arial", 18));
    	gc.setFill(Color.WHITE);
    	gc.fillText(Integer.toString(levelId_), 4, 22);
	} 
}
