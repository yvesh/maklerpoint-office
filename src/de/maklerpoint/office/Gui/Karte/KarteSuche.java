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

package de.maklerpoint.office.Gui.Karte;

import com.roots.map.MapPanel;
import de.maklerpoint.office.Gui.CRMView;
import de.maklerpoint.office.Gui.Leftpane.panelKarte;
import de.maklerpoint.office.Konstanten.ResourceStrings;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.Tools.ImageTools;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.xml.parsers.SAXParserFactory;
import org.noos.xing.mydoggy.ToolWindow;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class KarteSuche {

    public static boolean searching = false;
    private static ArrayList<SearchResult> results = new ArrayList<SearchResult>();
    private static final String NAMEFINDER_URL = "http://nominatim.openstreetmap.org/search?format=xml&email=dev%40maklerpoint.de";

    public static void doExteneralSearch(final String address, final CRMView crm) {
        if (searching)
          return;

        MapPanel map = Karte.getMap(6.94, 50.95, 10);
//        crm.panel_content.removeAll();
//        crm.panel_content.add(map, BorderLayout.CENTER);
//        crm.panel_content.revalidate();

        if(crm.toolWindowManager.getContentManager().getContent("map") == null) {
            crm.toolWindowManager.getContentManager().addContent("map",
                     "Karte",
                     ImageTools.createImageIcon(ResourceStrings.KARTE_ICON),  // An icon
                     map);
        }
        ToolWindow karte = crm.toolWindowManager.getToolWindow("Karte (Suche)");
        karte.setActive(true);

        crm.toolWindowManager.getContentManager().getContent("map").setSelected(true);

        doSearch(address, crm, map);
    }


    public static void doSearch(final String address, final CRMView crm, final MapPanel map) {
       if (searching)
          return;

       Runnable r = new Runnable() {
            public void run() {
                doSearchInternal(address, crm, map);
            }
       };

       searching = true;
       panelKarte.btn_search.setEnabled(false);
       panelKarte.btn_search.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       panelKarte.field_cardsearch.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       map.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

       Thread t = new Thread(r, "searcher "+ address);
       t.start();        
    }

    public static void doSearchInternal(final String newSearch, final CRMView crm, final MapPanel map) {
            results.clear();
            try {
                // Create a URL for the desired page
                String args = URLEncoder.encode(newSearch, "UTF-8");
                String path = NAMEFINDER_URL + "&q=" + args;
                if(Log.logger.isDebugEnabled())
                    Log.logger.debug("XML Lookup query: " + path);
                
                SAXParserFactory factory = SAXParserFactory.newInstance();
                factory.setValidating(false);
                factory.newSAXParser().parse(path, new DefaultHandler() {
                    private final ArrayList<String> pathStack = new ArrayList<String>();
                    private final ArrayList<SearchResult> namedStack = new ArrayList<SearchResult>();
                    private StringBuilder chars;
                                        
                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes) {
                        pathStack.add(qName);
                        String path = getPath();
                        //if ("display".equals(qName)) {
                            SearchResult result = new SearchResult();
                            result.setType(attributes.getValue("osm_type"));                            
                            result.setLat(tryDouble(attributes.getValue("lat")));
                            result.setLon(tryDouble(attributes.getValue("lon")));
                            
                            if(Log.logger.isDebugEnabled()) {
                                Log.logger.debug("Adresse Lat: " + result.getLat());
                                Log.logger.debug("Adresse Lon: " + result.getLon());
                            }
                            
                            result.setBoundingbox(getBoundary(attributes.getValue("boundingbox")));
                            
                            result.setName(attributes.getValue("display_name"));
                            result.setCategory(attributes.getValue("class"));
                            //result.setInfo(attributes.getValue("info"));
                            result.setZoom(calculateZoom(attributes.getValue("place_rank")));
                            namedStack.add(result);
                            if (pathStack.size() == 2)
                                results.add(result);
                        //} else if ("description".equals(qName)) {
                        //    chars = new StringBuilder();
                       // }
                    }
                @Override
                    public void endElement(String uri, String localName, String qName) {
                        if ("named".equalsIgnoreCase(qName)) {
                            namedStack.remove(namedStack.size() - 1);
                        } else if ("description".equalsIgnoreCase(qName)) {
                            namedStack.get(namedStack.size() - 1).setDescription(chars.toString());
                        }
                        pathStack.remove(pathStack.size() - 1);
                    }
                @Override
                    public void characters(char[] ch, int start, int length) throws SAXException {
                        if(chars != null)
                            chars.append(ch, start, length);
                    }
                    private String getPath() {
                        StringBuilder sb = new StringBuilder();
                        for (String p : pathStack)
                            sb.append("/").append(p);
                        return sb.toString();
                    }
                    
                    private double tryDouble(String s) {
                        if(s == null)
                            return 0d;
                        
                        try {
                            return Double.valueOf(s);
                        } catch (Exception e) {
                            System.out.println("Kein double " + s);
                            e.printStackTrace();
                            return 0d;
                        }
                    }
                    
                    private double[] getBoundary(String bnd) {
                        //System.out.println("BND: " + bnd);
                        
                        if(bnd == null)
                            return null;
                       
                        double[] dbl = new double[4];
                        String[] result = bnd.split(",");
                        
                        for(int i = 0; i < result.length; i++){
                            dbl[i] = Double.valueOf(result[i]);
                        }
                        
                        
                        return dbl;
                    }
                    
                    private int calculateZoom(String rank) {
//                        System.out.println("Rank: " + rank);
                        if(rank == null)
                            return 15;
                        
                        int zm = Integer.valueOf(rank);                                                
                                                                        
                        return zm;
                    }
                    
                    private int tryInteger(String s) {
                        if(s == null)
                            return 0;
                        
                        try {
                            return Integer.valueOf(s);
                        } catch (Exception e) {
                            System.out.println("Kein Integer " + s);
                            e.printStackTrace();
                            return 0;
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Log.logger.warn("Es ist ein Fehler bei der Suche aufgetreten", e);
            }

            if(results.isEmpty()) {
                searching = false;
                panelKarte.btn_search.setEnabled(true);
                panelKarte.btn_search.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                panelKarte.field_cardsearch.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                map.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                JOptionPane.showMessageDialog(null, "Es wurde leider kein Ort mit der eingegebenen Adresse gefunden.",
                                                       "Die Adresse wurde nicht gefunden", JOptionPane.ERROR_MESSAGE);
                return;
            }

            final StringBuilder html = new StringBuilder();
            html.append("<html><body>\r\n");
            for (int i = 0; i < results.size(); ++i) {
                SearchResult result = results.get(i);
                String description = result.getDescription();
                description = description.replaceAll("\\[.*?\\]", "");
                String shortName = result.getName();
                shortName = shortName.replaceAll("\\s(.*)$", "");
                String linkBody = shortName + " [" + result.getCategory() + "]";
                html.append("<a href='").append(i).append("'>").append(linkBody).append("</a><br>\r\n");
                html.append("<i>").append(description).append("<br><br>\r\n");
                //String description = result.getDescription() == null || result.getDescription().length() == 0 ? "-" : result.getDescription();
                //html.append(description).append("<br><br>\r\n");
            }
            html.append("</body></html>\r\n");
            String html2 = html.toString().replaceAll("place", "Ort");
            html2 = html2.replaceAll("highway", "Strasse");
            final String html_ = html2;


            Runnable r = new Runnable() {
                public void run() {
                    try {                        
                        
                        Font font = new JLabel().getFont();
                         String stylesheet =
                        "body { color:#808080; margin-top:0; margin-left:0; margin-bottom:0; margin-right:0; font-family:" + font.getName() + "; font-size:" + font.getSize()+ "pt;}" +
                        "a    { color:#4040D9; margin-top:0; margin-left:0; margin-bottom:0; margin-right:0; font-family:" + font.getName() + "; font-size:" + font.getSize() + "pt;}";

                        HTMLEditorKit kit = new HTMLEditorKit();
                        panelKarte.editor_ergebnis.setEditorKit(kit);
                        panelKarte.editor_ergebnis.setEditable(false);

                        HTMLDocument htmlDocument = (HTMLDocument)panelKarte.editor_ergebnis.getDocument();
                        StyleSheet sheet = new StyleSheet();
                        try {
                            sheet.loadRules(new StringReader(stylesheet), null);
                            htmlDocument.getStyleSheet().addStyleSheet(sheet);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Except");
                        }
                        htmlDocument.setAsynchronousLoadPriority(-1);
                        panelKarte.editor_ergebnis.setDocument(htmlDocument);

                        panelKarte.editor_ergebnis.setText(html_);
                        panelKarte.editor_ergebnis.setCaretPosition(0);
//                        System.out.println("HTML: " + html.toString());

                        panelKarte.editor_ergebnis.addHyperlinkListener(new HyperlinkListener() {
                            public void hyperlinkUpdate(HyperlinkEvent e) {
                                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                                    String s = e.getDescription();
                                    int index = Integer.valueOf(s);
                                    SearchResult result = results.get(index);
//                                    System.out.println("Lon: " + result.getLon());
//                                    System.out.println("Lat: " + result.getLat());
//                                    System.out.println("Zoom: " + result.getZoom());

                                    if(result.getZoom() > map.getTileServer().getMaxZoom())
                                        result.setZoom(map.getTileServer().getMaxZoom());
                                                                        
                                    map.setZoom(result.getZoom() < 1 || result.getZoom() > map.getTileServer().getMaxZoom() ? 8 : result.getZoom());
                                    Point position = map.computePosition(new Point2D.Double(result.getLon(), result.getLat()));           

                                    map.setCenterPosition(position);
                                    map.repaint();
                                }
                            }
                        });


                        panelKarte.editor_ergebnis.revalidate();
                        
                    } finally {
                        if(results.get(0) != null) {
//                            System.out.println("Lon: " + results.get(0).getLon());
//                            System.out.println("Lat: " + results.get(0).getLat());
//                            System.out.println("Zoom: " + results.get(0).getZoom());
                            if(results.get(0).getZoom() > map.getTileServer().getMaxZoom())
                                        results.get(0).setZoom(map.getTileServer().getMaxZoom());
                            
                            map.setZoom(results.get(0).getZoom() < 1 || results.get(0).getZoom() > map.getTileServer().getMaxZoom() ? 8 : results.get(0).getZoom());                            
                                                       
                            Point position = map.computePosition(new Point2D.Double(results.get(0).getLon(), results.get(0).getLat()));                         
                            //System.out.println("zoom: " + map.getZoom());
                            
                            map.setCenterPosition(position);
                            map.repaint();
                        }

                        searching = false;
                        panelKarte.btn_search.setEnabled(true);
                        panelKarte.btn_search.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        panelKarte.field_cardsearch.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        map.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            };
            SwingUtilities.invokeLater(r);
        }

    /**
     *
     */

    public static final class SearchResult {
        private String type;
        private double lat, lon;
        private double[] boundingbox;
        
        private String name;
        private String category;
        private String info;
        private int zoom;
        private String description = "";

        public SearchResult() {
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public double getLat() {
            return lat;
        }
        public void setLat(double lat) {
            this.lat = lat;
        }
        public double getLon() {
            return lon;
        }
        public void setLon(double lon) {
            this.lon = lon;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getCategory() {
            return category;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        public String getInfo() {
            return info;
        }
        public void setInfo(String info) {
            this.info = info;
        }
        public int getZoom() {
            return zoom;
        }
        public void setZoom(int zoom) {
            this.zoom = zoom;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }

        public double[] getBoundingbox() {
            return boundingbox;
        }

        public void setBoundingbox(double[] boundingbox) {
            this.boundingbox = boundingbox;
        }        
        
        public String toString() {
            return "SearchResult [category=" + category + ", info=" + info + ", lat=" + lat + ", lon=" + lon
                    + ", name=" + name + ", type=" + type + ", zoom=" + zoom + ", description=" + description + "]";
        }

    }
    
    public static final class BoundingBox {
        public int minX;
            public int minY;
            public int maxX;
            public int maxY;

            public static final int WORLD_MIN_X = (int) (-20000f * (1 << 16));;
            public static final int WORLD_MIN_Y = (int) (-20000f * (1 << 16));;
            public static final int WORLD_MAX_X = (int) (20000f * (1 << 16));;
            public static final int WORLD_MAX_Y = (int) (20000f * (1 << 16));;

            public BoundingBox() {

                this .minX = 0;
                this .minY = 0;
                this .maxX = 0;
                this .maxY = 0;
            }

            public BoundingBox(BoundingBox bbox) {

                this .minX = bbox.minX;
                this .minY = bbox.minY;
                this .maxX = bbox.maxX;
                this .maxY = bbox.maxY;
            }

            public BoundingBox(int minX, int maxX, int minY, int maxY) {

                this .minX = minX;
                this .minY = minY;
                this .maxX = maxX;
                this .maxY = maxY;
            }

            public boolean overlaps(BoundingBox box) {

                return overlaps(box.minX, box.minY, box.maxX, box.maxY);
            }

            public boolean overlaps(int minX, int minY, int maxX, int maxY) {

                if (maxX <= this .minX || minX >= this .maxX)
                    return false;

                if (maxY <= this .minY || minY >= this .maxY)
                    return false;

                return true;
            }

            public boolean isIncludedIn(int x1, int y1, int x2, int y2) {

                if (this .minX < x1 || this .maxX > x2)
                    return false;

                if (this .minY < y1 || this .maxY > y2)
                    return false;

                return true;
            }

            public boolean equals(Object object) {

                if (object instanceof  BoundingBox) {
                    BoundingBox bbox = (BoundingBox) object;
                    return (this .minX == bbox.minX) && (this .maxX == bbox.maxX)
                            && (this .minY == bbox.minY)
                            && (this .maxY == bbox.maxY);
                }
                return false;
            }
    }
    

}
