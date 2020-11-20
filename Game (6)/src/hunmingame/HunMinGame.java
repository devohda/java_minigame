package hunmingame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import tool.ResizeImg;

import javax.swing.Timer;

public class HunMinGame extends JPanel {
	private String hangul, added;
	private int index, t=10;
	private char one, two;
	private JLabel word, board, time;
	private ImageIcon icon;
	private Image resizeimg;
	private JButton reset;
	private Listener Listen;
	
	public HunMinGame() {
				
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
			
		ResizeImg bImg = new ResizeImg("images/board.jpg",950,550);
		resizeimg = bImg.getResizeImage();
		icon = new ImageIcon(resizeimg);
		board = new JLabel("",icon,SwingConstants.CENTER);
		board.setBounds(0, 0, 950, 550);
		
		
		hangul = "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";		
		index = (int)(Math.random()*14);
		one = hangul.charAt(index);
		index = (int)(Math.random()*14);
		two = hangul.charAt(index);
		added = String.valueOf(one);
		added = added + two;
		System.out.println(added);
		
		word = new JLabel(added);
		word.setBounds(250, 150, 500, 300);
		word.setFont(new Font("MD솔체", Font.BOLD, 200));
		word.setForeground(Color.white);
		word.setHorizontalAlignment(SwingConstants.CENTER);
		word.setVerticalAlignment(SwingConstants.CENTER);
		board.add(word);
		
		time = new JLabel("10");
		time.setBounds(400, 50, 500, 200);
		time.setFont(new Font("MD솔체", Font.BOLD, 150));
		board.add(time);
		
		reset = new JButton("다시뽑기");
		reset.setBounds(800, 50, 100, 50);
		reset.addActionListener(Listen);
		board.add(reset);
		
		while(t>=0) { try{ System.out.println("반복문"+t); t--;Thread.sleep(1000); } catch(InterruptedException e){ } }
		
		this.add(board);
	}
	public class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ev) {
			Object obj = ev.getSource();
			if(obj == reset) {
				one = hangul.charAt(index);
				index = (int)(Math.random()*14);
				two = hangul.charAt(index);
				added = String.valueOf(one);
				added = added + two;
				word.setText(added);
				t = 10;
			}
		}
	}

}
