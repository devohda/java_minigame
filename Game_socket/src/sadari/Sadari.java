package sadari;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import tool.*;

@SuppressWarnings("serial")
public class Sadari extends JPanel implements SadariInterFace{
	

	public STATUS mainStatus = STATUS.INIT; //기본상태 INIT
	
	private SadariPanel mainPanel;
	
	private JPanel inputPanel;  // 아래 텍스트필드와 버튼 넣는 패널
	private JTextField inputNumber; //정답입력 텍스트필드
	private JButton startButton; // 시작버튼과 초기화버튼
	private JButton resetButton; //리셋버튼
	
	private JLabel borderLab; //테두리 꾸미기
	private Color color,colorborder; //테두리 꾸미기
	

    private Customfont makeFnt;
    private Font fnt;
    
    
	public Sadari()
	{		
		
        makeFnt = new Customfont(); //외부 폰트 만들기
        fnt = makeFnt.getCustomFont("font/SSShinb7.ttf", Font.PLAIN, 20); //폰트 지정
        
		color= new Color(255, 255, 224);
		colorborder = new Color(201,200,208);
		
		borderLab = new JLabel("");
		borderLab.setBorder(new TitledBorder(new LineBorder(colorborder,5), ""));
		borderLab.setBounds(0, 0, 450, 500);
		add(borderLab); // 테두리 꾸미기
		
		setBounds(300, 170, 450, 500);
		setLayout(null);
		setBackground(color); //배경색지정

		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout()); // 왼쪽에서 오른쪽배치

		inputNumber = new JTextField(2);
		inputNumber.setHorizontalAlignment(SwingConstants.CENTER);
		inputNumber.setSize(new Dimension(10,10)); //정답 입력 텍스트 필드 설정


		startButton = new JButton("START");
		startButton.setFont(fnt);
		resetButton = new JButton("RESET");
		resetButton.setFont(fnt);


		inputPanel.setBounds(0, 450, 450, 50);
		inputPanel.add(inputNumber);
		inputPanel.add(startButton);
		inputPanel.add(resetButton);

		add(inputPanel);	 //inputPanel 설정

		mainPanel = new SadariPanel(this);
		mainPanel.setLayout(null);

		mainPanel.setBounds(0, 0, 450, 360); //메인패널 위치 설정

		mainPanel.setBackground(color); //메인패널 배경색설정
		add(mainPanel);   //스타트 버튼과 리셋버튼 생성후 글꼴 설정


		startButton.addActionListener(new StartListener());
		resetButton.addActionListener(new ResetListener());

	}
	
	private class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			String text = inputNumber.getText().trim();
			inputNumber.setText(" ");
			
			if( text.equals("")) text = "1"; //정답입력 공백이면 1로 치환
			int input = Integer.valueOf(text);
			if( input < 1 ) input = 1; 
			if( input > 5 ) input = 5;
			mainPanel.setStartPosition(input); //시작 지점 설정

			mainStatus=STATUS.DRAWING;
			mainPanel.repaint();	 //사다리 리페인트
		} 	 
	} 
	
	private class ResetListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			inputNumber.setText(" ");
			
			mainStatus = STATUS.INIT; // 사다리 초기화
			mainPanel.repaint(); //다시그리기
		} 	
	} 
	
}

