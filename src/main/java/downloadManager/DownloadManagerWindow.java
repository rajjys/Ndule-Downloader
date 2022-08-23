/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package downloadManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Jonathan Idy
 */
public class DownloadManagerWindow extends JDialog implements Observer{

    DownloadTableModel dtModel; ///to be Serialized
    private JPanel downloadPanel;
    JScrollPane mainScrollPane;
    JPanel contentPanel;
    public DownloadManagerWindow(){
       dtModel = new DownloadTableModel();
       downloadPanel = new JPanel();
       downloadPanel.setLayout(new BoxLayout(downloadPanel, BoxLayout.PAGE_AXIS));
       mainScrollPane = new JScrollPane();
       mainScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
       mainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
       contentPanel = new JPanel();
       contentPanel.setOpaque(true);
       contentPanel.setBackground(new Color(40,40,40));
       var gbc = new GridBagConstraints();
        contentPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        contentPanel.add(mainScrollPane, gbc);
        add(contentPanel);
       setModal(true);
       setResizable(false);
       setPreferredSize(new Dimension(500,380));
       setTitle("Downloads");
        
        pack();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - getWidth();
        int y = 100;
        setLocation(x, y);
    }
    public void updateContent(){
        int size = dtModel.getRowCount();
        downloadPanel.removeAll();
        for(int i  = size - 1; i >= 0; i--){
            ///Create downloadItemView for each download and add it to the panel
            DownloadItemView downloadItem = null;
            try {
                downloadItem = new DownloadItemView(dtModel.getDownload(i));
            } catch (ParseException ex) {
                Logger.getLogger(DownloadManagerWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            downloadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            downloadPanel.add(downloadItem);
            System.out.println("video " + i + ": " + downloadItem.titleLabel.getText());
        }
        mainScrollPane.setViewportView(downloadPanel);
        mainScrollPane.getVerticalScrollBar().setValue(0);
        mainScrollPane.revalidate();
    }
    public void actionAdd(Download d){
        dtModel.addDownload(d);
    }
    public void updateAndShow(){
        updateContent();
        setVisible(true);
    }
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
