/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.belony.nduledownload.main;

import youtubeQuery.YoutubeRequestModel;
import customViews.RoundJButton;
import downloadManager.Download;
import downloadManager.DownloadManagerWindow;
import downloadManager.FormatChooserWindow;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
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
    private final JButton menuBtn;
    private JPanel searchResultPanel;
    private final JPanel mainPanel;
    private final JScrollPane mainScrollPane;
    private final JLabel queryTypeLbl;
    private final ImageIcon yt_search_icon;
    private final ImageIcon yt_video_icon;
    private final ImageIcon menu_icon;
    private final YoutubeRequestModel ytModel;
    private final FormatChooserWindow formatChooser;
    private final DownloadManagerWindow dmPanel;
    
    ///Constructor
    public MainWindow(){

            mainScrollPane = new JScrollPane();
            mainPanel = new JPanel();
            yt_search_icon = new ImageIcon("assets/icons/red_yt_search.png");
            yt_video_icon = new ImageIcon("assets/icons/yt_video.png");
            menu_icon = new ImageIcon("assets/icons/menu_dots.png");
                    ///Init variables
            queryTypeLbl = new JLabel();
            menuBtn = new RoundJButton(menu_icon);
            ytModel = new YoutubeRequestModel();
            formatChooser = new FormatChooserWindow();
            dmPanel = new DownloadManagerWindow();
            
        ///Initialise the main GUI
        initGUIComponents();
        ///Activate Events
        InitGUIEvents();
    }
public void showWindow(){
    setVisible(true);
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
        
        queryTypeLbl.setMaximumSize(new Dimension(16,16));
        queryTypeLbl.setIcon(yt_search_icon);
        ///Setting up the queryField textfield
        queryField = new JTextField(); 
        queryField.setFont(new Font("Roboto", Font.PLAIN, 14));
        queryField.setMinimumSize(queryField.getPreferredSize());
        queryField.setMargin(new Insets(0,8,0,8));
        ///Customize the menu button
        
        ///Adding component to the main panel
        var layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        var c = new GridBagConstraints();
        ///Constraints for the queryTypeLbl
        c.ipady = 0;
       // c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1,4,1,4);
        addComponent(queryTypeLbl, mainPanel, layout, c, 0,0,1,1,0,0);
        ///Constraints for the queryField
        c.ipady = 4;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1,1,1,1);
        addComponent(queryField, mainPanel, layout, c, 1,0,1,1,1,0);
        ///Constraints for the menu button
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(6,1,6,1);
        addComponent(menuBtn, mainPanel, layout, c, 2,0,1,1,0,0);
        ///Constraints for the mainScrollPane
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1,1,1,1);
        addComponent(mainScrollPane, mainPanel, layout, c, 0,1,3,1,1,1);
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
    public void addComponent(JComponent component, Container container,
            GridBagLayout layout, GridBagConstraints gbc,
            int gridx, int gridy,
            int gridwidth, int gridheight,
            int weightx, int weighty){
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            gbc.gridwidth = gridwidth;
            gbc.gridheight = gridheight;
            gbc.weightx = weightx;
            gbc.weighty = weighty;
            container.add(component,gbc);
    }
    ///Hide this frame
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
           if (!query.isBlank() && !query.isEmpty()) {
               if (WebValidationUtil.isYoutubeVideo(query)) {
                   String videoId = WebValidationUtil.extractVideoID(query);
                   ///Make video information request from youtube
                   var video = ytModel.getVideoInfo(videoId);
                   if (video == null) {
                       JOptionPane.showMessageDialog(MainWindow.this, "Could not find the video", "ERROR", JOptionPane.ERROR_MESSAGE);
                       return;
                   } else {
                       ///Proceed to select the format
                       formatChooser.setLocationRelativeTo(MainWindow.this);
                       var selectedFormat = formatChooser.updateAndShow(video);
                       if(selectedFormat != null){
                           ///Proceed to download
                           var download = new Download(selectedFormat, video);
                           dmPanel.actionAdd(download);
                       }}
               } 
               else { ////Perform a youtube search
                 var videoList = ytModel.getSearchResult(query);
                 if(videoList != null){
                 for(Video v : videoList){
                     System.out.println(v.id);
                     System.out.println(v.title);
                     System.out.println(v.thumbnailLink);
                     System.out.println(v.channel);
                     System.out.println(v.releaseDate);
                     System.out.println(v.viewCount);
                     System.out.println(v.commentCount);
                     System.out.println(v.duration);
                     System.out.println();
                 }
                }
                 else System.out.print("No videos found");
               }
           }
           queryField.setText("");
       });
       ///menuBtn actionlisteners
       menuBtn.addActionListener((ActionEvent e) -> {
           dmPanel.updateAndShow();
       });
       addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                  ///Save the program state and check whether some download are still going
                dmPanel.saveInstanceState();
            }
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