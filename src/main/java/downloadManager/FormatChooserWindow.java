/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package downloadManager;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import com.mycompany.belony.nduledownload.main.Video;
import customViews.FormatRadioButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jonathan Idy
 */
public class FormatChooserWindow extends JDialog implements ActionListener{
    ///Icons
    ImageIcon vidTabIcon;
    ImageIcon audTabIcon;
    private final JButton downloadButton;
    private final JButton cancelButton;
    private FormatRadioButton selectedOption;
    private final JPanel vidContentPanel;
    private final JPanel audContentPanel;
    private final JTabbedPane tabbedPane;
    private final JPanel buttonPanel;
    
    
    public FormatChooserWindow(){
        ///image icons
        vidTabIcon = new ImageIcon("assets/icons/video_icon.png");
        audTabIcon = new ImageIcon("assets/icons/audio_icon.png");
        
        ///Panels to hold all the previous panels respective of type
        vidContentPanel = new JPanel();
        audContentPanel = new JPanel();
        vidContentPanel.setLayout(new BoxLayout(vidContentPanel, BoxLayout.LINE_AXIS));
        audContentPanel.setLayout(new BoxLayout(audContentPanel, BoxLayout.LINE_AXIS));
        
        ///Add the panels to the main window
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Video", vidTabIcon, vidContentPanel, "Video Formats");
        tabbedPane.addTab("Audio", audTabIcon, audContentPanel, "Audio Formats");
        ///Add control buttons for download and for cancel
         downloadButton = new JButton("Download");
         downloadButton.setActionCommand("download");
         downloadButton.addActionListener(this);
         cancelButton = new JButton("Cancel");
         cancelButton.setActionCommand("cancel");
         cancelButton.addActionListener(this);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(downloadButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setBorder(new EmptyBorder(5,0,5,0));
        var gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(tabbedPane, gbc);
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weighty = 0;
        add(buttonPanel, gbc);
        ///window configuration
        setModal(true);
        setResizable(false);
        setUndecorated(true);
        setPreferredSize(new Dimension(400,280));
        getRootPane().setBorder( BorderFactory.createLineBorder(Color.gray.brighter()) );
        ///Position window at the top right corner upon startup
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - getWidth() + 100;
        int y = 100;
        setLocation(x, y);
         pack();
    }
    
    public Format updateAndShow(Video video){
        selectedOption = null;
        downloadButton.setText("Download");
        ///Panels to hold video informations and thumbnail
        var vidInfoPanel = new VideoDetailsView(video);
        var audInfoPanel = new VideoDetailsView(video);
        var vidFormatsPanel = new JPanel();
        var audFormatsPanel = new JPanel();
        var buttonGroup = new ButtonGroup();
        vidFormatsPanel.setLayout(new BoxLayout(vidFormatsPanel, BoxLayout.PAGE_AXIS));
        audFormatsPanel.setLayout(new BoxLayout(audFormatsPanel, BoxLayout.PAGE_AXIS));
        ///Get downloadable formats for the video
        // init downloader with default config
        YoutubeDownloader downloader = new YoutubeDownloader();
        // sync parsing
        RequestVideoInfo request = new RequestVideoInfo(video.id);
        Response<VideoInfo> response = downloader.getVideoInfo(request);
        if(response.ok()){
            VideoInfo videoInfo = response.data();
            // get videos formats only with audio
            List<VideoWithAudioFormat> videoWithAudioFormats = videoInfo.videoWithAudioFormats();
            videoWithAudioFormats.forEach(it -> {
            ///Add option button to group
                var option = new FormatRadioButton(it);
                option.addActionListener(this);
                buttonGroup.add(option);
                vidFormatsPanel.add(option);
            });
            // get audio formats
            List<AudioFormat> audioFormats = videoInfo.audioFormats();
            ///Sort the audioFormats List
            // One by one move boundary of unsorted subarray
        for (int i = 0; i < audioFormats.size() - 1 ; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < audioFormats.size(); j++)
                if (audioFormats.get(j).averageBitrate() < audioFormats.get(min_idx).averageBitrate())
                    min_idx = j;
 
            // Swap the found minimum element with the first
            // element
            var temp = audioFormats.get(min_idx);
            audioFormats.set(min_idx, audioFormats.get(i));
            audioFormats.set(i, temp);
        }
            
            audioFormats.forEach(it -> {
                var option = new FormatRadioButton(it);
                option.addActionListener(this);
                buttonGroup.add(option);
                audFormatsPanel.add(option);
            });
            
        }
        ///Empty the content panel before adding new values
        vidContentPanel.removeAll();
        audContentPanel.removeAll();
        vidContentPanel.add(vidInfoPanel);
        vidContentPanel.add(vidFormatsPanel);
        audContentPanel.add(audInfoPanel);
        audContentPanel.add(audFormatsPanel);
        vidContentPanel.revalidate();
        audContentPanel.revalidate();
        setVisible(true);
        return selectedOption != null ? selectedOption.getSelectedFormat() : null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof FormatRadioButton){
            var radioButton = (FormatRadioButton) e.getSource();
            if(radioButton.isSelected()){
                var size = "Download(" + radioButton.getSelectedSize() + ")";
                downloadButton.setText(size);
                selectedOption = radioButton;
            }
        }
        else if(e.getActionCommand().equals("download")){
            ///
            if(selectedOption != null){
                ///Download selected format
                setVisible(false);
                dispose();
            }
        }
        else if(e.getActionCommand().equals("cancel")){
            ///Cancel
            selectedOption = null;
            setVisible(false);
            dispose();
        }
    }
}
