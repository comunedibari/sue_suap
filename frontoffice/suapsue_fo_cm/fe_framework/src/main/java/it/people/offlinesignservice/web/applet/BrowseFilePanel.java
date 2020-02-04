/**
 * 
 */
package it.people.offlinesignservice.web.applet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         20/giu/2012 21:43:51
 */
public class BrowseFilePanel extends JPanel implements ActionListener {

    private JFileChooser chooser;

    public BrowseFilePanel() {

	super(new BorderLayout());
	chooser = new JFileChooser();

	// ExampleFileFilter filter = new ExampleFileFilter();
	// filter.addExtension("jpg");
	// filter.addExtension("gif");
	// filter.setDescription("JPG & GIF Images");
	// chooser.setFileFilter(filter);

	int returnVal = chooser.showOpenDialog(super.getParent());
	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    System.out.println("You chose to open this file: "
		    + chooser.getSelectedFile().getName());
	    // chooser.gets
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub

    }

    public static void main(String args[]) {

	BrowseFilePanel browseFilePanel = new BrowseFilePanel();

    }

}
