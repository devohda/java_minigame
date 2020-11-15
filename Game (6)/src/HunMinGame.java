import tool.ResizeImg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

public class HunMinGame extends JPanel {
	private String hangul, added;
	private int index;
	private char one, two;
	private JLabel word, board;
	private ImageIcon icon;
	private Image resizeimg;
	public HunMinGame() {
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
			
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
		
		this.add(board);
	}

}
