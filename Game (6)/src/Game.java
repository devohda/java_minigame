

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class Game extends JPanel {

    private JButton btnHap;
    private JButton btnGeul;
    private JTextField userinput;
    private JLabel lblScore;
    private JLabel lblState;
    private int _score;

    private renewScore update; //actionListener class

    Font fnt = new Font("����ǹ��� �־�",Font.BOLD,20);

    public Game() {
    	
    	setBounds(275, 40, 500, 700);
        setPreferredSize(new Dimension(500,700));
        setBackground(new Color(223, 255, 188));
        setLayout(null);

        btnHap = new JButton("��");
        btnGeul = new JButton("��");
        userinput = new JTextField(5);
        lblScore = new JLabel("���� : 0");
        lblState = new JLabel("������ �����մϴ�.");

        update = new renewScore();
        btnHap.addActionListener(update);
        btnGeul.addActionListener(update);

        lblScore.setFont(fnt);
        lblState.setFont(fnt);

        lblScore.setBounds(10,0,200,50);
        lblState.setBounds(10,50,500,50);
        btnGeul.setBounds(50, 635, 70,30);
        btnHap.setBounds(180,635,70,30);
        userinput.setBounds(260,635, 160, 30);

        add(lblScore);
        add(lblState);
        add(btnHap);
        add(btnGeul);
        add(userinput);
    }//constructor

    private Card[] boardInfo;
    private ArrayList<HashSet> hapset;
    private ArrayList <HashSet> deleteHapset;

    public void init(){

        _score = 0;
        lblScore.setText("���� : " + _score);
        lblState.setText("������ �����մϴ�.");

        hapset = new ArrayList<>();
        deleteHapset = new ArrayList<>();

        boardInfo = new Card[9];
        setGameBoardInfo(); //���� �ʱ�ȭ
        GameBoardPanel gameBoard = new GameBoardPanel(boardInfo);
        gameBoard.setBounds(0,100,500,500);
        add(gameBoard);

        calculateHap();
    }

    /*******************************/
    /******** ���� �� ����� *********/
    /*******************************/


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


    public void calculateHap(){
        hapset = new ArrayList<>();
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

    }

    /*******************************/
    /********* ���� ����ϱ� *********/
    /*******************************/

    class renewScore implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnGeul){ //�� ���� ���
                if(hapset.isEmpty()){
                    _score += 3;
                    lblState.setText("'��' �Դϴ�. ¦¦¦!!! 3�� ȹ���ϼ̽��ϴ�!");
                    lblState.setForeground(Color.BLUE);
                    restartGame();
                }
                else{
                    _score -= 1;
                    lblState.setText("���� ���� �� �� �ִ� ��찡 ���ҽ��ϴ�.");
                    lblState.setForeground(Color.RED);
                }
            }
            else if(e.getSource() == btnHap){ //���� ���� ���

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
                                    lblState.setForeground(Color.RED);
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
                                        lblState.setForeground(Color.BLUE);
                                        _score++;
                                        isAnswer = true;
                                        break;
                                    }
                                }
                                if(!isAnswer){
                                    _score--;
                                    lblState.setText("ī�� " + userAnswerSet + " ��(��) ���� �ƴմϴ�.");
                                    lblState.setForeground(Color.RED);
                                }
                            }

                        }
                    }

                }catch (NumberFormatException ex){
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
        int select = JOptionPane.showOptionDialog(this,"������ ����Ǿ����ϴ�","��������",0,JOptionPane.INFORMATION_MESSAGE,null,option,option[0]);

        if(select == 0){ //�ٽ� ����
            init();
        }else{ //���� ������ �ý��� ����
            System.out.println("����");
            //�����ϱ� ���� ���� �гο� �Լ� ������ �� �� ����
        }
    }
}

