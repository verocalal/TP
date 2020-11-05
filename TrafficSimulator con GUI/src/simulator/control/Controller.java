package simulator.control;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;
import simulator.model.Event;

public class Controller {
	
	
	private TrafficSimulator sim;
	private Factory<simulator.model.Event> factoriaEventos;
	
	
	
	public Controller (TrafficSimulator sim, Factory<simulator.model.Event> eventsFactory) 
	{ 
		try 
		{
			if(sim==null || eventsFactory==null)
				throw new IllegalArgumentException("Excepcion en constructor de controler");
			else
			{
				this.sim=sim;
				this.factoriaEventos=eventsFactory;
			}
		}
		catch(IllegalArgumentException e)
		{
			e.getMessage();
		}
	}
	public void loadEvents(InputStream in)
	{
		JSONObject jo = new JSONObject(new JSONTokener(in)); 
		JSONArray events = jo.getJSONArray("events"); 
		for(int i = 0; i < events.length(); i++) 
		{
			sim.addEvent(factoriaEventos.createInstance(events.getJSONObject(i)));
			
		}
	}
	public void run(int n)
	{
	     for (int i = 0; i < n; i++) 
	    	 this.sim.advance();
	}
	public void run(int n,OutputStream out)
	{
		if (out == null) 
		{ 
			out = new OutputStream() 
			{ @Override 
				public void write(int b) throws IOException {} 
			}; 
		} 
		PrintStream p = new PrintStream(out);
		p.println("{"); 
		p.println("  \"states\": [");
		
		sim.setTiempo(n);
		for(int i=0;i<n;i++)
		{
			sim.advance(); 
			p.print(sim.report());
			p.println(","); 
		}
		
		p.print(sim.report());
		p.println("]");
		p.println("}");
	}
	public void reset()
	{
		sim.reset();
	}
	
	
	public void addObserver(TrafficSimObserver o)
	{
		this.sim.addObserver(o);
		
	}
	public void removeObserver(TrafficSimObserver o)
	{
		this.sim.removeObserver(o);
	}
	public void addEvent(Event e)
	{
		this.sim.addEvent(e);
	}
	public TrafficSimulator getTrafficSimulator()
	{
		return this.sim;
	}
	
}
