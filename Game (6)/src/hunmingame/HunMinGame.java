package hunmingame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import frame_panel.MainPanel;
import tool.LabelThread;
import tool.ResizeImg;

public class HunMinGame extends JPanel {
	private String hangul, added;
	private int index, i = 10;
	private char one, two;
	private JLabel word, board;
	private ImageIcon icon;
	private Image resizeimg;
	private JButton reset, back;
	private Listener Listen;
	private LabelThread time;
	private JPanel here;
	private MainPanel main;
    private ResizeImg rImg;
    private Image resizeImg;
    
	public HunMinGame(MainPanel m) {
		
		main = m;
						
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
		
		here = this;
		Listen = new Listener();
		
		ResizeImg bImg = new ResizeImg("images/board.jpg",950,550);
		resizeimg = bImg.getResizeImage();
		icon = new ImageIcon(resizeimg);
		board = new JLabel("",icon,SwingConstants.CENTER);
		board.setBounds(0, 0, 950, 550);
		
		
		hangul = "ぁいぇぉけげさしじずせぜそぞ";		
		index = (int)(Math.random()*14);
		one = hangul.charAt(index);
		index = (int)(Math.random()*14);
		two = hangul.charAt(index);
		added = String.valueOf(one);
		added = added + two;
		System.out.println(added);
		
		word = new JLabel(added);
		word.setBounds(250, 150, 500, 300);
		word.setFont(new Font("MD車端", Font.BOLD, 200));
		word.setForeground(Color.white);
		word.setHorizontalAlignment(SwingConstants.CENTER);
		word.setVerticalAlignment(SwingConstants.CENTER);
		board.add(word);
		
		time = new LabelThread("10",10);
		time.setBounds(400, 50, 500, 200);
		time.setFont(new Font("MD車端", Font.BOLD, 150));
		board.add(time);
		time.start();
		
		back = new JButton("");
		back.setBounds(50, 450, 100, 50);
		back.addActionListener(Listen);
		board.add(back);
	
		reset = new JButton("");
		reset.setBounds(800, 50, 100, 50);
		reset.addActionListener(Listen);
		board.add(reset);
		
        rImg = new ResizeImg("images/back.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        back.setIcon(icon);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
		
        rImg = new ResizeImg("images/rotate.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        reset.setIcon(icon);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.setFocusPainted(false);
        
		this.add(board);
	}
	public class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == reset) {
				one = hangul.charAt(index);
				index = (int)(Math.random()*14);
				two = hangul.charAt(index);
				added = String.valueOf(one);
				added = added + two;
				word.setText(added);
				
				if(time.getThread().isAlive()){
					time.getThread().interrupt(); 
				}
				board.remove(time);
				time = new LabelThread("10",10);
				time.setBounds(400, 50, 500, 200);
				time.setFont(new Font("MD車端", Font.BOLD, 150));
				board.add(time);
				revalidate();
				repaint();
				time.start();
			}
			else if(obj == back) {
				main.createGameSelector();
	        	main.addMainPanel();	
			}
		}
	}


}
