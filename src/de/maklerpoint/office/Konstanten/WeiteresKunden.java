/*
 * g5c Project
 *
 * Copyright (C) 2010 g5c. All Rights Reserved.
 * http://www.g5c.org
 *
 * This file is part of org.g5c
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See File LICENSE. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package de.maklerpoint.office.Konstanten;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class WeiteresKunden {

    public static final String[] FAMILIENSTAND = {
           "Unbekannt", "Ledig / alleinstehend", "Verheiratet",
           "Verwitwet", "Geschieden", "Getrennt lebend", "Eheähnliche Gemeinschaft", "Sonstiges"
    };

    public static final String[] ROLLEHAUSHALT = {
           "Unbekannt", "Ehepartner", "Elternteil", 
           "Enkel", "Freund(in)", "Geschwister", "Großeltern", 
           "Kind", "Nichte/Neffe", "Cousin(e)", "Onkel/Tante",
           "Verschwägert"
    };
    
    public static final String[] KV_TYP = {
           "Unbekannt", "Gesetzlich (Pflicht)", "Gesetzlich (freiwillig)", 
           "Privat", "Gesetzlich (Familie)", "Landwirtschaftlich", "Nicht versichert"
    };
    
    public static final int KV_UNBEKANNT = 0;
    public static final int KV_GESETZLICH_PFLICHT = 1;
    public static final int KV_GESETZLICH_FREIWELLIG = 2;
    public static final int KV_PRIVAT = 3;
    public static final int KV_GESETZLICH_FAMILIE = 4;
    public static final int KV_LANDWIRTSCHAFTLICH = 5;
    public static final int KV_NICHT = 6;

}
