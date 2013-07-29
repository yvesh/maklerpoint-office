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

package de.maklerpoint.office.Logging;

import de.maklerpoint.office.Gui.Log.LogPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.Level;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class TextAreaAppender extends WriterAppender{

       private JTextArea jTextArea = null;
       private LogPanel log = null;

       private Layout layout;


       public TextAreaAppender(Layout lay){
           this.layout = lay;
       }

        /** Set the target JTextArea for the logging information to appear.
         * @param jTextArea
         */
	void setTextArea(LogPanel log) {
                this.log = log;
		this.jTextArea = log.text_log;
	}
        /**
         * Format and then append the loggingEvent to the stored
	 * JTextArea.
         * @param loggingEvent
         */

        @Override
	public void append(LoggingEvent loggingEvent) {
		final String message = this.layout.format(loggingEvent);
                final org.apache.log4j.Level level = loggingEvent.getLevel();

		// Append formatted message to textarea using the Swing Thread.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
                            Log.completeLog.append(message);
                            
                            if(jTextArea != null)
				jTextArea.append(message);

                            int fatal = Integer.valueOf(log.label_infos.getText());
                            int warn = Integer.valueOf(log.label_warnings.getText());
                            int info = Integer.valueOf(log.label_infos.getText());

                            int err = fatal + warn;

                            if(level == Level.FATAL) {
                                Log.levellog[0]++;
                                Log.levellog[3]++;
                                log.label_fatalerrors.setText("" + Log.levellog[0]);
                                log.label_errors.setText("" + Log.levellog[3]);
                            } else if(level == Level.WARN) {
                                Log.levellog[1]++;
                                Log.levellog[3]++;
                                log.label_warnings.setText("" + Log.levellog[1]);
                                log.label_errors.setText("" + Log.levellog[3]);
                            } else if (level == Level.INFO) {
                                Log.levellog[2]++;
                                log.label_infos.setText("" + Log.levellog[2]);
                            }
			}
		});
	}

}
