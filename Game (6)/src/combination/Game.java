package combination;


import frame_panel.GameSelector;
import tool.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class Game extends JPanel {

    private JButton btnHap;
    private JButton btnGeul;
    private JTextField userinput;
    private JLabel lblScore;
    private JLabel lblState;
    private int _score;
    private GameBoardPanel gameBoard;

    private renewScore update; //actionListener class

    private ImageIcon background;
    private JLabel back;
    private Image resizeimg;

    private Customfont makeFnt;
    Font fnt;

    public Game(GameSelector g) {
    	
    	setBounds(125, 130, 800, 540); // Game �г� ũ�� ����
        setLayout(null); // ���̾ƿ� �Ŵ��� ����


        /**********************************/
        /**********���� �κ� �����***********/
        /**********************************/

        boardInfo = new Card[9]; // ������ ����
        setGameBoardInfo(); //���� �ʱ�ȭ
        gameBoard = new GameBoardPanel(boardInfo); // ������ �����
        gameBoard.setBounds(50,120,350,350); // ������ ��ġ, ũ�� ����
        gameBoard.setBackground(new Color(236, 191, 246)); // ������ ���� ����
        add(gameBoard); // ������ �߰�



        /**********************************/
        /**********���� �κ� �����***********/
        /**********************************/

        _score = 0; // ���� �ʱ�ȭ
        lblScore = new JLabel("���� : 0"); // lblScore �ʱ�ȭ
        lblState = new JLabel("������ �����մϴ�."); // lblState �ʱ�ȭ
        lblState.setHorizontalAlignment(JLabel.CENTER); //���� ��� ����
        lblState.setForeground(Color.black);

        btnHap = new JButton("��");
        btnGeul = new JButton("��");
        userinput = new JTextField(5);

        //add action listener
        update = new renewScore();
        btnHap.addActionListener(update);
        btnGeul.addActionListener(update);
        userinput.addActionListener(update);
        
        /************* ��Ʈ ���� **************/
        makeFnt = new Customfont();
        fnt = makeFnt.getCustomFont("font/���ü.ttf",Font.BOLD,24);
        lblState.setFont(fnt);
        fnt = makeFnt.getCustomFont("font/���ü.ttf",Font.PLAIN,22);
        lblScore.setFont(fnt);

        // ��ġ ����
        lblState.setBounds(170,35,500,50);
        lblScore.setBounds(490,120,200,50);
        btnGeul.setBounds(480, 380, 70,30);
        btnHap.setBounds(480,420,70,30);
        userinput.setBounds(560,420, 160, 30);

        add(lblState);
        add(lblScore);
        add(btnHap);
        add(btnGeul);
        add(userinput);

        // ������ ��� ����
        ResizeImg img = new ResizeImg("images/bg_combination.jpg",800,540);
        resizeimg = img.getResizeImage();
        background = new ImageIcon(resizeimg);
        back = new JLabel("", background, SwingConstants.CENTER);
        back.setBounds(0, 0, 800, 540);
        add(back); // ��� �̹��� �������� ��ġ -> ���� ���ʿ� ��ġ�ϱ� ����

        calculateHap();

    }//constructor

    private Card[] boardInfo;
    private ArrayList<HashSet> hapset;
    private ArrayList <HashSet> deleteHapset;

    public void init(){

        _score = 0;
        lblScore.setText("���� : " + _score);
        lblState.setText("������ �����մϴ�.");
        lblState.setForeground(Color.black);

        hapset = new ArrayList<>();
        deleteHapset = new ArrayList<>();

        boardInfo = new Card[9];


        setGameBoardInfo(); //���� �ʱ�ȭ
        gameBoard = new GameBoardPanel(boardInfo);
        gameBoard.setBounds(50,120,500,500);
        add(gameBoard);
        repaint();



        calculateHap();
    }

    /*******************************/
    /******** ���� �� ����� *********/
    /*******************************/


    // Ÿ�� �ߺ� ���� ������ �����
    public void setGameBoardInfo(){
        for (int i = 0; i < 9; i++) {
            Card tmp = new Card();

            boolean duplicated = false;
            //�ߺ� ���� ī���� �����
            for(int j = 0 ; j < i; j++){
                if(tmp.getCardColor() == boardInfo[j].getCardColor()){
                    if (tmp.getShape() == boardInfo[j].getShape()) {
                        if (tmp.getBackgroundColor() == boardInfo[j].getBackgroundColor()){
                            System.out.println("same~!");
                            duplicated = true;
                            break;
                        }
                    }
                }
            }
            if(!duplicated) boardInfo[i] = tmp;
            else i--;
        }
    }//setGameBoardInfo()


    /*******************************/
    /******* '��' ���� ���ϱ� ********/
    /*******************************/

    private boolean stateBg(Card a, Card b, Card c){

        boolean state = false;
        if ((a.getBackgroundColor() == b.getBackgroundColor()) && (b.getBackgroundColor() == c.getBackgroundColor()))
            state = true;
        if ((a.getBackgroundColor() != b.getBackgroundColor()) && (b.getBackgroundColor() != c.getBackgroundColor()) && (a.getBackgroundColor() != c.getBackgroundColor()))
            state = true;

        return state;
    }

    private boolean stateShape(Card a, Card b, Card c){

        boolean state = false;
        if ((a.getShape() == b.getShape()) && (b.getShape() == c.getShape()))
            state = true;
        if ((a.getShape() != b.getShape()) && (b.getShape() != c.getShape()) && (a.getShape() != c.getShape()))
            state = true;

        return state;
    }

    private boolean stateColor(Card a, Card b, Card c){
        boolean state = false;
        if ((a.getCardColor() == b.getCardColor()) && (b.getCardColor() == c.getCardColor()))
            state = true;
        if ((a.getCardColor() != b.getCardColor()) && (b.getCardColor() != c.getCardColor()) && (a.getCardColor()!= c.getCardColor()))
            state = true;

        return state;
    }

    private boolean isHap(Card a, Card b, Card c){
        return(stateBg(a,b,c)&&stateColor(a,b,c)&&stateShape(a,b,c));
    }

    // �� ����ϴ� �Լ�
    public void calculateHap(){
        hapset = new ArrayList<>(); //�� ���� ���� ����
        deleteHapset = new ArrayList<>(); //�̹� �Էµ� �� ���� ���� ����
        
        int hapNum = 0;

        //ī�� �� ���� ������ ���� �� ã��
        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 8; j++){
                for(int k = j + 1; k < 9 ; k++){
                    if(isHap(boardInfo[i],boardInfo[j],boardInfo[k])){
                        HashSet<Integer> set = new HashSet<Integer>(3);
                        set.add(i+1);
                        set.add(j+1);
                        set.add(k+1);
                        hapset.add(set);
                        hapNum++;
                    }
                }
            }
        }

        //�� ���� �³� ������
        if(hapNum == 0){
            System.out.println("NONE");
        }
        else{
            for(HashSet obj : hapset){
                System.out.println(obj);
            }
        }
    } //calculateHap()

    
    /*******************************/
    /********* ���� ����ϱ� *********/
    /*******************************/

    class renewScore implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnGeul){ //�� ���� ���
                if(hapset.isEmpty()){
                    _score += 3;
                    lblState.setText("'��' �Դϴ�. ������ �����մϴ�!");
                    lblState.setForeground(new Color(31, 76, 224));
                    lblScore.setText("���� : " + _score);

                    restartGame();
                }
                else{
                    _score -= 1;
                    lblState.setText("���� ���� �� �� �ִ� ��찡 ���ҽ��ϴ�.");
                    lblState.setForeground(new Color(184, 22, 22));
                }
            }
            else if(e.getSource() == btnHap || e.getSource() == userinput){ //���� ���� ���, enter ģ ���

                int answer = 0;

                //���� 3���� �Էµ� ��찡 �ƴ� ��(���� ���� ��ȣ�� �� ���)
                try{
                    String userInput = userinput.getText();
                    answer = Integer.parseInt(userInput);
                    System.out.println(answer);
                    if(answer>=1000||answer<100) lblState.setText("1 ~ 9���� ���� �ٸ� ���� 3���� �Է��ϼ���"); //���ڰ� 3������ �۰ų� ���� ���
                    else{
                        HashSet<Integer> userAnswerSet = new HashSet<Integer>(3);
                        int num1, num2, num3;
                        num1 = answer/100;
                        num2 = answer%100/10;
                        num3 = answer%10;
                        if(num1 == 0 || num2 == 0 || num3 == 0) lblState.setText("1 ~ 9���� ���� �ٸ� ���� 3���� �Է��ϼ���"); //0�� ���� ���
                        else{
                            userAnswerSet.add(num1);
                            userAnswerSet.add(num2);
                            userAnswerSet.add(num3);
                        }


                        if(userAnswerSet.size() != 3) lblState.setText("1 ~ 9���� ���� �ٸ� ���� 3���� �Է��ϼ���");
                        else{
                            boolean isBefore = false;
                            for (HashSet i : deleteHapset){
                                if(userAnswerSet.equals(i)){
                                    lblState.setText("ī�� " + userAnswerSet + " ��(��) �̹� ���Խ��ϴ�.");
                                    lblState.setForeground(new Color(184, 22, 22));
                                    _score--;
                                    isBefore = true;
                                    break;
                                }
                            }

                            if(!isBefore){ //������ ������ ���� ���
                                boolean isAnswer = false;
                                for(HashSet i : hapset){
                                    if(userAnswerSet.equals(i)){
                                        deleteHapset.add(i);
                                        hapset.remove(i);
                                        lblState.setText("ī�� " + userAnswerSet + " ��(��) '��' �Դϴ�.");
                                        lblState.setForeground(new Color(31, 76, 224));
                                        _score++;
                                        isAnswer = true;
                                        break;
                                    }
                                }
                                if(!isAnswer){
                                    _score--;
                                    lblState.setText("ī�� " + userAnswerSet + " ��(��) ���� �ƴմϴ�.");
                                    lblState.setForeground(new Color(184, 22, 22));
                                }
                            }

                        }
                    }

                }catch (NumberFormatException ex){
                    lblState.setForeground(Color.black);
                    lblState.setText("���� 3���� �Է��ϼ���");
                }finally {
                    userinput.setText("");
                }
            }
            lblScore.setText("���� : " + _score);
        }
    }

    public void restartGame(){
        String[] option = {"�ٽ� ����","����"};
        int select = JOptionPane.showOptionDialog(this,"������ ����Ǿ����ϴ�","��������",JOptionPane.DEFAULT_OPTION ,JOptionPane.INFORMATION_MESSAGE,null,option,option[0]);

        if(select == 0){ //�ٽ� ����
            init();
        }else{ //���� ������ �ý��� ����
            setVisible(false);
            //�����ϱ� ���� ���� �гο� �Լ� ������ �� �� ����
        }
    }
}

