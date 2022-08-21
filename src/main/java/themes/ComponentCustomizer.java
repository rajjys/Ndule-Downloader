/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package themes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jonathan Idy
 */
public class ComponentCustomizer {
    ///Customize component by id
    ///We need uniformity of components across the app
    public static void customizeLabel(JLabel label, int id){
        switch(id){
            case 1: customizeLabelStyle1(label);
                
                break;
            case 2: customizeLabelStyle2(label);
                break;
        }
    }
    private static void customizeLabelStyle1(JLabel label) {
      var courrier = new Font("Roboto", Font.BOLD,12);
        label.setFont(courrier);
        label.setForeground(Color.white);
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(2,4,2,4));
        label.setBackground(new Color(0,0,0,156));
    }
    private static void customizeLabelStyle2(JLabel label){
        var courrier = new Font("Sans Serif", Font.PLAIN,10);
        label.setFont(courrier);
        label.setForeground(new Color(128, 128,128));
        label.setBorder(new EmptyBorder(2,4,2,4));
        ///label.setOpaque(true);
        ///label.setBackground(new Color(0,0,0,156));
    }
}
