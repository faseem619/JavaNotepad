import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;

public class Notepad {

    Notepad(){
        JFrame frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setBackground(new Color(0xf9fafa));
        textArea.setFont(new Font("Arial",Font.PLAIN,18));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
       

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500,550));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        JPanel topPanel = new JPanel();
        JLabel sizLabel = new JLabel("Font Size:");
        JSpinner sizeSpinner = new JSpinner();
        sizeSpinner.setPreferredSize(new Dimension(45,25));
        sizeSpinner.setValue(18);
        sizeSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),textArea.getFont().getStyle(),(int)sizeSpinner.getValue()));
                
            }
            
        });
        topPanel.add(sizLabel);
        topPanel.add(sizeSpinner);

        

        frame.add(scrollPane,BorderLayout.CENTER);
        frame.add(topPanel,BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        textArea.requestFocusInWindow();

    }
    
}
