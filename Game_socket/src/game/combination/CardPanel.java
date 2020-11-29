package game.combination;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    private Card card;

    public CardPanel(Card c){
        card = c;
    }//constructor
    
    //ī�� �г� GUI �����
    public void paintComponent(Graphics paint){

        super.paintComponent(paint); //�г� �� �ܻ� �����

        // ���� ���� �����ϱ�
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

        //���� �׸���
        switch (card.getShape()){
            case CIRCLE : { //�� �׸���
                paint.fillOval(15,15,50,50);
                break;
            }
            case SQUARE : { // �簢�� �׸���
                paint.fillRect(15,15,50,50);
                break;
            }
            case TRIANGLE : { //�ﰢ�� �׸���
                int[] x = { 15, 40, 65 };
                int[] y = { 65, 15, 65 };
                paint.fillPolygon( x, y, 3 );
                break;
            }
        }

        //ī�� ��� ĥ�ϱ�
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
