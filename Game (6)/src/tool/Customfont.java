package tool;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class Customfont { //폰트 만들어주는 함수
    Font fnt;
    
    public Font getCustomFont(String file_name, int style, float size){
        try {
            fnt = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(file_name))).deriveFont(style,size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(" not loaded.  Using serif font.");
            fnt = new Font("serif", Font.PLAIN, 24);
        }
        return fnt;
    } // getCustomFont()

}//class Customfont()
