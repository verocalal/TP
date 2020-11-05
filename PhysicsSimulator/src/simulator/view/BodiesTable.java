package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	
	private BodiesTableModel bodies_model;
	
	   BodiesTable(Controller ctrl) {
	      setLayout(new BorderLayout());
	      setBorder(BorderFactory.createTitledBorder(
	          BorderFactory.createLineBorder(Color.black, 2),
	          "Bodies",
	          TitledBorder.LEFT, TitledBorder.TOP));
	      this.bodies_model = new BodiesTableModel(ctrl);
	      JTable table = new JTable(this.bodies_model);
	      JScrollPane pane = new JScrollPane(table);
	      this.add(pane);
	      this.setPreferredSize(new Dimension(10, 10));
	   }
}