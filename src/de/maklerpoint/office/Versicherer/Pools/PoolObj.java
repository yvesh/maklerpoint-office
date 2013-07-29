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

package de.maklerpoint.office.Versicherer.Pools;

import de.maklerpoint.office.Communication.CommunicationTypes;
import de.maklerpoint.office.System.Status;

/**
 *
 * @author yves
 */
public class PoolObj {

    private int _id;

    private String _communication1;
    private String _communication2;
    private String _communication3;
    private String _communication4;
    private String _communication5;
    private String _communication6;

    private int _communication1Type = CommunicationTypes.TELEFON;
    private int _communication2Type = CommunicationTypes.TELEFON2;
    private int _communication3Type = CommunicationTypes.FAX;
    private int _communication4Type = CommunicationTypes.MOBIL;
    private int _communication5Type = CommunicationTypes.EMAIL;
    private int _communication6Type = CommunicationTypes.WEBSEITE;

    private String _comments;
    private String _custom1;
    private String _custom2;
    private String _custom3;
    private String _custom4;
    private String _custom5;

    private java.sql.Timestamp _created;
    private java.sql.Timestamp _modified;

    private int status = Status.NORMAL;


}
