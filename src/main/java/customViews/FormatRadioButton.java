/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customViews;

import com.github.kiulian.downloader.model.videos.VideoInfo;
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
import javax.swing.border.LineBorder;

/**
 *
 * @author Jonathan Idy
 */
public class FormatRadioButton extends JRadioButton {
    
    public FormatRadioButton(Format videoFormat){
             var builder = new StringBuilder();
            if(videoFormat instanceof VideoFormat)
                builder.append(((VideoFormat) videoFormat).height() + "p");   
            else{ ///case of Audio format
                var sampleRate = ((AudioFormat)videoFormat).audioSampleRate();
                builder.append(formatBitrate(sampleRate));
            }
            var sizeBytes = videoFormat.contentLength();
            if(sizeBytes == null) sizeBytes = 0L;
            var size = formatSizeBytes(sizeBytes);
            builder.append("(" + size + ")");
            setText(size);
            setPreferredSize(new Dimension(getPreferredSize().width, 25));
            setMargin(new Insets(2,4,2,4));
            setBorder(BorderFactory.createLineBorder(Color.red.brighter()));     
    }
    private String formatSizeBytes(long sizeBytes){
        long absB = sizeBytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(sizeBytes);
        if (absB < 1024) {
            return sizeBytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(sizeBytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }
    private String formatBitrate(int sampleRate){
        long absB = sampleRate == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(sampleRate);
        if (absB < 1024) {
            return sampleRate + " bps";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("kmgtpe");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(sampleRate);
        return String.format("%.1f %cibps", value / 1024.0, ci.current());
    }
}
