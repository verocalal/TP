package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private Controller c;
	private List<Event> eventos;
	private String[] columnas = {"Tiempo", "Descripcion"};
	
	public EventsTableModel(Controller ctlr)
	{
		c=ctlr;
		eventos = new ArrayList<Event>(c.getTrafficSimulator().getListaEventos());
		ctlr.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return eventos.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex>=eventos.size())
			return null;
		else
		{
			Event e = eventos.get(rowIndex);
			if(columnIndex==0)
				{
					return e.getTime();
				}
			else if(columnIndex==1)
				return e.toString();
			else
				return null;
		}
	}
	public void add(Event e)
	{
		eventos.add(e);
		this.fireTableDataChanged();
	}
	
	public String getColumnName(int col) {
		return columnas[col];
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.fireTableDataChanged();

	}
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> evento, int time) {
		this.eventos = new ArrayList<Event>(evento);
		this.fireTableDataChanged();
		
	}
	@Override
	public void onEventAdded(RoadMap map, List<Event> evento, Event e, int time) {
		add(e);
		this.fireTableDataChanged();

	}
	@Override
	public void onReset(RoadMap map, List<Event> evento, int time) {
		this.eventos.removeAll(eventos);
		this.fireTableDataChanged();

	}
	@Override
	public void onRegister(RoadMap map, List<Event> evento, int time) {
		// TODO Auto-generated method stub
		this.fireTableDataChanged();

	}
	@Override
	public void onError(String err) {
		System.out.println(err);

	}

}
