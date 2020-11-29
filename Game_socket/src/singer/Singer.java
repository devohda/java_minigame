package singer;

import tool.Customfont;
import tool.LabelThread;
import tool.ResizeImg;
import tool.Sound;
import tool.LabelThread;
import javax.swing.*;
import frame_panel.GameSelector;
import frame_panel.MainPanel;
import socket.ClientGame;

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
   private LabelThread time; // 타이머
   private ResizeImg rImg; // 이미지 사이즈 조절용도 
    private Image resizeImg;// 이미지 사이즈 조절용도
   
   // 네트워크 구현위한 버튼
   private JButton btnReset, back; // 리셋, 뒤로가기 버튼
   
   // 채팅구현
   private JTextArea chatArea;
   private JScrollPane scrollPane;
   private JTextField chat;
   private String nickName; 
       
   private String name; 
   private ClientGame cl;
   
   private GameSelector gameselector;
   private MainPanel main; 
   
   // 외부 폰트 적용
   private Customfont makeFnt;
   private Font fnt;
   
   public Singer(ClientGame c, GameSelector gs, MainPanel m)  {
      
      gameselector = gs; // 게임셀렉터 객체
      main = m; // 메인패널 객체
      cl = c; // 클라이언트 객체
      
      setBounds(50, 100, 950, 550);
      this.setLayout(null);
      
      singerList = "블랙핑크,소녀시대,  BTS,트와이스,레드벨벳, 아이유,  EXO,  선미, 아이콘, 헤이즈,멜로망스, 김하온,  위너,여자친구, 마마무,모모랜드, 아이들,키드밀리,엠씨더맥스,  있지, 장범준, 백예린,  태연,오마이걸, 잔나비"; // 가수들 저장
      pickSinger = singerList.split(",");
      
      // 아래 두 행 네트워크 구현위한 코드 
      index = cl.getSingerIndex(); // 여기는 아직 서버로 간 메세지가 클라이언트에 닿지 못한 상태 초기화 버튼으로 refresh
      
      ResizeImg bImg = new ResizeImg("images/sing_backimg3.png",750,550); // 크기조절 950 -> 750
      resizeimg = bImg.getResizeImage(); // 재설정된 이미지 반환
      icon = new ImageIcon(resizeimg); // 이미지 아이콘에서 사용할 수 있게 설정
      boardimg = new JLabel("",icon,SwingConstants.CENTER); // j라벨에 배경사진 삽입
      boardimg.setBounds(0, 0, 750, 550); // 크기조절 950 -> 750
       
      board = new JLabel();
      board.setBounds(250, 100, 600, 100);
      board.setBackground(Color.white);
      board.setLayout(null);
            
      makeFnt = new Customfont();
      fnt = makeFnt.getCustomFont("font/Typo_HelloPOP_OutlineM.ttf", Font.PLAIN, 100);
      
      word = new JLabel(pickSinger[index]); //j라벨에 뽑힌 가수 넣으며 생성
      word.setBounds(120, 10, 520, 200); // 사이즈 및 위치 조절
      word.setFont(fnt); // 글자 스타일 변경
      word.setForeground(new Color(0,0,255)); // 글자색 파란색으로 변경
      
      btnReset = new JButton(""); // 재생성 버튼
      btnReset.setBounds(600, 70, 100, 50); // 위치 및 사이즈 조절
      add(btnReset); 
      
      back = new JButton(""); // 뒤로가기 버튼
      back.setBounds(40, 450, 100, 50); // 위치 및 사이즈 조절
      add(back);
      
      // 채팅창 구현            
      chatArea = new JTextArea(); 
      scrollPane = new JScrollPane(chatArea);
      chatArea.setEditable(false); 
      scrollPane.setBounds(750, 0, 200, 525);
      add(scrollPane);      
      chat = new JTextField();
      chat.setBounds(750, 525, 200, 25);
       
      chat.addActionListener(new sendMessage());
      btnReset.addActionListener(new ResetListener());
      back.addActionListener(new BackListener());
      
      // 타이머 쓰레드 생성
      time = new LabelThread(10);
      time.start();
      
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
        btnReset.setIcon(icon);
        btnReset.setBorderPainted(false);
        btnReset.setContentAreaFilled(false);
        btnReset.setFocusPainted(false);
      
      this.add(word);
      this.add(board);
      this.add(boardimg);
      add(chat);   
      
      cl.setGuiSing(this);
      name = cl.getNickname();
   }
   
   public void init() {
      index = cl.getSingerIndex();
      word.setText(pickSinger[index]);
      
      //타이머 쓰레드 적용
      if(time.getThread().isAlive()){
         time.getThread().interrupt(); 
      }
      remove(time);
      time = new LabelThread(10);
      time.setBounds(300, 150, 500, 200);
      time.setFont(new Font("MD솔체", Font.BOLD, 150));
      add(time);
      add(board);
      add(boardimg);   
      revalidate();
      repaint();
      time.start();
   }
   
   public String getList() {
      return singerList;
   }
   
   // 채팅창에 메세지 출력
   public void appendMsg(String msg) {
        chatArea.append(msg);
    }     
   
   // 서버에 메세지 전달
   private class sendMessage implements ActionListener {
      public void actionPerformed(ActionEvent e) {
           String msg = name + " : " + chat.getText() + "\n";
           cl.sendMessage(msg);
           chat.setText("");
       }
   }
   
   // 초기화 리스너
   private class ResetListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         cl.sendMessage("[SINGERINIT]");
       }
   }
   
   // 뒤로가기 리스너
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