package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private Controller c;
	private String[] columnas = {"ID","Green","Queues"};
	private List<Junction> cruces;
	
	public JunctionsTableModel(Controller ctlr)
	{
		super();
		c=ctlr;
		cruces = new ArrayList<Junction>(c.getTrafficSimulator().getMapaCarreteras().getJunctions().size());
		ctlr.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return cruces.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex>=cruces.size())
			return null;
		else
		{
			Junction e = cruces.get(rowIndex);
			if(columnIndex==0)
				return e.getId();
			else if(columnIndex==1)
				return e.getSemaforoVerde();
			else if(columnIndex==2)
				return e.getIncomingRoads();
			else
				return null;
		}
	}
	public String getColumnName(int col) {
		return columnas[col];
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		if(this.cruces.isEmpty())
			for(int i=0;i<map.getJunctions().size();i++)
				this.cruces.add(map.getJunctions().get(i));
		this.fireTableDataChanged();		

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> evento, int time) {
		this.fireTableDataChanged();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> evento, Event e, int time) {
		this.fireTableDataChanged();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> evento, int time) {
		this.cruces.removeAll(cruces);
		this.fireTableDataChanged();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> evento, int time) {
		this.fireTableDataChanged();
		
	}

	@Override
	public void onError(String err) {
		System.out.println(err);

	}

}
