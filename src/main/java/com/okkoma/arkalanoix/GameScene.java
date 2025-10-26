package com.okkoma.arkalanoix;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

public class GameScene extends Pane {
    private Canvas canvas_;
    private GraphicsContext gc_;
    private GameUI ui_;
    
    private Paddle paddle_;
    private ArrayList<Ball> balls_;
    private Level level_;
    private Life life_;
    private Score score_;
    private ArrayList<Bonus> activeBonuses_ = new ArrayList<>();
    
    private boolean leftPressed_ = false;
    private boolean rightPressed_ = false;
    private boolean spacePressed_ = false;
    
    private boolean isPlaying_ = false;
    
    public static int screenWidth = 800;
    public static int screenHeight = 600;
    
    public GameScene(StackPane root) {
        	
    	// Créer l'UI de game over
    	ui_ = new GameUI(this, root);
    	        
        canvas_ = new Canvas(screenWidth, screenHeight);
        gc_ = canvas_.getGraphicsContext2D();
        this.getChildren().add(canvas_);

        life_ = new Life(3);
        score_ = new Score(0);
        level_ = new Level(this);
        balls_ = new ArrayList<Ball>();
        //level_.set(2);
        
        setFocusTraversable(true);

        // togglePause if unfocus
        focusedProperty().addListener((obs, oldVal, newVal) -> {
        	if (newVal == false && isPlaying_) {
        		System.out.println("Focus changed : " + newVal);
        		togglePause();
        	}
        });
    
        // Gestion des touches pressées
        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                case KP_LEFT:
                case Q:
                    leftPressed_ = true;
                    break;
                case RIGHT:
                case KP_RIGHT:
                case D:
                    rightPressed_ = true;
                    break;
                case SPACE:
                	spacePressed_ = true;
                	break;
                case M:
                	applyBonus(10);
                	break;
                case W:
                	gameWin();
                	break;
                case ESCAPE:
                	togglePause();
                	break;
                default: break;
            }
        });

        // Gestion des touches relâchées
        setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                case KP_LEFT:
                case Q:
                    leftPressed_ = false;
                    break;
                case RIGHT:
                case KP_RIGHT:
                case D:
                    rightPressed_ = false;
                    break;
                case SPACE:
                	spacePressed_ = false;
                	break;                    
                default: break;
            }
        });
    
        // Animation du jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
            	
            	if (!isPlaying_)
        			return;
            	
                update();
                render();
            }
        }.start();
    }
    
    public void addBonus(double x, double y, int type) {
    	
        addBonus(new Bonus(x, y, 20, 20, type));   	
    }
    
    private void applyBonus(int type) {
    	
    	switch (type) {
    	case 0: // Reduit la raquette
    		paddle_.setWidth(paddle_.getWidth() / 1.5);    		
    		break;
    	case 1: // Agrandit la raquette
    		paddle_.setWidth(paddle_.getWidth() * 1.5);
    		break;
    	case 2: // Ajoute une vie
    		life_.add();
    		break;
    	case 3: // Colle à la raquette
    		paddle_.setSticky(true);
    		break;
    	case 4: // Décolle de la raquette
    		paddle_.setSticky(false);
    		for (Ball ball : balls_)
    			ball.unstick();
    		break;
    	case 5: // réduit la vitesse de la raquette
    		paddle_.setSpeed(paddle_.getSpeed() / 1.5f);
    		break;
    	case 6: // augmente la vitesse de la raquette
    		paddle_.setSpeed(paddle_.getSpeed() * 1.5f);
    		break;
    	case 7: // réduit la vitesse de la balle
    		for (Ball ball : balls_)
    			ball.setSpeed(ball.getSpeed() / 1.5f);
    		break;
    	case 8: // augmente la vitesse de la balle
    		for (Ball ball : balls_)
    			ball.setSpeed(ball.getSpeed() * 1.5f);
    		break;
    	case 9: // Balle d'acier
    		for (Ball ball : balls_)
    			ball.setSteel(10);
    		break;
    	case 10: // Multi-balle
    		balls_.add(new Ball(balls_.getFirst().getX(), balls_.getFirst().getY(), 10., Color.WHITE));
    		balls_.getLast().setDx(-balls_.getFirst().getDx());
    		break;    		
    	}
    	
    	if (type < Bonus.BonusType.Max)
    		System.out.println("Apply Bonus : " + Bonus.BonusType.Names[type] + "  " + type);
    }

    private void update() {
    	
        if (leftPressed_) 
            paddle_.move(-1);
        
        if (rightPressed_) 
            paddle_.move(1);
        
        for (Ball ball : balls_) {
            
        	if (spacePressed_ && ball.isStickOn(paddle_))
                ball.unstick();
            
        	ball.move();
    
	        if (!ball.isStickOn(paddle_) && ball.intersects(paddle_))
	        {
	        	ball.bounce(1);
	        	if (paddle_.isSticky())
	        		ball.stickOn(paddle_, false);
	        	else
	        		ball.putOver(paddle_);        	
	        }
        }
        
        // Faire tomber les bonus
        for (Bonus bonus : activeBonuses_) {
        	if (bonus.isDestroyed())
        		continue;
        	
            bonus.fall();

            // Vérifier si le bonus est récupéré par la raquette
            if (bonus.intersects(paddle_)) {
                applyBonus(bonus.getType());
                bonus.destroy();
            }

            // Supprimer les bonus qui sortent de l'écran
            if (bonus.getY() > GameScene.screenHeight) {
                bonus.destroy();
            }
        }

        if (level_.update(score_, balls_))
        	gameWin();
        
        activeBonuses_.removeIf(Bonus::isDestroyed);
        balls_.removeIf(Ball::isDestroyed);
        
        if (balls_.size() == 0) 
        {
        	life_.remove();
        	if (life_.get() < 1)
        		gameOver();
        	else
        		start();
        }
    }

    private void render() {    	
    	
    	gc_.setFill(Color.BLACK);
        gc_.fillRect(0, 0, screenWidth, screenHeight);
        
        level_.draw(gc_);
        
        for (Ball ball : balls_)
        	ball.draw(gc_);
        
        paddle_.draw(gc_);
        
        for (Bonus bonus : activeBonuses_) {
        	bonus.draw(gc_);
        }
        
        score_.draw(gc_);
        life_.draw(gc_);
    }
    
    private void gameOver() {
    	
    	isPlaying_ = false;
    	System.out.println("Game Over!");
    	if (ui_.gameMenu_ != null)
    		ui_.gameMenu_.setState(0);    	
    	if (ui_.gameOver_ != null)
    		ui_.gameOver_.show();    
    }
    
    private void gameWin() {
    	
    	isPlaying_ = false;
    	System.out.println("Game Win!");
    	if (ui_.gameWin_ != null)
    		ui_.gameWin_.show();
    }

    public void addBonus(Bonus bonus) {
    	activeBonuses_.add(bonus);
    }
    
    public void togglePause() {
    	
    	isPlaying_ = !isPlaying_;
    	if (ui_.gameMenu_ != null) {
    		if (!isPlaying_) {
    			ui_.gameMenu_.show();
    		} else {
    			ui_.gameMenu_.hide();
    			requestFocus(); // Demande le focus
    		}
    	}
    }
    
    private void start() {
    	
        System.out.println("New ball.");
        balls_.clear();
        balls_.add(new Ball(0., 0., 10., Color.WHITE));
        balls_.getFirst().stickOn(paddle_, true);
        isPlaying_ = true;
        
        requestFocus(); // Reprendre le focus        
    }
    
    public void nextLevel() {
    	
    	System.out.println("Level Finished!");
    	level_.next();
    	
        start();
    }
    
    public void restartGame() {
        // Réinitialiser le jeu
        life_ = new Life(3);
        score_ = new Score(0);
        level_.reset();
        paddle_ = new Paddle(350., 550., 100., 10., Color.ALICEBLUE);
        activeBonuses_.clear();
        
    	if (ui_.gameMenu_ != null)
    		ui_.gameMenu_.setState(1);
    	
        start();
    }    
    
}