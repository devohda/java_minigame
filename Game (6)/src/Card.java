

import java.util.Random;

enum CardColor{
    RED, YELLOW, GREEN
}
enum Shape{
    CIRCLE, SQUARE, TRIANGLE
}
enum BackgroundColor{
    BLACK, GRAY, WHITE
}


public class Card {
    private CardColor cardColor;
    private Shape shape;
    private BackgroundColor backgroundColor;


    public Card(){
        setCardColor();
        setShape();
        setBackgroundColor();
    }// constructor

    //set 함수
    //랜덤으로 배경색, 모양, 도형의 색 결정
    Random random = new Random();

    public void setCardColor(){ //
        CardColor[] cardColorSet = CardColor.values();
        cardColor = cardColorSet[random.nextInt(3)];
    }
    public void setShape(){
        Shape[] shapeSet = Shape.values();
        shape = shapeSet[random.nextInt(3)];
    }
    public void setBackgroundColor(){
        BackgroundColor[] backgroundColorSet = BackgroundColor.values();
        backgroundColor = backgroundColorSet[random.nextInt(3)];
    }

    //get 함수
    public CardColor getCardColor(){
        return cardColor;
    }
    public Shape getShape(){
        return shape;
    }
    public BackgroundColor getBackgroundColor() { return backgroundColor; }

    // to String
    @Override
    public String toString() {
        return "Card{" +
                "cardColor=" + cardColor +
                ", shape=" + shape +
                ", backgroundColor=" + backgroundColor +
                '}';
    }
}//class Card

