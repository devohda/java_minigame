package combination;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    private Card card;

    public CardPanel(Card c){
        setPreferredSize(new Dimension(50,50));
        card = c;
    }//constructor
    
    //카드 패널 GUI 만들기
    public void paintComponent(Graphics paint){

        super.paintComponent(paint); //패널 내 잔상 지우기

        // 도형 색깔 결정하기
        switch (card.getCardColor()){
            case RED : {
                paint.setColor(Color.RED);
                break;
            }
            case YELLOW : {
                paint.setColor(Color.YELLOW);
                break;
            }
            case GREEN : {
                paint.setColor(Color.GREEN);
                break;
            }
        }

        //도형 그리기
        switch (card.getShape()){
            case CIRCLE : { //원 그리기
                paint.fillOval(10,10,40,40);
                break;
            }
            case SQUARE : { // 사각형 그리기
                paint.fillRect(10,10,40,40);
                break;
            }
            case TRIANGLE : { //삼각형 그리기
                int[] x = { 0, 10, 0 };
                int[] y = { 75, 10, 75 };
                paint.fillPolygon( x, y, 3 );
                System.out.println(card);
                break;
            }
        }

        //카드 배경 칠하기
        switch (card.getBackgroundColor()){
            case WHITE : {
                super.setBackground(Color.WHITE);
                break;
            }
            case GRAY : {
                super.setBackground(Color.GRAY);
                break;
            }
            case BLACK : {
                super.setBackground(Color.BLACK);
                break;
            }
        }

    }//paintComponent()
}
