package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

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
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONException;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver{

	private Controller ctrl;
	private JButton load_events_file;
	private JButton change_cont_class;
	private JButton change_weather;
	private JButton RUN;
	private JButton STOP;
	private JButton exit;
	private JSpinner ticks;
	private JLabel ticks_texto;
	private JFileChooser ficheros;
	private final int max_ticks = 1000; 
	private boolean _stopped=true;
	
	public ControlPanel(Controller c)
	{
		ctrl = c;
		initGUI();
		c.addObserver(this);
	}
	
	public void initGUI() {
		JToolBar control_panel = new JToolBar();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(control_panel);
		this.load_events_file = crear_boton("resources/icons/open.png", "Load a file");
		this.change_cont_class = crear_boton("resources/icons/co2class.png", "Change CO2 class");
		this.change_weather = crear_boton("resources/icons/weather.png", "Change weather");
		this.RUN = crear_boton("resources/icons/run.png", "Start the simulation");
		this.STOP = crear_boton("resources/icons/stop.png", "Stop the simulation");
		this.ticks_texto = new JLabel();
		this.ticks_texto.setText(" Ticks:");
		SpinnerNumberModel spinner_ticks = new SpinnerNumberModel(300, 0, max_ticks, 1);
		this.ticks = new JSpinner(spinner_ticks);
		this.exit = crear_boton("resources/icons/exit.png", "Close the simulator");
		
		this.load_events_file.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				openfileChooser();
			}
			
		});
		this.change_cont_class.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent arg0) {
				try {
				new ChangeCO2ClassDialog(ctrl);
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(control_panel, "No dispones de vehiculos todavia :(","¡ERROR!",
							JOptionPane.ERROR_MESSAGE);
					
				}
			}
					
		});
		this.change_weather.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					new ChangeWeatherDialog(ctrl);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(control_panel, "No hay carreteras todavia :(","¡ERROR!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
					
		});
		this.exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
					
		});
		this.RUN.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				_stopped=false;
				enableToolBar(false);
				run_sim((int)ticks.getValue());			}				
		});
		this.STOP.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				stop();
			}
					
		});
		
		this.STOP.setEnabled(false);
		control_panel.add(this.load_events_file);
		control_panel.addSeparator();
		control_panel.add(this.change_cont_class);
		control_panel.add(this.change_weather);
		control_panel.addSeparator();
		control_panel.add(this.RUN);
		control_panel.add(this.STOP);
		control_panel.add(this.ticks_texto);
		control_panel.add(this.ticks);
		control_panel.add(Box.createHorizontalGlue());
		control_panel.add(Box.createHorizontalStrut(400));
		control_panel.addSeparator();
		control_panel.add(this.exit);
		
	}
	
	private JButton crear_boton(String path, String tooltip) {
		JButton boton = new JButton();
		boton.setIcon(new ImageIcon(path));
		boton.setToolTipText(tooltip);
		boton.setBounds(0, 0, 50, 50);
		return boton;
	}
	
	public void openfileChooser()
	{
		try 
		{
			this.ctrl.reset();
			JFileChooser choose = new JFileChooser();
			choose.setCurrentDirectory(new File("resources/examples"));
			int v = choose.showOpenDialog(this);
			if (v == JFileChooser.APPROVE_OPTION) 
			{
				File file = choose.getSelectedFile();
				InputStream is;
				is = new FileInputStream(file);
				this.ctrl.reset();
				this.ctrl.loadEvents(is);;
			}
		} 
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(this, "Error en la carga", "ERROR!",
					JOptionPane.ERROR_MESSAGE);
		}
		catch(JSONException e)
		{
			JOptionPane.showMessageDialog(this, "Ese fichero no es valido :(", "ERROR!",
					JOptionPane.ERROR_MESSAGE);
		}
	}


	public void exit() {
		Object[] opciones = {"Si", "No"};
		int n = JOptionPane.showOptionDialog(new JFrame(),
				"¿Desea salir de la aplicacion?", "EXIT", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null, opciones, opciones[1]);
		if (n == 0) { 
			System.exit(0);
		}
	}
	
	private void  run_sim(int n) 
	{ 
		if(n>0 && !_stopped)
		{
			try 
			{
				ctrl.run(1);
			} 
			catch(Exception e) 
			{ 
				e.getMessage();
				_stopped = true; 
				return; 
			} 
			SwingUtilities.invokeLater(new  Runnable() 
			{
				@Override
				public void run()
				{
					run_sim(n-1);
				}
			}); 
		} 
		else
			{ 
				_stopped= true; 
			}
				
	}
		 
		 
	private void stop() { 
		_stopped = true; 
		enableToolBar(true);
	}
		
	
	public void enableToolBar(boolean e)
	{
		this.RUN.setEnabled(e);
		this.exit.setEnabled(e);
		this.load_events_file.setEnabled(e);
		this.change_cont_class.setEnabled(e);
		this.change_weather.setEnabled(e);
		this.STOP.setEnabled((e==true)? false:true); //va al reves que el resto de botones
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.STOP.setEnabled(true);
		this.enableToolBar(false);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> evento, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> evento, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> evento, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> evento, int time) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				//ticks_texto.setText(String.valueOf(time));
			}
			
		});
	}

	@Override
	public void onError(String err) {
		System.out.println(err);

	}

}
