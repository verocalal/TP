package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;;


public class MapByRoadComponent extends JComponent implements TrafficSimObserver{

	private static final int _JRADIUS = 10;
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private RoadMap _map;
	
	
	 MapByRoadComponent(Controller c) {
		setPreferredSize(new Dimension (300, 200));
		c.addObserver(this);
	}
	
	public void paintComponent(Graphics graphics) {
		
		
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if (_map == null || _map.getJunctions().size() == 0) 
		{
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} 
		else
		{
			drawMap(g);	
		}
	}
	private void drawMap(Graphics g)
	{
		drawRoads(g);
	}
	private void drawRoads(Graphics g)
	{
		int numVueltas =0;
		for(Road r : this._map.getRoads())
		{
			int x1 = 50;
			int x2 = getWidth()-100;

			int y = (numVueltas+1)*50; 
			
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(x1, y, x2,y);
			g.setColor(Color.black); // etiqueta de la carretera
			g.drawString(r.getId(), x1-20, y);
			g.setColor(Color.blue); //junction origen
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getOrigen().getId(), x1, y-10);
			
			//DESTINO
			int idx = r.getDestino().getSemaforoVerde();
			if(idx!=-1 && r.equals(r.getDestino().getIncomingRoads().get(idx)))
				g.setColor(Color.green);
			else
				g.setColor(Color.red);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getDestino().getId(), x2, y-10);
			
			String tiempo = null;
			switch(r.getCondicionesAmbientales())
			{
				case SUNNY: 
					tiempo ="sun";
				break;
				case WINDY: 
					tiempo ="wind";
					break;
				case STORM: 
					tiempo ="storm";
					break;
				case RAINY: 
					tiempo ="rain";
					break;
				case CLOUDY: 
					tiempo ="cloud";
					break;
			}
			Image weather = loadImage(tiempo+".png");
			g.drawImage(weather, x2+10, y-10 , 32, 32, this);
			
			int c = (int) Math.floor(Math.min((double)r.getContaminacionTotal()/(1.0+(double) r.getLimiteContaminacion()),1.0) / 0.19);
			String cont = "cont_" + Integer.toString(c);
			Image contaminacion = loadImage(cont+".png");
			g.drawImage(contaminacion,x2+50,y-10,32,32,this);
			
			drawVehicleOnRoad(g, r, numVueltas);
			
			numVueltas++;
		}
	}
	private void drawVehicleOnRoad(Graphics g,Road r, int i)
	{
		Image _car = loadImage("car.png");
		//System.out.println(r.getId());

		for(Vehicle v : r.getListaVehiculos())
		{

			int x1 =50;
			int x2= getWidth()-100;
			int y=(i+1)*50;
			
			int vLabelColor = (int) (25.0 * (10.0 - (double) v.getGradoContaminacion()));
			g.setColor(new Color(0, vLabelColor, 0));

			int coorX= x1 + (int)((x2-x1)*((double)v.getLocalizacion()/ (double)r.getLonguitud()));
			
			g.drawImage(_car, coorX, y-6, 16, 16, this);
			g.drawString(v.getId(), coorX, y - 6);
		}
	}
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map)
	{
		_map=map;
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
		repaint();
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> evento, int time) {
		update(map);
	}
	@Override
	public void onEventAdded(RoadMap map, List<Event> evento, Event e, int time) {
		update(map);	
	}
	@Override
	public void onReset(RoadMap map, List<Event> evento, int time) {
		update(map);
		
	}
	@Override
	public void onRegister(RoadMap map, List<Event> evento, int time) {
		update(map);
	}
	@Override
	public void onError(String err) {
		System.out.println(err);

	}

}
