/*
Copyright (c) 2001-2014, JGraph Ltd
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the JGraph nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL JGRAPH BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package uk.ac.soton.itinnovation.xifiinteroperability.guitool.editor.actions;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.view.mxGraph;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.xml.sax.SAXException;
import uk.ac.soton.itinnovation.xifiinteroperability.guitool.data.DataModel;
import uk.ac.soton.itinnovation.xifiinteroperability.guitool.editor.BasicGraphEditor;
import uk.ac.soton.itinnovation.xifiinteroperability.guitool.editor.EditorPopupMenu;
import uk.ac.soton.itinnovation.xifiinteroperability.guitool.editor.GUIdentifier;
import uk.ac.soton.itinnovation.xifiinteroperability.guitool.editor.MainDisplayPanel;
import uk.ac.soton.itinnovation.xifiinteroperability.guitool.editor.PatternCheckThread;
import uk.ac.soton.itinnovation.xifiinteroperability.modelframework.specification.PatternValidation;

/**
 * The set of GUI actions e.g. save, open, etc. that correspond to operations
 * selected from the UI.
 */
public final class EditorActions {

    /**
     * Dialogue string about pattern verification.
     */
    private static final String VER_DIALOGUE = "Pattern verification";

    /**
     * Utility class, therefore use a private constructor.
     */
    private EditorActions() {
        // empty implementation
    }

    /**
     * Retrieve the editor where the event originated.
     * @param actionEvent The UI event
     * @return Returns the graph for the given action event.
     */
    public static BasicGraphEditor getEditor(final ActionEvent actionEvent) {
        if (actionEvent.getSource() instanceof Component) {
            Component component = ((Component) actionEvent.getSource()).getParent();
            if (component != null
                            && (component instanceof EditorPopupMenu)) {

                return ((EditorPopupMenu) component).getEditor();
            }

            while (component != null
                            && !(component instanceof BasicGraphEditor)) {
                    component = component.getParent();
            }

            return (BasicGraphEditor) component;
        }

        return null;
    }

    /**
     * Close UI when close/exit selected.
     */
    public static class ExitAction extends AbstractAction {
        /**
         * Editor for the action.
         */
        private final transient BasicGraphEditor editor;

        /**
         * The action method constructor.
         * @param edtr The editor context.
         */
        public ExitAction(final BasicGraphEditor edtr) {
            super();
            this.editor = edtr;
        }

        /**
         * When the action is performed do the following. Close the editor.
         * @param actionEvent Event info (not used).
         */
        @Override
        public final void actionPerformed(final ActionEvent actionEvent) {
                if (editor != null) {
                        editor.exit();
                }
        }
    }

    /**
     * Perform the execution action. This is the execution of the pattern and
     * corresponding testing framework.
     */
    public static class ExecuteAction extends AbstractAction {
        /**
         * The method to start the execution of the pattern.
         * @param actionEvent The received UI event.
         */
        @Override
        public final void actionPerformed(final ActionEvent actionEvent) {
            final BasicGraphEditor editor = getEditor(actionEvent);
//            editor.getCodePanel().getTestingPanel().clearTestingPanel();
            final CardLayout cardLayout = (CardLayout) editor.getMainArea().getLayout();
            cardLayout.show(editor.getMainArea(), MainDisplayPanel.REPORTPANEL);

            try {
                final PatternCheckThread checkThread = new PatternCheckThread(editor.getDataModel().getGraphXML(),
                        editor.getCodePanel().getTestingPanel().getInteroperabilityReport(), editor);
                checkThread.start();
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(editor,
                        "Pattern is not valid: " + ex.getMessage(),
                        VER_DIALOGUE,
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Interface selection to verify the specification of the pattern.
     */
    public static class VerifyAction extends AbstractAction {
        /**
         * Method to verify if the graphical specification is valid.
         * @param actionEvent The UI action.
         */
        @Override
        public final void actionPerformed(final ActionEvent actionEvent) {
            final BasicGraphEditor editor = getEditor(actionEvent);
            try {
                final String xml = editor.getDataModel().getGraphXML();
                if (PatternValidation.validatePattern(xml)) {
                    JOptionPane.showMessageDialog(editor,
                        "Pattern is verified as correct.",
                        VER_DIALOGUE,
                        JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(editor,
                        "Pattern is not valid",
                        VER_DIALOGUE,
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (SAXException ex) {
                JOptionPane.showMessageDialog(editor,
                        "Pattern is not valid: " + ex.getMessage(),
                        VER_DIALOGUE,
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * GUI action to open up the XML panel and view it in the editor.
     */
    public static class XMLAction extends AbstractAction {

        /**
         * The editor context for action to be associated with.
         */
        private transient BasicGraphEditor editor;

        /**
         * Create instance of the XML action with the editor context.
         * @param edtr The GUI editor context for the action.
         */
        public XMLAction(final BasicGraphEditor edtr) {
            super();
            this.editor = edtr;
        }

        /**
         * XML action selected.
         * @param actionEvent The UI event selecting the XML panel to be shown.
         */
        @Override
        public final void actionPerformed(final ActionEvent actionEvent) {
            if (editor == null) {
                editor = getEditor(actionEvent);
            }
            editor.getCodePanel().getXMLPanel().displayXMLSpecification();
            final CardLayout cardLayout = (CardLayout) editor.getMainArea().getLayout();
            cardLayout.show(editor.getMainArea(), MainDisplayPanel.CODEPANEL);
        }
    }

        /**
	 * Switch to the Graph view in the user interface.
	 */
	public static class GraphAction extends AbstractAction {
            /**
            * The editor context for action to be associated with.
            */
            private transient BasicGraphEditor editor;

            /**
             * Create an instance of the graph selected action.
             * @param edtr The UI editor context for the action.
             */
            public GraphAction(final BasicGraphEditor edtr) {
                super();
                this.editor = edtr;
            }
            /**
             * The show graph action has been selected.
             * @param actionEvent The UI event of the selection.
             */
            public final void actionPerformed(final ActionEvent actionEvent) {
                if (editor == null) {
                    editor = getEditor(actionEvent);
                }
                editor.getCodePanel().getXMLPanel().displayXMLSpecification();
                final CardLayout cardLayout = (CardLayout) editor.getMainArea().getLayout();
                cardLayout.show(editor.getMainArea(), MainDisplayPanel.GRAPHPANEL);
            }
	}

        /**
	 * The delete action - to delete elements from the graph view and data
         * model.
	 */
	@SuppressWarnings("serial")
	public static class Delete extends AbstractAction {
            /**
            * The editor context for action to be associated with.
            */
            private BasicGraphEditor editor;

            /**
             * Create an instance of the delete action in this UI context.
             * @param edtr The UI editor context.
             */
            public Delete(final BasicGraphEditor edtr) {
                super();
                this.editor = edtr;
            }

            /**
             * The delete action has been selected via mouse press or keyboard
             * input.
             * @param actionEvent The UI event.
             */
            @Override
            public final void actionPerformed(final ActionEvent actionEvent) {
                if (editor == null) {
                    editor = getEditor(actionEvent);
                }
                final DataModel dModel = editor.getDataModel();

                final mxGraphComponent graphComponent = editor.getBehaviourGraph();
                final mxGraphComponent arcgraphComponent = editor.getSystemGraph();
                final mxGraph graph = graphComponent.getGraph();
                final mxGraph graph2 = arcgraphComponent.getGraph();

                Object[] selectionCells = graph.getSelectionCells();
                for (int i = 0; i < graph.getSelectionCount(); i++) {
                    dModel.deleteNode(((mxCell) selectionCells[i]).getId());
                }

                graph.removeCells(selectionCells);

                selectionCells = graph2.getSelectionCells();
                for (int i = 0; i < graph2.getSelectionCount(); i++) {
                    final String identf = ((mxCell) selectionCells[i]).getId();
                    dModel.deleteNode(GUIdentifier.setArchID(identf));
                }
                graph2.removeCells(selectionCells);

                mxGraphActions.getDeleteAction().actionPerformed(actionEvent);
            }
	}

}
