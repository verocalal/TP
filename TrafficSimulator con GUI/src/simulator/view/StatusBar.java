package simulator.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver{

	private JLabel time;
	private JLabel tiempo;
	private JLabel eventadded;
	private JLabel evento;
	
	public StatusBar(Controller c)
	{
		c.addObserver(this);
		initGUI();
	}
	
	public void initGUI()
	{
		JToolBar status= new JToolBar();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//this.setLayout(new GridLayout());
		this.add(status);
		time = new JLabel();
		eventadded = new JLabel();
		tiempo = new JLabel();
		evento = new JLabel();
		time.setText("Time");
		tiempo.setText("0");
		status.addSeparator();
		eventadded.setText("Event added");
		eventadded.setVisible(true);
		evento.setText("-None-");
		evento.setVisible(true);
		status.add(time);
		status.addSeparator();
		status.add(tiempo);
		status.addSeparator();
		status.add(eventadded);
		status.addSeparator();
		status.add(evento);
		status.add(Box.createHorizontalGlue());
		status.add(Box.createHorizontalStrut(600));
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		eventadded.setVisible(false);
		evento.setVisible(false);
		tiempo.setText(Integer.toString(time));
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> evento, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> evento, Event e, int time) {
		this.evento.setText(e.toString());
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> evento, int time) {
		tiempo.setText("0");
		this.evento.setText("-None-");
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> evento, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		System.out.println(err);

	}

}
