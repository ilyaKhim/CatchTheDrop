package ru.geekbrains;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {

    private static GameWindow gameWindow;
    private static Image background;
    private static Image gameOver;
    private static Image drop;
    private static float dropLeft = 200;
    private static float dropTop = -100;
    private static long lastFrameTime;
    private static float dropValue = 200;
    private static int score=0;


    public static void main(String[] args) throws IOException {

			background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
			drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
			gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("gameOver.png"));
			gameWindow = new GameWindow();

			 gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			 gameWindow.setBounds(200,100, 906,478);
			 gameWindow.setResizable(false);
			 lastFrameTime = System.nanoTime();
			 GameField gameField = new GameField();
			 gameField.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseReleased(MouseEvent e) {
					 int x = e.getX();
					 int y = e.getY();
					 float dropRight = dropLeft + drop.getWidth(null);
					 float dropBottom = dropTop + drop.getHeight(null);
					 boolean isDrop = (x >= dropLeft && x <= dropRight && y >= dropTop && y <= dropBottom);
					 if(isDrop) {
					 	score++;
					 	gameWindow.setTitle("Score: " + score);
					 	dropTop = -100;
					 	dropLeft =(float) Math.random() * (gameField.getWidth() - drop.getWidth(null));
					 	dropValue = dropValue + 20;
					 }


				 }
			 });

			 gameWindow.add(gameField);
			 gameWindow.setVisible(true);
    }

    private static void onRepaint(Graphics graphics){
    	long currentTime = System.nanoTime();
    	float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
    	lastFrameTime = currentTime;
    	dropTop = dropTop + dropValue * deltaTime;

		graphics.drawImage(background,0,0,null);
		graphics.drawImage(drop, (int)dropLeft, (int)dropTop, null);
		if(dropTop > gameWindow.getHeight()){

			graphics.drawImage(gameOver, 280, 120, null);

		}
	}

	private static class GameField extends JPanel {
    	@Override
		protected void paintComponent (Graphics graphics){
    		super.paintComponent(graphics);
    		onRepaint(graphics);
    		repaint();
		}
	}
}
