/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package downloadManager;

import customViews.RoundJButton;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import themes.ComponentCustomizer;
import themes.TextFormatUtil;

/**
 *
 * @author Jonathan Idy
 */
public class DownloadItemView extends JPanel implements Observer{
    
    JLabel thumbLabel;
    JLabel titleLabel;
    JLabel durationLabel;
    JLabel sizeLabel;
    JLabel locationLabel;
    JLabel downDateLabel;
    JButton removeButton;
    JButton pauseButton;
    JProgressBar jpb;
    Download d;
    ImageIcon videoFileIcon, audioFileIcon;
    ImageIcon pauseIcon, resumeIcon, removeIcon;
    public DownloadItemView(Download d) throws ParseException{
        this.d = d;
        d.addObserver(this);
        videoFileIcon = new ImageIcon("assets/icons/video_file32.png");
        audioFileIcon = new ImageIcon("assets/icons/audio_file32.png");
        pauseIcon = new ImageIcon("assets/icons/pause_icon.png");
        resumeIcon = new ImageIcon("assets/icons/resume_icon.png");
        removeIcon = new ImageIcon("assets/icons/remove_icon.png");
        
        var title = d.getTitle();
        if(title.length() > 50) title = title.substring(0, 50) + "...";
        titleLabel = new JLabel(title);
        ComponentCustomizer.customizeLabel(titleLabel, 2);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD,12));
        titleLabel.setForeground(new Color(50,50,50));
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));
        durationLabel = new JLabel(d.getDuration());
        ComponentCustomizer.customizeLabel(durationLabel, 2);
        sizeLabel = new JLabel(TextFormatUtil.formatSizeBytes(d.getSize()));
        ComponentCustomizer.customizeLabel(sizeLabel, 2);
        locationLabel = new JLabel(d.getPath());
        ComponentCustomizer.customizeLabel(locationLabel, 2);
        var date = d.getTimeStamp().toString();
        downDateLabel = new JLabel(TextFormatUtil.formatPastTime(date, "EEE MMM dd HH:mm:ss zzzz yyyy"));
        ComponentCustomizer.customizeLabel(downDateLabel, 2);
        ////Pick icon according to type
        if(d.isRessourceVideo()) thumbLabel = new JLabel(videoFileIcon);
        else thumbLabel = new JLabel(audioFileIcon);
        thumbLabel.setPreferredSize(new Dimension(64,64));
        thumbLabel.setMaximumSize(new Dimension(64,64));
        ///Buttons
        removeButton = new RoundJButton(removeIcon);
        pauseButton = new RoundJButton(pauseIcon);
        ///progressBar
        jpb = new JProgressBar();
        jpb.setStringPainted(false); ///No percentage show
        jpb.setValue(d.getProgress());
        ///Inserting items to the panel
        ///progress bar as background
        var layout = new GridBagLayout();
        setLayout(layout);
        var gbc = new GridBagConstraints();
        ///thumnbail label
        gbc.fill = GridBagConstraints.HORIZONTAL; //The object will extend only horizontally.
        addComponent(thumbLabel, this, layout, gbc, 0, 0, 1, 4, 0.2f, 1);
        ///Title bar
        gbc.fill = GridBagConstraints.BOTH; //The object will extend only horizontally.
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(titleLabel, this, layout, gbc, 1, 0, 2, 1, 0.8f, 0);
        ///remove button
        gbc.anchor = GridBagConstraints.CENTER;
        ///gbc.fill = GridBagConstraints.NONE; //The object will not extend 
        addComponent(removeButton, this, layout, gbc, 3, 0, 1, 1, 0, 0);
        ///Duration label
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(durationLabel, this, layout, gbc, 1,1,1,1, 0, 0);
        ///Size label
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(sizeLabel, this, layout, gbc, 2, 1, 1, 1, 0, 0);
       
        ///download date label
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(downDateLabel, this, layout, gbc, 1,2, 1, 1, 0, 0);
         ///pause button
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(pauseButton, this, layout, gbc, 3, 2, 1, 1, 0, 0);
        ///location label
        ///gbc.anchor = GridBagConstraints.WEST;
        ///addComponent(locationLabel, this, layout, gbc, 1, 4, 1, 1, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(jpb, this, layout, gbc, 0, 3, 4, 1, 1, 0);
        
        setBorder(BorderFactory.createLineBorder(new Color(160,128,128)));
        setOpaque(true);
        setBackground(new Color(235,230,230));
        setMinimumSize(new Dimension(460, 80));
        setPreferredSize(new Dimension(460, 80));
        setMaximumSize(new Dimension(460, 80));
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
    @Override
    public void update(Observable o, Object arg) {
        jpb.setValue(d.getProgress());
    }
    
}
