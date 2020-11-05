package simulator.view;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	// ...
	private Controller _ctrl;
	private JButton carpeta;
	private JButton laws;
	private JButton play;
	private JButton stop;
	private JButton exit;
	private JLabel texto_steps;
	private JLabel texto_delta;
	private JSpinner steps;
	private JTextField delta;
	private JFileChooser fileChooser = new JFileChooser();
	private final double dt = 10000.0;
	private final int max_num_steps = 10000;
	private JSpinner spinner_delay;
	private final int max_delay = 1000;
	private JLabel texto_delay;
	private volatile Thread _thread;
	
	public ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	public void initGUI() {
		JToolBar control_panel = new JToolBar();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(control_panel);
		//etiqueta delay
		this.texto_delay = new JLabel();
		this.texto_delay.setText(" Delay:");
		//JSpinner delay
		SpinnerNumberModel spinner_delay_model = new SpinnerNumberModel(1, 0, max_delay, 1);
		this.spinner_delay = new JSpinner(spinner_delay_model);
		//boton cargar
		this.carpeta = crear_boton("resources/icons/open.png", "Load a file");
		//boton leyes
		this.laws = crear_boton("resources/icons/physics.png", "Load a law");
		//boton play
		this.play = crear_boton("resources/icons/run.png", "Start the simulation");
		//boton stop
		this.stop = crear_boton("resources/icons/stop.png", "Stop the simulation");
		//etiqueta steps
		this.texto_steps = new JLabel();
		this.texto_steps.setText(" Steps:");
		//JSpinner steps
		SpinnerNumberModel spinner_steps_model = new SpinnerNumberModel(max_num_steps, 0, max_num_steps, 25);
		this.steps = new JSpinner(spinner_steps_model);
		//etiqueta delta_time
		this.texto_delta = new JLabel();
		this.texto_delta.setText(" Delta-time:");
		//JTextField delta time
		this.delta = new JTextField();
		this.delta.setText(String.valueOf(dt));
		//boton apagar
		this.exit = crear_boton("resources/icons/exit.png", "Close the simulator");
		this.carpeta.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				openfileChooser();
			}
			
		});
		this.laws.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent arg0) {
				selectLaw();
			}
					
		});
		this.exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				closeOperation();
			}
					
		});
		this.play.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				playEvent();
			}
					
		});
		this.stop.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				stopEvent();
			}
					
		});
		
		this.stop.setEnabled(false);
		
		
		control_panel.add(this.carpeta);
		control_panel.addSeparator();
		control_panel.add(this.laws);
		control_panel.addSeparator();
		control_panel.add(this.play);
		control_panel.add(this.stop);
		control_panel.add(this.texto_delay);
		control_panel.add(this.spinner_delay);
		control_panel.add(this.texto_steps);
		control_panel.add(this.steps);		
		control_panel.add(this.texto_delta);
		control_panel.add(Box.createHorizontalGlue());
		control_panel.add(this.delta);
		control_panel.add(Box.createHorizontalStrut(400));
		control_panel.addSeparator();
		control_panel.add(this.exit);
		
	}
	
	//other private/protected methods
	//...

	public void openfileChooser() {
		try {
			this._ctrl.reset();
			int v = fileChooser.showOpenDialog(this);
			if (v == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				InputStream is;
				is = new FileInputStream(file);
				this._ctrl.reset();
				this._ctrl.loadBodies(is);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar el archivo", "Atencion!",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void selectLaw() {
		String option = "";
		Object[] possibilities = {"Newtons law of universal gravitation (nlug)","Falling to center gravity (ftcg)","No gravity (ng)"};
		String n = (String) JOptionPane.showInputDialog(
				this, // contenedor padre
				"Elige una opcioÌ�n:", // mensaje en la ventana 
				"Leyes de gravedad", // etiqueta de la ventana 
				JOptionPane.DEFAULT_OPTION, // icono seleccionado 
				null, // icono seleccionado por el usuario (Icon)
				possibilities, // opciones para seleccionar
				"Newtons law of universal gravitation (nlug)");
		if (n != null) {
			if (n.equals(possibilities[0])) option = "nlug";
			if (n.equals(possibilities[1])) option = "ftcg";
			if (n.equals(possibilities[2])) option = "ng";
			JSONObject info = null;
			for (JSONObject fe : this._ctrl.getGravityLawsFactory().getInfo()) {
				if (option.equals(fe.getString("type"))) {
					info = fe;
					break;
				}
			}
			this._ctrl.setGravityLaws(info);
		}
	}
	
	public void closeOperation() {
		Object[] opciones = {"Si", "No"};
		int n = JOptionPane.showOptionDialog(new JFrame(),
				"¿Quereis salir de la aplicacion?", "Piensalo", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null, opciones, opciones[1]);
		if (n == 0) { System.exit(0); }
	}
	
	public void playEvent() {
		this.play.setEnabled(false);
		this.exit.setEnabled(false);
		this.carpeta.setEnabled(false);
		this.laws.setEnabled(false);
		this.stop.setEnabled(true);
		this._ctrl.setDeltaTime((int) Double.parseDouble(this.delta.getText()));
		if (this._thread == null) {
			_thread = new Thread() {
				public void run() {
					run_sim((Integer) steps.getValue(), (long)(Integer)spinner_delay.getValue());
					_thread = null;
				}
			};
			_thread.start();
		}
	}
	
	public void stopEvent() {
		this.play.setEnabled(true);
		this.exit.setEnabled(true);
		this.carpeta.setEnabled(true);
		this.laws.setEnabled(true);
		this.stop.setEnabled(false);
		if (_thread != null) {
			_thread.interrupt();
		}
	}
	
	private JButton crear_boton(String path, String tooltip) {
		JButton boton = new JButton();
		boton.setIcon(new ImageIcon(path));
		boton.setToolTipText(tooltip);
		boton.setBounds(0, 0, 50, 50);
		return boton;
	}
	
	private void run_sim(int n, long delay) {
		while ( n>0 && !_thread.isInterrupted()) {
			// 1. execute the simulator one step, i.e., call method
			try {
				_ctrl.run(1);
				//    _ctrl.run(1) and handle exceptions if any
				// 2. sleep the current thread for �delay� milliseconds
				Thread.sleep(delay);
			}catch(Exception e) {
				this.play.setEnabled(true);
				this.exit.setEnabled(true);
				this.carpeta.setEnabled(true);
				this.laws.setEnabled(true);
				this.stop.setEnabled(false);
				return;
			}
			n--;
		}
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				delta.setText(String.valueOf(dt));
			}
			
		});
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				delta.setText(String.valueOf(dt));
			}
			
		});
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				delta.setText(String.valueOf(dt));
			}
			
		});
	}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		
	}

}
