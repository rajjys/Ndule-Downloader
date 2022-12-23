/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package youtubeQuery;

///import com.alibaba.fastjson.JSONObject;
import com.mycompany.belony.nduledownload.main.Video;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
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
    
    private final String videoInformationRequest = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&key=" + APIKey.API_Key;
    private final String ytSearchRequest = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&key=" + APIKey.API_Key; ///&q=windows%2011
    private final String contentDetailsRequest = "https://youtube.googleapis.com/youtube/v3/videos?part=contentDetails%2Cstatistics&key=" + APIKey.API_Key;
    private ArrayList<Video> videoList = new ArrayList<Video>();
    
    public Video getVideoInfo(String videoId) {
       var builder = new StringBuilder(videoInformationRequest);
       builder.append("&id=" + videoId);
       var query = builder.toString();
       ///Make an http request and get the json response
       var response = makeRequest(query);
       ///parse the json response
       if(response != null)
       return parseVideoInfo(response);  
       return null;
    }
    public ArrayList<Video> getSearchResult(String searchQuery){
        ///Empty previous content
        videoList.clear();
        String encodedQuery;
        try {
            encodedQuery = URLEncoder.encode(searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            encodedQuery = searchQuery;
        }
        var builder = new StringBuilder(ytSearchRequest);
        builder.append("&q=" + encodedQuery);
        var query = builder.toString();
        ///Make an http request and get the json response
        var response = makeRequest(query);
         if(response != null){
              ///Save result to file
                try (PrintWriter out = new PrintWriter("response.txt")) {
                    ///out.println("");
                    out.append(response.toString());
                } catch (FileNotFoundException ex) {
                Logger.getLogger(YoutubeRequestModel.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            var videoList = parseSearchResult(response);
            ///Get content details(duration) and statistics(Views and)
            ///get the videoId of all videos and concatenate to make one request only
            builder = new StringBuilder(contentDetailsRequest);
            builder.append("&id=");
            for(var v : videoList){
                builder.append(v.id);
                builder.append(",");
            }
          var detailsResponse = makeRequest(builder.toString());
          parseUpdateSearchResult(detailsResponse);
          return videoList;
         }
       return null;
    }
    private String makeRequest(String query){
        try {
            var obj = new URL(query);
            var connection = (HttpURLConnection) obj.openConnection();
            var responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                Charset utfCharset = Charset.forName("UTF8");
                var reader = new BufferedReader(
                             new InputStreamReader(
                                connection.getInputStream(), utfCharset));
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
                var highRes = (JSONObject)thumbnails.get("high");
                v.thumbnailLink = (String) highRes.get("url");
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
        }
        return v;
    }
    private ArrayList<Video> parseSearchResult(String response){
        
        videoList = new ArrayList<Video>();
        var parser = new JSONParser();
        try{
           var object = (JSONObject)parser.parse(response); 
           JSONArray itemArray = (JSONArray)object.get("items");
           Iterator<JSONObject> it = itemArray.iterator();
           while(it.hasNext()){
                var v = new Video();
                var item = it.next();
                var idObject = (JSONObject) item.get("id");
                v.id = (String) idObject.get("videoId");
                var snippetObject = (JSONObject) item.get("snippet");
                var date = (String)snippetObject.get("publishedAt");
                v.releaseDate = TextFormatUtil.formatPastTime(date, "yyyy-MM-dd'T'HH:mm:ss'Z'");
                v.title = (String)snippetObject.get("title");
                System.out.println((String)snippetObject.get("title"));
                v.description = (String)snippetObject.get("description");
                v.channel = (String)snippetObject.get("channelTitle");
                var thumbnails = (JSONObject)snippetObject.get("thumbnails");
                var medium = (JSONObject)thumbnails.get("medium");
                v.thumbnailLink = (String) medium.get("url");
                videoList.add(v);
            }
           return videoList;
        } catch (ParseException ex) {
            Logger.getLogger(YoutubeRequestModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
////To update the search result items by adding missing informations
    private void parseUpdateSearchResult(String detailsResponse) {
        var parser = new JSONParser();
        try{
            var object = (JSONObject)parser.parse(detailsResponse);
            JSONArray itemArray = (JSONArray)object.get("items");
            Iterator<JSONObject> it = itemArray.iterator();
            int i = 0;
            while(it.hasNext()){
                var item = it.next();
                var contentDetails = (JSONObject) item.get("contentDetails");
                var duration = (String)contentDetails.get("duration");
                duration = TextFormatUtil.formatDuration(duration);
                
                var statistics = (JSONObject) item.get("statistics");
                var viewCount = (String)statistics.get("viewCount");
                if(viewCount == null) viewCount = "0";
                var commentCount = (String)statistics.get("commentCount");
                if(commentCount == null) commentCount = "0"; ///Happens for videos where comments are turned off
                
                var v = videoList.get(i);
                v.duration = duration;
                v.viewCount = TextFormatUtil.formatNumberPrefix(Long.parseLong(viewCount));
                v.commentCount = TextFormatUtil.formatNumberPrefix(Long.parseLong(commentCount));
                videoList.set(i, v);
                i++;
            }
        } catch (ParseException ex) {
            Logger.getLogger(YoutubeRequestModel.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
}