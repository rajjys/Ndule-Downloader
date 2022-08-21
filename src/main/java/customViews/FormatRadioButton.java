/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customViews;

import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import javax.swing.BorderFactory;
import javax.swing.JRadioButton;
import themes.TextFormatUtil;

/**
 *
 * @author Jonathan Idy
 */
public class FormatRadioButton extends JRadioButton {
        Format videoFormat;
        String size;
    public FormatRadioButton(Format videoFormat){
            this.videoFormat = videoFormat;
             var builder = new StringBuilder();
            if(videoFormat instanceof VideoFormat)
                builder.append(((VideoFormat) videoFormat).height() + "p ");   
            else{ ///case of Audio format
                var sampleRate = ((AudioFormat)videoFormat).averageBitrate();
                builder.append(TextFormatUtil.formatBitrate(sampleRate));
            }
            size = TextFormatUtil.formatSizeBytes(videoFormat.contentLength());
            builder.append("(" + size + ")");
            setText(builder.toString());
            setMargin(new Insets(2,4,2,4));
            setBorder(BorderFactory.createLineBorder(Color.red.brighter()));
    }
    public Format getSelectedFormat(){
        return videoFormat;
    }
    public String getSelectedSize(){
        return size;
    }
}
