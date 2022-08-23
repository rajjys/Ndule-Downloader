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
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import themes.TextFormatUtil;

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
                ///return the raw json reponse
                System.out.println("Request complete");
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
                var date = (String)snippet.get("publishedAt");
                v.releaseDate = TextFormatUtil.formatPastTime(date, "yyyy-MM-dd'T'HH:mm:ss'Z'");
                v.title = (String)snippet.get("title");
                v.description = (String)snippet.get("description");
                v.channel = (String)snippet.get("channelTitle");
                var thumbnails = (JSONObject)snippet.get("thumbnails");
                var medium = (JSONObject)thumbnails.get("high");
                v.thumbnailLink = (String) medium.get("url");
                ///Read content from the contentDetails braces
                var details = (JSONObject)item.get("contentDetails");
                
                v.duration = TextFormatUtil.formatDuration((String)details.get("duration"));
                ///Read content from the statistics braces
                var stats = (JSONObject)item.get("statistics");
                var viewsCount = Long.valueOf((String)stats.get("viewCount"));
                var commentCount = Long.valueOf((String)stats.get("commentCount"));
                v.viewCount = TextFormatUtil.formatNumberPrefix(viewsCount);
                v.commentCount = TextFormatUtil.formatNumberPrefix(commentCount);
            }
            else return null;
        } catch (ParseException ex) {
            return null;
        } catch (java.text.ParseException ex) {
            Logger.getLogger(YoutubeRequestModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    public void getSearchResult(String query){
        
    }
}
