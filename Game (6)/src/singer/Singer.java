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
	private String singerList; // 가수 목록
	private String[] pickSinger; // 뽑힌 가수 나누기
	private JLabel word, boardimg, board; // 가수 제시어, 배경 이미지, 타이머 넣기 위함
	private ImageIcon icon; // 이미지 아이콘
	private Image resizeimg; // 이미지 사이즈 조절용도
	private int index;// 가수들이 ","로 나누어져 만들어진 행렬의 인덱스
	private JButton reset, back; // 리셋, 뒤로가기 버튼
	private LabelThread time; // 타이머
	private ButtonListener Listen; // 리스너
	private MainPanel main; // 메인패널
    private ResizeImg rImg; // 이미지 사이즈 조절용도 
    private Image resizeImg;// 이미지 사이즈 조절용도
    private GameSelector game; // 게임셀렉터
	
	public Singer(MainPanel m, GameSelector g) { // 메인패널, 게임셀릭터 클래스 받아옴
		
		main = m;
		game = g;
		
		Listen = new ButtonListener(); // 리스너 할당
		
		setBounds(50, 100, 950, 550); // 게임 패널 위치 및 사이즈 조정
		this.setLayout(null); // Layout 설정 null
		singerList = "블랙핑크,소녀시대,  BTS,트와이스,레드벨벳, 아이유,  EXO,  선미, 아이콘, 헤이즈,멜로망스, 김하온,  위너,여자친구, 마마무,모모랜드, 아이들,키드밀리,엠씨더맥스,  있지, 장범준, 백예린,  태연,오마이걸, 잔나비"; // 가수들 저장
		pickSinger = singerList.split(",");
		
		index = (int)(Math.random()*26); // 25개의 인자를 가진 행렬의 인덱스 무작위로 생성
		
		ResizeImg bImg = new ResizeImg("images/sing_backimg3.png",950,550); // 배경사진 사이즈 950*550으로 재 설정
		resizeimg = bImg.getResizeImage(); // 재설정된 이미지 반환
		icon = new ImageIcon(resizeimg); // 이미지 아이콘에서 사용할 수 있게 설정
		boardimg = new JLabel("",icon,SwingConstants.CENTER); // j라벨에 배경사진 삽입
		boardimg.setBounds(0, 0, 950, 550); // 위치 및 사이즈 조절
				
		word = new JLabel(pickSinger[index]); //j라벨에 뽑힌 가수 넣으며 생성
		word.setBounds(300, 10, 500, 200);// 사이즈 및 위치 조절
		word.setFont(new Font("타이포_헬로피오피 테두리M", Font.BOLD, 100));// 글자 스타일 변경
		word.setForeground(new Color(0,0,255)); // 글자색 파란색으로 변경
		
		reset = new JButton("");// 재생성 버튼
		reset.setBounds(800, 50, 100, 50); // 위치 및 사이즈 조절
		reset.addActionListener(Listen); // 리스너 추가
		
		back = new JButton(""); // 뒤로가기 버튼
		back.setBounds(50, 450, 100, 50); // 위치 및 사이즈 조절
		back.addActionListener(Listen); // 리스너 추가
	
		board = new JLabel(""); //타이머 넣기 
		board.setBounds(0, 0, 950, 550); // 위치 및 사이즈 조절
		
		time = new LabelThread(10); // 타이머 10초에서 시작
		time.setBounds(400, 150, 500, 200); // 위치 및 사이즈 조절
		time.setFont(new Font("MD솔체", Font.BOLD, 150)); // 타이머 폰트 설정
		board.add(time); // 타이머 add
		time.start(); // 타이머 시작
		
        rImg = new ResizeImg("images/back.png", 50, 50); // 뒤로가기 버튼 사이즈 조절한 이미지 추가 후 테두리 없에주기
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        back.setIcon(icon);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        
        rImg = new ResizeImg("images/rotate.png", 50, 50); // 재시작 버튼 사이즈 조절한 이미지 추가 후 테두리 없에주기
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        reset.setIcon(icon);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.setFocusPainted(false);
		
		this.add(back); // 뒤로가기 add
		this.add(reset); // 재시작 add
		this.add(board); // 타이머 넣기용 add
		this.add(word); // 제시어 add
		this.add(time); // 타이머 add
		this.add(boardimg);	// 배경사진 add
	}
	
	public String getList() {
		return singerList; // 뽑힌 가수 반환
	}
	
	public class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == reset) { // 재시작 버튼을 누르면
				index = (int)(Math.random()*26); // 인덱스 다시 뽑기
				word.setText(pickSinger[index]); // 해당 인덱스의 가수로 제시어 재설정
				
				if(time.getThread().isAlive()){
					time.getThread().interrupt(); 
				}
				remove(time); // 타이머 삭제
				time = new LabelThread(10); // 타이머 재성
				time.setBounds(400, 150, 500, 200); // 사이즈 및 위치 조정
				time.setFont(new Font("MD솔체", Font.BOLD, 150)); // 폰트 설정
				add(time); // 타이머 더해주기
				add(board); // 보드 더해주기
				add(boardimg);//배경사진 더하기
				revalidate();
				repaint();
				time.start();//타이머 시작
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
