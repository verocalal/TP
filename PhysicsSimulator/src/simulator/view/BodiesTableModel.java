package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	// ...
	private List<Body> _bodies;
	private  String[] columnNames = {"Id",
        	"Mass",
        	"Velocity",
        	"Acceleration"};
	
	public BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return this._bodies.size();
	}
	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}
	@Override
	public String getColumnName(int column) {
	   return this.columnNames[column];
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	   Body body = this._bodies.get(rowIndex);
	   
	   if (columnIndex == 0)	return body.getId();
	   if (columnIndex == 1) 	return body.getM();
	   if (columnIndex == 2)  	return body.getVelocity();
	   if (columnIndex == 3)  	return body.getAcceleration();
	   return null;
	   
	}
		// SimulatorObserver methods
		//... 
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_bodies = bodies;
			}
			
		});
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		_bodies = bodies;
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				fireTableStructureChanged();
			}
		});
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				fireTableStructureChanged();
			}
			
		});
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		
	}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		
	}
}
