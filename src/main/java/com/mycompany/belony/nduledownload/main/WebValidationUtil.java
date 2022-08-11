/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.belony.nduledownload.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Jonathan Idy
 */
public class WebValidationUtil {
    public static boolean isValidWebsite(String url){
        return true;
    }
    
    public static boolean isYoutubeVideo(String url){
         String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
         if (!url.isEmpty() && url.matches(pattern)) 
             return true;
         return false;
    }
    public static boolean isYoutubePlaylist(){
        return false;
    }
    public static String extractVideoID(String url){
         String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
       Pattern compiledPattern = Pattern.compile(pattern);
       //url is youtube url for which you want to extract the id.
       Matcher matcher = compiledPattern.matcher(url);
       if (matcher.find()) {
           return matcher.group();
       }
       return null;
    }
    
    public static boolean isInternetAvailable(){
        return false;
    }
    
}
