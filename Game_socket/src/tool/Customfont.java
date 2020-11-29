package tool;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class Customfont { //폰트 만들어주는 함수
    Font fnt;

    public Font getCustomFont(String file_name, int style, float size) {
        try { //파일에서 폰트 파일 가져오기
            fnt = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(file_name))).deriveFont(style, size);
        } catch (Exception ex) { // 해당 이름의 파일이 없는 경우 serif 폰트 적용
            ex.printStackTrace();
            System.err.println(" not loaded.  Using serif font.");
            fnt = new Font("serif", Font.PLAIN, 24);
        }
        return fnt; //폰트 반환

    } // getCustomFont()

}//class Customfont()
