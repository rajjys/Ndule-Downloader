/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package themes;

import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ocpsoft.prettytime.PrettyTime;

/**
 *
 * @author Jonathan Idy
 */
public class TextFormatUtil {
    
    ///DUration is given as PT01M34S56 and returned as 01:34:56
    public static String formatDuration(String duration){
        try{
            String result = duration.replace("PT","").replace("H",":").replace("M",":").replace("S","");
            String arr[]=result.split(":");
            StringBuilder formattedTime;
            if(arr.length == 1)
                return String.format("%02d:%02d",0,
                    Integer.parseInt(arr[0]));
            if(arr.length == 2)
                return String.format("%02d:%02d", 
                    Integer.parseInt(arr[0]), 
                    Integer.parseInt(arr[1]));
            return String.format("%d:%02d:%02d", Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),Integer.parseInt(arr[2]));
            }
        catch(Exception e){
            return "00:00";
        }
        }
    ///Date is given as a "2011-08-12T20:17:46.384Z" format and returned as "11 years Ago"
    public static String formatPastTime(String timeStamp, String dateFormat){
        ///youtube date format: yyyy-MM-dd'T'HH:mm:ss'Z'
        ///windows date format: 
        DateFormat format = new SimpleDateFormat(dateFormat);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = format.parse(timeStamp);
        } catch (ParseException ex) {
            Logger.getLogger(TextFormatUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        var p = new PrettyTime(); ///Maven formatter library
        return p.format(date);
    }
    ///from 1000 to 1K, from 99000787 to 99M
    public static String formatNumberPrefix(long number){
        long absB = number == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(number);
        if (absB < 1000) {
            return number + "";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMBT");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(number);
        return String.format("%.1f%c", value / 1000.0, ci.current());
    }
    
    public static String formatSizeBytes(Long sizeBytes){
        if(sizeBytes == null) sizeBytes = 0L;
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
    public static String formatBitrate(int sampleRate){
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
