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

package de.maklerpoint.office.Gui.Painters;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class SimplePainters {

    public static Painter getGradientBWPainter() {
          int width = 100;
          int height = 100;
          Color color1 = Color.white;
          Color color2 = Color.gray;

          LinearGradientPaint gradientPaint =
              new LinearGradientPaint(0.0f, 0.0f, width, height,
                                      new float[]{0.0f, 1.0f},
                                      new Color[]{color1, color2});
          MattePainter mattePainter = new MattePainter(gradientPaint);
          return mattePainter;
    }

}
