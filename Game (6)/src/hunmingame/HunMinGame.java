package hunmingame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import frame_panel.GameSelector;
import frame_panel.MainPanel;
import tool.LabelThread;
import tool.ResizeImg;

public class HunMinGame extends JPanel {
	private String hangul, added; // ㄱ~ㅎ 자음 모음, 2개의 자음 조합
	private int index; // 배열 인덱스
	private char one, two; // 무작위로 뽑힌 자음
	private JLabel word, board; // 초성, 타이머 담을 라벨
	private ImageIcon icon; // 아이콘
    private ResizeImg rImg;// 리사이즈이미지 클래스 
	private Image resizeimg; // 사이즈 재 조정 이미지
	private JButton reset, back; // 재시작, 뒤로가기 버튼
	private Listener Listen; // 리스너
	private LabelThread time;;// 타이머
	private MainPanel main; // 메인패널 클래스
    private GameSelector game; // 게임셀렉터 게임
    
	public HunMinGame(MainPanel m, GameSelector g) { // 메인패널, 게임 셀렉터 받아오면서 생성
		
		main = m; 
		game = g;
		
		setBounds(50, 100, 950, 550);// 훈민정음 패널 위치 및 사이즈 조절
		this.setLayout(null); // 레이아웃 설절 널
		
		Listen = new Listener(); // 리스터 생성
		
		ResizeImg bImg = new ResizeImg("images/board.jpg",950,550);// 배경이미지 넣으며 객체 생성
		resizeimg = bImg.getResizeImage(); // 재조정된 이미지 반환
		icon = new ImageIcon(resizeimg); // 이미지 아이콘화
		board = new JLabel("",icon,SwingConstants.CENTER); // 아이콘을 넣으며 라벨로 배경화면 삽입
		board.setBounds(0, 0, 950, 550); // 위치 및 사이즈 조절
		
		
		hangul = "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ"; // 자음 모음
		index = (int)(Math.random()*14); // ㄱ ~ ㅎ까지 인덱스 무작위 뽑기
		one = hangul.charAt(index); // 자음 뽑기
		index = (int)(Math.random()*14); // ㄱ ~ ㅎ까지 인덱스 무작위 뽑기
		two = hangul.charAt(index); // 자음 뽑기
		added = String.valueOf(one); // 뽑힌 자음 넣기
		added = added + two; // 자음 두개 더하기
		System.out.println(added); 
		
		word = new JLabel(added); // 더해진 초성 라벨에 넣기
		word.setBounds(250, 150, 500, 300); // 위치 및 사이즈 조정
		word.setFont(new Font("MD솔체", Font.BOLD, 200)); // 폰트 설정
		word.setForeground(Color.white); // 글자 색 설정
		word.setHorizontalAlignment(SwingConstants.CENTER); // 위치 설정
		word.setVerticalAlignment(SwingConstants.CENTER); // 위치 설정
		board.add(word); // 라벨 더해주기
		
		time = new LabelThread(10); // 타이머 10초부터 시작
		time.setBounds(400, 50, 500, 200); // 위치 및 사이즈 조절
		time.setFont(new Font("MD솔체", Font.BOLD, 150)); // 폰트 설정
		board.add(time); // 타이머 더해주기
		time.start(); // 타이머 시작
		
		back = new JButton(""); // 뒤로가기 버튼
		back.setBounds(50, 450, 100, 50); // 위치 및 사이즈 조절
		back.addActionListener(Listen); // 리스너 더해주기
		board.add(back); // 버튼 화면에 더하기
	
		reset = new JButton(""); // 재시작 버튼
		reset.setBounds(800, 50, 100, 50); // 위치 및 사이즈 조절
		reset.addActionListener(Listen);// 리스너 더해주기
		board.add(reset);// 버튼 화면에 더하기
		
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
        reset.setIcon(icon);
        reset.setBorderPainted(false);// 버튼에 아이콘 적용으로 이미지 적용
        reset.setContentAreaFilled(false);
        reset.setFocusPainted(false);
        
		this.add(board);// 배경화면 패널에 더해주기
	}
	public class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == reset) { // 재시작 버튼 누르면
				index = (int)(Math.random()*14); // 인덱스 다시 뽑아주기
				one = hangul.charAt(index); // 첫 자음
				index = (int)(Math.random()*14); // 인덱스 다시 뽑아주기
				two = hangul.charAt(index);// 두번째 자음
				added = String.valueOf(one);
				added = added + two;
				word.setText(added);// 자음 조합 넣어주기
				
				if(time.getThread().isAlive()){
					time.getThread().interrupt(); // 타이머 작동 중이면 끝내기
				}
				board.remove(time);//타이머 삭제
				time = new LabelThread(10); // 타이머 재 생성
				time.setBounds(400, 50, 500, 200); // 위치 및 사이즈 조절
				time.setFont(new Font("MD솔체", Font.BOLD, 150)); // 폰트 설정
				board.add(time);// 타이머 더해주기
				revalidate();
				repaint();
				time.start(); // 타이머 시작
			}
			else if(obj == back) { // 뒤로가기 버튼 누르면
				main.createGameSelector();  // 게임셀랙터로 돌아감
	        	main.addMainPanel(); // 메인 패널은 배경으로
	        	main.offMainIntro(); // 인트로 꺼주기
	        	game.offIntro(); // 인트로 꺼주기
	        	game.setgameNumZero(); // 인트로 관련 번호 0으로 설정
			}
		}
	}

}
