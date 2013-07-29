/*
 *  Program:    MaklerPoint System
 *  Module:     Main
 *  Language:   Java / Swing
 *  Date:       2010/09/03 13:10
 *  Web:        http://www.maklerpoint.de
 *  Version:    0.6.1
 *
 *  Copyright (C) 2010 Yves Hoppe.  All Rights Reserved.
 *  See License.txt or http://www.maklerpoint.de/copyright for details.
 *
 *  This software is distributed WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *  above copyright notices for details.
 */

package de.maklerpoint.office.Scanner;

import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type;
import uk.co.mmscomputing.device.scanner.ScannerListener;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class ScannerGetImage implements ScannerListener {

    Scanner scanner;

    boolean isWorking = false;
    boolean isFinished = false;

    private static Preferences prefs = Preferences.userRoot().node(Config.class.getName());

    private String imagename;

    public ScannerGetImage(String imagename) throws ScannerIOException{
        scanner = Scanner.getDevice();
        this.imagename = imagename;
        if(scanner != null) {
            scanner.addListener(this);
            scanner.acquire();
        }
    }

    public void update(Type type, ScannerIOMetadata metadata) {
        
        if(type.equals(ScannerIOMetadata.ACQUIRED)) {
            BufferedImage image = metadata.getImage();

            String imageFormat = prefs.get("scannerImageformat", "png");

            Log.logger.info("Image im Cache");
            try {
                ImageIO.write(image, imageFormat, new File(imagename));
            } catch (IOException e) {
                Log.logger.fatal("Konnte gescannte Datei nicht speichern", e);
                ShowException.showException("Konnte die gescannte Datei nicht speichern ",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datei nicht scannen");                
            }
        } else if(type.equals(ScannerIOMetadata.NEGOTIATE)) {
            ScannerDevice device=metadata.getDevice();
            try{
      //        device.setShowUserInterface(false);
      //        device.setShowProgressBar(false);
                device.setShowProgressBar(Config.getConfigBoolean("scannerProgressbar", true));
                device.setResolution(Config.getConfigInt("scannerResolution", 150));
            }catch(Exception e){
                Log.logger.fatal("Konnte die Scanner Einstellungen nicht initialisieren", e);
                ShowException.showException("Konnte die Scanner Einstellungen nicht initialisieren.",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Scanner nicht konfigurieren");                
            }
        } else if(type.equals(ScannerIOMetadata.STATECHANGE)){
            System.err.println(metadata.getStateStr());
            if(metadata.isFinished()){
              isFinished = true;
            }
        }else if(type.equals(ScannerIOMetadata.EXCEPTION)){
            metadata.getException().printStackTrace();
        }
    }

}
