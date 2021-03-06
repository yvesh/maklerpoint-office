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

/*
 * BackupDialog.java
 *
 * Created on Jul 15, 2010, 2:58:16 PM
 */

package de.maklerpoint.office.Gui.Backup;

import de.maklerpoint.office.Backup.BackupDatabase;
import de.maklerpoint.office.Backup.BackupFiletypes;
import de.maklerpoint.office.Backup.BackupInfoFile;
import de.maklerpoint.office.Backup.BackupObj;
import de.maklerpoint.office.Backup.BackupTypes;
import de.maklerpoint.office.Backup.Tools.BackupSQLMethods;
import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Filesystem.FilesystemBackup;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.BenutzerRegistry;
import de.maklerpoint.office.Schnittstellen.ExportImportTypen;
import de.maklerpoint.office.Security.Security;
import de.maklerpoint.office.Security.SecurityTasks;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.Tools.DirectoryTools;
import de.maklerpoint.office.Tools.ZipFiles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import org.jvnet.flamingo.common.CommandButtonDisplayState;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.JCommandMenuButton;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.icon.ImageWrapperResizableIcon;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.common.popup.JCommandPopupMenu;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class BackupDialog extends javax.swing.JDialog {

    private String filename;
    private int filetype;

    private String tmpDatabaseFile;
    private String tmpFolder;

    /** Creates new form BackupDialog */
    public BackupDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        if(!Security.isAllowed(SecurityTasks.BACKUP)) {            
            this.dispose();
            throw new SecurityException("Warnung Benutzer \"" + BasicRegistry.currentUser.getKennung()
                                                                        + "\" hat keine Zugriffsrechte");
        }

        this.filename = FilesystemBackup.getBackupPath() + File.separatorChar +
                                          FilesystemBackup.getBackupFilename();
        this.filetype = Config.getConfigInt("backupType", BackupFiletypes.ZIP);
        initComponents();
    }

    /**
     * 
     * @param backuptype
     */

    public void backup(int backuptype) {
        this.backupButton.setEnabled(false);
        this.restoreButton.setEnabled(false);
        this.setSize(this.getWidth(), this.getHeight() + 120);
        this.label_status.setVisible(true);
        this.label_status.setBusy(true);
        this.progressBar.setVisible(true);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        

        if(filename == null)
            return;

        SimpleDateFormat df = new SimpleDateFormat("MMdd_HHmmss");
        ArrayList<String> fileNames = new ArrayList<String>();

        this.label_status.setText("Starte Sicherung");

        BackupObj backup = new BackupObj();
        backup.setFiletype(filetype);
        backup.setAutomatic(false);
        backup.setBenutzerId(BasicRegistry.currentUser.getId());
        backup.setPath(filename);
        backup.setType(backuptype);
        backup.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));

        tmpFolder = Filesystem.getTmpPath() + File.separatorChar + df.format(new Date(System.currentTimeMillis()));
        File folder = new File(tmpFolder);
        folder.mkdirs();

        // Add swing worker
        if(backuptype == BackupTypes.DATENBANK || backuptype == BackupTypes.KOMPLETT)
        {
            try {
                this.label_status.setText("Sichere Datenbank");
                tmpDatabaseFile = tmpFolder + File.separatorChar + "database.sql";
                BackupDatabase back = new BackupDatabase(tmpDatabaseFile);
                back.backup();
                fileNames.add(tmpDatabaseFile);
            } catch (SQLException e) {
                Log.databaselogger.fatal("Fehler: Konnte Datenbanksicherung nicht durchführen", e);
                ShowException.showException("Die Datenbanksicherung konnte nicht durchgeführt werden",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Datenbanksicherung nicht durchführen");                
                return;
            } catch (IOException e) {
                Log.databaselogger.fatal("Fehler: Konnte Sicherung nicht speichern", e);
                ShowException.showException("Konnte sql Datei nicht schreiben",
                    ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sicherung nicht speichern");                
                return;
            }
        }

        if(backuptype == BackupTypes.KOMPLETT || backuptype == BackupTypes.DATEIEN) {
            this.label_status.setText("Sichere Dateien");
            //fileNames.add();
        }
        
        backup.setSuccess(false);

        try {

            int id = BackupSQLMethods.insertIntoBackup(DatabaseConnection.open(), 
                    backup.getPath(), backup.getType(), backup.getCreated(),
                    backup.isAutomatic(), backup.getBenutzerId(), backup.isSuccess(),
                    backup.isFileAvailable(), backup.getBackupSize(), backup.getFiletype());

            if(id == -1)
                System.out.println("ADD EXCEPTION");

            backup.setId(id);

            BackupInfoFile binfo = new BackupInfoFile(tmpFolder + File.separatorChar + ".backup", backup);
            binfo.write();

            fileNames.add(tmpFolder + File.separatorChar + ".backup");
            
            String[] filePaths = new String[fileNames.toArray().length];
                    
            for(int i = 0; i < fileNames.size(); i++) {
                filePaths[i] = (String) fileNames.get(i);
            }

            boolean success = ZipFiles.zipFiles(filename, filePaths);

            if(!success) {
                System.out.println("ADD EXCEPTION: Fehler beim sichern");
            }

            //Todo: Delete all files
            //File tmpFolderFile = new File(tmpFolder);
            //tmpFolderFile.delete();

            File backFile = new File(filename);
            backup.setBackupSize((int) (backFile.length() / 1000));
            backup.setSuccess(true);

            
            success = BackupSQLMethods.updateBackup(DatabaseConnection.open(), backup);
            if(!success) {
                System.out.println("ADD EXCEPTION: Fehler beim DB update");
            }

            DirectoryTools.deleteDirectory(folder);


        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Sicherung nicht speichern", e);
            ShowException.showException("Die Sicherung konnte nicht in der Datenbank gespeichert werden",
                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sicherung nicht speichern");            
            return;
        } catch (IOException e) {
            Log.databaselogger.fatal("Fehler: Konnte Sicherung nicht speichern", e);
            ShowException.showException("Konnte ZIP Datei nicht schreiben",
                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sicherung nicht speichern");            
            return;
        }        

        this.setCursor(Cursor.getDefaultCursor());
        this.label_status.setText("Sicherung beendet");

        JOptionPane.showMessageDialog(null, "Die Sicherung wurde erfolgreich durchgeführt",
                "Sicherung erfolgreiche beendet", JOptionPane.INFORMATION_MESSAGE);

        this.backupButton.setEnabled(true);
        this.restoreButton.setEnabled(true);
        this.setSize(this.getWidth(), this.getHeight() - 120);
        this.label_status.setVisible(false);
        this.label_status.setBusy(false);
        this.progressBar.setVisible(false);
    }


     private class BackupPopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton csv = new JCommandMenuButton("Komplette Sicherung",
                    new EmptyResizableIcon(new Dimension(16, 16)));
            csv.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    backup(BackupTypes.KOMPLETT);
                }
            });

            popupMenu.addMenuButton(csv);

            JCommandMenuButton xls = new JCommandMenuButton("Datenbank Sicherung",
                    new EmptyResizableIcon(new Dimension(16, 16)));
            xls.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    backup(BackupTypes.DATENBANK);
                }
            });

            popupMenu.addMenuButton(xls);

            JCommandMenuButton xlsx = new JCommandMenuButton("Dateien Sicherung",
                    new EmptyResizableIcon(new Dimension(16, 16)));
            xlsx.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    backup(BackupTypes.DATEIEN);
                }
            });

            popupMenu.addMenuButton(xlsx);    

            return popupMenu;
        }
     }          

     private class RestorePopupCallback implements PopupPanelCallback {

        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            JCommandMenuButton csv = new JCommandMenuButton("Komplette Wiederherstellung",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/csv.png"));
            csv.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });

            popupMenu.addMenuButton(csv);

            JCommandMenuButton xls = new JCommandMenuButton("Datenbank Wiederherstellung",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/excel7.png"));
            xls.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });

            popupMenu.addMenuButton(xls);

            JCommandMenuButton xlsx = new JCommandMenuButton("Daten Wiederherstellung",
                    getResizableIconFromResource("de/acyrance/CRM/Gui/resources/bigicons/excel7.png"));
            xlsx.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });

            popupMenu.addMenuButton(xlsx);

            return popupMenu;
        }
     }

     public static ResizableIcon getResizableIconFromResource(String resource) {
         return ImageWrapperResizableIcon.getIcon(CRM.class.getClassLoader().getResource(resource), new Dimension(128,128));
     }


    public void addCommandButtons() {
        addBackupCommandButton();
        addRestoreCommandButton();
    }

    public void addBackupCommandButton() {
         backupButton = new JCommandButton("Sicherung",
                 getResizableIconFromResource("de/acyrance/CRM/Gui/Backup/resources/backup.png"));
         backupButton.setExtraText("Daten sichern");
         backupButton.setPopupCallback(new BackupPopupCallback());
         backupButton.setPopupOrientationKind(JCommandButton.CommandButtonPopupOrientationKind.DOWNWARD);
         backupButton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
         backupButton.setDisplayState(CommandButtonDisplayState.TILE);
         backupButton.setFlat(false);
         backupButton.setPreferredSize(new Dimension(257, 100));
         panel_buttons.add(backupButton, BorderLayout.WEST);
    }

    public void addRestoreCommandButton() {
         restoreButton = new JCommandButton("Wiederherstellung",
                 getResizableIconFromResource("de/acyrance/CRM/Gui/Backup/resources/restore.png"));
         restoreButton.setExtraText("Daten wiederherstellen");
         restoreButton.setPopupCallback(new BackupPopupCallback());
         restoreButton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
         restoreButton.setPopupOrientationKind(JCommandButton.CommandButtonPopupOrientationKind.DOWNWARD);
         restoreButton.setDisplayState(CommandButtonDisplayState.TILE);
         restoreButton.setFlat(false);
         restoreButton.setPreferredSize(new Dimension(257, 100));
         panel_buttons.add(restoreButton, BorderLayout.EAST);
    }

    /**
     * 
     */

    public void showlatestBackup() {
        try {
            BackupObj latestBack = BackupSQLMethods.loadLatestBackup(DatabaseConnection.open());

            this.label_ziel.setText(filename);
            this.setSize(this.getWidth(), this.getHeight() - 120);

            if(latestBack != null) {
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            this.label_lastbackup.setText(df.format(latestBack.getCreated()));

            if(latestBack.isSuccess()) {
                this.label_lastbackupstatus.setText("Die letzte Sicherung war erfolgreich");
                this.label_lastbackupstatus.setForeground(Color.green);
            } else {
                this.label_lastbackupstatus.setText("Die letzte Sicherung war nicht erfolgreich");
                this.label_lastbackupstatus.setForeground(Color.red);
            }

            if(latestBack.getBenutzerId() == -1)
                this.label_creator.setText("(System)");
            else
                this.label_creator.setText("(" + BenutzerRegistry.getBenutzer(latestBack.getBenutzerId(), false) + ")");

            } else {
                this.label_lastbackup.setText("Keine Backups vorhanden");
                this.label_lastbackupstatus.setText("");
                this.label_creator.setText("");
            }

            if(Config.get("autoBackupNextTime", null) != null) {
                this.label_nextbackup.setText(Config.get("autoBackupNextTime", null));
            } else {
                this.label_nextbackup.setText("Keine automatischen Sicherungen geplant.");
            }
            
            panel_holder.revalidate();
        } catch (SQLException e) {
            Log.databaselogger.fatal("Fehler: Konnte Sicherung nicht aus der Datenbank laden", e);
            ShowException.showException("Die letzte Sicherung konnte nicht aus der Datenbank geladen werden",
                ExceptionDialogGui.LEVEL_WARNING, e, "Schwerwiegend: Konnte Sicherung nicht laden");            
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_holder = new javax.swing.JPanel();
        panel_buttons = new javax.swing.JPanel();
        panel_content = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        label_status = new org.jdesktop.swingx.JXBusyLabel();
        progressBar = new org.openswing.swing.miscellaneous.client.ProgressBar();
        jSeparator2 = new javax.swing.JSeparator();
        label_lastbackup = new javax.swing.JLabel();
        label_lastbackupstatus = new javax.swing.JLabel();
        label_nextbackup = new javax.swing.JLabel();
        label_creator = new javax.swing.JLabel();
        label_ziel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(BackupDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        panel_holder.setBackground(resourceMap.getColor("panel_holder.background")); // NOI18N
        panel_holder.setName("panel_holder"); // NOI18N

        panel_buttons.setBackground(resourceMap.getColor("panel_buttons.background")); // NOI18N
        panel_buttons.setName("panel_buttons"); // NOI18N
        panel_buttons.setLayout(new java.awt.BorderLayout());

        panel_content.setBackground(resourceMap.getColor("panel_content.background")); // NOI18N
        panel_content.setName("panel_content"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        label_status.setText(resourceMap.getString("label_status.text")); // NOI18N
        label_status.setName("label_status"); // NOI18N

        javax.swing.GroupLayout progressBarLayout = new javax.swing.GroupLayout(progressBar);
        progressBar.setLayout(progressBarLayout);
        progressBarLayout.setHorizontalGroup(
            progressBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        progressBarLayout.setVerticalGroup(
            progressBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jSeparator2.setName("jSeparator2"); // NOI18N

        label_lastbackup.setText(resourceMap.getString("label_lastbackup.text")); // NOI18N
        label_lastbackup.setName("label_lastbackup"); // NOI18N

        label_lastbackupstatus.setForeground(resourceMap.getColor("label_lastbackupstatus.foreground")); // NOI18N
        label_lastbackupstatus.setText(resourceMap.getString("label_lastbackupstatus.text")); // NOI18N
        label_lastbackupstatus.setName("label_lastbackupstatus"); // NOI18N

        label_nextbackup.setText(resourceMap.getString("label_nextbackup.text")); // NOI18N
        label_nextbackup.setName("label_nextbackup"); // NOI18N

        label_creator.setText(resourceMap.getString("label_creator.text")); // NOI18N
        label_creator.setName("label_creator"); // NOI18N

        label_ziel.setText(resourceMap.getString("label_ziel.text")); // NOI18N
        label_ziel.setName("label_ziel"); // NOI18N

        javax.swing.GroupLayout panel_contentLayout = new javax.swing.GroupLayout(panel_content);
        panel_content.setLayout(panel_contentLayout);
        panel_contentLayout.setHorizontalGroup(
            panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_contentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_contentLayout.createSequentialGroup()
                        .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_contentLayout.createSequentialGroup()
                                .addComponent(label_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93))
                            .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_contentLayout.createSequentialGroup()
                        .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_contentLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label_ziel))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_contentLayout.createSequentialGroup()
                                .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(42, 42, 42)
                                .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_nextbackup)
                                    .addComponent(label_lastbackupstatus)
                                    .addGroup(panel_contentLayout.createSequentialGroup()
                                        .addComponent(label_lastbackup)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_creator)))))
                        .addContainerGap())))
        );
        panel_contentLayout.setVerticalGroup(
            panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_contentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(label_ziel))
                .addGap(28, 28, 28)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(label_lastbackup)
                    .addComponent(label_creator))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(label_lastbackupstatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(label_nextbackup))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(label_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        this.label_status.setVisible(false);
        this.progressBar.setVisible(false);
        showlatestBackup();

        jLabel5.setIcon(resourceMap.getIcon("jLabel5.icon")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout panel_holderLayout = new javax.swing.GroupLayout(panel_holder);
        panel_holder.setLayout(panel_holderLayout);
        panel_holderLayout.setHorizontalGroup(
            panel_holderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        panel_holderLayout.setVerticalGroup(
            panel_holderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_holderLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        this.addCommandButtons();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_holder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_holder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BackupDialog dialog = new BackupDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label_creator;
    private javax.swing.JLabel label_lastbackup;
    private javax.swing.JLabel label_lastbackupstatus;
    private javax.swing.JLabel label_nextbackup;
    private org.jdesktop.swingx.JXBusyLabel label_status;
    private javax.swing.JLabel label_ziel;
    private javax.swing.JPanel panel_buttons;
    private javax.swing.JPanel panel_content;
    private javax.swing.JPanel panel_holder;
    private org.openswing.swing.miscellaneous.client.ProgressBar progressBar;
    // End of variables declaration//GEN-END:variables

    private JCommandButton backupButton;
    private JCommandButton restoreButton;
}
