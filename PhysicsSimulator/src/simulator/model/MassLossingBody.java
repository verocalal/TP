package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body{
    protected double lossFactor;
    protected int lossFrequency;
    private int c = 0;
    
	public MassLossingBody(String id, Vector v, Vector p, Vector a, double m, int lossFrequency, double lossFactor) {
		super(id, v, p, a, m);
		this.lossFrequency = lossFrequency;
		this.lossFactor = lossFactor;
		
	}
    
	void move(double t) {
		super.move(t);
		if (c == 0) {
			c+=t;
		}
		if (c >= this.lossFrequency) {
			this.m = this.m * (1 - (this.lossFactor));
			c = 0;
		}
		else c+=t;
	}

	public double getLossFactor() {
		return lossFactor;
	}

	public void setLossFactor(double lossFactor) {
		this.lossFactor = lossFactor;
	}

	public double getLossFrequency() {
		return lossFrequency;
	}

	public void setLossFrequency(int lossFrequency) {
		this.lossFrequency = lossFrequency;
	}
	
}
