/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package downloadManager;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.mycompany.belony.nduledownload.main.Video;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Observable;

/**
 *
 * @author Jonathan Idy
 */
public class Download extends Observable implements Runnable , Serializable{

	public static final String STATUSES[]={"Downloading","Paused","Complete","Canceled","Error"};

	public static final int DOWNLOADING = 0;
	public static final int PAUSED=1;
	public static final int COMPLETE=2;
	public static final int CANCELED=3;
	public static final int ERROR=4;

	private final Long size;
	private int status;
        private boolean isRessourceVideo;
        private final String home = System.getProperty("user.home");
        private final String downloadFolder = home + "\\Downloads\\";
        private final transient Format videoFormat; ///not serializable
        private int progression = 0;
        private Video video;
        private final Date timeStamp;
        
    public Download(Format videoFormat, Video video){
        this.videoFormat = videoFormat;
        this.video = video;
        timeStamp = new Date();///Time when the download was initiated
        size = videoFormat.contentLength();
        status = DOWNLOADING;
        isRessourceVideo =  videoFormat instanceof VideoFormat ? true:false;
           download();
	}
        ///getters
    public String getTitle(){
	return video.title;
	}
    public Long getSize(){
	return size;
	}
    public int getProgress(){
	return progression;
	}
    public int getStatus(){
	return status;
	}
    public String getDuration(){
        return video.duration;
    }
    public String getPath(){
        return downloadFolder;
    }
    public Date getTimeStamp(){
        return timeStamp;
    }
    public boolean isRessourceVideo(){ ///Whether it's an audio or video
        return isRessourceVideo;
    }
        ///actions
    public void pause(){
            status=PAUSED;
            stateChanged();
	}
    public void resume(){
             status=DOWNLOADING;
            stateChanged();
            download();
	}
    public void cancel(){
            status = CANCELED;
            stateChanged();
	}
    private void error(){
            status=ERROR;
            stateChanged();
	}
    public void download(){
            var thread = new Thread(this);
            thread.start();
	}
    
    @Override
    public void run() {
        var downloader = new YoutubeDownloader();
        // async downloading with callback
        var request = new RequestVideoFileDownload(videoFormat)
        .callback(new YoutubeProgressCallback<File>() {
            @Override
            public void onDownloading(int progress) {
                ///notify observers on change
                status = DOWNLOADING;
                progression = progress;
                ///stateChanged();
                System.out.printf("Downloaded %d%%\n", progress);
            }
    
            @Override
            public void onFinished(File videoInfo) {
                ///Move file to download folder
               status = COMPLETE;
              // stateChanged();
                System.out.println("Finished file: " + videoInfo);
            }
    
            @Override
            public void onError(Throwable throwable) {
                status = ERROR;
                ///stateChanged();
                System.out.println("Error: " + throwable.getLocalizedMessage());
            }
        }).async().renameTo(video.title).saveTo(new File(downloadFolder));
         
        Response<File> response = downloader.downloadVideoFile(request);
        File data = response.data(); // will block current thread 
        System.out.println("Got here too");
    }
    
    ///updater
    private void stateChanged(){
	setChanged();
	notifyObservers();
	}
    
}
