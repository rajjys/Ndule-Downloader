/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package downloadManager;

import Utils.SerializerUtil;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
/**
 *
 * @author Jonathan Idy
 */
public class DownloadTableModel implements PropertyChangeListener{
    //These are the names for the tables's columns.
	private static final String[] columnNames = {"title","Size","Progress","Status"};
        //The tables's list of download.
	private ArrayList<Download> downloadList;
        public DownloadTableModel(){
            ///Get the saved instance of downloadList if any
            downloadList = SerializerUtil.deserializeDownloadList();
            if(downloadList == null) downloadList = new ArrayList<Download>();
        }
        //Add new download to the table.
	public void addDownload(Download download){
            //Register to be notified when the download changes.
            download.addPropertyChangeListener(this);
            downloadList.add(download);
            ///Save the state of the downloadList object 
            ///This should be done everytime a download changes its state and notify us
            saveInstanceState();///Serialize downloadList to local memory
        }
        public Download getDownload(int row){
            return downloadList.get(row);
	}
        public void clearDownload(int row){
            downloadList.remove(row);
            ///notify table change of deletion
	}
        public String getTitle(int row){
            return downloadList.get(row).getTitle();
        }
        public Long getSize(int row){
            return downloadList.get(row).getSize();
        }
        public int getStatus(int row){
            return downloadList.get(row).getStatus();
        }
        public float getProgress(int row){
            return downloadList.get(row).getProgress();
        }
        public int getColumnCount(){
            return columnNames.length;
	}
	public String getColumnName(int col){
            return columnNames[col];
	}
        
	public int getRowCount(){
            return downloadList.size();
	}

        public void saveInstanceState() {
            SerializerUtil.serializeDownloadList(downloadList);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ///System.out.println("downloading: " + evt.getNewValue());
    }
}
