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
package de.maklerpoint.office.Communication;

import de.maklerpoint.office.start.CRM;
import de.maklerpoint.office.Kontakte.KontaktObj;
import de.maklerpoint.office.Kunden.FirmenObj;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.ZusatzadressenObj;
import de.maklerpoint.office.Tools.ImageTools;
import de.maklerpoint.office.Versicherer.VersichererObj;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Yves Hoppe <info at yves-hoppe.de>
 */
public class CommunicationTypes {

    public static final int UNKNOWN = -1;
    public static final int TELEFON = 0;
    public static final int TELEFON2 = 1;
    public static final int TELEFON_BERUFLICH = 2;
    public static final int TELEFON_PRIVAT = 3;
    public static final int FAX = 4;
    public static final int FAX2 = 5;
    public static final int FAX_BERUFLICH = 6;
    public static final int MOBIL = 7;
    public static final int MOBIL2 = 8;
    public static final int MOBIL_BERUFLICH = 9;
    public static final int EMAIL = 10;
    public static final int EMAIL2 = 11;
    public static final int EMAIL_BERUFLICH = 12;
    public static final int DURCHWAHL = 13;
    public static final int WEBSEITE = 14;
    public static final int WEBSEITE2 = 15;
    public static final Integer[] COMMUNICATION_INTARRAY = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    public static final String[] COMMUNICATIONTYPES = new String[]{"Telefon", "Telefon 2",
        "Telefon beruflich", "Telefon privat", "Fax", "Fax 2", "Fax beruflich", "Mobil",
        "Mobil 2", "Mobil beruflich", "E-Mail", "E-Mail 2", "E-Mail beruflich", "Durchwahl",
        "Webseite", "Webseite2"
    };
    public static final ImageIcon[] COMMUNCATION_IMAGES = new ImageIcon[]{
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone-fax.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone-fax.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone-fax.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/mobil.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/mobil.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/mobil.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/email.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/email.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/email.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/telephone.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/homepage.png"),
        ImageTools.createImageIcon("de/acyrance/CRM/Gui/resources/icon_clean/homepage.png"),};
    
    /**
     * 
     */
    
    public static JLabel getCommunicationLabel(KundenObj pk, int nr) {
        JLabel com = new JLabel();

        if (nr == 1) {
            if (pk.getCommunication1() != null
                    && pk.getCommunication1().length() > 0) {
                com.setText(pk.getCommunication1());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication1Type()]);
            }
        } else if (nr == 2) {
            if (pk.getCommunication2() != null
                    && pk.getCommunication2().length() > 0) {
                com.setText(pk.getCommunication2());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication2Type()]);
            }
        } else if (nr == 3) {
            if (pk.getCommunication3() != null
                    && pk.getCommunication3().length() > 0) {
                com.setText(pk.getCommunication3());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication3Type()]);
            }
        } else if (nr == 4) {
            if (pk.getCommunication4() != null
                    && pk.getCommunication4().length() > 0) {
                com.setText(pk.getCommunication4());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication4Type()]);
            }
        } else if (nr == 5) {
            if (pk.getCommunication5() != null
                    && pk.getCommunication5().length() > 0) {
                com.setText(pk.getCommunication5());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication5Type()]);
            }
        } else if (nr == 6) {
            if (pk.getCommunication6() != null
                    && pk.getCommunication6().length() > 0) {
                com.setText(pk.getCommunication6());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication6Type()]);
            }
        }   

        return com;
    }
    
    /**
     * 
     * @param pk
     * @param nr
     * @return JLabel mit Nr und ICon
     */
    public static JLabel getCommunicationLabel(KontaktObj pk, int nr) {
        JLabel com = new JLabel();

        if (nr == 1) {
            if (pk.getCommunication1() != null
                    && pk.getCommunication1().length() > 0) {
                com.setText(pk.getCommunication1());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication1Type()]);
            }
        } else if (nr == 2) {
            if (pk.getCommunication2() != null
                    && pk.getCommunication2().length() > 0) {
                com.setText(pk.getCommunication2());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication2Type()]);
            }
        } else if (nr == 3) {
            if (pk.getCommunication3() != null
                    && pk.getCommunication3().length() > 0) {
                com.setText(pk.getCommunication3());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication3Type()]);
            }
        } else if (nr == 4) {
            if (pk.getCommunication4() != null
                    && pk.getCommunication4().length() > 0) {
                com.setText(pk.getCommunication4());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication4Type()]);
            }
        } else if (nr == 5) {
            if (pk.getCommunication5() != null
                    && pk.getCommunication5().length() > 0) {
                com.setText(pk.getCommunication5());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication5Type()]);
            }
        } else if (nr == 6) {
            if (pk.getCommunication6() != null
                    && pk.getCommunication6().length() > 0) {
                com.setText(pk.getCommunication6());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication6Type()]);
            }
        }   

        return com;
    }
    
    /**
     * 
     * @param pk
     * @param nr
     * @return Label mit Icon
     */
    public static JLabel getCommunicationLabel(ZusatzadressenObj pk, int nr) {
        JLabel com = new JLabel();

        if (nr == 1) {
            if (pk.getCommunication1() != null
                    && pk.getCommunication1().length() > 0) {
                com.setText(pk.getCommunication1());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication1Type()]);
            }
        } else if (nr == 2) {
            if (pk.getCommunication2() != null
                    && pk.getCommunication2().length() > 0) {
                com.setText(pk.getCommunication2());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication2Type()]);
            }
        } else if (nr == 3) {
            if (pk.getCommunication3() != null
                    && pk.getCommunication3().length() > 0) {
                com.setText(pk.getCommunication3());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication3Type()]);
            }
        } else if (nr == 4) {
            if (pk.getCommunication4() != null
                    && pk.getCommunication4().length() > 0) {
                com.setText(pk.getCommunication4());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication4Type()]);
            }
        } else if (nr == 5) {
            if (pk.getCommunication5() != null
                    && pk.getCommunication5().length() > 0) {
                com.setText(pk.getCommunication5());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication5Type()]);
            }
        } else if (nr == 6) {
            if (pk.getCommunication6() != null
                    && pk.getCommunication6().length() > 0) {
                com.setText(pk.getCommunication6());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication6Type()]);
            }
        }   

        return com;
    }
    
    
    public static JLabel getCommunicationLabel(FirmenObj pk, int nr) {
        JLabel com = new JLabel();

        if (nr == 1) {
            if (pk.getCommunication1() != null
                    && pk.getCommunication1().length() > 0) {
                com.setText(pk.getCommunication1());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication1Type()]);
            }
        } else if (nr == 2) {
            if (pk.getCommunication2() != null
                    && pk.getCommunication2().length() > 0) {
                com.setText(pk.getCommunication2());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication2Type()]);
            }
        } else if (nr == 3) {
            if (pk.getCommunication3() != null
                    && pk.getCommunication3().length() > 0) {
                com.setText(pk.getCommunication3());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication3Type()]);
            }
        } else if (nr == 4) {
            if (pk.getCommunication4() != null
                    && pk.getCommunication4().length() > 0) {
                com.setText(pk.getCommunication4());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication4Type()]);
            }
        } else if (nr == 5) {
            if (pk.getCommunication5() != null
                    && pk.getCommunication5().length() > 0) {
                com.setText(pk.getCommunication5());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication5Type()]);
            }
        } else if (nr == 6) {
            if (pk.getCommunication6() != null
                    && pk.getCommunication6().length() > 0) {
                com.setText(pk.getCommunication6());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication6Type()]);
            }
        }   

        return com;
    }
    
    public static JLabel getCommunicationLabel(VersichererObj pk, int nr) {
        JLabel com = new JLabel();

        if (nr == 1) {
            if (pk.getCommunication1() != null
                    && pk.getCommunication1().length() > 0) {
                com.setText(pk.getCommunication1());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication1Type()]);
            }
        } else if (nr == 2) {
            if (pk.getCommunication2() != null
                    && pk.getCommunication2().length() > 0) {
                com.setText(pk.getCommunication2());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication2Type()]);
            }
        } else if (nr == 3) {
            if (pk.getCommunication3() != null
                    && pk.getCommunication3().length() > 0) {
                com.setText(pk.getCommunication3());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication3Type()]);
            }
        } else if (nr == 4) {
            if (pk.getCommunication4() != null
                    && pk.getCommunication4().length() > 0) {
                com.setText(pk.getCommunication4());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication4Type()]);
            }
        } else if (nr == 5) {
            if (pk.getCommunication5() != null
                    && pk.getCommunication5().length() > 0) {
                com.setText(pk.getCommunication5());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication5Type()]);
            }
        } else if (nr == 6) {
            if (pk.getCommunication6() != null
                    && pk.getCommunication6().length() > 0) {
                com.setText(pk.getCommunication6());
                com.setIcon(CommunicationTypes.COMMUNCATION_IMAGES[pk.getCommunication6Type()]);
            }
        }   

        return com;
    }

    public static String getName(int id) {
        switch (id) {

            default:
            case UNKNOWN:
                return "Unbekannt";

            case TELEFON:
                return "Telefon";

            case TELEFON2:
                return "Telefon 2";

            case TELEFON_BERUFLICH:
                return "Telefon beruflich";

            case TELEFON_PRIVAT:
                return "Telefon privat";

            case FAX:
                return "Fax";

            case FAX2:
                return "Fax 2";

            case FAX_BERUFLICH:
                return "Fax beruflich";

            case MOBIL:
                return "Mobil";

            case MOBIL2:
                return "Mobil 2";

            case MOBIL_BERUFLICH:
                return "Mobil beruflich";

            case EMAIL:
                return "E-Mail";

            case EMAIL2:
                return "E-Mail 2";

            case EMAIL_BERUFLICH:
                return "E-Mail beruflich";

            case DURCHWAHL:
                return "Durchwahl";

            case WEBSEITE:
                return "Webseite";

            case WEBSEITE2:
                return "Webseite 2";

        }
    }
}
