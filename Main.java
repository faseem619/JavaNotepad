import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
public class Main{
    public static void main(String[] args) {
        if(System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) //checking if os is mac os
		{
		 System.setProperty("apple.laf.useScreenMenuBar", "true");
		  System.setProperty(
            "com.apple.mrj.application.apple.menu.about.name", "Name");
		}
        if(!new File("details.ser").isFile()){

            try{
                
                FileOutputStream fout = new FileOutputStream("details.ser");
                Details D = new Details(new Color(0xf9fafa),Color.BLACK,Color.BLACK);
                ObjectOutputStream Oout = new ObjectOutputStream(fout);
                Oout.writeObject(D);
                Oout.close();
                fout.close();
    
            }catch(Exception e){e.printStackTrace();}
        }

        new Notepad();
    }
}