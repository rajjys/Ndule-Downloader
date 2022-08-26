/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.belony.nduledownload.main;

import downloadManager.Download;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import customViews.CustomComponent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 *
 * @author Jonathan Idy
 */
public class VideoItemView extends JPanel implements Runnable{
    
    JLabel thumbnailLabel;
    JLabel titleLabel;
    JLabel durationLabel;
    JLabel downDateLabel;
    JLabel viewCountLabel;
    JLabel commentCountLabel;
    JLabel channelLabel;
    JButton downloadButton;
    String thumbnailURL;
    private ImageIcon vidThumbnailIcon;
    Video video;
    ///ImageIcon videoFileIcon, audioFileIcon;
    ///ImageIcon pauseIcon, resumeIcon, removeIcon;
    public VideoItemView(Video v){
        video = v;
        thumbnailURL = v.thumbnailLink;
        thumbnailLabel = new JLabel();
        thumbnailLabel.setMinimumSize(new Dimension(130, 70));
        thumbnailLabel.setPreferredSize(new Dimension(150,80));
        thumbnailLabel.setMaximumSize(new Dimension(150,80));
        vidThumbnailIcon = new ImageIcon();
        var title = v.title;
        if(title.length() > 50) title = title.substring(0, 47) + "...";
        titleLabel = new JLabel(title);
        System.out.println(title);
        CustomComponent.customizeLabel(titleLabel, 3);
        durationLabel = new JLabel(v.duration);
        CustomComponent.customizeLabel(durationLabel, 1);
        downDateLabel = new JLabel(v.releaseDate);
        CustomComponent.customizeLabel(downDateLabel, 2);
        viewCountLabel = new JLabel(v.viewCount + " Views");
        CustomComponent.customizeLabel(viewCountLabel, 2);
        commentCountLabel = new JLabel(v.commentCount + " Comments");
        CustomComponent.customizeLabel(commentCountLabel, 2);
        var channel = v.channel;
        if(channel.length() > 20) channel = channel.substring(0, 17) + "...";
        channelLabel = new JLabel(v.channel);
        CustomComponent.customizeLabel(channelLabel, 2);
        ///add components
        
        var layout = new GridBagLayout();
        setLayout(layout);
        var gbc = new GridBagConstraints();
        addComponent(thumbnailLabel, this, layout, gbc,0,0,2,3,0,1);
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(titleLabel, this, layout, gbc, 2, 0, 2, 1, 1, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(downDateLabel, this, layout, gbc, 2, 1, 1, 1, 0, 0);
        addComponent(channelLabel,this, layout, gbc, 3, 1, 1, 1, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        addComponent(durationLabel, this, layout, gbc, 1, 2, 1, 1, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(viewCountLabel, this, layout, gbc, 2, 2, 1, 1, 0, 0);
        addComponent(commentCountLabel, this, layout, gbc, 3, 2, 1, 1, 0, 0);
        setBorder(BorderFactory.createLineBorder(new Color(160,128,128)));
        setOpaque(true);
        setBackground(new Color(235,230,230));
        ///load the thumbnail
        loadThumnail();
    }

    
    @Override
    public void run() {
        try{
                    var imageURL = new URL(thumbnailURL);
                    vidThumbnailIcon = new ImageIcon(ImageIO.read(imageURL));  
                } catch (MalformedURLException ex) {
                    vidThumbnailIcon = new ImageIcon("assets/icons/default_thumbnail.png");
                } catch (Exception ex) {
                    
                    vidThumbnailIcon = new ImageIcon("assets/icons/default_thumbnail.png");
                }
        repaint();
    }
    public void addComponent(JComponent component, Container container,
            GridBagLayout layout, GridBagConstraints gbc,
            int gridx, int gridy,
            int gridwidth, int gridheight,
            float weightx, float weighty){
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            gbc.gridwidth = gridwidth;
            gbc.gridheight = gridheight;
            gbc.weightx = weightx;
            gbc.weighty = weighty;
            gbc.insets = new Insets(1,1,1,1);
            container.add(component,gbc);
    }

    private void loadThumnail() {
        var t = new Thread(this);
        t.start();
       }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
         Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(vidThumbnailIcon.getImage(), 0, 0, thumbnailLabel.getWidth(),thumbnailLabel.getHeight(), thumbnailLabel);
    }
    public Video getVideo(){
        return video;
    }
}
