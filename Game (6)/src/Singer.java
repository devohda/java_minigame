import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

public class Singer extends JPanel {
	private String singerList;
	private String[] pickSinger;
	private JLabel word, backGround, boardimg;
	private ImageIcon icon;
	private Image resizeimg;
	private JLabel board;
	private int index;
	
	public Singer() {
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
		singerList = "블랙핑크,소녀시대,BTX";
		pickSinger = singerList.split(",");
		
		index = (int)(Math.random()*3);
		System.out.println(pickSinger[index]);
		
		ResizeImg rImg = new ResizeImg("images/singcontest.png",950,550);
		resizeimg = rImg.getResizeImage();
		icon = new ImageIcon(resizeimg);
		backGround = new JLabel("",icon,SwingConstants.CENTER);
		backGround.setBounds(0, 0, 1000, 600);
		
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
		this.add(backGround);	
	}
	
	public String getList() {
		return singerList;
	}
}
