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
import javax.swing.border.LineBorder;
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
        videoFileIcon = new ImageIcon("assets/icons/video_file64.png");
        audioFileIcon = new ImageIcon("assets/icons/audio_file64.png");
        pauseIcon = new ImageIcon("assets/icons/pause_icon.png");
        resumeIcon = new ImageIcon("assets/icons/resume_icon.png");
        removeIcon = new ImageIcon("assets/icons/remove_icon.png");
        
        titleLabel = new JLabel(d.getTitle());
        ComponentCustomizer.customizeLabel(titleLabel, 2);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD,12));
        titleLabel.setForeground(new Color(50,50,50));
        durationLabel = new JLabel(d.getDuration());
        ComponentCustomizer.customizeLabel(durationLabel, 2);
        sizeLabel = new JLabel(TextFormatUtil.formatSizeBytes(d.getSize()));
        ComponentCustomizer.customizeLabel(sizeLabel, 2);
        locationLabel = new JLabel(d.getPath());
        ComponentCustomizer.customizeLabel(locationLabel, 2);
        var date = d.getTimeStamp().toString();
        downDateLabel = new JLabel(TextFormatUtil.formatPastTime(date, "EEE MMM dd HH:mm:ss zzzz yyyy"));
        ComponentCustomizer.customizeLabel(downDateLabel, 2);
        if(d.isRessourceVideo()) thumbLabel = new JLabel(videoFileIcon);
        else thumbLabel = new JLabel(audioFileIcon);
        ///Buttons
        removeButton = new RoundJButton(removeIcon);
        pauseButton = new RoundJButton(pauseIcon);
        ///progressBar
        jpb = new JProgressBar();
        jpb.setStringPainted(false); ///No percentage show
        jpb.setValue(d.getProgress());
        
        ///setMaximumSize(new Dimension(240, 100));
        ///setPreferredSize(new Dimension(240, 100));
        ///Inserting items to the panel
        ///progress bar as background
        var layout = new GridBagLayout();
        setLayout(layout);
        var gbc = new GridBagConstraints();
        ///thumnbail label
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(thumbLabel, this, layout, gbc, 0, 0, 1, 5, 0, 1);
        ///Title bar
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(titleLabel, this, layout, gbc, 1, 0, 1, 1, 1, 0);
        ///remove button
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(removeButton, this, layout, gbc, 2, 0, 1, 1, 0, 0);
        ///Duration label
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(durationLabel, this, layout, gbc, 1,1,1,1, 1, 0);
        ///Size label
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(sizeLabel, this, layout, gbc, 1, 2, 1, 1, 1, 0);
        ///pause button
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(pauseButton, this, layout, gbc, 2, 2, 1, 1, 0, 0);
        ///download date label
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(downDateLabel, this, layout, gbc, 1,3, 1, 1, 1, 0);
        ///location label
        gbc.anchor = GridBagConstraints.WEST;
        addComponent(locationLabel, this, layout, gbc, 1, 4, 1, 1, 1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(jpb, this, layout, gbc, 0, 5, 3, 1, 1, 0);
        setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
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
            container.add(component,gbc);
    }
    @Override
    public void update(Observable o, Object arg) {
        jpb.setValue(d.getProgress());
    }
    
}
