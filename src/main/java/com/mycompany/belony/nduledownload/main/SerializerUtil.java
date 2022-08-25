/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.belony.nduledownload.main;

import downloadManager.Download;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Jonathan Idy
 */
///This is a utility class to help save last instance of some object of the program for later load
///It consist of 2 static methods for serialize and deserialize the  downloadTbaleModel object
public class SerializerUtil {
    
    public static void serializeDownloadList(ArrayList<Download> downloadList){
         try {
             var fileOut = new File("tmp/downloads.ser");
             fileOut.getParentFile().mkdirs();
             fileOut.createNewFile();
             var fileOutStream = new FileOutputStream(fileOut);
             var out = new ObjectOutputStream(fileOutStream);
             out.writeObject(downloadList);
             out.close();
             fileOutStream.close();
             System.out.printf("Serialized data is saved in /tmp/downloads.ser");
      } catch (IOException i) {
         i.printStackTrace();
      }
    }
    
    public static ArrayList<Download> deserializeDownloadList(){
         try {
            var fileIn = new FileInputStream("tmp/downloads.ser");
            var in = new ObjectInputStream(fileIn);
            var downloadList = (ArrayList<Download>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(downloadList.size() + " Downloads found");
           return downloadList;
      } catch (IOException i) {
         ///i.printStackTrace();
         return null;
      } catch (ClassNotFoundException c) {
         System.out.println("Download list not found");
         ///c.printStackTrace();
         return null;
      }
    }
}
