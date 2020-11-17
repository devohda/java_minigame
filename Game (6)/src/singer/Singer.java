<<<<<<< HEAD:Game (6)/src/singer/Singer.java
package singer;
=======
import tool.ResizeImg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
>>>>>>> ohda/design:Game (6)/src/Singer.java

import java.awt.*;
import javax.swing.*;
import tool.ResizeImg;

public class Singer extends JPanel {
	private String singerList;
	private String[] pickSinger;
	private JLabel word, boardimg;
	private ImageIcon icon;
	private Image resizeimg;
	private JLabel board;
	private int index;
	
	public Singer() {
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
		singerList = "블랙핑크,소녀시대,BTS";
		pickSinger = singerList.split(",");
		
		index = (int)(Math.random()*3);
		System.out.println(pickSinger[index]);
		
		ResizeImg bImg = new ResizeImg("images/sing_backimg2.jpeg",950,550);
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
		
		this.add(word);
		this.add(board);
		this.add(boardimg);	
	}
	
	public String getList() {
		return singerList;
	}
}
