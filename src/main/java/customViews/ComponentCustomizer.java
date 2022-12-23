/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customViews;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
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
            case 3: customizeLabelStyle3(label);
                break;
            case 4: customizeLabelStyle4(label);
        }
    }
    private static void customizeLabelStyle1(JLabel label) {
        ///Roboto font, White text color with black transparent background
        label.setFont(new Font("Roboto", Font.BOLD,11));
        label.setForeground(Color.white);
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(2,4,2,4));
        label.setBackground(new Color(0,0,0,156));
    }
    private static void customizeLabelStyle2(JLabel label){
        ///Grey text color with no background
        var courrier = new Font("SansSerif", Font.PLAIN,10);
        label.setFont(courrier);
        label.setForeground(new Color(5, 75,40));
        label.setBorder(new EmptyBorder(2,4,2,4));
        
    }
    private static void customizeLabelStyle3(JLabel label){
        ///Darker text with no background and bigger font for titles
        label.setFont(new Font("SansSerif", Font.BOLD,12));
        label.setForeground(new Color(50,50,50));
        label.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));
    }
    private static void customizeLabelStyle4(JLabel label){
        ///Bigger Roboto font, White text color with black and transparent background
        ///Adapted for titles on top of a background
        label.setFont(new Font("Roboto", Font.BOLD,13));
        label.setForeground(Color.white);
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(2,4,2,4));
        label.setBackground(new Color(0,0,0,156));
    }
    
}
