package com.okkomastudio.arkalanoix.core;

import java.util.ArrayList;

public class GameScene {

	public IGameUI ui_;
	public IRenderer renderer_;
	
    public Paddle paddle_;
    public ArrayList<Ball> balls_;
    public Level level_;
    public Life life_;
    public Score score_;
    public ArrayList<Bonus> activeBonuses_ = new ArrayList<>();
    
    public boolean leftPressed_ = false;
    public boolean rightPressed_ = false;
    public boolean spacePressed_ = false;
    public boolean isPlaying_ = false;
    
    public GameScene() {
        life_ = new Life(3);
        score_ = new Score(0);
        level_ = new Level(this);
        balls_ = new ArrayList<Ball>();
    }
    
    public void addBonus(int x, int y, int type) {
    	
        addBonus(new Bonus(x, y, GameContext.getScreenWidth() / 40, GameContext.getScreenWidth() / 40, type));
    }
    
    public void applyBonus(int type) {
    	
    	switch (type) {
    	case 0: // Reduit la raquette
    		paddle_.setWidth(paddle_.getRect().width / 3 * 2);    		
    		break;
    	case 1: // Agrandit la raquette
    		paddle_.setWidth(paddle_.getRect().width * 3 / 2);
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
    		paddle_.setSpeed(paddle_.getSpeed() * 2 / 3);
    		break;
    	case 6: // augmente la vitesse de la raquette
    		paddle_.setSpeed(paddle_.getSpeed() * 3 / 2);
    		break;
    	case 7: // réduit la vitesse de la balle
    		for (Ball ball : balls_)
    			ball.setSpeed(ball.getSpeed() * 2 / 3);
    		break;
    	case 8: // augmente la vitesse de la balle
    		for (Ball ball : balls_)
    			ball.setSpeed(ball.getSpeed() * 3 / 2);
    		break;
    	case 9: // Balle d'acier
    		for (Ball ball : balls_)
    			ball.setSteel(10);
    		break;
    	case 10: // Multi-balle
    		balls_.add(new Ball(balls_.get(0).getRect().x, balls_.get(0).getRect().y, GameContext.getScreenWidth() / 80, Color.WHITE));
    		balls_.get(balls_.size()-1).setDx(-balls_.get(0).getDx());
    		break;    		
    	}
    	
    	if (type < Bonus.BonusType.Max)
    		System.out.println("Apply Bonus : " + Bonus.BonusType.Names[type] + "  " + type);
    }

    public void update() {
    	
        if (leftPressed_) 
            paddle_.move(-1);
        
        if (rightPressed_) 
            paddle_.move(1);
        
        for (Ball ball : balls_) {
            
        	if (spacePressed_ && ball.isStickOn(paddle_)) {
                ball.unstick();
            }

        	ball.move();
    
	        if (!ball.isStickOn(paddle_) && ball.intersects(paddle_)) {
	        	ball.bounce(1);
	        	if (paddle_.isSticky()) {
                    ball.stickOn(paddle_, false);
                } else {
                    ball.putOver(paddle_);
                }
	        }
        }
        
        // Faire tomber les bonus
        for (Bonus bonus : activeBonuses_) {
        	if (bonus.isDestroyed()) {
                continue;
            }

            bonus.fall();

            // Vérifier si le bonus est récupéré par la raquette
            if (bonus.intersects(paddle_)) {
                applyBonus(bonus.getType());
                bonus.destroy();
            }

            // Supprimer les bonus qui sortent de l'écran
            if (bonus.getY() > GameContext.getScreenHeight()) {
                bonus.destroy();
            }
        }

        if (level_.update(score_, balls_))
        	gameWin();
        
        activeBonuses_.removeIf(Bonus::isDestroyed);
        balls_.removeIf(Ball::isDestroyed);
        
        if (balls_.isEmpty())
        {
        	life_.remove();
        	if (life_.get() < 1)
        		gameOver();
        	else
        		start();
        }
    }
    
	public void render() {    	
    	
    	renderer_.setFill(Color.BLACK);
        renderer_.fillRect(0, 0, GameContext.getScreenWidth(), GameContext.getScreenHeight());
        
        if (isPlaying_) {
	        level_.draw(renderer_);
	        
	        for (Ball ball : balls_)
	        	ball.draw(renderer_);
	        
	        paddle_.draw(renderer_);
	        
	        for (Bonus bonus : activeBonuses_) {
	        	bonus.draw(renderer_);
	        }
	        
	        score_.draw(renderer_);
	        life_.draw(renderer_);
        }
    }

    public void gameOver() {
    	
    	isPlaying_ = false;
    	System.out.println("Game Over!");
    	if (ui_.getGameMenuPanel() != null)
    		ui_.getGameMenuPanel().setState(0);    	
    	if (ui_.getGameOverPanel() != null)
    		ui_.getGameOverPanel().show();    
    }
    
    public void gameWin() {
    	
    	isPlaying_ = false;
    	System.out.println("Game Win!");
    	if (ui_.getGameWinPanel() != null)
    		ui_.getGameWinPanel().show();
    }

    public void addBonus(Bonus bonus) {
    	activeBonuses_.add(bonus);
    }
    
    public void togglePause() {
        if (isPlaying_) {
            pause();
        } else {
            resume();
        }
    }

    public void pause() {
        isPlaying_ = false;
        if (ui_.getGameMenuPanel() != null) {
            ui_.getGameMenuPanel().show();
        }
    }

    public void resume() {
        isPlaying_ = true;
        if (ui_.getGameMenuPanel() != null) {
            ui_.getGameMenuPanel().hide();
        }
    }
    
    public void start() {

        if (paddle_ == null) {
            final int paddleWidth = GameContext.getScreenWidth() / 8;
            final int paddleHeight = paddleWidth / 10;
            paddle_ = new Paddle(GameContext.getScreenWidth() / 2, Math.min(3 * level_.getBottomBorder() / 2, GameContext.getScreenHeight() - 50),
                    paddleWidth, paddleHeight, Color.WHITE);
            System.out.println("New paddle.");
        }

        System.out.println("New ball.");
        balls_.clear();
        balls_.add(new Ball(0, 0, GameContext.getScreenWidth() / 80, Color.WHITE));
        balls_.get(0).stickOn(paddle_, true);

        isPlaying_ = true;     
    }
    
    public void nextLevel() {
    	
    	System.out.println("Level Finished!");
        
    	level_.next();
        level_.reset();
        start();
    }
    
    public void restartGame() {

        // Réinitialiser le jeu
        life_ = new Life(3);
        score_ = new Score(0);
        activeBonuses_.clear();
        paddle_ = null;

        if (ui_.getGameMenuPanel() != null) {
            ui_.getGameMenuPanel().setState(1);
        }

        level_.reset();
        start();
    }
}