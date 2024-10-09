/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.view.containers;

import isf.common.request.RequestProxy;
import isf.common.utils.LogManager;
import isf.common.utils.PTMConfig;
import isf.controls.controllers.LoadManager;
import isf.common.utils.InscriptConfigLoader;
import isf.controls.utils.ProgressManager;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author IN030223
 */
public class ProgressPane extends JPanel implements ItemListener {
    private PTMConfig ptm;
    JPanel panel;
    JProgressBar progress;
    Logger log = LogManager.getLogger(isf.controls.view.containers.ProgressPane.class);
    public ProgressPane() {
        super();
        setBackground(new Color(0, 0, 0));
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(0, 0, 0));

        JComboBox cmbFiles = new JComboBox();
        JLabel label = new JLabel("Select RTI to load:");
        label.setForeground(Color.white);
        
        JLabel copyright = new JLabel("<html><body><font face='Arial' size='10pts' color='C8C0BC'>© Copyright 2001 - "
                +(new GregorianCalendar()).get(GregorianCalendar.YEAR)+" University "
                + "of Southern California. All Rights Reserved.</font></body></html>");
        copyright.setForeground(Color.white);
        add(copyright,"West");

        if ("WEB".equalsIgnoreCase(InscriptConfigLoader.getInstance().getProperty("mode"))) {

            panel.add(label);
            panel.add(cmbFiles);
            String files = fetchFileNames();
            StringTokenizer tokenizer = new StringTokenizer(files, ",");
            while (tokenizer.hasMoreTokens()) {
                cmbFiles.addItem(tokenizer.nextToken());
            }
            cmbFiles.setSelectedIndex(-1);
            cmbFiles.addItemListener(this);
            ProgressManager.registerCombo(cmbFiles);
        }
        add(panel,"Center");


    }

    public void registerBar()
    {
       progress = new JProgressBar() {

            public Dimension getPreferredSize() {
                return new Dimension(350, 20);
            }
        };
        // progress.setPreferredSize(new Dimension(400,30));
        progress.setVisible(false);
       // progress.setIndeterminate(true);
        progress.setStringPainted(true);
        progress.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        progress.setForeground(Color.cyan);
        panel.add(progress);
        LoadManager.getInstance().registerMonitor(progress);
    }

    private String fetchFileNames() {
        try {
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String serverConfig = InscriptConfigLoader.getInstance().getProperty("configUrl");
            org.w3c.dom.Document dom = builder.parse(serverConfig);
            NodeList nodes = dom.getElementsByTagName("PTMS");
            //assert(nodes.getLength() == 1);
            Node node = nodes.item(0);
            NamedNodeMap nodemap = node.getAttributes();
            String names = nodemap.getNamedItem("names").getNodeValue();
            return names;
        } catch (Exception exp) {
            return "";
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == e.SELECTED) {
            // PTMWindow ptmw = PTMFrameManager.getInstance().getPTMWindow();
            String name = (String) e.getItem();
            PTMConfig ptm = RequestProxy.getRequestDispatcher().getPTMConfig(name);
            while (ptm.getCached().equalsIgnoreCase("no")) {
                try {
                    Thread.sleep(500);
                    ptm = RequestProxy.getRequestDispatcher().getPTMConfig(name);
                } catch (Exception p) {
                    log.error(p);
                }
            }

            ProgressManager.setConfig(ptm);
        }
    }
}
