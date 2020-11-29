
package game.hunmingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;

import tool.Customfont;
import tool.LabelThread;
import frame_panel.GameSelector;
import frame_panel.MainPanel;
import socket.ClientGame;
import tool.ResizeImg;

public class HunMinGame extends JPanel {
	private String hangul, added; // ㄱ~ㅎ 자음 모음, 2개의 자음 조합
	private int[] index; // 배열 인덱스
	private char one, two; // 무작위로 뽑힌 자음
	private JLabel word, board; // 초성, 타이머 담을 라벨
	private ImageIcon icon; // 아이콘
    private ResizeImg rImg;// 리사이즈이미지 클래스 
	private Image resizeimg; // 사이즈 재 조정 이미지
	
	// 타이머
	private LabelThread time; // 타이머
	
	// 네트워크 구현위한 버튼
	private JButton btnReset, back; // 재시작, 뒤로가기 버튼
	
	// 채팅구현
	private JTextArea chatArea;
    private JScrollPane scrollPane;
    private JTextField chat;
    private String nickName; 
    
    private String name; 
    private ClientGame cl;
    
    private GameSelector gameselector;
    private MainPanel main; 

    private Customfont makeFnt;
    private Font fnt;

	public HunMinGame(ClientGame c, GameSelector gs, MainPanel m) {
			
		gameselector = gs;
		main = m;
		cl = c;
		
		setBounds(50, 100, 950, 550);
		this.setLayout(null);
			
		ResizeImg bImg = new ResizeImg("images/board.jpg",750,550); // 크기조절 950 -> 750
		resizeimg = bImg.getResizeImage(); // 재조정된 이미지 반환
		icon = new ImageIcon(resizeimg); // 이미지 아이콘화
		board = new JLabel("",icon,SwingConstants.CENTER); // 아이콘을 넣으며 라벨로 배경화면 삽입
		board.setBounds(0, 0, 750, 550); // 크기조절 950 -> 750

		makeFnt = new Customfont();
		fnt = makeFnt.getCustomFont("font/헤움아빠와크레파스A.ttf", Font.PLAIN, 250);

		hangul = "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ"; // 자음 모음
		
		index = cl.getHunminIndex();  // 자음 초기화
		
		one = hangul.charAt(index[0]); // ㄱ ~ ㅎ까지 인덱스 무작위 뽑기
		two = hangul.charAt(index[1]); // ㄱ ~ ㅎ까지 인덱스 무작위 뽑기
		added = String.valueOf(one); // 뽑힌 자음 넣기
		added = added + two; // 자음 두개 더하기
		
		word = new JLabel(added); // 더해진 초성 라벨에 넣기
		word.setBounds(110, 70, 500, 300); // 크기조절 250 -> 125
		word.setFont(fnt); // 폰트 설정
		word.setForeground(Color.white); // 글자 색 설정
		word.setHorizontalAlignment(SwingConstants.CENTER); // 위치 설정
		word.setVerticalAlignment(SwingConstants.CENTER); // 위치 설정
		board.add(word); // 라벨 더해주기
		
		// 초기화 버튼
		btnReset = new JButton("");
		btnReset.setBounds(600, 70, 100, 50); // 위치 및 사이즈 조절
		add(btnReset); // 버튼 화면에 더하기
		
		back = new JButton(""); // 뒤로가기 버튼
		back.setBounds(40, 450, 100, 50); // 위치 및 사이즈 조절
		add(back); // 버튼 화면에 더하기
		
		rImg = new ResizeImg("images/back.png", 50, 50); // 버튼 이미지 사이즈 조절해주기
        resizeimg = rImg.getResizeImage();
        icon = new ImageIcon(resizeimg);
        back.setIcon(icon);// 버튼에 아이콘 적용으로 이미지 적용
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
		
        rImg = new ResizeImg("images/rotate.png", 50, 50);  // 버튼 이미지 사이즈 조절해주기
        resizeimg = rImg.getResizeImage();
        icon = new ImageIcon(resizeimg);
        btnReset.setIcon(icon);
        btnReset.setBorderPainted(false);// 버튼에 아이콘 적용으로 이미지 적용
        btnReset.setContentAreaFilled(false);
        btnReset.setFocusPainted(false);
		
		// 채팅창 구현				
		chatArea = new JTextArea(); 
		scrollPane = new JScrollPane(chatArea);
		chatArea.setEditable(false); 
		scrollPane.setBounds(750, 0, 200, 525);
		add(scrollPane);		
		chat = new JTextField();
		chat.setBounds(750, 525, 200, 25);
	    
		// 타이머 쓰레드 생성
		time = new LabelThread(10);
		time.start();
		
		chat.addActionListener(new sendMessage());
		btnReset.addActionListener(new ResetListener());
		back.addActionListener(new BackListener());
		
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
			time.getThread().interrupt(); // 타이머 작동 중이면 끝내기
		}
		board.remove(time); //타이머 삭제
		time = new LabelThread(10); // 타이머 재 생성
		time.setBounds(340, 30, 500, 200); // 위치 및 사이즈 조절
		fnt = makeFnt.getCustomFont("font/헤움아빠와크레파스A.ttf", Font.BOLD, 90);
		time.setFont(fnt); // 폰트 설정
		board.add(time); // 타이머 더해주기
		revalidate();
		repaint();
		time.start(); // 타이머 시작
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
	
	private class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			main.createGameSelector();  // 게임셀랙터로 돌아감
        	main.addMainPanel(); // 메인 패널은 배경으로
        	main.offMainIntro(); // 인트로 꺼주기
        	gameselector.offIntro(); // 인트로 꺼주기
        	gameselector.setgameNumZero(); // 인트로 관련 번호 0으로 설정
	    }
	}
}
