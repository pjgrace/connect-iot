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

package uk.ac.soton.itinnovation.xifiinteroperability.guitool.editor;

import javax.swing.JOptionPane;
import uk.ac.soton.itinnovation.xifiinteroperability.architecturemodel.Architecture;
import uk.ac.soton.itinnovation.xifiinteroperability.modelframework.InteroperabilityReport;
import uk.ac.soton.itinnovation.xifiinteroperability.modelframework.statemachine.InvalidStateMachineException;

/**
 * Execute a thread that runs interoperability tests and then reports it
 * to the UI via asynchronous reporting.
 *
 * @author pjg
 */
public class PatternCheckThread extends Thread {

    /**
     * The pattern in XML to run the test with.
     */
    private final transient String patternToTest;

    /**
     * The reporting output.
     */
    private final transient InteroperabilityReport report;

    /**
     * The editor context to report errors.
     */
    private final transient BasicGraphEditor editor;

    /**
     * Construct a thread to perform a pattern test procedure.
     * @param xmlInput The pattern specification to formulate the test.
     * @param rep THe reference to the output stream.
     * @param edit The editor context.
     */
    public PatternCheckThread(final String xmlInput, final InteroperabilityReport rep,
            final BasicGraphEditor edit) {
        super();
        patternToTest = xmlInput;
        report = rep;
        editor = edit;
    }

    /**
     * The thread executable. Create a testing framework - Report the configuration
     * and then run the tests.
     */
    @Override
    public final void run() {
        try {
            final Architecture arch = new Architecture(patternToTest, report);
            if (arch.getStateMachine().getStartState() == null) {
                JOptionPane.showMessageDialog(editor,
                                "Pattern is not valid:",
                                "Pattern verification",
                                JOptionPane.ERROR_MESSAGE);
            } else  {
                arch.executePattern(null);
                arch.cleanup();
            }
        } catch (InvalidStateMachineException ex) {
             JOptionPane.showMessageDialog(editor,
                                "Pattern is not valid: " + ex.getMessage(),
                                "Pattern verification",
                                JOptionPane.ERROR_MESSAGE);
        }
    }

}
