/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.belony.nduledownload.main;

import javax.swing.SwingUtilities;

/**
 *
 * @author Jonathan Idy
 */
public class NduleDownload {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new MainWindow().showWindow();
            }
        });
    }
}
