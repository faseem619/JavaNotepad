import java.io.Serializable;
import java.awt.Color;

public class Details implements Serializable {
    Color theme;
    Color foreGround;
    Color caretColor;
    public Details(Color theme){
        this.theme = theme;
    }
    public Details(Color theme,Color foreGround, Color caretColor){
        this.theme = theme;
        this.foreGround = foreGround;
        this.caretColor = caretColor;
    }
}
