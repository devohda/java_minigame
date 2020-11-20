package frame_panel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
	
public class Help extends JFrame {
	private JPanel helpPanel;
	private JButton game1, game2, game3, game4, game5, back;
	private ButtonListener buttonL;
	private JLabel sentence;
	private ImageIcon image = new ImageIcon("images/sadari.png");
	Help(){
		setPreferredSize(new Dimension(700,400));
		setLayout(null);
		setResizable(false);
		setLocation(350, 200);
		
		
		buttonL = new ButtonListener();
		
		helpPanel = new JPanel();
		helpPanel.setBounds(0, 0, 700, 400);
		helpPanel.setLayout(null);

		
		
		game1 = new JButton("°áÇÕ");
		game1.setBounds(250, 55, 200, 50);
		game1.addActionListener(buttonL);
		helpPanel.add(game1);
		
		game2 = new JButton("º´¶Ñ²±");
		game2.setBounds(250, 115, 200, 50);
		game2.addActionListener(buttonL);
		helpPanel.add(game2);

		game3 = new JButton("°ÔÀÓ3");
		game3.setBounds(250, 175, 200, 50);
		game3.addActionListener(buttonL);
		helpPanel.add(game3);
		
		game4 = new JButton("ÈÆ¹ÎÁ¤À½");
		game4.setBounds(250, 235, 200, 50);
		game4.addActionListener(buttonL);
		helpPanel.add(game4);
		
		game5 = new JButton("Àü±¹³ë·¡ÀÚ¶û");
		game5.setBounds(250, 295, 200, 50);
		game5.addActionListener(buttonL);
		helpPanel.add(game5);
		
		sentence = new JLabel("");
		sentence.setBounds(50, 50, 600, 300);
		sentence.setVisible(false);
		helpPanel.add(sentence);
		
		back = new JButton("µÚ·Î°¡±â");
		back.setBounds(550, 300, 100, 50);
		back.addActionListener(buttonL);
		back.setVisible(false);
		helpPanel.add(back);
		
		getContentPane().add(helpPanel);
		pack();
		setVisible(true);
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			Object object = event.getSource();
			if(object == game1||object == game2||object==game3||object==game4||object==game5) {
				game1.setVisible(false);
				game2.setVisible(false);
				game3.setVisible(false);
				game4.setVisible(false);
				game5.setVisible(false);
				sentence.setVisible(true);
				back.setVisible(true);
			}
			if(object == game1) {
				sentence.setText("1");
			}
			else if(object == game2) {
				sentence.setText("2");
			}
			else if(object == game3) {
				sentence.setText("3");
			}
			else if(object == game4) {
				sentence.setText("4");
			}
			else if(object == game5) {
				sentence.setText("5");
			}
			else if(object == back) {
				sentence.setText("");
				game1.setVisible(true);
				game2.setVisible(true);
				game3.setVisible(true);
				game4.setVisible(true);
				game5.setVisible(true);
				sentence.setVisible(false);
				back.setVisible(false);
			}
		}
		
	}
	
}
