package singer;

import tool.LabelThread;
import tool.ResizeImg;
import tool.Sound;
import tool.LabelThread;
import javax.swing.*;

import frame_panel.GameSelector;
import frame_panel.MainPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Singer extends JPanel {
	private String singerList;
	private String[] pickSinger;
	private JLabel word, boardimg;
	private ImageIcon icon;
	private Image resizeimg;
	private JLabel board;
	private int index;
	private JButton reset, back;
	private LabelThread time;
	private ButtonListener Listen;
	private MainPanel main;
    private ResizeImg rImg;
    private Image resizeImg;
    private GameSelector game;
	
	public Singer(MainPanel m, GameSelector g) {
		
		main = m;
		game = g;
		
		Listen = new ButtonListener();
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
		singerList = "블랙핑크,소녀시대,  BTS";
		pickSinger = singerList.split(",");
		
		index = (int)(Math.random()*3);
		
		ResizeImg bImg = new ResizeImg("images/sing_backimg3.png",950,550);
		resizeimg = bImg.getResizeImage();
		icon = new ImageIcon(resizeimg);
		boardimg = new JLabel("",icon,SwingConstants.CENTER);
		boardimg.setBounds(0, 0, 950, 550);
		
		board = new JLabel();
		board.setBounds(450, 100, 600, 100);
		board.setBackground(Color.white);
		board.setLayout(null);
				
		word = new JLabel(pickSinger[index]);
		word.setBounds(300, 10, 500, 200);
		word.setFont(new Font("타이포_헬로피오피 테두리M", Font.BOLD, 100));
		word.setForeground(new Color(0,0,255));
		
		reset = new JButton("");
		reset.setBounds(800, 50, 100, 50);
		reset.addActionListener(Listen);
		
		back = new JButton("");
		back.setBounds(50, 450, 100, 50);
		back.addActionListener(Listen);
		
		time = new LabelThread("10",10);
		time.setBounds(400, 150, 500, 200);
		time.setFont(new Font("MD솔체", Font.BOLD, 150));
		board.add(time);
		time.start();
		
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
		
		this.add(back);
		this.add(reset);
		this.add(word);
		this.add(time); 
		this.add(board);
		this.add(boardimg);	
	}
	
	public String getList() {
		return singerList;
	}
	
	public class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == reset) {
				index = (int)(Math.random()*3);
				word.setText(pickSinger[index]);
				
				if(time.getThread().isAlive()){
					time.getThread().interrupt(); 
				}
				remove(time);
				time = new LabelThread("10",10);
				time.setBounds(400, 150, 500, 200);
				time.setFont(new Font("MD솔체", Font.BOLD, 150));
				add(time);
				add(board);
				add(boardimg);	
				revalidate();
				repaint();
				time.start();
			}
			else if(obj == back) {
				main.createGameSelector();
	        	main.addMainPanel();
	        	main.offMainIntro();
	        	game.offIntro();
	        	game.setgameNumZero();
			}
			
		}
		
	}
}
