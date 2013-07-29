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

package de.maklerpoint.office.Gui.Kalender;

import com.toedter.components.JSpinField;
import java.awt.Color;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class TimeSpinField extends JSpinField{

    private int hour;
    private int minutes;

    public TimeSpinField(){

        hour = super.value / 60;
        minutes = super.value % 60;
        
        if(hour < 10) {
            if(minutes < 10)
                super.textField.setText("0" + hour + ":" + "0" + minutes);
            else 
                super.textField.setText("0" + hour + ":" + minutes);
        } else {
            if(minutes < 10)
                super.textField.setText(hour + ":" + "0" + minutes);
            else
                super.textField.setText(hour + ":" + minutes);
        }
    }

    public String calculateValue() {
        hour = super.value / 60;
        minutes = super.value % 60;
        if(hour < 10) {
            if(minutes < 10) {
                super.textField.setText("0" + hour + ":" + "0" + minutes);
                return ("0" + hour + ":" + "0" + minutes);
            } else {
                super.textField.setText("0" + hour + ":" + minutes);
                return ("0" + hour + ":" + minutes);
            }
        } else {
            if(minutes < 10) {
                super.textField.setText(hour + ":" + "0" + minutes);
                return (hour + ":" + "0" + minutes);
            }
            else {
                super.textField.setText(hour + ":" + minutes);
                return (hour + ":" + minutes);
            }
        }
    }
    
    public String calculateValue2(int newValue) {
        hour = newValue / 60;
        minutes = newValue % 60;
        if(hour < 10) {
            if(minutes < 10) {
                super.textField.setText("0" + hour + ":" + "0" + minutes);
                return ("0" + hour + ":" + "0" + minutes);
            } else {
                super.textField.setText("0" + hour + ":" + minutes);
                return ("0" + hour + ":" + minutes);
            }
        } else {
            if(minutes < 10) {
                super.textField.setText(hour + ":" + "0" + minutes);
                return (hour + ":" + "0" + minutes);
            }
            else {
                super.textField.setText(hour + ":" + minutes);
                return (hour + ":" + minutes);
            }
        }
    }




    @Override
    protected void setValue(int newValue, boolean updateTextField, boolean firePropertyChange) {

        int oldvalue = value;
        value = newValue;

        if(updateTextField) {
            textField.setText(calculateValue2(newValue));
            textField.setForeground(Color.black);
        }

        if(firePropertyChange) {
            firePropertyChange("value", oldvalue, value);
        }
    }


    @Override
    public void setValue(int newValue) {
        setValue(newValue, true, true);
        spinner.setValue(new Integer(value));
    }


}
