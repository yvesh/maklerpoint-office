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
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class Karte {

    /**
     * 
     * @param lon
     * @param lat
     * @return
     */

    public static MapPanel getMap(double lon, double lat, int zoom) {
        MapPanel mapPanel = new MapPanel();
        mapPanel.setZoom(zoom);
        mapPanel.getOverlayPanel().setVisible(false);
        
        Point position = mapPanel.computePosition(new Point2D.Double(lon, lat));
        mapPanel.setCenterPosition(position);
        mapPanel.repaint();

        return mapPanel;
    }

    /**
     * 
     * @param search
     */


    public static void searchMap(String search, MapPanel map) {
        
    }
}
