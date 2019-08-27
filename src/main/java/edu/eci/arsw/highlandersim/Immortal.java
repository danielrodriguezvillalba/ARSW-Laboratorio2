package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;

public class Immortal extends Thread {

	private ImmortalUpdateReportCallback updateCallback = null;

	private int health;

	private int defaultDamageValue;

	private final List<Immortal> immortalsPopulation;

	private final String name;

	private boolean enPausa;

	private final Random r = new Random(System.currentTimeMillis());

	public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue,
			ImmortalUpdateReportCallback ucb) {
		super(name);
		this.updateCallback = ucb;
		this.name = name;
		this.immortalsPopulation = immortalsPopulation;
		this.health = health;
		this.defaultDamageValue = defaultDamageValue;
		this.enPausa = false;
	}

	public void run() {

		while (true) {
			
			if (!ControlFrame.stop) {
				
				if (!enPausa) {

					Immortal im;

					int myIndex = immortalsPopulation.indexOf(this);

					int nextFighterIndex = r.nextInt(immortalsPopulation.size());

					// avoid self-fight
					if (nextFighterIndex == myIndex) {
						nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
					}

					im = immortalsPopulation.get(nextFighterIndex);

					this.fight(im);

					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					pausar();
				}
			}
			
		}

	}

	public void fight(Immortal i2) {
		if(this.getId() < i2.getId()) {
			synchronized (i2) {
				synchronized (this) {
					hagalo(i2);
				}
				
			}
		}
		else {
			synchronized (this) {
				synchronized (i2) {
					hagalo(i2);
				}
			}
		}
		
	}
	
	public void hagalo(Immortal i2) {
		if (i2.getHealth() > 0) {
			i2.changeHealth(i2.getHealth() - defaultDamageValue);
			this.health += defaultDamageValue;
			updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
		} else {
			updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
			immortalsPopulation.remove(i2);
			enPausa = true;
		}
	}
	
	public void changeHealth(int v) {
		health = v;
	}

	public int getHealth() {
		return health;
	}

	public void pausar() {
		if (enPausa) {
			synchronized (ControlFrame.getMonitor()) {
				try {
					ControlFrame.getMonitor().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		enPausa = false;
	}

	public void setPausa(boolean p) {
		enPausa = p;
	}

	@Override
	public String toString() {

		return name + "[" + health + "]";
	}

}
