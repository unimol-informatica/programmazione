package MCC.utils;

import javax.swing.JPanel;

public class SwapPanel {
    
     public static void SwapPanel(JPanel fromPanel, JPanel toPanel){
         fromPanel.setVisible(false);
         toPanel.setVisible(true);
     }
    
}
