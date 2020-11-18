package jun_chang;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import frame_panel.GameSelector;
import frame_panel.MainPanel;

public class BottleCapPanel extends JPanel {

	private JPanel wrongAns, blank;
	private JLabel blankLab, sojuLabel, capLabel, num, wrongAnswerLabel, back;
	private JTextField enterNum;
	private JButton enterNumButton, init;
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
	
	public BottleCapPanel(GameSelector gs, MainPanel m) {
		
		gameselector = gs;
		main = m;
		
		personNum = gs.getPeopleNum(); // 처음 화면에서 입력받은 인원수 
		
		Random generator = new Random();
		iRanNum = generator.nextInt(50)+1; //1~50 랜덤숫자 생성.. 50은 범위 너무 커서 수정하는게 나으려나?
		
		setBounds(325, 200, 400, 400);
		setPreferredSize(new Dimension(400, 400));
		setLayout(null);
		
		// 플레이어의 순번을 나타내는 라벨
		personNumLabel = new JLabel("1 번째 사람의 차례입니다.");
		personNumLabel.setBounds(250, 150, 140, 10);
		personNumLabel.setForeground(Color.black);
		add(personNumLabel);
		
		// 오답을 나타내는 패널
		wrongAns = new JPanel();
		wrongAns.setBounds(250, 10, 140, 130);
		wrongAns.setBackground(Color.white);
		TitledBorder oneTb = new TitledBorder(new LineBorder(Color.black), "오답");
		wrongAns.setBorder(oneTb);
		add(wrongAns);
		
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
		
		wrongAnswerLabel = new JLabel(); // 정답 오답 표시
		wrongAnswerLabel.setBounds(2, 2, 136, 126);
		wrongAns.add(wrongAnswerLabel);
		
		
		//TextField + Button
		enterNum = new JTextField(); // 정답입력필드
		enterNum.setBounds(165, 360, 30, 32);
		add(enterNum);
		
		enterNumButton = new JButton("OK"); // 정답입력 확인 버튼
		enterNumButton.setFont(new Font("Arial", Font.BOLD, 10));
		enterNumButton.setBounds(195, 360, 49, 30);
		add(enterNumButton);
		
		init = new JButton("Reset"); // 게임 초기화 버튼
		init.setFont(new Font("Arial", Font.BOLD, 10));
		init.setBounds(10, 360, 65, 30);
		add(init);
		
		//배경이미지 우선순위 꼴찌
		back = new JLabel("", background, SwingConstants.CENTER);
		back.setBounds(0, 0, 400, 400);
		add(back);
		
		init.addActionListener(new InitListener());
		enterNumButton.addActionListener(new AnwserListener());

		
	} // construct
	
	// 시작화면 구현하려고 추가한 get/set
	public void setWrongList(int[] list) { iWrongAns = list; }
	
	
	
	public void init(JLabel num, JLabel wrongAnswerLabel, JPanel blank) {
		Random generator = new Random();
		
		int[] ar = new int[50];
		filled=0;
		iWrongAns= ar;
		iRanNum = generator.nextInt(10)+1;
		System.out.println(iRanNum);
		WrongAnswers = "";
		num.setText("" + iRanNum);
		wrongAnswerLabel.setText(WrongAnswers);
		blank.setVisible(true);
	}
	
	
	private  String result(int iRandNum, int iAnswer, String wrongStr, JPanel blank) {

		int tmp,i,j;
		String str ="";
		
		if( iRandNum != iAnswer) {	
			iWrongAns[filled] = iAnswer; 
			filled++;
			
			for(i=filled-1; i>0; i--) { // 틀린 오답들 오름차순으로 나열하는 Bubble Sort
				for(j=0; j<i; j++) {
					if(iWrongAns[j] > iWrongAns[j+1] ) {
						tmp = iWrongAns[j];
						iWrongAns[j] = iWrongAns[j+1];
						iWrongAns[j+1] = tmp;
					}
				}
			}
			for(i=0; i<filled; i++) { // Label에 표현하기위해 오답 int배열을 문자열로 변경
				str += String.valueOf(iWrongAns[i]) + " ";
			}
		}
		else {
			str = " 정답! ";
			blank.setVisible(false);
			String[] option = {"다시 시작", "종료"};
			int select = JOptionPane.showOptionDialog(this, "게임이 종료되었습니다", "게임종료", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);

			if (select == 0) {
				init(num, wrongAnswerLabel, blank);
			} else if (select == JOptionPane.NO_OPTION) {
				main.createGameSelector();
	        	main.addMainPanel();		
			} else {
				System.out.println("CANCLE");
			}
		}
		return str;

	}
	
	private static void audio1()
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

	private class InitListener implements ActionListener { // 게임 초기화
		public void actionPerformed(ActionEvent e) {
			try {
				init(num, wrongAnswerLabel, blank);
				enterNum.setText("");
			} catch (Exception e1) {
	            System.out.println("오류 발생! >> " + e1); 
			}
			
		}
		
	}
	
	private class AnwserListener implements ActionListener { // 숫자 입력 및, 오답 판 갱신
		public void actionPerformed(ActionEvent e) {
			try {
				WrongAnswers = result(iRanNum, Integer.valueOf(enterNum.getText()), WrongAnswers, blank);
				if(WrongAnswers!=" 정답! ") {
					wrongAnswerLabel.setText(WrongAnswers);
					audio1();
				}
				enterNum.setText("");
				
				// 순번 갱신
				if(n != personNum) {
					n++;
					personNumLabel.setText("" + n + " 번째 사람의 차례입니다.");
				}
				if( n==personNum) n=0;

				
			} catch (Exception e1) {
				 System.out.println("오류 발생! >> " + e1); 
			}
		}
		
	}

	
}

