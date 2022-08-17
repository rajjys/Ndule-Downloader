/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.belony.nduledownload.main;

import customViews.RoundJButton;
import downloadManager.Download;
import downloadManager.FormatChooserWindow;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
    private JLabel queryTypeLbl;
    private final ImageIcon yt_search_icon;
    private final ImageIcon yt_video_icon;
    private YoutubeRequestModel ytModel;
    private FormatChooserWindow formatChooser;
    
    ///Constructor
    public MainWindow(){
        ///Init variables
            yt_search_icon = new ImageIcon("assets/icons/red_yt_search.png");
            yt_video_icon = new ImageIcon("assets/icons/yt_video.png");
            ytModel = new YoutubeRequestModel();
            formatChooser = new FormatChooserWindow();
            
        ///Initialise the main GUI
        initGUIComponents();
        ///Activate Events
        InitGUIEvents();
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
        ///Setting up the querytype label
        queryTypeLbl = new JLabel();
        queryTypeLbl.setMaximumSize(new Dimension(16,16));
        queryTypeLbl.setIcon(yt_search_icon);
        ///Setting up the queryField textfield
        queryField = new JTextField(); 
        queryField.setFont(new Font("Roboto", Font.PLAIN, 14));
        queryField.setMinimumSize(queryField.getPreferredSize());
        queryField.setMargin(new Insets(0,8,0,8));
        ///Customize the menu button
        menuBtn = new RoundJButton();
       
        mainScrollPane = new JScrollPane();
        mainPanel = new JPanel();
        ///Adding component to the main panel
        mainPanel.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        ///Constraints for the queryTypeLbl
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 0;
       // c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1,4,1,4);
        mainPanel.add(queryTypeLbl, c);
        ///Constraints for the queryField
        c.gridx = 1;
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
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(6,1,6,1);
        mainPanel.add(menuBtn, c);
        ///Constraints for the mainScrollPane
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1,1,1,1);
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
    ///Display this frame
    public void showWindow(){
        setVisible(true);
    }
    private void InitGUIEvents() {
        
       ///queryField Focus Event
       queryField.addFocusListener(new FocusListener(){
       @Override
       public void focusGained(FocusEvent e) {
       String temp = queryField.getText();
       if(temp.equals(Constants.defaultSearch)){
           queryField.setText("");
       }
    }
       @Override
        public void focusLost(FocusEvent e) {
        String temp = queryField.getText();
       if(temp.equals("")){
           queryField.setText(Constants.defaultSearch);
       }
    }
       });
       ///Document listener to react when the user is typing content
       queryField.getDocument().addDocumentListener(new DocumentListener(){
           @Override
           public void insertUpdate(DocumentEvent e) {
                updateQueryIcon(queryField.getText()); 
           }

           @Override
           public void removeUpdate(DocumentEvent e) {
                updateQueryIcon(queryField.getText()); 
           }

           @Override
           public void changedUpdate(DocumentEvent e) {
                updateQueryIcon(queryField.getText()); 
           }
       });
       ///Enter key pressed.
       queryField.addActionListener((ActionEvent e) -> {
           String query = queryField.getText();
           ///Check the query nature. Either youtube download if valid youtube video or youtube search
               if(!query.isBlank() && !query.isEmpty()) ///Assert it's not empty or otherwise blank
               if(WebValidationUtil.isYoutubeVideo(query)){
                  String videoId = WebValidationUtil.extractVideoID(query);
                  ///Make video information request from youtube
                  var video = ytModel.getVideoInfo(videoId);
                  if(video == null){
                      JOptionPane.showMessageDialog(this, "Could not find the video",
                              "ERROR", JOptionPane.ERROR_MESSAGE);
                      return;
                  }
                  else{
                      ///Proceed to select the format
                      formatChooser.setLocationRelativeTo(this);
                      var selectedFormat = formatChooser.updateAndShow(video); 
                      if(selectedFormat != null){
                          ///Proceed to download
                          new Download(selectedFormat, video.title);
                      }
                  }
               }
               else JOptionPane.showMessageDialog(this, "Youtube search",
                                   "SEARCH", JOptionPane.ERROR_MESSAGE);
               queryField.setText("");
       });
}
    
    public void updateQueryIcon(String query){
        if(WebValidationUtil.isYoutubeVideo(query)){
            ///change the type query type icon to youtube video
            queryTypeLbl.setIcon(yt_video_icon);
        }
        else{
            ///change back the type query type icon to youtube search
            queryTypeLbl.setIcon(yt_search_icon);
        }
    }
}
