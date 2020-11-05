package simulator.view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	// ...
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	
	Viewer(Controller ctrl) {
	   initGUI();
	   ctrl.addObserver(this);
	}
	private void initGUI() {
		setLayout(new BorderLayout());
	    setBorder(BorderFactory.createTitledBorder(
	    		BorderFactory.createLineBorder(Color.black, 2),
	    		"Viewer",
	    		TitledBorder.LEFT, TitledBorder.TOP));
	   this.setPreferredSize(new Dimension(300, 300));
	   _bodies = new ArrayList<>();
	   _scale = 1.0;
	   _showHelp = true;
	   addKeyListener(new KeyListener() {
		      // ...
		      @Override
		      public void keyPressed(KeyEvent e) {
		         switch (e.getKeyChar()) {
		            case '-':
		               _scale = _scale * 1.1;
		               break;
		            case '+':
		               _scale = Math.max(1000.0, _scale / 1.1);
		               break;
		            case '=':
		               autoScale();
		               break;
		            case 'h':
		               _showHelp = !_showHelp;
		               break;
		            default:
		         }
		         repaint(); 
		      }

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	   addMouseListener(new MouseListener() {
	      // ...
	      @Override
	      public void mouseEntered(MouseEvent e) {
	         requestFocus();
	      }

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}); }
	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   Graphics2D gr = (Graphics2D) g;
	   gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
               RenderingHints.VALUE_ANTIALIAS_ON);
	   gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
               RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	   	// use ’gr’ to draw not ’g’
	      // calculate the center
	      _centerX = getWidth() / 2;
	      _centerY = getHeight() / 2;
	      // TODO draw a cross at center
	      gr.setColor(Color.RED);
	      gr.drawLine(_centerX, _centerY + 5, _centerX, _centerY - 5);
	      gr.setColor(Color.RED);
	      gr.drawLine(_centerX + 5, _centerY, _centerX - 5, _centerY);
	      // TODO draw bodies
	      for (Body b: this._bodies) {
	    	  double x = b.getPosition().coordinate(0);
	    	  double y = b.getPosition().coordinate(1);
	    	  int x_cuerpo = _centerX + (int) (x/_scale);
	    	  int y_cuerpo = _centerY - (int) (y/_scale);
	    	  gr.setColor(Color.BLUE);
	    	  gr.fillOval(x_cuerpo, y_cuerpo, 10, 10);
	    	  gr.setColor(Color.BLACK);
	    	  gr.drawString(b.getId(), x_cuerpo - 3, y_cuerpo - 10);
	      }
	      // TODO draw help if _showHelp is true
	      if (_showHelp) {
	    	  gr.setColor(Color.RED);
	    	  gr.drawString("h: toggle help, + : zoom-in, - : zoom-out, = : fit", 8, 25);
	    	  gr.drawString("Scaling ratio: " + _scale, 8, 40);
	      }
	      
	      
	}
	   // other private/protected methods
	   // ...
	   private void autoScale() {
	      double max = 1.0;
	      for (Body b : _bodies) {
	          Vector p = b.getPosition();
	         for (int i = 0; i < p.dim(); i++)
	            max = Math.max(max,
	                           Math.abs(b.getPosition().coordinate(i)));
	      }
	      double size = Math.max(1.0, Math.min((double) getWidth(),
	                                           (double) getHeight()));
	      _scale = max > size ? 4.0 * max / size : 1.0;
	   }
	   // SimulatorObserver methods
	// ... 
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				autoScale();
				repaint();
			}
			
		});
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {			
				autoScale();
				repaint();
			}
			
		});
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				autoScale();
				repaint();
			}
			
		});	
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_bodies = bodies;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				repaint();
			}
			
		});
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}
}