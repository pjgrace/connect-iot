/////////////////////////////////////////////////////////////////////////
//
// © University of Southampton IT Innovation Centre, 2015
//
// Copyright in this library belongs to the University of Southampton
// University Road, Highfield, Southampton, UK, SO17 1BJ
//
// This software may not be used, sold, licensed, transferred, copied
// or reproduced in whole or in part in any manner or form or in or
// on any media by any person other than in accordance with the terms
// of the Licence Agreement supplied with the software, or otherwise
// without the prior written consent of the copyright owners.
//
// This software is distributed WITHOUT ANY WARRANTY, without even the
// implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
// PURPOSE, except where stated in the Licence Agreement supplied with
// the software.
//
// Created By : Paul Grace
// Created for Project : XIFI (http://www.fi-xifi.eu)
//
/////////////////////////////////////////////////////////////////////////
//
//  License : GNU Lesser General Public License, version 3
//
/////////////////////////////////////////////////////////////////////////

package uk.ac.soton.itinnovation.xifiinteroperability.guitool.data.tables;

import uk.ac.soton.itinnovation.xifiinteroperability.guitool.data.XMLReader;


/**
 * A REST Interface is an identifier and the url description.
 * @author pjg
 */
public class InterfaceData {

    /**
     * Unique label to identify the REST interface within the GUI and the pattern
     * e.g.
     */
    private transient String restID;

    /**
     * Getter for the ID of the REST interface.
     * @return The id of the REST Interface.
     */
    public final String getRestID() {
        return restID;
    }

    /**
     * Set the name field to identify the Rest interface.
     * @param restidentify The new id of the rest interface.
     */
    public final void setRestID(final String restidentify) {
        restID = restidentify;
    }

    /**
     * The full URL of the rest interface in String form.
     */
    private transient String url;


    /**
     * Getter for the URL of the REST interface.
     * @return The URL of the REST Interface.
     */
    public final String getRestURL() {
        return url;
    }

    /**
     * Set the url field for the Rest interface.
     * @param resturl The new id of the rest interface.
     */
    public final void setRestURL(final String resturl) {
        url = resturl;
    }


    /**
     * Create a new interface data element.
     * @param ident The named identifier of the REST interface.
     * @param addr The full url string of the interface.
     */
    public InterfaceData(final String ident, final String addr) {
        this.restID = ident;
        this.url = addr;
    }

    /**
    * Generate the XML content for this data.
    *
    * @return The XML pattern string for this data object.
    */
    public final String generateTransitionXML() {
        final StringBuilder strBuild = new StringBuilder();
        strBuild.append("\n<ID>").append(this.restID).append("</ID>").append("<URL>").append(this.url).append("</URL>");
        return strBuild.toString();
    }

    /**
     * Transform an xml data into a new Interface Data object.
     * @param xml The xml string with the interface data
     * @return a new InterfaceData object
     * @throws InvalidXMLInputException error in the XML input.
     */
    public static InterfaceData readXML(final String xml) throws
            InvalidXMLInputException {

        try {
            return new InterfaceData(
                    XMLReader.readXPATHValue(xml, "//ID"),
                    XMLReader.readXPATHValue(xml, "//URL"));
        } catch (Exception ex) {
            throw new InvalidXMLInputException("InterfaceData XML must be <ID>id</ID><URL>url</URL>", ex);
        }

    }

}
