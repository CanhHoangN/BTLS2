package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Swing Panel hiển thị thông tin th�?i gian, điểm mà ngư�?i chơi đạt được
 */
public class InfoPanel extends JPanel {
	
	private JLabel timeLabel;
	private JLabel pointsLabel;
        private JLabel heart;
        
        
	public InfoPanel(Game game) {
		setLayout(new GridLayout());
		
		timeLabel = new JLabel("Time: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.BLUE);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.BLUE);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);
		
                
               /* ImageIcon img = new ImageIcon(getClass().getResource("/textures/heart_1.png")
                        );
                heart = new JLabel(img);
                heart.setSize(20, 20);
                
                heart.setVisible(true);
                pointsLabel.setHorizontalAlignment(JLabel.CENTER);
                
                add(heart);*/
		add(timeLabel);
		add(pointsLabel);
		
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(0, 40));
	}
	
	public void setTime(int t) {
		timeLabel.setText("Time: " + t);
	}
         
	public void setPoints(int t) {
		pointsLabel.setText("Score: " + t);
	}
	
}
