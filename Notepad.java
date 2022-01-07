import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Notepad implements ActionListener {
    JComboBox<String> fontpicker; 
    JTextArea textArea;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenuItem exitItem;
    JMenuItem openItem;
    JMenuItem saveItem;
    JRadioButtonMenuItem darkThemeItem;
    JRadioButtonMenuItem lightThemeItem;
    JMenu themeItem;
    String [] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); 
    Notepad(){
        JFrame frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // getting serialized object
         Details D = null;
         try{
            FileInputStream fin = new FileInputStream("details.ser");
            ObjectInputStream Oout = new ObjectInputStream(fin);
            D = (Details)Oout.readObject();
            Oout.close();
            fin.close();

        }catch(Exception e2){e2.printStackTrace();}
        finally{

            textArea = new JTextArea(); 
            textArea.setBackground(D!=null?D.theme:new Color(0xf9fafa));
            textArea.setFont(new Font("Arial",Font.PLAIN,18));
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            if(D!=null){
                textArea.setForeground(D.foreGround);
                textArea.setCaretColor(D.caretColor);

            }
        }
       

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500,550));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        JPanel topPanel = new JPanel();
        JLabel sizLabel = new JLabel("Size:");
        JLabel fontLabel = new JLabel("Font :");
        JSpinner sizeSpinner = new JSpinner();
        sizeSpinner.setPreferredSize(new Dimension(45,25));
        sizeSpinner.setValue(18);
        sizeSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),textArea.getFont().getStyle(),(int)sizeSpinner.getValue()));
                
            }
            
        });

        fontpicker = new JComboBox<String>(fonts);
        fontpicker.setPreferredSize(new Dimension(150,25));
        fontpicker.setSelectedItem("Arial");
        fontpicker.addActionListener(this);
        
        //-------menubar-----

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");


        exitItem = new JMenuItem("Exit");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");

        ButtonGroup group = new ButtonGroup();
        darkThemeItem = new JRadioButtonMenuItem("Dark Theme");
        lightThemeItem = new JRadioButtonMenuItem("Light Theme");
        group.add(darkThemeItem);
        group.add(lightThemeItem);

        darkThemeItem.addActionListener(this);
        lightThemeItem.addActionListener(this);

        themeItem = new JMenu("Theme"); //submenu
        themeItem.add(darkThemeItem);
        themeItem.add(lightThemeItem);
        

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        editMenu.add(themeItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        exitItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);

        /* menubar*/

        topPanel.add(fontLabel);
        topPanel.add(fontpicker);
        topPanel.add(sizLabel);
        topPanel.add(sizeSpinner);

        frame.setJMenuBar(menuBar);
        frame.add(scrollPane,BorderLayout.CENTER);
        frame.add(topPanel,BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        textArea.requestFocusInWindow();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==fontpicker){  
            textArea.setFont(new Font(fontpicker.getSelectedItem().toString(),textArea.getFont().getStyle(),textArea.getFont().getSize()));
       }
       else if(e.getSource()==exitItem){
           System.exit(0);
       }
       else if(e.getSource()==saveItem){
           JFileChooser fileChooser = new JFileChooser();
           fileChooser.setCurrentDirectory(new File("."));

           int response = fileChooser.showSaveDialog(null);
           if(response==JFileChooser.APPROVE_OPTION){
               File myFile ;
               PrintWriter writer = null;

               myFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
               
               try {
                writer = new PrintWriter(myFile);
                writer.println(textArea.getText());
            } catch (FileNotFoundException e1) {
               
                e1.printStackTrace();
            }
            finally{

                writer.close();
            }


           }
       }
       else if(e.getSource()==openItem){
            JFileChooser fileChooser = new JFileChooser();
           fileChooser.setCurrentDirectory(new File("."));
           fileChooser.setFileFilter( new FileNameExtensionFilter("Text Files", "txt"));

           int response = fileChooser.showOpenDialog(null);
           if(response==JFileChooser.APPROVE_OPTION){
               Scanner sc = null ;
               File myFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
               try {
                sc = new Scanner(myFile);
                if(myFile.isFile()){
                    while(sc.hasNextLine()){
                        textArea.append(sc.nextLine()+"\n");

                    }
                }
            } catch (FileNotFoundException e1) {
                
                e1.printStackTrace();
            }
            finally{
                sc.close();
            }
            
           }
       }
       else if(e.getSource()==darkThemeItem){
           textArea.setBackground(new Color(0x272727));
           textArea.setForeground(Color.yellow);
           textArea.setCaretColor(Color.yellow);
               try{
            Details D = new Details(new Color(0x272727),Color.yellow,Color.yellow);
            FileOutputStream fout = new FileOutputStream("details.ser");
            ObjectOutputStream Oout = new ObjectOutputStream(fout);
            Oout.writeObject(D);
            Oout.close();
            fout.close();

        }catch(Exception e2){e2.printStackTrace();}
       }
       else if(e.getSource()==lightThemeItem){
           textArea.setBackground(new Color(0xf9fafa));
            textArea.setForeground(Color.BLACK);
            textArea.setCaretColor(Color.BLACK);
            try{
            Details D = new Details(new Color(0xf9fafa),Color.BLACK,Color.BLACK);
            FileOutputStream fout = new FileOutputStream("details.ser");
            ObjectOutputStream Oout = new ObjectOutputStream(fout);
            Oout.writeObject(D);
            Oout.close();
            fout.close();

        }catch(Exception e2){e2.printStackTrace();}
       }
    }
    
}
