import java.io.Serializable;
import java.awt.Color;

public class Details implements Serializable {
    Color theme;
    public Details(Color theme){
        this.theme = theme;
    }
}
