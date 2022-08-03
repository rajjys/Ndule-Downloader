/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.belony.nduledownload.main;

import customViews.RoundJButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Jonathan Idy
 */
public class MainWindow extends JFrame {
    ///Main Window components
    private JTextField queryField;
    private JButton searchBtn;
    private JButton menuBtn;
    private JPanel searchResultPanel;
    private JPanel downloadsPanel;
    private JPanel mainPanel;
    private JScrollPane mainScrollPane;
    
    ///Constructor
    public MainWindow(){
        initGUIComponents();
    }

    ///Initialise Main window GUI components
    private void initGUIComponents() {
        ///Setting up JFrame 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 380)); ///defaul
        ///setLayout(new GridBagLayout());
        setUndecorated(true); ///remove the title bar
        ///Set program icon
        setIconImage(new ImageIcon("assets/icons/download_icon.png").getImage());
        ///Setting up the queryField textfield
        queryField = new JTextField(); 
        queryField.setFont(new Font("Roboto", Font.PLAIN, 14));
        queryField.setMinimumSize(queryField.getPreferredSize());
        queryField.setMargin(new Insets(0,6,0,6));
    
        searchBtn = new JButton("?");
        ///Customize the menu button
        menuBtn = new RoundJButton();
       
        mainScrollPane = new JScrollPane();
        mainPanel = new JPanel();
        ///Adding component to the main panel
        mainPanel.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        ///Constraints for the queryField
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipady = 4;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1,1,1,1);
        mainPanel.add(queryField, c);
        ///Constraints for the search button
        
        ///Constraints for the menu button
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        mainPanel.add(menuBtn, c);
        ///Constraints for the mainScrollPane
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        mainPanel.add(mainScrollPane, c);
        
        add(mainPanel);///Add the main panel to the main window
        pack();
        ///Position window at the top right corner upon startup
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - getWidth();
        int y = 0;
         setLocation(x, y);
    }
    ///Hide this frame
    public void hideWindow(){
        setVisible(false);
    }
    public void showWindow(){
        setVisible(true);
    }
    
}
