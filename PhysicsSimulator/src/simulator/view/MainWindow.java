package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import simulator.control.Controller;

public class MainWindow extends JFrame {
		   // ...
		   Controller _ctrl;
		   JPanel mainPanel;
		   JToolBar toolbar;
		   JPanel boxlayout;
		   
		   public MainWindow(Controller ctrl) {
		     super("Physics Simulator");
		     _ctrl = ctrl;
		     initGUI();
		}
		   private void initGUI() {
		     mainPanel = new JPanel(new BorderLayout());
		     boxlayout = new JPanel();
		     boxlayout.setLayout(new BoxLayout(boxlayout, BoxLayout.Y_AXIS));
		     boxlayout.add(new BodiesTable(get_ctrl()));
		     boxlayout.add(new Viewer(get_ctrl()));
		     boxlayout.setPreferredSize(new Dimension(300, 600));
		     this.setLayout(new BorderLayout());
		     setContentPane(mainPanel);
		     mainPanel.add(new ControlPanel(get_ctrl()), BorderLayout.PAGE_START);		
		     mainPanel.add(new StatusBar(get_ctrl()), BorderLayout.PAGE_END);
		     mainPanel.add(boxlayout, BorderLayout.CENTER);
		     setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		     pack();
		     this.setLocationRelativeTo(null);
		     this.setVisible(true);
		     // TODO complete this method to build the GUI
		// .. 
		     }
		   // other private/protected methods
		// ... 
		public Controller get_ctrl() {
			return _ctrl;
		}
		public void set_ctrl(Controller _ctrl) {
			this._ctrl = _ctrl;
		}
}


