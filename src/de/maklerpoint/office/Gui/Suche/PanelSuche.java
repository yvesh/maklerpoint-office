/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelSuche.java
 *
 * Created on 20.06.2011, 16:35:44
 */
package de.maklerpoint.office.Gui.Suche;

import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Filesystem.Filesystem;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Konstanten.FileTypes;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Table.AbstractStandardModel;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.DuplicateFilter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.openide.util.Exceptions;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class PanelSuche extends javax.swing.JPanel {

    class SearchObj {

        private String _contents;
        private String _path;
        private int _type = -1;
        // DB
        private int _dbid;
        private String _table;
        private double _score = 0.00;
        private String _modified;
        private String _filesize;

        public String getContents() {
            return _contents;
        }

        public void setContents(String _contents) {
            this._contents = _contents;
        }

        public int getDbid() {
            return _dbid;
        }

        public void setDbid(int _dbid) {
            this._dbid = _dbid;
        }

        public String getPath() {
            return _path;
        }

        public void setPath(String _path) {
            this._path = _path;
        }

        public String getTable() {
            return _table;
        }

        public void setTable(String _table) {
            this._table = _table;
        }

        public int getType() {
            return _type;
        }

        public void setType(int _type) {
            this._type = _type;
        }

        public double getScore() {
            return _score;
        }

        public void setScore(double _score) {
            this._score = _score;
        }

        public String getModified() {
            return _modified;
        }

        public void setModified(String _modified) {
            this._modified = _modified;
        }

        public String getFilesize() {
            return _filesize;
        }

        public void setFilesize(String _filesize) {
            this._filesize = _filesize;
        }
    }
    private String querytxt = null;
    private static String indexDir = Filesystem.getRootPath() + File.separator + "sonstiges" + File.separator + "index";
    private String[] Columns = {"Hidden", "Typ", "Pfad oder Datenbankfeld", "Inhalt (Ausschnitt)", "Größe", "Letzte Änderung"};
    private Desktop desktop = Desktop.getDesktop();

    /** Creates new form PanelSuche */
    public PanelSuche(String query) {
        this.querytxt = query;
        initComponents();
        search();
    }

    private void search() {
        if (querytxt == null) {
            return;
        }

        try {
            Object[][] data = null;

            FSDirectory dir = FSDirectory.open(new File(indexDir));
            IndexSearcher isearcher = new IndexSearcher(dir);
            Analyzer anal = new StandardAnalyzer(Version.LUCENE_32);
            QueryParser parser = new QueryParser(Version.LUCENE_32, "contents", anal);
            Query query = parser.parse(querytxt);

            DuplicateFilter df = new DuplicateFilter("path");

            TopDocs hits = isearcher.search(query, df, 30);
            ScoreDoc[] scoreDocs = hits.scoreDocs;

            SearchObj[] sr = new SearchObj[scoreDocs.length];

            for (int i = 0; i < scoreDocs.length; i++) {
                ScoreDoc sd = scoreDocs[i];
                float score = sd.score;
                int docId = sd.doc;
                Document d = isearcher.doc(docId);

                sr[i] = new SearchObj();

                if (d.get("dbid") != null) {
                    sr[i].setDbid(Integer.valueOf(d.get("dbid")));
                }
                if (d.get("type") != null) {
                    sr[i].setType(Integer.valueOf(d.get("type")));
                }
                sr[i].setTable(d.get("table"));
                sr[i].setContents(d.get("contents"));
                sr[i].setPath(d.get("path"));

                sr[i].setModified(d.get("modified"));
                sr[i].setFilesize(d.get("filesize"));
                sr[i].setScore(score);

            }

            isearcher.close();

            if (sr != null) {
                data = new Object[sr.length][6];

                for (int i = 0; i < sr.length; i++) {
                    data[i][0] = sr[i];
                    data[i][1] = FileTypes.TYPE_IMAGES[sr[i].getType()];


                    if(sr[i].getType() == FileTypes.DATABASE_TABLE) {
                        data[i][2] = sr[i].getPath() + " [" + sr[i].getTable() + "]";
                    } else {
                        data[i][2] = sr[i].getPath();
                    }


                    if (sr[i].getContents() != null) {
                        if (sr[i].getContents().length() > 80) {
                            data[i][3] = sr[i].getContents().substring(0, 80);
                        } else {
                            data[i][3] = sr[i].getContents();
                        }
                    } else {
                        data[i][3] = "Keine Vorschau";
                    }

                    if (sr[i].getType() == FileTypes.DATABASE_TABLE) {
                        data[i][4] = "-";
                    } else {
                        data[i][4] = sr[i].getFilesize() + " KB";
                    }

                    if (sr[i].getType() == FileTypes.DATABASE_TABLE) {
                        data[i][5] = sr[i].getModified();
                    } else {
                        data[i][5] = sr[i].getModified();
                    }
                }
                this.label_activekunde.setText(sr.length + " Ergebnisse");
            } else {
                this.label_activekunde.setText("Keine Ergebnisse");
                data = null;
            }

            setTable(data, Columns);

        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
    }

    private void setTable(Object[][] data, String[] columns) {

//        if (data == null) {
//            System.out.println("Data is null");
//        } else {
//            System.out.println("Date length: " + data.length);
//        }

        this.table_res.setModel(new AbstractStandardModel(data, columns));

        table_res.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_res.setColumnSelectionAllowed(false);
        table_res.setCellSelectionEnabled(false);
        table_res.setRowSelectionAllowed(true);
        table_res.setAutoCreateRowSorter(true);

        table_res.setFillsViewportHeight(true);
        table_res.removeColumn(table_res.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_res.addMouseListener(popupListener);
        table_res.setColumnControlVisible(true);

        table_res.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_res.getColumnModel().getColumn(0).setMaxWidth(20);

        JTableHeader header = table_res.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_res.packAll();

        table_res.tableChanged(new TableModelEvent(table_res.getModel()));
        table_res.revalidate();
    }

    class TablePopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
//             tableNachrichtenPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
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

        panel_tableStatus = new javax.swing.JPanel();
        label_activekunde = new javax.swing.JLabel();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_res = new org.jdesktop.swingx.JXTable();

        setName("Form"); // NOI18N

        panel_tableStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_tableStatus.setName("panel_tableStatus"); // NOI18N
        panel_tableStatus.setPreferredSize(new java.awt.Dimension(1294, 26));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(PanelSuche.class);
        label_activekunde.setText(resourceMap.getString("label_activekunde.text")); // NOI18N
        label_activekunde.setName("label_activekunde"); // NOI18N

        javax.swing.GroupLayout panel_tableStatusLayout = new javax.swing.GroupLayout(panel_tableStatus);
        panel_tableStatus.setLayout(panel_tableStatusLayout);
        panel_tableStatusLayout.setHorizontalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tableStatusLayout.createSequentialGroup()
                .addContainerGap(654, Short.MAX_VALUE)
                .addComponent(label_activekunde)
                .addContainerGap())
        );
        panel_tableStatusLayout.setVerticalGroup(
            panel_tableStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_activekunde, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        scroll_protokolle.setMinimumSize(new java.awt.Dimension(450, 160));
        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_res.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_res.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_res.setMinimumSize(new java.awt.Dimension(400, 150));
        table_res.setName("table_res"); // NOI18N
        table_res.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_resMouseClicked(evt);
            }
        });
        scroll_protokolle.setViewportView(table_res);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 747, Short.MAX_VALUE)
            .addComponent(panel_tableStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panel_tableStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void table_resMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_resMouseClicked
        int row = table_res.getSelectedRow();
        if (row == -1) {
            return;
        }

        if (evt.getClickCount() >= 2) {
            SearchObj so = (SearchObj) this.table_res.getModel().getValueAt(row, 0);

            if (so == null) {
                return;
            }

            if (so.getType() != FileTypes.DATABASE_TABLE) {
                try {
                    desktop.open(new File(so.getPath())); // TODO Voller Pfad?
                } catch (IOException ex) {
                    Log.logger.fatal("Fehler: Konnte Dateiexplorer nicht öffnen", ex);
                    ShowException.showException("Der Dateiexplorer konnte nicht geöffnet werden. Sie finden die Briefvorlagen im Verzeichnis \""
                            + Filesystem.getTemplatePath() + File.separatorChar + "word" + File.separatorChar + "\" .",
                            ExceptionDialogGui.LEVEL_WARNING, ex,
                            "Schwerwiegend: Konnte Dateiexplorer nicht öffnen");

                }
            }
        }

}//GEN-LAST:event_table_resMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel label_activekunde;
    private javax.swing.JPanel panel_tableStatus;
    private javax.swing.JScrollPane scroll_protokolle;
    private org.jdesktop.swingx.JXTable table_res;
    // End of variables declaration//GEN-END:variables
}
