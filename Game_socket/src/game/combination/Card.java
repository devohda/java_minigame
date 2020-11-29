package game.combination;

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
    
    // ---------------------네트워크-----------------------------------
    private int[] indexCombi = new int[3];
    private int colorRand, shapeRand, backRand;


    public Card(){
        setCardColor();
        setShape();
        setBackgroundColor();
    }// constructor
    
    // 네트워크----------------------------------------
    public Card(int[] index){
        setCardColor(index[0]);
        setShape(index[1]);
        setBackgroundColor(index[2]);
    }// constructor
    //여까지-------------------------------------------------------
    
    //set 함수
    //랜덤으로 배경색, 모양, 도형의 색 결정
    Random random = new Random();

    public void setCardColor(){ //
        CardColor[] cardColorSet = CardColor.values();
        colorRand = random.nextInt(3);
        cardColor = cardColorSet[colorRand];
        indexCombi[0] = colorRand;
    }
    public void setShape(){
        Shape[] shapeSet = Shape.values();
        shapeRand = random.nextInt(3);
        shape = shapeSet[shapeRand];
        indexCombi[1] = shapeRand;
    }
    public void setBackgroundColor(){
        BackgroundColor[] backgroundColorSet = BackgroundColor.values();
        backRand = random.nextInt(3);
        backgroundColor = backgroundColorSet[backRand];
        indexCombi[2] = backRand;
    }
    
    // --------------------네트워크----------------------------------
    public void setCardColor(int index){ //
        CardColor[] cardColorSet = CardColor.values();
        cardColor = cardColorSet[index];
    }
    public void setShape(int index){
        Shape[] shapeSet = Shape.values();
        shape = shapeSet[index];
    }
    public void setBackgroundColor(int index){
        BackgroundColor[] backgroundColorSet = BackgroundColor.values();
        backgroundColor = backgroundColorSet[index];
    }
    // ----------------------------네트워크 완료--------------------------
    
    //get 함수
    public CardColor getCardColor(){
        return cardColor;
    }
    public Shape getShape(){
        return shape;
    }
    public BackgroundColor getBackgroundColor() { return backgroundColor; }
    
    //--------------------네트워크---------------------------
    public int[] getIndexCombi() { return  indexCombi; }
    //
    
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

