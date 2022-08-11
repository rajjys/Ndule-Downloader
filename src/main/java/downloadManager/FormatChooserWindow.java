/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package downloadManager;

import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.mycompany.belony.nduledownload.main.Video;
import customViews.FormatRadioButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jonathan Idy
 */
public class FormatChooserWindow extends JPanel {
    ///Information fields
    String vidTitle;
    String vidDuration;
    String vidViews;
    String vidPublishDate;
    String vidChannel;
    String vidID;
    String vidThumbURL;
    String vidComments;
    ///Icons
    ImageIcon vidThumbnailIcon;
    ImageIcon vidTabIcon;
    ImageIcon audTabIcon;
    ///Components panels
    JPanel videoPanel; ////should hold the thumnail image, the title and the video formats
    JPanel audioPanel; ///Shoudl hold then thumbnail image, the title and the audio formats
    ///Informations labels
    JLabel vidTitleLabel; ///to hold the title for the videos tab
    JLabel vidThumbnailLabel;  ///to display the image for the videos tab
    JLabel vidViewsLabel;
    JLabel vidCommentsLabel;
    JLabel vidChannelLabel;
    JLabel vidDurationLabel;
    JLabel vidDateLabel;
    JLabel audTitleLabel; ///to hold the title for the audios tab
    
    JLabel audThumbnailLabel;   ///to display the image for the audios tab
    JLabel audViewsLabel;
    JLabel audChannelLabel;
    JLabel audDateLabel;
    JLabel audDurationLabel;
    JLabel audCommentsLabel;
    
    boolean paintNow = false; ///flag to allow painting of the image only when its done loading
    
    JTabbedPane tabbedPane;   ////container to hold different tabs
    Dimension defaultThumbSize = new Dimension(240,160);
    public FormatChooserWindow(Video video){
        ///setPreferredSize(new Dimension(400, 260))
        
        vidTitle = video.title;
        vidThumbURL = video.thumbnailLink;
        vidDuration = video.duration;
        vidChannel = video.channel;
        vidPublishDate = video.releaseDate;
        vidViews = video.viewCount;
        vidID = video.id;
        vidComments = video.commentCount;
        vidTabIcon = new ImageIcon("assets/icons/video_icon.png");
        audTabIcon = new ImageIcon("assets/icons/audio_icon.png");
        initGUIComponents();
        var imageLoader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    var imageLink = new URL(vidThumbURL);
                    vidThumbnailIcon = new ImageIcon(ImageIO.read(imageLink));
                } catch (MalformedURLException ex) {
                    vidThumbnailIcon = new ImageIcon("assets/icons/default_thumbnail.png");
                } catch (Exception ex) {
                    
                    vidThumbnailIcon = new ImageIcon("assets/icons/default_thumbnail.png");
                }
                finally{
                  ///vidThumbnailLabel.setIcon(vidThumbnailIcon);
                  ///audThumbnailLabel.setIcon(vidThumbnailIcon);
                  paintNow = true;
                }
            }
        });
       imageLoader.start();
    }
    public void initGUIComponents(){
        
        setMinimumSize(new Dimension(300, 380));
        tabbedPane = new JTabbedPane();        
        videoPanel = new JPanel();
        audioPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        
        vidTitleLabel = new JLabel(vidTitle);
        vidThumbnailLabel = new JLabel();
        vidViewsLabel = new JLabel(vidViews);
        vidChannelLabel = new JLabel(vidChannel);
        vidDateLabel = new JLabel(vidPublishDate);
        vidDurationLabel = new JLabel(vidDuration);
        vidCommentsLabel = new JLabel(vidComments);
        audTitleLabel = new JLabel(vidTitle);
        audThumbnailLabel = new JLabel();
        audViewsLabel= new JLabel(vidViews);
        audChannelLabel = new JLabel(vidChannel);
        audDateLabel = new JLabel(vidPublishDate);
        audDurationLabel = new JLabel(vidDuration);
        audCommentsLabel = new JLabel(vidComments);
        

        
        var vidInfoPanel = new JPanel();
        vidInfoPanel.setLayout(new GridBagLayout());
        var gbc = new GridBagConstraints();
        ///Setup the video tab content first
        vidThumbnailLabel.setPreferredSize(new Dimension(250,150));
        ///insert the the thumbnail label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(1,1,1,1);
        gbc.fill = GridBagConstraints.BOTH;
        vidInfoPanel.add(vidThumbnailLabel, gbc);
        ///Add view count
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        vidInfoPanel.add(vidViewsLabel, gbc);
        ///Add comment count
        gbc.gridx = 0;
        gbc.gridy = 1;
        vidInfoPanel.add(vidCommentsLabel, gbc);
        ///Add channel 
        gbc.gridx = 0;
        gbc.gridy = 2;
        vidInfoPanel.add(vidChannelLabel, gbc);
        ///Add released time
        gbc.gridx = 0;
        gbc.gridy = 3;
        vidInfoPanel.add(vidDateLabel, gbc);
        ///Add Duration
        gbc.gridx = 0;
        gbc.gridy = 4;
        vidInfoPanel.add(vidDurationLabel, gbc);
        ///Add title label
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0;
        vidInfoPanel.add(vidTitleLabel, gbc);
        ///Audio Panel
        var audInfoPanel = new JPanel();
        audInfoPanel.setLayout(new GridBagLayout());
        ///Setup the video tab content first
        audThumbnailLabel.setPreferredSize(new Dimension(250,150));
        ///insert the the thumbnail label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        audInfoPanel.add(audThumbnailLabel, gbc);
        ///Add view count
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        audInfoPanel.add(audViewsLabel, gbc);
        ///Add comment count
        gbc.gridx = 0;
        gbc.gridy = 1;
        audInfoPanel.add(audCommentsLabel, gbc);
        ///Add channel 
        gbc.gridx = 0;
        gbc.gridy = 2;
        audInfoPanel.add(audChannelLabel, gbc);
        ///Add released time
        gbc.gridx = 0;
        gbc.gridy = 3;
        audInfoPanel.add(audDateLabel, gbc);
        ///Add Duration
        gbc.gridx = 0;
        gbc.gridy = 4;
        audInfoPanel.add(audDurationLabel, gbc);
        ///Add title label
         ///Add title label
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0;
        audInfoPanel.add(audTitleLabel, gbc);
        ///Add the panels to the main window
        tabbedPane.addTab("Video", vidTabIcon, vidInfoPanel, "Video Formats");
        tabbedPane.addTab("Audio", audTabIcon, audInfoPanel, "Audio Formats");
        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(tabbedPane, gbc);
    }
    public void getDownloadFormat(){
        this.setVisible(true);  
    }
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        while(!paintNow){try {
            Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(FormatChooserWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
}
        g.drawImage(vidThumbnailIcon.getImage(), 0, 0,  this.getWidth(), this.getHeight(), null);
    }
}
