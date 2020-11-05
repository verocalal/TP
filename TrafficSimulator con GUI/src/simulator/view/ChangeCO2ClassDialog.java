package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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
import simulator.model.Vehicle;
import simulator.control.Controller;
import simulator.misc.Pair;

public class ChangeCO2ClassDialog extends JDialog{

	private JTextArea informacion;
	
	private JLabel vehiculo;
	private JLabel contaminacion;
	private JLabel Ticks;
	private JSpinner boxvehiculos;
	private JSpinner contlist;
	private JSpinner ticks;
	
	private JButton cancel;
	private JButton ok;
	private int maxCont = 10;
	private String[] valores;
	private Controller _c;
	/*
	 * SpinnerNumberModel spinner_ticks = new SpinnerNumberModel(1, 0, max_ticks, 1);
	 * ticks = new JSpinner (spinner_ticks)
	 */
	public ChangeCO2ClassDialog(Controller c) throws Exception
	{
		_c=c;
		if(c.getTrafficSimulator().getMapaCarreteras() == null)
			throw new Exception();
		List<Vehicle> aux = new ArrayList<Vehicle>(c.getTrafficSimulator().getMapaCarreteras().getVehicles());
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
		informacion = new JTextArea("Schedule an event to change the CO2 class of a vehicle after given number of simulaiton ticks from now");
		informacion.setLineWrap(true);
		informacion.setOpaque(false);
		arriba.add(informacion);
		
		this.vehiculo = new JLabel("Vehiculo");
		centro.add(vehiculo,BorderLayout.CENTER);
		
		SpinnerListModel VModel = new SpinnerListModel(valores);
		this.boxvehiculos = new JSpinner(VModel);
		centro.add(boxvehiculos,BorderLayout.CENTER);
		
		this.contaminacion = new JLabel("Contaminacion");
		centro.add(contaminacion,BorderLayout.CENTER);
		
		SpinnerNumberModel spinner_ticks = new SpinnerNumberModel(1, 0, maxCont, 1);
		this.contlist = new JSpinner(spinner_ticks);
		centro.add(contlist,BorderLayout.CENTER);
		
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
				int c = (int) contlist.getValue();
				int t = (int) ticks.getValue();
				System.out.println("dfsdfsdf" + t);
				Pair<String,Integer> par = new Pair<String,Integer>(v,c);
				List<Pair<String,Integer>> lista = new ArrayList<Pair<String,Integer>>();
				lista.add(par);
				_c.addEvent(new SetContaminationClass(t, lista));
				dispose();
			}
		});
		
		abajo.add(cancel);
		abajo.add(ok);
		this.add(abajo,BorderLayout.SOUTH);
		
		this.setSize(400, 175);
	}
}
