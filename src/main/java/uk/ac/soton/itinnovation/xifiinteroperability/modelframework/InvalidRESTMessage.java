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

package uk.ac.soton.itinnovation.xifiinteroperability.modelframework;

/**
 * Exception to identify an error in the creation of a rest message object
 * from the specification.
 * @author pjg
 */
public class InvalidRESTMessage extends Exception {

    /**
     * Error creating a rest message from specification.
     * @param exceptionMessage The qualifying error message.
     */
    public InvalidRESTMessage(final String exceptionMessage) {
        super(exceptionMessage);
    }

    /**
     * Error creating a rest message from specification.
     * @param exceptionMessage The qualifying error message.
     * @param excep the stack trace.
     */
    public InvalidRESTMessage(final String exceptionMessage, final Exception excep) {
        super(exceptionMessage, excep);
    }

}
