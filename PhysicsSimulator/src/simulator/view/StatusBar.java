package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	// ...
	private JLabel _currTime;     // for current time
	private JLabel _currLaws;     // for gravity laws
	private JLabel _numOfBodies;  // for number of bodies
	StatusBar(Controller ctrl) {
	initGUI();
	ctrl.addObserver(this);
	}
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		this._currTime = new JLabel();
		this._currTime.setText("Time: ");
		this._numOfBodies = new JLabel();
		this._numOfBodies.setText("Bodies: ");
		this._currLaws = new JLabel();
		this._currLaws.setText("Laws: ");
		this.add(this._currTime);
		this.add(Box.createHorizontalStrut(100));
		this.add(this._numOfBodies);
		this.add(Box.createHorizontalStrut(50));
		this.add(this._currLaws);
		
	}
	// other private/protected methods
	// ...
	// SimulatorObserver methods
	//... 
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_currTime.setText("Time: " + 0.0);
				_numOfBodies.setText("Bodies: " + bodies.size());
			}
			
		});
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_currTime.setText("Time: " + time);
			}
			
		});
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {

	}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_currLaws.setText("Laws: " + gLawsDesc);
			}
			
		});

	}
}
