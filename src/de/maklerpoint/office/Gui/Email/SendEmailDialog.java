/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SendEmail.java
 *
 * Created on 10.06.2011, 18:06:12
 */
package de.maklerpoint.office.Gui.Email;

import de.maklerpoint.office.Benutzer.BenutzerObj;
import de.maklerpoint.office.Briefe.BriefObj;
import de.maklerpoint.office.Briefe.Tools.BriefTools;
import de.maklerpoint.office.Briefe.Tools.FirmenKundenBriefTools;
import de.maklerpoint.office.Briefe.Tools.KundenBriefTools;
import de.maklerpoint.office.Briefe.Tools.VersichererBriefTools;
import de.maklerpoint.office.Database.DatabaseConnection;
import de.maklerpoint.office.Email.EmailObj;
import de.maklerpoint.office.Email.Tools.EmailSQLMethods;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Konstanten.FileTypes;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Registry.BasicRegistry;
import de.maklerpoint.office.Registry.VersicherungsRegistry;
import de.maklerpoint.office.Schnittstellen.Email.MultiEmailSender;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.BenutzerHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.FirmenHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.GenerateAnsprechpartnerHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.KundenHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.MandantenHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.ProduktHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.StoerfallHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.VersichererHashtable;
import de.maklerpoint.office.Schnittstellen.Word.Hashtables.VertragHashtable;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Status;
import de.maklerpoint.office.Table.AbstractStandardModel;
import de.maklerpoint.office.Tools.FileTools;
import de.maklerpoint.office.Tools.FileTypeDetection;
import de.maklerpoint.office.Tools.FormatFileSize;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.Produkte.ProduktObj;
import de.maklerpoint.office.Versicherer.VersichererObj;
import de.maklerpoint.office.Vertraege.VertragObj;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class SendEmailDialog extends javax.swing.JDialog {

    class attachment {

        String filename;
        String filesize;
        int filetype;

        public attachment() {
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFilesize() {
            return filesize;
        }

        public void setFilesize(String filesize) {
            this.filesize = filesize;
        }

        public int getFiletype() {
            return filetype;
        }

        public void setFiletype(int filetype) {
            this.filetype = filetype;
        }
    }
    private int type = -1;
    private int kdtype = -1;
    public static final int PRIVAT = 0;
    public static final int GESCH = 1;
    public static final int VERSICHERER = 2;
    public static final int PRODUKT = 3;
    public static final int VERTRAG = 4;
    public static final int STOERFALL = 5;
    public static final int BENUTZER = 6;
    private BriefObj brief = null;
    private KundenObj kunde = null;
    private FirmenObj firma = null;
    private String kdnr = null;
    private String text = null;
    private VersichererObj vers = null;
    private Object stoer = null;
    private VertragObj vertr = null;
    private ProduktObj prod = null;
    private BenutzerObj ben = null;
    public ArrayList attachmentsList = new ArrayList();
    private boolean open = false;
    public static final String[] Columns = {"Hidden", "Typ", "Dateiname", "Größe"};
    public static final ImageIcon ICON_OPEN = ImageTools.createImageIcon(
            "de/acyrance/CRM/Gui/resources/icon_clean/arrow-270-medium.png");
    public static final ImageIcon ICON_CLOSE = ImageTools.createImageIcon(
            "de/acyrance/CRM/Gui/resources/icon_clean/arrow-000-medium.png");
    private String empfaenger = null;
    private String betreff = null;
    private String template = null;
    private Hashtable ht = new Hashtable();
    private SimpleDateFormat df_day = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat df_hour = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private Desktop desktop = Desktop.getDesktop();

    private void setNull() {
        this.brief = null;
        this.kdnr = null;
        this.ben = null;
        this.vers = null;
        this.vertr = null;
        this.prod = null;
        this.kunde = null;
        this.firma = null;
        this.text = null;

    }

    /** Creates new form SendEmail */
    public SendEmailDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        attachmentsList.clear();
        empfaenger = null;
        betreff = null;
        template = null;
        initComponents();
        setUp();

    }

    public SendEmailDialog(java.awt.Frame parent, boolean modal,
            String template, String betreff, String empfaenger) {
        super(parent, modal);
        setNull();
        attachmentsList.clear();
        this.template = template;
        this.betreff = betreff;
        this.empfaenger = empfaenger;
        initComponents();
        this.field_betreff.setText(betreff);
        this.field_adress.setText(empfaenger);
        try {
            loadTemplate();
        } catch (Exception e) {
            Log.logger.fatal("Konnte E-Mail Template " + template + " nicht laden", e);
            ShowException.showException("Beim laden des E-Mail Templates ist ein Fehler aufgetretten. Bitte überprüfen"
                    + " Sie den Dateipfad.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte E-Mail Template nicht laden");
        }
        setUp();
    }

    public SendEmailDialog(java.awt.Frame parent, boolean modal, String template,
            String betreff, String empfaenger, KundenObj kunde) {
        super(parent, modal);
        attachmentsList.clear();
        this.template = template;
        this.betreff = betreff;
        this.empfaenger = empfaenger;
        this.kunde = kunde;
        initComponents();
        this.field_betreff.setText(betreff);
        this.field_adress.setText(empfaenger);
        try {
            loadTemplate();
        } catch (Exception e) {
            Log.logger.fatal("Konnte E-Mail Template " + template + " nicht laden", e);
            ShowException.showException("Beim laden des E-Mail Templates ist ein Fehler aufgetretten. Bitte überprüfen"
                    + " Sie den Dateipfad.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte E-Mail Template nicht laden");
        }
        setUp();
    }

    public SendEmailDialog(java.awt.Frame parent, boolean modal, String template,
            String betreff, String empfaenger, FirmenObj firma) {
        super(parent, modal);
        setNull();
        attachmentsList.clear();
        this.template = template;
        this.betreff = betreff;
        this.empfaenger = empfaenger;
        this.firma = firma;
        initComponents();
        this.field_betreff.setText(betreff);
        this.field_adress.setText(empfaenger);
        try {
            loadTemplate();
        } catch (Exception e) {
            Log.logger.fatal("Konnte E-Mail Template " + template + " nicht laden", e);
            ShowException.showException("Beim laden des E-Mail Templates ist ein Fehler aufgetretten. Bitte überprüfen"
                    + " Sie den Dateipfad.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte E-Mail Template nicht laden");
        }
        setUp();
    }

    public SendEmailDialog(java.awt.Frame parent, boolean modal, String template,
            String betreff, String empfaenger, BenutzerObj ben) {
        super(parent, modal);
        setNull();
        attachmentsList.clear();
        this.template = template;
        this.betreff = betreff;
        this.empfaenger = empfaenger;
        this.ben = ben;
        initComponents();
        this.field_betreff.setText(betreff);
        this.field_adress.setText(empfaenger);
        try {
            loadTemplate();
        } catch (Exception e) {
            Log.logger.fatal("Konnte E-Mail Template " + template + " nicht laden", e);
            ShowException.showException("Beim laden des E-Mail Templates ist ein Fehler aufgetretten. Bitte überprüfen"
                    + " Sie den Dateipfad.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte E-Mail Template nicht laden");
        }
        setUp();
    }

    public SendEmailDialog(java.awt.Frame parent, boolean modal, String template,
            String betreff, String empfaenger, ProduktObj prod) {
        super(parent, modal);
        setNull();
        attachmentsList.clear();
        this.template = template;
        this.betreff = betreff;
        this.empfaenger = empfaenger;
        this.prod = prod;
        this.vers = VersicherungsRegistry.getVersicher(prod.getVersichererId());
        initComponents();
        this.field_betreff.setText(betreff);
        this.field_adress.setText(empfaenger);
        try {
            loadTemplate();
        } catch (Exception e) {
            Log.logger.fatal("Konnte E-Mail Template " + template + " nicht laden", e);
            ShowException.showException("Beim laden des E-Mail Templates ist ein Fehler aufgetretten. Bitte überprüfen"
                    + " Sie den Dateipfad.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte E-Mail Template nicht laden");
        }
        setUp();
    }

    public SendEmailDialog(java.awt.Frame parent, boolean modal, String template,
            String betreff, String empfaenger, VersichererObj vers) {
        super(parent, modal);
        setNull();
        attachmentsList.clear();
        this.template = template;
        this.betreff = betreff;
        this.empfaenger = empfaenger;
        this.vers = vers;
        initComponents();
        this.field_betreff.setText(betreff);
        this.field_adress.setText(empfaenger);
        try {
            loadTemplate();
        } catch (Exception e) {
            Log.logger.fatal("Konnte E-Mail Template " + template + " nicht laden", e);
            ShowException.showException("Beim laden des E-Mail Templates ist ein Fehler aufgetretten. Bitte überprüfen"
                    + " Sie den Dateipfad.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte E-Mail Template nicht laden");
        }
        setUp();
    }

    private void generateHashTable() {
        ht.putAll(MandantenHashtable.generateMandantenhash(true));
        if (kunde != null) {
            ht.putAll(KundenHashtable.generatKundenhash(kunde, true));
        }
        if (firma != null) {
            ht.putAll(FirmenHashtable.generatFirmenhash(firma));
        }
        if (vers != null) {
            ht.putAll(VersichererHashtable.generateVersichererhash(vers));
        }
        if (ben != null) {
            ht.putAll(BenutzerHashtable.generateBenutzerhash(ben, true));
        } else {
            ht.putAll(BenutzerHashtable.generateBenutzerhash(BasicRegistry.currentUser, true));
        }
        if (prod != null) {
            ht.putAll(ProduktHashtable.generateProdukthash(prod));
        }
        if (stoer != null) {
            ht.putAll(StoerfallHashtable.generateStoerfallhash(stoer));
        }
        if (vertr != null) {
            ht.putAll(VertragHashtable.generatVersichererhash(vertr));
        }
    }

    private void generateBriefTable() {
        if (kunde != null || firma != null) {
            hashPut("ANSCHRIFT", generateKundenAnschrift());
            if (kunde != null) {
                hashPut("ANSCHRIFT_EINZEILER", BriefTools.generateKundenAnschrift(kunde));
            } else {
                hashPut("ANSCHRIFT_EINZEILER", BriefTools.generateKundenAnschrift(firma));
            }

            if (kunde != null) {
                hashPut("ANREDE", KundenBriefTools.getKundenBriefAnrede(kunde));
            } else {
                hashPut("ANREDE", FirmenKundenBriefTools.getKundenBriefAnrede());
            }
        } else {
            if (vers != null) {
                hashPut("ANSCHRIFT", BriefTools.generateVersichererAnschricht(vers));
                hashPut("ANREDE", VersichererBriefTools.getVersichererAnrede(vers));
                hashPut("ANSCHRIFT_EINZEILER", VersichererBriefTools.getVersichererAnschriftONLINE(vers));
            }
        }

        hashPut("BETREFF", this.field_betreff.getText());

        if (ben != null) {
            ht.putAll(GenerateAnsprechpartnerHashtable.generateAnsprechpartnerHashmap(
                    ben, BasicRegistry.currentMandant));
        } else {
            ht.putAll(GenerateAnsprechpartnerHashtable.generateAnsprechpartnerHashmap());
        }

        hashPut("TEXT", text);

        hashPut("DATUM", df_day.format(new Date(System.currentTimeMillis())));
    }

    private String generateKundenAnschrift() {

        String anschrift = "";
        if (kunde != null) {
            anschrift = BriefTools.generateKundenAnschrift(kunde);
        } else if (firma != null) {
            anschrift = BriefTools.generateKundenAnschrift(firma);
        }

        return anschrift;
    }

    private void loadTemplate() throws FileNotFoundException, IOException {
        if (template == null) {
            return;
        }

        generateHashTable();
        generateBriefTable();

        File tfile = new File(template);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                new FileInputStream(tfile)));
        StringBuilder contentOfFile = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            for (java.util.Enumeration e = ht.keys(); e.hasMoreElements();) {
                String name = (String) e.nextElement();
                String value = ht.get(name).toString();
                line = line.replaceAll("##" + name.toUpperCase() + "##", value);
            }

            contentOfFile.append(line);
        }
        String content = contentOfFile.toString();
        if (Log.logger.isDebugEnabled()) {
            Log.logger.debug("E-Mail Template Content: " + content);
        }

        this.editor_msg.setText(content);
    }

    private void setUp() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                scroll_protokolle.setVisible(false);
                table_anlagen.setVisible(false);
                open = false;
                setDialogsizeSmall();
            }
        });


        loadAttachmentTable();
        checkSettings();
    }

    private void checkSettings() {
        if (Config.get("mailSendermail", "").length() < 1) {
            JOptionPane.showMessageDialog(null, "Bitte konfigurieren Sie Ihre E-Mail "
                    + "Daten zuerst unter Optionen -> Einstellungen.",
                    "Keine E-Mail Einstellungen", JOptionPane.OK_OPTION);
        }
    }

    public void setDialogsizeSmall() {
        this.setSize(this.getWidth(), this.getHeight() - 80);
    }

    public void setDialogsizeExtended() {
        this.setSize(this.getWidth(), this.getHeight() + 85);
    }

    private void loadAttachmentTable() {
        if (attachmentsList.isEmpty()) {
            //System.out.println("Keine Anlagen");
            setTable(null, Columns);
            this.label_anlagen.setText("Keine Anlagen");
            return;
        }

        Object[][] data = null;

        Object[] atts = attachmentsList.toArray();
        attachment[] attcls = new attachment[atts.length];
        data = new Object[atts.length][4];

        for (int i = 0; i < atts.length; i++) {
            String filename = String.valueOf(atts[i]);
            File tmpfile = new File(filename);
            attcls[i] = new attachment();

            attcls[i].setFilename(filename);
            attcls[i].setFilesize(FormatFileSize.formatSize(tmpfile.length(),
                    FormatFileSize.KB) + " kB");
            attcls[i].setFiletype(FileTypeDetection.getFileType(tmpfile));

            data[i][0] = attcls[i];

            ImageIcon icon = FileTypes.UNKNOWN_IMAGE;

            if (attcls[i].getFiletype() != -1) {
                icon = FileTypes.TYPE_IMAGES[attcls[i].getFiletype()];
            }

            icon.setDescription(FileTypes.getName(attcls[i].getFiletype()));

            data[i][1] = icon;

            data[i][2] = attcls[i].getFilename();
            data[i][3] = attcls[i].getFilesize();
        }

        if (atts.length == 1) {
            this.label_anlagen.setText("Eine Anlagen");
        } else {
            this.label_anlagen.setText(atts.length + " Anlagen");
        }

        setTable(data, Columns);
    }

    private void setTable(Object[][] data, String[] columns) {
        this.table_anlagen.setModel(new AbstractStandardModel(data, columns));

        table_anlagen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_anlagen.setColumnSelectionAllowed(false);
        table_anlagen.setCellSelectionEnabled(false);
        table_anlagen.setRowSelectionAllowed(true);
        table_anlagen.setAutoCreateRowSorter(true);

        table_anlagen.setFillsViewportHeight(true);
        table_anlagen.removeColumn(table_anlagen.getColumnModel().getColumn(0));

        MouseListener popupListener = new TablePopupListener();
        table_anlagen.addMouseListener(popupListener);
        table_anlagen.setColumnControlVisible(true);

        JTableHeader header = table_anlagen.getTableHeader();
        header.addMouseListener(popupListener);
        header.validate();

        table_anlagen.packAll();

        table_anlagen.tableChanged(new TableModelEvent(table_anlagen.getModel()));
        table_anlagen.revalidate();
        table_anlagen.setVisible(true);
    }

    /**
     * Mousepopup
     */
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
                popupAttachments.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void hashPut(String val, Object val2) {
        if (val == null) {
//            System.out.println("Val: " + val + " is null " + val2);
            return;
        }

        if (val2 == null) {
//            System.out.println("Val2 is null: " + val);
            val2 = "";
        }

        ht.put(val, val2);
    }

    private void addAttachment() {
        String file = FileTools.openFile("Anlage wählen");
        if (file != null) {
            attachmentsList.add(file);
        }

        this.loadAttachmentTable();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupAttachments = new javax.swing.JPopupMenu();
        neuMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        editor_msg = new net.atlanticbb.tantlinger.shef.HTMLEditorPane();
        btnAn = new javax.swing.JButton();
        btnCC = new javax.swing.JButton();
        field_adress = new javax.swing.JTextField();
        field_cc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        field_betreff = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        label_anlagen = new javax.swing.JLabel();
        btnAddAnlage = new javax.swing.JButton();
        btnAnlageliste = new javax.swing.JButton();
        scroll_protokolle = new javax.swing.JScrollPane();
        table_anlagen = new org.jdesktop.swingx.JXTable();

        popupAttachments.setName("popupAttachments"); // NOI18N

        neuMenuItem.setMnemonic('h');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.maklerpoint.office.start.CRM.class).getContext().getResourceMap(SendEmailDialog.class);
        neuMenuItem.setText(resourceMap.getString("neuMenuItem.text")); // NOI18N
        neuMenuItem.setName("neuMenuItem"); // NOI18N
        neuMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neuMenuItemActionPerformed(evt);
            }
        });
        popupAttachments.add(neuMenuItem);

        openMenuItem.setMnemonic('\u00f6');
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setToolTipText(resourceMap.getString("openMenuItem.toolTipText")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        popupAttachments.add(openMenuItem);

        deleteMenuItem.setMnemonic('e');
        deleteMenuItem.setText(resourceMap.getString("deleteMenuItem.text")); // NOI18N
        deleteMenuItem.setName("deleteMenuItem"); // NOI18N
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        popupAttachments.add(deleteMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        editor_msg.setName("editor_msg"); // NOI18N

        btnAn.setText(resourceMap.getString("btnAn.text")); // NOI18N
        btnAn.setName("btnAn"); // NOI18N
        btnAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnActionPerformed(evt);
            }
        });

        btnCC.setText(resourceMap.getString("btnCC.text")); // NOI18N
        btnCC.setToolTipText(resourceMap.getString("btnCC.toolTipText")); // NOI18N
        btnCC.setName("btnCC"); // NOI18N
        btnCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCCActionPerformed(evt);
            }
        });

        field_adress.setText(resourceMap.getString("field_adress.text")); // NOI18N
        field_adress.setName("field_adress"); // NOI18N

        field_cc.setText(resourceMap.getString("field_cc.text")); // NOI18N
        field_cc.setName("field_cc"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        field_betreff.setName("field_betreff"); // NOI18N

        btnCancel.setMnemonic('A');
        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.setPreferredSize(new java.awt.Dimension(120, 27));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setMnemonic('S');
        btnSave.setText(resourceMap.getString("btnSave.text")); // NOI18N
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setPreferredSize(new java.awt.Dimension(120, 27));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, resourceMap.getColor("jPanel1.border.matteColor"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        label_anlagen.setIcon(resourceMap.getIcon("label_anlagen.icon")); // NOI18N
        label_anlagen.setText(resourceMap.getString("label_anlagen.text")); // NOI18N
        label_anlagen.setName("label_anlagen"); // NOI18N

        btnAddAnlage.setMnemonic('h');
        btnAddAnlage.setText(resourceMap.getString("btnAddAnlage.text")); // NOI18N
        btnAddAnlage.setToolTipText(resourceMap.getString("btnAddAnlage.toolTipText")); // NOI18N
        btnAddAnlage.setName("btnAddAnlage"); // NOI18N
        btnAddAnlage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAnlageActionPerformed(evt);
            }
        });

        btnAnlageliste.setIcon(resourceMap.getIcon("btnAnlageliste.icon")); // NOI18N
        btnAnlageliste.setMnemonic('a');
        btnAnlageliste.setText(resourceMap.getString("btnAnlageliste.text")); // NOI18N
        btnAnlageliste.setName("btnAnlageliste"); // NOI18N
        btnAnlageliste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnlagelisteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAnlageliste)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 411, Short.MAX_VALUE)
                .addComponent(btnAddAnlage, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 307, Short.MAX_VALUE)
                    .addComponent(label_anlagen)
                    .addGap(0, 308, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAnlage)
                    .addComponent(btnAnlageliste))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 18, Short.MAX_VALUE)
                    .addComponent(label_anlagen)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );

        scroll_protokolle.setName("scroll_protokolle"); // NOI18N

        table_anlagen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Typ", "Dateiname", "Größe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_anlagen.setColumnControlVisible(true);
        table_anlagen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_anlagen.setName("table_anlagen"); // NOI18N
        scroll_protokolle.setViewportView(table_anlagen);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAn, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(btnCC, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(field_adress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                    .addComponent(field_cc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                    .addComponent(field_betreff, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(editor_msg, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scroll_protokolle, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(456, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_adress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_cc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCC))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_betreff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editor_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_protokolle, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

        int dial = JOptionPane.NO_OPTION;
        dial = JOptionPane.showConfirmDialog(null, "Wollen Sie das Fenster wirklich schließen? Alle ihre Eingaben "
                + "gehen in diesem Fall verloren.", "Wollen Sie das Fenster schließen?", JOptionPane.YES_NO_OPTION);
        if (dial == JOptionPane.YES_OPTION) {
            this.dispose();
        }

        this.dispose();

}//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        String empflist = this.field_adress.getText().trim();
        String ccs = this.field_cc.getText().trim();              
        
        if (empflist == null || empflist.length() < 1) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Empfänger an.",
                    "Kein Empfänger", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] empf = empflist.split(",");
        
        String[] cc = null;
        
        if(ccs != null && ccs.length() > 1) {
            cc = ccs.split(",");
        }
        
        String btrf = this.field_betreff.getText().trim();
        String body = this.editor_msg.getText();
        String nohtml = Config.get("mailNohtml", "Ihr E-Mail Client untersützt keine HTML Nachrichten"); // TODO

        if (btrf == null || btrf.length() < 1) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Betreff an.",
                    "Kein Betreff", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        MultiEmailSender mail = new MultiEmailSender(empf, btrf, cc, body, nohtml);


        if (!attachmentsList.isEmpty()) {
            Object[] atts = attachmentsList.toArray();
            File[] files = new File[atts.length];

            for (int i = 0; i < atts.length; i++) {
                files[i] = new File(String.valueOf(atts[i]));
            }

            mail.setFiles(files);
        }

        EmailObj eobj = new EmailObj();
        eobj.setAbsender(Config.get("mailSendermail", "") + " " + Config.get("mailSender", ""));
        eobj.setCreatorId(BasicRegistry.currentUser.getId());
        eobj.setBetreff(betreff);
        eobj.setEmpfaenger(empflist);
        eobj.setCc(ccs);
        eobj.setBody(body);
        eobj.setNohtml(nohtml);

        if (this.ben != null) {
            eobj.setBenutzerId(this.ben.getId());
        }

        if (this.kunde != null) {
            eobj.setKundenKennung(kunde.getKundenNr());
        }

        if (this.firma != null) {
            eobj.setKundenKennung(firma.getKundenNr());
        }

        if (this.vers != null) {
            eobj.setVersichererId(vers.getId());
        }

        if (this.prod != null) {
            eobj.setVersichererId(prod.getVersichererId());
            eobj.setProduktId(prod.getId());
        }

        if (this.vertr != null) {
            eobj.setVertragId(vertr.getId());
        }

        eobj.setCreated(new java.sql.Timestamp(System.currentTimeMillis()));
        eobj.setStatus(Status.NORMAL);

        try {
            mail.send();
            eobj.setSend(true);
            eobj.setSendTime(new java.sql.Timestamp(System.currentTimeMillis()));

        } catch (Exception e) {
            eobj.setSend(false);
            Log.logger.fatal("E-Mail Fehler: Beim versenden der E-Mail ist ein Fehler aufgetretten", e);
            ShowException.showException("Beim versenden der E-Mail ist ein Fehler aufgetretten. Bitte überprüfen Sie "
                    + "Ihre E-Mail Einstellungen.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte E-Mail nicht senden");
        }


        try {
            eobj.setModified(new java.sql.Timestamp(System.currentTimeMillis()));
            EmailSQLMethods.insertIntoEmails(DatabaseConnection.open(), eobj);
        } catch (Exception e) {
            Log.databaselogger.fatal("Beim speichern der E-Mail ist ein Fehler aufgetretten", e);
            ShowException.showException("Beim speichern der E-Mail ist ein Datenbankfehler "
                    + "aufgetretten. Die E-Mail wurde trotzdem verschickt.",
                    ExceptionDialogGui.LEVEL_WARNING, e,
                    "Schwerwiegend: Konnte E-Mail nicht speichern");
        }

        this.dispose();
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnAnlagelisteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnlagelisteActionPerformed
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (!open) {
                    open = true;
                    btnAnlageliste.setIcon(ICON_CLOSE);
                    scroll_protokolle.setVisible(true);
                    table_anlagen.setVisible(true);
                    table_anlagen.validate();
                    setDialogsizeExtended();
                    rootPane.validate();
                    loadAttachmentTable();
                } else {
                    open = false;
                    btnAnlageliste.setIcon(ICON_OPEN);
                    scroll_protokolle.setVisible(false);
                    table_anlagen.setVisible(false);
                    table_anlagen.validate();
                    setDialogsizeSmall();
                    rootPane.validate();
                    loadAttachmentTable();
                }
            }
        });

    }//GEN-LAST:event_btnAnlagelisteActionPerformed

    private void btnAddAnlageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAnlageActionPerformed
        addAttachment();
    }//GEN-LAST:event_btnAddAnlageActionPerformed

    private void btnAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnActionPerformed
        String mail = EmailChooserHelper.getEmail();

        String current = this.field_adress.getText();

        if (current != null && current.length() > 1) {
            this.field_adress.setText(current + ", " + mail);
        } else {
            this.field_adress.setText(mail);
        }
    }//GEN-LAST:event_btnAnActionPerformed

    private void neuMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neuMenuItemActionPerformed
        addAttachment();
    }//GEN-LAST:event_neuMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        int row = table_anlagen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Anlage aus.");
            return;
        }

        attachment att = (attachment) this.table_anlagen.getModel().getValueAt(row, 0);

        if (att == null) {
            return;
        }

        try {
            desktop.open(new File(att.getFilename())); //att.getFilename()
        } catch (IOException ex) {
            Log.logger.warn("Fehler: Konnte Datei nicht öffnen", ex);
            ShowException.showException("Die Datei konnte nicht automatisch "
                    + "geöffnet werden. Bitte öffnen Sie sie manuell",
                    ExceptionDialogGui.LEVEL_INFO, ex,
                    "Schwerwiegend: Konnte Datei nicht öffnen");
        }

    }//GEN-LAST:event_openMenuItemActionPerformed

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemActionPerformed
        int row = table_anlagen.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine Anlage aus.");
            return;
        }

        attachment att = (attachment) this.table_anlagen.getModel().getValueAt(row, 0);

        if (att == null) {
            return;
        }

        attachmentsList.remove(att.getFilename());

        this.loadAttachmentTable();
    }//GEN-LAST:event_deleteMenuItemActionPerformed

    private void btnCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCCActionPerformed
        String mail = EmailChooserHelper.getEmail();

        String current = this.field_cc.getText();

        if (current != null && current.length() > 1) {
            this.field_cc.setText(current + ", " + mail);
        } else {
            this.field_cc.setText(mail);
        }
    }//GEN-LAST:event_btnCCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                SendEmailDialog dialog = new SendEmailDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddAnlage;
    private javax.swing.JButton btnAn;
    private javax.swing.JButton btnAnlageliste;
    private javax.swing.JButton btnCC;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JMenuItem deleteMenuItem;
    private net.atlanticbb.tantlinger.shef.HTMLEditorPane editor_msg;
    private javax.swing.JTextField field_adress;
    private javax.swing.JTextField field_betreff;
    private javax.swing.JTextField field_cc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_anlagen;
    private javax.swing.JMenuItem neuMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JPopupMenu popupAttachments;
    private javax.swing.JScrollPane scroll_protokolle;
    private org.jdesktop.swingx.JXTable table_anlagen;
    // End of variables declaration//GEN-END:variables
}
