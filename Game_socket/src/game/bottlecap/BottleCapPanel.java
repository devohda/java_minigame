package game.bottlecap;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import frame_panel.GameSelector;
import frame_panel.MainPanel;
import sadari.Sadari;
import socket.ClientGame;
import tool.Customfont;
import tool.ResizeImg;
import tool.RoundedButton;

public class BottleCapPanel extends JPanel {

	private JPanel blank;
	private JLabel blankLab, sojuLabel, capLabel, num, wrongBoard, wrong, wrongAnswerLabel, back;
	private JTextField enterNum;
	private RoundedButton enterNumButton, init;
	private String WrongAnswers= "";
	private int filled = 0;
	private int iWrongAns[] = new int[50];
	private int iRanNum;
	
	// 게임선택화면 
	private GameSelector gameselector;
	// 벌칙화면
	private Sadari sadari;
	
	// 게임 종료시 게임선택화면으로 갈때 사용되는 객체
	private MainPanel main; 
	// 플레이어들의 차례 및 인원수 정보
	private int personNum; 
	private JLabel personNumLabel;
	private int n=1; // 차례
		
	// 아이콘
	private ImageIcon wrongBoardImg;
	// 이미지 크기 조절
	private Image resizeImg;
	private ResizeImg rImg;
	    
	//폰트 지정
	private Customfont makeFnt;
	Font fnt;
	Font fnt2;
	
	// 채팅구현
	private JTextArea chatArea;
	private JScrollPane scrollPane;
	private JTextField chat;
	private String nickName; 
		    
	private String name; 
	private ClientGame cl;
	
	public BottleCapPanel(ClientGame c, GameSelector gs, MainPanel m) {
		
		gameselector = gs; // 게임선택 클래스의 객체 전달 받기
		main = m; // 메인패널의 객체 전달 받기
		
		cl = c; // 클라이언트 객체 전달 받기
		
		iRanNum = 7; // 7로 초기화 reset 버튼 클릭 시 계속 변경될예정 (아래 리스너로 처리)
		
		setBounds(225, 200, 600, 400);
		setPreferredSize(new Dimension(400, 400));
		setLayout(null);
		
		// 플레이어의 순번을 나타내는 라벨
		personNumLabel = new JLabel("1 번째 사람의 차례입니다.");
		personNumLabel.setBounds(230, 140, 150, 20);
		personNumLabel.setForeground(Color.black);
		add(personNumLabel);
		
		//번호가리기 판넬 + 라벨
		blank = new JPanel();
		blank.setBounds(185, 190, 20, 20);
		blank.setBackground(Color.black);
		blank.setLayout(null);
		add(blank);
		
		blankLab = new JLabel("?", SwingConstants.CENTER);
		blankLab.setBounds(0, 0, 20, 20);
		blankLab.setForeground(Color.white);
		blank.add(blankLab);

		//Image
		ImageIcon background = new ImageIcon("images/Background.png");//배경이미지
		ImageIcon cap = new ImageIcon("images/Cap1.png");//병뚜껑이미지
		ImageIcon soju = new ImageIcon("images/ms21.png");//소주이미지
		
		//Label
		sojuLabel = new JLabel("", soju, SwingConstants.CENTER	); // 병뚜껑 이미지
		sojuLabel.setBounds(20, 70, 100, 250);
		add(sojuLabel);
		
		capLabel = new JLabel("", cap, SwingConstants.CENTER	); // 배경 이미지
		capLabel.setBounds(162, 178, 70, 80);
		add(capLabel);
			
		
		num = new JLabel("" + iRanNum);  //랜덤 숫자 표시
		num.setBounds(191, 190, 20, 20);
		add(num);
		
		// 오답 표시를 위한 라벨 3개 --------------------
		wrong = new JLabel("<오답>");
		wrong.setBounds(240, 30, 50, 30);
		wrong.setForeground(Color.white);
		add(wrong);
				
		wrongAnswerLabel = new JLabel(); // 정답 오답 표시
		wrongAnswerLabel.setBounds(240, 10, 146, 126);
		wrongAnswerLabel.setForeground(Color.white);
		add(wrongAnswerLabel);
				
		rImg = new ResizeImg("images/wrongBoard.png", 170, 130);
		resizeImg = rImg.getResizeImage();
		wrongBoardImg = new ImageIcon(resizeImg);  //배경이미지
				
		wrongBoard = new JLabel("", wrongBoardImg, SwingConstants.CENTER); //오답 칠판이미지
		wrongBoard.setBounds(220, 10, 170, 130);
		add(wrongBoard);
				
		 /************* 폰트 지정 **************/
		makeFnt = new Customfont();
		fnt = makeFnt.getCustomFont("font/고양체.ttf", Font.BOLD, 15);
		fnt2 = makeFnt.getCustomFont("font/지마켓.ttf", Font.PLAIN, 20);
		wrong.setFont(fnt);
		wrongAnswerLabel.setFont(fnt);
		personNumLabel.setFont(fnt);
		        
		// 오답 표시를 위한 라벨 3개 여기까지 ----------------------------------
		
		
		//TextField + Button
		enterNum = new JTextField(); // 정답입력필드
		enterNum.setBounds(165, 360, 30, 32);
		add(enterNum);
		
		enterNumButton = new RoundedButton("OK"); // 정답입력 확인 버튼
		enterNumButton.setFont(new Font("Arial", Font.BOLD, 10));
		enterNumButton.setBounds(195, 360, 49, 30);
		enterNumButton.setBackground(new Color(200, 160, 16));
		enterNumButton.setFont(fnt2);
		add(enterNumButton);
		
		init = new RoundedButton("Reset"); // 게임 초기화 버튼
		init.setFont(new Font("Arial", Font.BOLD, 10));
		init.setBounds(10, 360, 65, 30);
		init.setBackground(new Color(200, 160, 16));
		init.setFont(fnt2);
		add(init);
		
		//배경이미지 우선순위 꼴찌
		back = new JLabel("", background, SwingConstants.CENTER);
		back.setBounds(0, 0, 400, 400);
		back.setBorder(new TitledBorder(new LineBorder(new Color(200, 160, 16),5), ""));
		add(back);
		
		// 채팅창 구현
		chatArea = new JTextArea(); // 채팅내용을 나타낼 객체 생성
		scrollPane = new JScrollPane(chatArea); // JScrollPane을 이용하여 긴 내용도 표시가능
		chatArea.setEditable(false); // 점수판 편집 불가
		scrollPane.setBounds(400, 0, 200, 375);
		add(scrollPane);
				
		chat = new JTextField(); //채팅을 입력할 객체 생성
		chat.setBounds(400, 375, 200, 25);
		add(chat);
		
		init.addActionListener(new InitListener()); // 게임 재시작을 위한 리스너
		enterNumButton.addActionListener(new AnwserListener()); // 정답입력 시 실행되는 리스너
		chat.addActionListener(new sendMessage()); // 채팅입력시 실행되는 리스너

		cl.setGuiBottle(this); // 클라이언트 구현을 위한 보틀캡 객체 전달
		name = cl.getNickname(); // 처음 게임 실행 시 입력 한 닉네임 설정
		
	} // construct
	
	// 시작화면 구현하려고 추가한 get/set
	public void setWrongList(int[] list) { iWrongAns = list; }
	
	
	
	public void init() {
		iRanNum = cl.getRandNum(); // ClientGame 클래스로부터 랜덤 변수 가져오기
		enterNum.setText(""); // 입력하는 칸 비우기
		wrongAnswerLabel.setText(""); // 오답 칸 비우기
		blank.setVisible(true); // 정답 숫자 가리기 
		
		// 추가된 부분 초기화 시 순번 1로
		n = 1; 
		personNumLabel.setText("" + n + " 번째 사람의 차례입니다.");
	}
	
	public void initTurn() {
		// 순번 갱신
		if(n != personNum) {
			n++;
			personNumLabel.setText("" + n + " 번째 사람의 차례입니다.");
		}
		if( n==personNum) n=0;
	}
	
	public void exit() {
		
		// 정답 시 실행되는 JOptionPane
		String[] option = {"다시 시작", "종료"};
		int select = JOptionPane.showOptionDialog(this, "게임이 종료되었습니다", "게임종료", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);

		// 다시 시작하는 경우
		if (select == 0) {
			cl.sendMessage("[initBottle]"); // 서버에 초기화 메세지 보내기
			gameselector.offIntro(); // 인트로끄기
        	main.offMainIntro();
		} 
		// 종료하는 경우
		else if (select == JOptionPane.NO_OPTION) {
			main.createGameSelector(); // 게임 선택화면으로 이동하기 위한 객체 생성
        	main.addMainPanel(); // 메인패널을 구성하는 라벨, 버튼들 추가
        	gameselector.offIntro(); // 인트로끄기
        	main.offMainIntro();
        	gameselector.setgameNumZero();		
		} else {
			System.out.println("CANCLE");
		}
	}
	
	// 오답 시  실행되는 오디오
	public void audio1()
	{ 	
		 File bgm;
         AudioInputStream stream;
         AudioFormat format;
         DataLine.Info info;
         
         bgm = new File("sounds/beep.wav"); // 사용시에는 개별 폴더로 변경할 것
         
         Clip clip;
         
         try {
                stream = AudioSystem.getAudioInputStream(bgm);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip)AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
                
         } catch (Exception e) {
                System.out.println("소리실행안됨" + e);
         }
		
	}
	
	// 채팅창에 본인 또는 상대방이 입력한 메세지 출력
	public void appendMsg(String msg) {
        chatArea.append(msg);
    }     
	
	// 서버로 부터 전달받은 정답 또는 오답 메세지를 통해 오답 칠판에 출력
	public void appendWrongAns(String msg) {
		if ( msg.startsWith("[TRUE]")) msg = msg.substring(6);
		else if (msg.startsWith("[FALSE]")) msg = msg.substring(7); 
    	wrongAnswerLabel.setText(msg); // 오답 칠판에 출력
    }
	
	// 본인이 채팅창에 입력한 내용을 서버로 전달
	private class sendMessage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        String msg = name + " : " + chat.getText() + "\n";
	        cl.sendMessage(msg);
	        chat.setText("");
	    }
	}
	
	private class InitListener implements ActionListener { // 게임 초기화
		public void actionPerformed(ActionEvent e) {
			try {
				cl.sendMessage("[initBottle]"); // 서버로 보틀캡 게임 초기화 메세지 전달
			} catch (Exception e1) {
	            System.out.println("오류 발생! >> " + e1); 
			}
			
		}
		
	}
	
	private class AnwserListener implements ActionListener { // 숫자 입력 및, 오답 판 갱신
		public void actionPerformed(ActionEvent e) {
			try {
				cl.sendMessage("[Answer]" + enterNum.getText()); // 서버로 입력한 숫자의 정답 여부 판단을 요구하는 메세지 전달
				enterNum.setText("");
			} catch (Exception e1) {
				 System.out.println("오류 발생! >> " + e1); 
			}
		}
		
	}

	
}

