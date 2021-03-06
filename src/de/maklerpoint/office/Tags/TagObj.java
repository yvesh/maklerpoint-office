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

package de.maklerpoint.office.Tags;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */

public class TagObj {

    public int _id = -1;
    public String _tagColor;
    public String _fontColor;
    public String _name;

    public TagObj(){
    }

    public TagObj(String _tagColor, String _name) {
        this._tagColor = _tagColor;
        this._name = _name;
    }

     public TagObj(int id, String _tagColor, String _name, String fontColor) {
        this._tagColor = _tagColor;
        this._name = _name;
        this._id = id;
        this._fontColor = fontColor;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getTagColor() {
        return _tagColor;
    }

    public void setTagColor(String _tagColor) {
        this._tagColor = _tagColor;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getFontColor() {
        return _fontColor;
    }

    public void setFontColor(String _fontColor) {
        this._fontColor = _fontColor;
    }

    @Override
    public String toString() {
        return _name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this._tagColor != null ? this._tagColor.hashCode() : 0);
        hash = 97 * hash + (this._name != null ? this._name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TagObj other = (TagObj) obj;
        if ((this._tagColor == null) ? (other._tagColor != null) : !this._tagColor.equals(other._tagColor)) {
            return false;
        }
        if ((this._name == null) ? (other._name != null) : !this._name.equals(other._name)) {
            return false;
        }

        return true;
    }
   
}