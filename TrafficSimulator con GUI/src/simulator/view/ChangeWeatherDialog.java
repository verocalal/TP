package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.multi.MultiLabelUI;

import simulator.model.Event;
import simulator.model.SetContaminationClass;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;
import simulator.model.Road;
import simulator.control.Controller;
import simulator.misc.Pair;

public class ChangeWeatherDialog extends JDialog{

	private JTextArea informacion;
	
	private JLabel vehiculo;
	private JLabel contaminacion;
	private JLabel Ticks;
	private JSpinner boxvehiculos;
	private JSpinner contlist;
	private JSpinner ticks;
	
	private JButton cancel;
	private JButton ok;
	private int maxCont = 1000;
	private String[] valores;
	private Controller _c;
	
	
	public ChangeWeatherDialog(Controller c) throws Exception
	{

		_c=c;
		if(c.getTrafficSimulator().getMapaCarreteras() == null)
			throw new Exception();
		List<Road> aux = new ArrayList<Road>(c.getTrafficSimulator().getMapaCarreteras().getRoads());
		valores = new String[aux.size()];
		for(int i=0;i<aux.size();i++)
				valores[i]= aux.get(i).getId();
		initGUI();
		
	}
	
	public void initGUI()
	{
		
		JPanel centro = new JPanel();
		JPanel abajo = new JPanel();
		JPanel arriba = new JPanel();
		
		this.setLayout(new BorderLayout(10,10));
		this.setVisible(true);
		centro.setLayout(new FlowLayout());
		abajo.setLayout(new FlowLayout());
		arriba.setLayout(new GridLayout(1,1,5,5));
		
		//informacion = new JLabel("<html>Schedule an event to change the CO2 class of a vehicle after given<p> <html>number of simulaiton ticks from now<p>");
		informacion = new JTextArea("Schedule an event to change the weather of a road after given number of simulaiton ticks from now");
		informacion.setLineWrap(true);
		informacion.setOpaque(false);
		arriba.add(informacion);
		
		this.vehiculo = new JLabel("Road");
		centro.add(vehiculo,BorderLayout.CENTER);
		
		SpinnerListModel VModel = new SpinnerListModel(valores);
		this.boxvehiculos = new JSpinner(VModel);
		centro.add(boxvehiculos,BorderLayout.CENTER);
		
		this.contaminacion = new JLabel("Weather");
		centro.add(contaminacion,BorderLayout.CENTER);
		
		JComboBox spinner_ticks = new JComboBox<Weather>(Weather.values());
		centro.add(spinner_ticks,BorderLayout.CENTER);
		
		this.Ticks = new JLabel("Ticks");
		centro.add(Ticks);
		
		SpinnerNumberModel time_ticks = new SpinnerNumberModel(1, 0, maxCont, 1);
		this.ticks = new JSpinner(time_ticks);
		centro.add(ticks,BorderLayout.CENTER);
		
		this.add(arriba,BorderLayout.NORTH);
		this.add(centro,BorderLayout.CENTER);
		this.cancel = new JButton("Cancel");
		this.ok = new JButton("OK");
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();// la cierra
			}
		});
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String v = (String) boxvehiculos.getValue();
				Weather c =  (Weather) spinner_ticks.getSelectedItem();
				int t = (int) ticks.getValue();
				
				Pair<String,Weather> par = new Pair<String,Weather>(v,c);
				List<Pair<String,Weather>> lista = new ArrayList<Pair<String,Weather>>();
				lista.add(par);
				_c.addEvent(new SetWeatherEvent(t, lista));
				dispose();
			}
		});
		
		abajo.add(cancel);
		abajo.add(ok);
		this.add(abajo,BorderLayout.SOUTH);
		
		this.setSize(400, 175);
	}
}
