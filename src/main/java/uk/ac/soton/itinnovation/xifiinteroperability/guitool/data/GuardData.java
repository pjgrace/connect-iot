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

package uk.ac.soton.itinnovation.xifiinteroperability.guitool.data;

import uk.ac.soton.itinnovation.xifiinteroperability.guitool.data.Function.FunctionType;

/**
 * The data structure describing a single guard e.g. A must equal B
 * @author pjg
 */
public class GuardData {

    /**
     * The function type of the guard.
     */
    private transient FunctionType function;

    /**
     * The type of this guard function: equals, contains, etc.
     * @return the type of the function as enumerated type.
     */
    public final FunctionType getFuntionType() {
        return function;
    }

    /**
     * Set this to be a new type from the functiontype enumeration.
     * @param newType The new type to be set.
     */
    public final void setFunctionType(final FunctionType newType) {
        this.function = newType;
    }

    /**
     * Field name of the guard.
     */
    private transient String data;

    /**
     * Get the data value of the guard.
     * @return The data.
     */
    public final String getGuardData() {
        return data;
    }

    /**
     * Setter of the data value.
     * @param nData Data to update the field value.
     */
    public final void setGuardData(final String nData) {
        data = nData;
    }

    /**
     * required rule value.
     */
    private transient String value;

    /**
     * Get the value of the rule.
     * @return The value of the rule to compare against the data.
     */
    public final String getGuardValue() {
        return value;
    }

    /**
     * Setter of the guard rule value.
     * @param nValue The new rule value.
     */
    public final void setGuardValue(final String nValue) {
        value = nValue;
    }

    /**
     * Construct a new data object representing an inputted guard in the GUI.
     * @param fun The selected function type.
     * @param newData The Guard data expression (comparitor)
     * @param val The value to compare the expression/rule against.
     */
    public GuardData(final FunctionType fun, final String newData, final String val) {
        this.data = newData;
        this.value = val;
        this.function = fun;
    }

    /**
    * Generate the XML string to match this object's data format.
    *
    * @return The generated xml expression of this guard data.
    */
    public final String generateTransitionXML() {
        final StringBuilder strBuild = new StringBuilder();
        strBuild.append("\n\t\t\t\t\t<").append(this.function).append(">");
        strBuild.append("\n\t\t\t\t\t\t<param>").append(this.data).append("</param>");
        strBuild.append("\n\t\t\t\t\t\t<value>").append(this.value).append("</value>");
        strBuild.append("\n\t\t\t\t\t</").append(this.function).append(">");
        return strBuild.toString();
    }

}
