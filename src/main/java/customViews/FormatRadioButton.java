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
                builder.append(formatBitrate(sampleRate));
            }
            var sizeBytes = videoFormat.contentLength();
            if(sizeBytes == null) sizeBytes = 0L;
            size = formatSizeBytes(sizeBytes);
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
        return String.format("%.1f %cB", value / 1024.0, ci.current());
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
        return String.format("%.1f %cbps", value / 1024.0, ci.current());
    }
}
