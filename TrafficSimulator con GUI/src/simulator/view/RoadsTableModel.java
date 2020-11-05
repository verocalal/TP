package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	private Controller c;
	private String[] columnas = {"ID","Longuitud","Weather","VelMax","VelActual","CO2Total","CO2Limit"};
	private List<Road> road;
	
	public RoadsTableModel(Controller ctlr)
	{
		c=ctlr;
		road = new ArrayList<Road>(c.getTrafficSimulator().getMapaCarreteras().getRoads().size());

		ctlr.addObserver(this);

	}
	@Override
	public int getRowCount() {
		return road.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex>=road.size())
			return null;
		else
		{
			Road e = road.get(rowIndex);
			if(columnIndex==0)
				return e.getId();
			else if(columnIndex==1)
				return e.getLonguitud();
			else if(columnIndex==2)
				return e.getCondicionesAmbientales();
			else if(columnIndex==3)
				return e.getLimiteVelocidad();
			else if(columnIndex==4)
				return e.getVelocidadMax();
			else if(columnIndex==5)
				return e.getContaminacionTotal();
			else if(columnIndex==6)
				return e.getLimiteContaminacion();
			else
				return null;
		}
	}

	public String getColumnName(int col) {
		return columnas[col];
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		if(this.road.isEmpty())
			for(int i=0;i<map.getRoads().size();i++)
				this.road.add(map.getRoads().get(i));
		this.fireTableDataChanged();		


	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> evento, int time) {
		// TODO Auto-generated method stub
		this.fireTableDataChanged();		

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> evento, Event e, int time) {
		// TODO Auto-generated method stub
		this.fireTableDataChanged();		

	}

	@Override
	public void onReset(RoadMap map, List<Event> evento, int time) {
		this.road.removeAll(road);
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
