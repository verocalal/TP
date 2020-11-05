package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	
	private Controller ctlr;
	public MainWindow(Controller c) 
	{
		super("Traffic simulator"); 
		ctlr= c;
		initGUI(); 
	} 
	 
	
	private void initGUI()
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		mainPanel.add(new ControlPanel(ctlr), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(ctlr),BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1,2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel); 
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(ctlr)),"Events");
		eventsView.setPreferredSize(new Dimension(500,200));
		tablesPanel.add(eventsView);
		
		JPanel CarView = createViewPanel(new JTable(new VehiclesTableModel(ctlr)),"Vehicles");
		eventsView.setPreferredSize(new Dimension(500,200));
		tablesPanel.add(CarView);
		
		JPanel RoadView = createViewPanel(new JTable(new RoadsTableModel(ctlr)),"Roads");
		eventsView.setPreferredSize(new Dimension(500,200));
		tablesPanel.add(RoadView);
		
		JPanel JunctionView = createViewPanel(new JTable(new JunctionsTableModel(ctlr)),"Junctions");
		eventsView.setPreferredSize(new Dimension(500,200));
		tablesPanel.add(JunctionView);
		
		JPanel mapView = createViewPanel(new MapComponent(ctlr),"Map");
		mapView.setPreferredSize(new Dimension(500,400));
		mapsPanel.add(mapView);
		
		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(ctlr),"MapByRoad");
		mapsPanel.add(mapByRoadView);
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true); 
		
	}
	
	private JPanel createViewPanel(JComponent c, String title)
	{
		JPanel p = new JPanel(new BorderLayout());
		//TODO add a framed border to c with title
		Border a = BorderFactory.createLineBorder(Color.black);
		TitledBorder borde = BorderFactory.createTitledBorder(a, title);
		p.setBorder(borde);
		p.add(new JScrollPane(c));
		return p; 
	}	
}
