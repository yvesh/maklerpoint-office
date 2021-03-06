/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.maklerpoint.office.Konstanten;

/**
 *
 * @author yves
 */
public class Produkte {

    public static final int UMSATZSTEUER_FREI = 0;
    public static final int UMSATZSTEUER_ANTEILIG = 1;
    public static final int UMSATZSTEUER_VOLL = 2;

    public static final int VORSTEUERABZUG_ANTEILIG = 0;
    public static final int VORSTEUERABZUG_VOLL = 1;
    public static final int VORSTEUERABZUG_KEIN = 2;

    public static final int HAFTUNGTYP_FIX = 0;
    public static final int HAFTUNGTYP_VARIABEL = 1;

    public static final int HAFTUNGART_VOLL = 0;
    public static final int HAFTUNGART_RATIERLICH = 1;
    public static final int HAFTUNGART_KOMBINIERT = 2;

    public static final int HAFTUNGSART_RATIERLICH_TAGGENAU = 0;
    public static final int HAFTUNGSART_RATIERLICH_INTERVALLE = 1;
    public static final int HAFTUNGSART_RATIERLICH_TABELLE = 2;

    public static final int HAFTUNGSART_KOMBINIERT_FEST = 0;
    public static final int HAFTUNGSART_KOMBINIERT_VARIABEL = 1;

    public static final int HAFTUNGSART_KOMBINIERT_RATIERLICH_GESAMTHAFTUNGSZEIT = 0;
    public static final int HAFTUNGSART_KOMBINIERT_RATIERLICH_RESTHAFTUNGSZEIT = 1;
    // Berechnung der ratierlichen Haftung über Gesamthaftungszeit oder Resthaftungszeit
}
