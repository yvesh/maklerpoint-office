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
package de.maklerpoint.office.Schnittstellen.Google;

import com.google.gdata.client.GoogleAuthTokenFactory.UserToken;
import com.google.gdata.client.GoogleService.CaptchaRequiredException;
import com.google.gdata.client.contacts.*;
import com.google.gdata.client.http.HttpGDataRequest;
import com.google.gdata.data.Link;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.*;
import com.google.gdata.data.contacts.*;
import com.google.gdata.data.extensions.Street;
import com.google.gdata.util.*;
import de.maklerpoint.office.Briefe.Tools.KundenBriefTools;
import de.maklerpoint.office.Exception.ShowException;
import de.maklerpoint.office.Gui.Exception.ExceptionDialogGui;
import de.maklerpoint.office.Kunden.FirmenObj;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import de.maklerpoint.office.Kunden.KundenObj;
import de.maklerpoint.office.Kunden.Tools.KundenMailHelper;
import de.maklerpoint.office.Logging.Log;
import de.maklerpoint.office.System.Configuration.Config;
import de.maklerpoint.office.System.Version;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Yves Hoppe <hoppe at maklerpoint.de>
 */
public class GoogleContacts {

    private static ContactsService myService;
    private static URL feedUrl;
    private static final String DEFAULT_PROJECTION = "full";
    private static SimpleDateFormat dfparse = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat dfchange = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Reference to the logger for setting verbose mode.
     */
    private static final Logger httpRequestLogger =
            Logger.getLogger(HttpGDataRequest.class.getName());

    public static ContactEntry commitPrivatKunde(KundenObj kunde) throws AuthenticationException,
            MalformedURLException, IOException, ServiceException, java.text.ParseException {

//        printAllContacts(myService);

        Log.logger.info("Übertrage Kunde " + kunde + " zu Ihren Google Kontakten");

        ContactEntry contact = new ContactEntry();

        Name name = new Name();

        final String NO_YOMI = null;

        String fullname = kunde.getVorname() + " " + kunde.getNachname();
        if (kunde.getVorname2() != null && kunde.getVorname().length() > 0) {
            fullname = kunde.getVorname() + " " + kunde.getVorname2() + " " + kunde.getNachname();
        }

        name.setFullName(new FullName(fullname, NO_YOMI));
        name.setGivenName(new GivenName(kunde.getVorname(), NO_YOMI));
        name.setFamilyName(new FamilyName(kunde.getNachname(), NO_YOMI));

        contact.setName(name);

        Email primaryMail = new Email();
        primaryMail.setAddress(KundenMailHelper.getKundenMail(kunde));
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);

//        Organization org = new Organization();
//        org.setOrgName(new OrgName(kunde.getFirma(), NO_YOMI));
//        contact.addOrganization(org);

        StructuredPostalAddress pa = new StructuredPostalAddress();
        pa.setCity(new City(kunde.getStadt()));
        pa.setCountry(new Country("DE", kunde.getLand())); // DE 
        pa.setPostcode(new PostCode(kunde.getPlz()));
        pa.setStreet(new Street(kunde.getStreet()));
        pa.setRegion(new Region(kunde.getBundesland()));
        pa.setRel(StructuredPostalAddress.Rel.HOME);
        pa.setPrimary(true);
        contact.addStructuredPostalAddress(pa);

        if (kunde.getTitel() != null && kunde.getTitel().length() > 1) {
            contact.setTitle(TextConstruct.plainText(kunde.getTitel()));
        }

        PhoneNumber tel = new PhoneNumber();
        tel.setPhoneNumber(kunde.getCommunication1());
        tel.setPrimary(true);
        tel.setRel(PhoneNumber.Rel.HOME);
        contact.addPhoneNumber(tel);

        Nickname nick = new Nickname();
        nick.setValue(kunde.getKundenNr());
        contact.setNickname(nick);

        if (kunde.getGeburtsdatum() != null && kunde.getGeburtsdatum().length() > 0) {
            Date geb = dfparse.parse(kunde.getGeburtsdatum());
            Birthday bd = new Birthday();
            bd.setValue(dfchange.format(geb));  // --MM-dd oder yyyy-mm-ddd
            contact.setBirthday(bd);
        }


        ExtendedProperty kdnr = new ExtendedProperty();
        kdnr.setName("Kunden-Nr.");
        kdnr.setValue(kunde.getKundenNr());
        contact.addExtendedProperty(kdnr);

        return myService.insert(feedUrl, contact);
    }
    
    
    public static ContactEntry commitFirmenKunde(FirmenObj kunde) throws AuthenticationException,
            MalformedURLException, IOException, ServiceException, java.text.ParseException {

//        printAllContacts(myService);

        Log.logger.info("Übertrage Kunde " + kunde + " zu Ihren Google Kontakten");

        ContactEntry contact = new ContactEntry();

        Name name = new Name();

        final String NO_YOMI = null;

        String fullname = kunde.getFirmenName();
        if (kunde.getFirmenNameZusatz() != null && kunde.getFirmenNameZusatz().length() > 0) {
            fullname = kunde.getFirmenName() + " - " + kunde.getFirmenNameZusatz();
        }

        name.setFullName(new FullName(fullname, NO_YOMI));
        contact.setName(name);

        Email primaryMail = new Email();
        primaryMail.setAddress(KundenMailHelper.getGeschKundenMail(kunde));
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);

//        Organization org = new Organization();
//        org.setOrgName(new OrgName(kunde.getFirma(), NO_YOMI));
//        contact.addOrganization(org);

        StructuredPostalAddress pa = new StructuredPostalAddress();
        pa.setCity(new City(kunde.getFirmenStadt()));
        pa.setCountry(new Country("DE", kunde.getFirmenLand())); // DE 
        pa.setPostcode(new PostCode(kunde.getFirmenPLZ()));
        pa.setStreet(new Street(kunde.getFirmenStrasse()));
        pa.setRegion(new Region(kunde.getFirmenBundesland()));
        pa.setRel(StructuredPostalAddress.Rel.HOME);
        pa.setPrimary(true);
        contact.addStructuredPostalAddress(pa);

        PhoneNumber tel = new PhoneNumber();
        tel.setPhoneNumber(kunde.getCommunication1());
        tel.setPrimary(true);
        tel.setRel(PhoneNumber.Rel.HOME);
        contact.addPhoneNumber(tel);

        Nickname nick = new Nickname();
        nick.setValue(kunde.getKundenNr());
        contact.setNickname(nick);

        if (kunde.getFirmenGruendungDatum() != null) {
            //Date geb = dfparse.parse();
            Birthday bd = new Birthday();
            bd.setValue(dfchange.format(kunde.getFirmenGruendungDatum()));  // --MM-dd oder yyyy-mm-ddd
            contact.setBirthday(bd);
        }


        ExtendedProperty kdnr = new ExtendedProperty();
        kdnr.setName("Kunden-Nr.");
        kdnr.setValue(kunde.getKundenNr());
        contact.addExtendedProperty(kdnr);

        return myService.insert(feedUrl, contact);
    }

    public static void transmitKunden(final Object[] kunden) throws
            MalformedURLException, AuthenticationException {
        if (Config.get("userGoogle", null) == null || Config.get("passwordGoogle", null) == null) {
            JOptionPane.showMessageDialog(null, "Vor dem Export müssen Sie Ihre "
                    + "Google Benutzerdaten in den Einstellungen angeben.");
            return;
        }

        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {

                String username = Config.get("userGoogle", null);
                String password = Config.get("passGoogle", null);

                feedUrl = new URL("https://www.google.com/m8/feeds/contacts/" + username
                        + "/" + DEFAULT_PROJECTION);

                myService = new ContactsService("MaklerPoint-Office-" + Version.build);

                if (Config.get("tokenGoogle", null) == null) {
                    try {
                        myService.setUserCredentials(username, password);
                    } catch (CaptchaRequiredException e) {
                        Log.logger.warn("Google Catpcha erforderlich", e);
                        String captcha = JOptionPane.showInputDialog(null, "Bitte besuchen Sie die folgende Adresse "
                                + e.getCaptchaUrl() + " und geben Sie das Captcha in das untere Feld ein");

                        myService.setUserCredentials(username, password, e.getCaptchaToken(), captcha);
                    } catch (AuthenticationException e) {
                        Log.logger.fatal("Die Authorisierung mit den Google Servern ist fehlgeschlagen.", e);
                    }

                    UserToken auth_token = (UserToken) myService.getAuthTokenFactory().getAuthToken();
                    Config.set("tokenGoogle", auth_token.getValue());
                } else {
                    System.out.println("Using google token");
                    myService.setUserToken(Config.get("tokenGoogle", null));
                }

                httpRequestLogger.setLevel(Level.WARNING);
                ConsoleHandler handler = new ConsoleHandler();
                handler.setLevel(Level.WARNING);
                httpRequestLogger.addHandler(handler);
                httpRequestLogger.setUseParentHandlers(false);
                if (Log.logger.isDebugEnabled()) {
                    Log.logger.debug("Anzahl an Kunden für den Google Export: " + kunden.length);
                }

                for (int i = 0; i < kunden.length; i++) {
                    try {

                        if(kunden[i].getClass().equals(KundenObj.class)) {
                            KundenObj knd = (KundenObj) kunden[i];
                            ContactEntry ce = GoogleContacts.commitPrivatKunde(knd);
                            
                            if (Log.logger.isDebugEnabled()) {
                                Log.logger.debug("ID des neuen Google Kontaktes: " + ce.getId());
                            }
                        } else if (kunden[i].getClass().equals(FirmenObj.class)) {
                            
                            FirmenObj knd = (FirmenObj) kunden[i];
                            ContactEntry ce = GoogleContacts.commitFirmenKunde(knd);
                            
                            if (Log.logger.isDebugEnabled()) {
                                Log.logger.debug("ID des neuen Google Kontaktes: " + ce.getId());   
                            }
                        }
  
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.logger.fatal("Die Kunden konnte nicht zu Ihren Google Kontakten hinzugefügt werden.", e);
                        ShowException.showException("Die Kunden konnte nicht zu Ihren Google Kontakten hinzugefügt werden.",
                                ExceptionDialogGui.LEVEL_WARNING, e,
                                "Schwerwiegend: Export zu Google gescheitert");
                    }
                }

                return null;
            }
//

            protected void done() {
                try {
                    JOptionPane.showMessageDialog(null,
                            "Die ausgewählten Kunden wurden erfolgreich zu Ihren "
                            + "Google Kontakten übertragen.");
                } catch (Exception ignore) {
                }
            }
        };

        sw.execute();
    }

    public static void printAllContacts(ContactsService myService)
            throws ServiceException, IOException {
        // Request the feed
        URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        com.google.gdata.data.contacts.ContactFeed resultFeed =
                myService.getFeed(feedUrl, com.google.gdata.data.contacts.ContactFeed.class);
        // Print the results
        System.out.println(resultFeed.getTitle().getPlainText());
        for (int i = 0; i < resultFeed.getEntries().size(); i++) {
            ContactEntry entry = resultFeed.getEntries().get(i);
            if (entry.hasName()) {
                Name name = entry.getName();
                if (name.hasFullName()) {
                    String fullNameToDisplay = name.getFullName().getValue();
                    if (name.getFullName().hasYomi()) {
                        fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
                    }
                    System.out.println("\t\t" + fullNameToDisplay);
                } else {
                    System.out.println("\t\t (no full name found)");
                }
                if (name.hasNamePrefix()) {
                    System.out.println("\t\t" + name.getNamePrefix().getValue());
                } else {
                    System.out.println("\t\t (no name prefix found)");
                }
                if (name.hasGivenName()) {
                    String givenNameToDisplay = name.getGivenName().getValue();
                    if (name.getGivenName().hasYomi()) {
                        givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
                    }
                    System.out.println("\t\t" + givenNameToDisplay);
                } else {
                    System.out.println("\t\t (no given name found)");
                }
                if (name.hasAdditionalName()) {
                    String additionalNameToDisplay = name.getAdditionalName().getValue();
                    if (name.getAdditionalName().hasYomi()) {
                        additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
                    }
                    System.out.println("\t\t" + additionalNameToDisplay);
                } else {
                    System.out.println("\t\t (no additional name found)");
                }
                if (name.hasFamilyName()) {
                    String familyNameToDisplay = name.getFamilyName().getValue();
                    if (name.getFamilyName().hasYomi()) {
                        familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
                    }
                    System.out.println("\t\t" + familyNameToDisplay);
                } else {
                    System.out.println("\t\t (no family name found)");
                }
                if (name.hasNameSuffix()) {
                    System.out.println("\t\t" + name.getNameSuffix().getValue());
                } else {
                    System.out.println("\t\t (no name suffix found)");
                }
            } else {
                System.out.println("\t (no name found)");
            }

            System.out.println("Email addresses:");
            for (Email email : entry.getEmailAddresses()) {
                System.out.print(" " + email.getAddress());
                if (email.getRel() != null) {
                    System.out.print(" rel:" + email.getRel());
                }
                if (email.getLabel() != null) {
                    System.out.print(" label:" + email.getLabel());
                }
                if (email.getPrimary()) {
                    System.out.print(" (primary) ");
                }
                System.out.print("\n");
            }

            System.out.println("IM addresses:");
            for (Im im : entry.getImAddresses()) {
                System.out.print(" " + im.getAddress());
                if (im.getLabel() != null) {
                    System.out.print(" label:" + im.getLabel());
                }
                if (im.getRel() != null) {
                    System.out.print(" rel:" + im.getRel());
                }
                if (im.getProtocol() != null) {
                    System.out.print(" protocol:" + im.getProtocol());
                }
                if (im.getPrimary()) {
                    System.out.print(" (primary) ");
                }
                System.out.print("\n");
            }

            System.out.println("Groups:");
            for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
                String groupHref = group.getHref();
                System.out.println("  Id: " + groupHref);
            }

            System.out.println("Extended Properties:");
            for (ExtendedProperty property : entry.getExtendedProperties()) {
                if (property.getValue() != null) {
                    System.out.println("  " + property.getName() + "(value) = "
                            + property.getValue());
                } else if (property.getXmlBlob() != null) {
                    System.out.println("  " + property.getName() + "(xmlBlob)= "
                            + property.getXmlBlob().getBlob());
                }
            }

            Link photoLink = entry.getContactPhotoLink();
            String photoLinkHref = photoLink.getHref();
            System.out.println("Photo Link: " + photoLinkHref);

            if (photoLink.getEtag() != null) {
                System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
            }

            System.out.println("Contact's ETag: " + entry.getEtag());
        }
    }
}
