/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package downloadManager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Jonathan Idy
 */
public class DownloadTableModel implements Observer{
    //These are the names for the tables's columns.
	private static final String[] columnNames = {"title","Size","Progress","Status"};
        //The tables's list of download.
	private ArrayList<Download> downloadList = new ArrayList<Download>();
        //Add new download to the table.
	public void addDownload(Download download){
            //Register to be notified when the download changes.
            download.addObserver(this);
            downloadList.add(download);
            ///Insert the download to the table
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

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
