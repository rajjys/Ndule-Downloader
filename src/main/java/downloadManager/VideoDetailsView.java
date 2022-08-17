/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package downloadManager;

import com.mycompany.belony.nduledownload.main.Video;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jonathan Idy
 */
public class VideoDetailsView extends JPanel{

    private ImageIcon vidThumbnailIcon;
    private Video video;
    
    public VideoDetailsView(Video video){
        this.video = video;
        try{
                    var imageLink = new URL(video.thumbnailLink);
                    vidThumbnailIcon = new ImageIcon(ImageIO.read(imageLink));
                } catch (MalformedURLException ex) {
                    vidThumbnailIcon = new ImageIcon("assets/icons/default_thumbnail.png");
                } catch (Exception ex) {
                    
                    vidThumbnailIcon = new ImageIcon("assets/icons/default_thumbnail.png");
                }
        initGUIComponents();
    }

    private void initGUIComponents() {
        setMinimumSize(new Dimension(184, 138));
        var vidTitleLabel = new JLabel(video.title);
        customizeLabel(vidTitleLabel);
        var vidViewsLabel = new JLabel(video.viewCount);
        customizeLabel(vidViewsLabel);
        var vidChannelLabel = new JLabel(video.channel);
        customizeLabel(vidChannelLabel);
        var vidDateLabel = new JLabel(video.releaseDate);
        customizeLabel(vidDateLabel);
        var vidDurationLabel = new JLabel(video.duration);
        customizeLabel(vidDurationLabel);
        var vidCommentsLabel = new JLabel(video.commentCount);
        customizeLabel(vidCommentsLabel);
        
        var layout = new GridBagLayout();    
        var gbc = new GridBagConstraints();
        setLayout(layout);
   
        ///Add view count
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(vidViewsLabel, this, layout,gbc, 0,0,1,1);
        ///Add comment count
        addComponent(vidCommentsLabel, this, layout, gbc, 0,1,1,1);
        ///Add channel
        addComponent(vidChannelLabel, this, layout,gbc, 0,2,1,1);
        ///Add released time
        addComponent(vidDateLabel, this, layout,gbc, 0,3,1,1);
        ///Add Duration
        addComponent(vidDurationLabel, this, layout,gbc, 0,4,1,1);
        ///Add title label
        gbc.weightx = 1;
        addComponent(vidTitleLabel, this, layout,gbc, 0,6,2,2);
        
    }

    private void customizeLabel(JLabel label) {
      var courrier = new Font("Roboto", Font.BOLD,12);
        label.setFont(courrier);
        label.setForeground(Color.white);
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(2,4,2,4));
        label.setBackground(new Color(0,0,0,156));
        ///label.setBorder(new RoundedBorder(Color.black, 2)); 
    }
    public ImageIcon getThumbnail(){
        return vidThumbnailIcon;
    }

   public void addComponent(JComponent component, Container container,
            GridBagLayout layout, GridBagConstraints gbc,
            int gridx, int gridy,
            int gridwidth, int gridheight ){
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            gbc.gridwidth = gridwidth;
            gbc.gridheight = gridheight;
            container.add(component,gbc);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(vidThumbnailIcon.getImage(), 0, 0, this.getWidth(),this.getHeight(), null);
    }
}
