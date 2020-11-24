
package hunmingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;

import tool.LabelThread;
import clientgame.ClientGame;
import tool.ResizeImg;

public class HunMinGame extends JPanel {
	private String hangul, added;
	private int[] index;
	private char one, two;
	private JLabel word, board;
	private ImageIcon icon;
	private Image resizeimg;
	
	// 타이머
	private LabelThread time;
	
	// 네트워크 구현위한 버튼
	private JButton btnReset;
	
	// 채팅구현
	private JTextArea chatArea;
    private JScrollPane scrollPane;
    private JTextField chat;
    private String nickName; 
    
    private String name; 
    private ClientGame cl;
    
	public HunMinGame(ClientGame c) {
		
		cl = c;
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
			
		ResizeImg bImg = new ResizeImg("images/board.jpg",750,550); // 크기조절 950 -> 750
		resizeimg = bImg.getResizeImage();
		icon = new ImageIcon(resizeimg);
		board = new JLabel("",icon,SwingConstants.CENTER);
		board.setBounds(0, 0, 750, 550); // 크기조절 950 -> 750
		
		
		hangul = "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";	
		
		index = cl.getHunminIndex();  // 여기는 아직 서버로 간 메세지가 클라이언트에 닿지 못한 상태 초기화 버튼으로 refresh
		
		one = hangul.charAt(index[0]);	
		two = hangul.charAt(index[1]);
		added = String.valueOf(one);
		added = added + two;
		
		word = new JLabel(added);
		word.setBounds(125, 150, 500, 300); // 크기조절 250 -> 125
		word.setFont(new Font("MD솔체", Font.BOLD, 200));
		word.setForeground(Color.white);
		word.setHorizontalAlignment(SwingConstants.CENTER);
		word.setVerticalAlignment(SwingConstants.CENTER);
		board.add(word);
		
		// 네트워크 구현위한 버튼
		btnReset = new JButton("초기화");
		btnReset.setBounds(50, 500, 100, 40);
		add(btnReset);
		
		// 채팅창 구현				
		chatArea = new JTextArea(); 
		scrollPane = new JScrollPane(chatArea);
		chatArea.setEditable(false); 
		scrollPane.setBounds(750, 0, 200, 525);
		add(scrollPane);		
		chat = new JTextField();
		chat.setBounds(750, 525, 200, 25);
	    
		// 타이머 쓰레드 생성
		time = new LabelThread("10",10);
		time.start();
		
		chat.addActionListener(new sendMessage());
		btnReset.addActionListener(new ResetListener());
		
		add(board);
		add(chat);	
		
		cl.setGuiHunmin(this);
		name = cl.getNickname();
	} 	
	
	public void init() {
		index = cl.getHunminIndex();
		one = hangul.charAt(index[0]);	
		two = hangul.charAt(index[1]);
		added = String.valueOf(one);
		added = added + two;
		word.setText(added);
		
		// 타이머 쓰레드 적용
		if(time.getThread().isAlive()){
			time.getThread().interrupt(); 
		}
		board.remove(time);
		time = new LabelThread("10",10);
		time.setBounds(400, 50, 500, 200);
		time.setFont(new Font("MD솔체", Font.BOLD, 150));
		board.add(time);
		revalidate();
		repaint();
		time.start();
	}
	
	public void appendMsg(String msg) {
        chatArea.append(msg);
    }     
	
	private class sendMessage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        String msg = name + " : " + chat.getText() + "\n";
	        cl.sendMessage(msg);
	        chat.setText("");
	    }
	}
	
	private class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cl.sendMessage("[HUNMININIT]");
	    }
	}
	

}
