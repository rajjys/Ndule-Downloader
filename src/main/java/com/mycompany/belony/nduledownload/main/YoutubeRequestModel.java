/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.belony.nduledownload.main;

///import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ocpsoft.prettytime.PrettyTime;

/**
 *
 * @author Jonathan Idy
 */
public class YoutubeRequestModel {
    
    private final String videoRequestModel = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&key=" + APIKey.API_Key;
   
    public Video getVideoInfo(String videoId) {
       var builder = new StringBuilder(videoRequestModel);
       builder.append("&id=" + videoId);
       var query = builder.toString();
       ///Make an http request and get the json response
       var response = makeRequest(query);
       ///parse the json response
       if(response != null)
       return parseVideoInfo(response);  
       return null;
    }
    
    private String makeRequest(String query){
        try {
            var obj = new URL(query);
            var connection = (HttpURLConnection) obj.openConnection();
            var responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                
                var reader = new BufferedReader(
                             new InputStreamReader(
                                connection.getInputStream()));
                var inputLine = new String();
                var response = new StringBuffer();
                while((inputLine = reader.readLine()) != null){
                    response.append(inputLine);
                }
                reader.close();
                System.out.println("Request completed");
                ///return the raw json reponse
                return response.toString();
            }
        } catch (IOException ex) {
            
        }
        return null;
    }
    
    private Video parseVideoInfo(String response){
        var v = new Video();
        var parser = new JSONParser();
        try {
            
            var object = (JSONObject)parser.parse(response);
            ///Check whether content is more than 0 to parse
            var pageInfo = (JSONObject)object.get("pageInfo");
            var count = (Long)pageInfo.get("totalResults");
            if(count > 0){
                ///Parse the data  into the video v
                var items = (JSONArray)object.get("items");
                var item = (JSONObject)items.get(0);
                v.id = (String)item.get("id"); ///video ID
                ///Read content from the snippet braces
                var snippet = (JSONObject)item.get("snippet");
                v.releaseDate = pastTime((String)snippet.get("publishedAt"));
                v.title = (String)snippet.get("title");
                v.description = (String)snippet.get("description");
                v.channel = (String)snippet.get("channelTitle");
                var thumbnails = (JSONObject)snippet.get("thumbnails");
                var medium = (JSONObject)thumbnails.get("high");
                v.thumbnailLink = (String) medium.get("url");
                ///Read content from the contentDetails braces
                var details = (JSONObject)item.get("contentDetails");
                
                v.duration = formatDuration((String)details.get("duration"));
                ///Read content from the statistics braces
                var stats = (JSONObject)item.get("statistics");
                var viewsCount = Long.valueOf((String)stats.get("viewCount"));
                var commentCount = Long.valueOf((String)stats.get("commentCount"));
                v.viewCount = formatNumber(viewsCount);
                v.commentCount = formatNumber(commentCount);
            }
            else return null;
        } catch (ParseException ex) {
            return null;
        } catch (java.text.ParseException ex) {
            Logger.getLogger(YoutubeRequestModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    private String pastTime(String timeStamp) throws java.text.ParseException{
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        var date = format.parse(timeStamp);
        var p = new PrettyTime();
        return p.format(date);
    }
    private String formatDuration(String duration){
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
    private String formatNumber(long number){
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
    public void getSearchResult(String query){
        
    }
}
