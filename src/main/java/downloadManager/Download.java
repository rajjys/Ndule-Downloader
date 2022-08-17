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
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Observable;

/**
 *
 * @author Jonathan Idy
 */
public class Download extends Observable implements Runnable{

        private static final int MAX_BUFFER_SIZE = 1024;
	public static final String STATUSES[]={"Downloading","Paused","Complete","Canceled","Error"};

	public static final int DOWNLOADING = 0;
	public static final int PAUSED=1;
	public static final int COMPLETE=2;
	public static final int CANCELED=3;
	public static final int ERROR=4;

	private Long size;
	private int downloaded;
	private int status;
        private String home = System.getProperty("user.home");
        private String downloadFolder = home + "\\Downloads\\";
        private final Format videoFormat;
        private final String title;
        private float progression = 0;
        
    public Download(Format videoFormat, String title){
        this.videoFormat = videoFormat;
        this.title = title;
        size = videoFormat.contentLength();
            downloaded=0;
            status=DOWNLOADING;
            download();
	}
        ///getters
    public String getTitle(){
	return title;
	}
    public Long getSize(){
	return size;
	}
    public float getProgress(){
	return progression;
	}
    public int getStatus(){
	return status;
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
    private void download(){
            var thread = new Thread(this);
            thread.start();
	}
    @Override
    public void run() {
        var downloadDirectory = new File(downloadFolder);
        var downloader = new YoutubeDownloader();
        // async downloading with callback
        var request = new RequestVideoFileDownload(videoFormat)
        .callback(new YoutubeProgressCallback<File>() {
            @Override
            public void onDownloading(int progress) {
                ///notify observers on change
                status = DOWNLOADING;
                progression = progress;
                stateChanged();
                System.out.printf("Downloaded %d%%\n", progress);
            }
    
            @Override
            public void onFinished(File videoInfo) {
                ///Move file to download folder
               status = COMPLETE;
               stateChanged();
                System.out.println("Finished file: " + videoInfo);
            }
    
            @Override
            public void onError(Throwable throwable) {
                status = ERROR;
                stateChanged();
                System.out.println("Error: " + throwable.getLocalizedMessage());
            }
        }).async().renameTo(title).saveTo(new File(downloadFolder));
         
        Response<File> response = downloader.downloadVideoFile(request);
        File data = response.data(); // will block current thread
        
    }
    
    ///updater
    private void stateChanged(){
	setChanged();
	notifyObservers();
	}
    
}
