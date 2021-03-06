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

package de.maklerpoint.office.History;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class HistoryObj {

    private int _id;
    private int _creatorId = -1;    
    private int _eventType = -1;
    
    private String _kundenKennung = "-1";    
    private int _aufgabenId = -1;
    private int _backupId = -1;
    private int _bankkontenId = -1;
    private int _benutzerId = -1;
    private int _benutzer_aclId;
    private int _beratungsprotokollId = -1;
    private int _briefeId = -1;
    private int _briefe_catId = -1;
    private int _dokumenteId = -1;
    private int _emailsId = -1;
    private int _firmenkundenId = -1;
    private int _firmen_ansprechpartnerId = -1;
    private int _kinderId = -1;
    private int _kundenId = -1;
    private int _kunden_betreuuungId = -1;
    private int _kunden_grpID = -1;
    private int _kunden_zusatzadressenId = -1;
    private int _mandantenId = -1;
    private int _messagesId = -1;
    private int _nachrichtenId = -1;
    private int _newsletterId = -1;
    private int _newsletter_subId = -1;
    private int _notizenId = -1;
    private int _produkteId = -1;
    private int _schaedenId = -1;
    private int _sessionId = -1;
    private int _spartenId = -1;
    private int _stoerfaelleId = -1;
    private int _termineId = -1;
    private int _textbausteineId = -1;
    private int _textbausteine_grpId = -1;
    private int _versichererId = -1;
    private int _versicherer_allId = -1;
    private int _vertraegeId = -1;
    private int _vertraege_grpId = -1;
    private int _waehrungenId = -1;
    private int _webconfigId = -1;
    private int _wiedervorlagenId = -1;
    private int _wissensdokumenteId = -1;
    
    private String _event_msg = null;   
    
    private java.sql.Timestamp _eventTime = new java.sql.Timestamp(System.currentTimeMillis());
    
    
}
