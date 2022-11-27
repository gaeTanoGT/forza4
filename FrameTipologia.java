import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameTipologia extends Interfaccia{

	JFrame frame = new JFrame();

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    Point p = new Point(500, 500);
	Point a = new Point(dim.width / 2 - 500, dim.height / 2 - 700 / 2);
	
	FontResized lab1;			//forza 4
	JPanel pan1 = new JPanel();	//lab1
    JPanel pan2 = new JPanel();	//lab2
    FontResized lab2;			//selezionare tipologia
    JButton butt = new JButton();	//1vs1
    JButton butt2 = new JButton();	//1vsBot
    
	private boolean ascoltaTipologia = false;
	private boolean tipologia = false;		//0: 1vs1;	1: 1vsBot
	
	private boolean setSized = false;
	public boolean isSetSized() {
		return setSized;
	}

	public void setSetSized(boolean setSized) {
		this.setSized = setSized;
	}

	public boolean isSetMoved() {
		return setMoved;
	}

	public void setSetMoved(boolean setMoved) {
		this.setMoved = setMoved;
	}

	private boolean setMoved = false;
	
	public boolean getAscoltaTipologia() {
		return ascoltaTipologia;
	}

	public void setAscoltaTipologia(boolean ascoltaTipologia) {
		this.ascoltaTipologia = ascoltaTipologia;
	}

	public boolean getTipologia() {
		return tipologia;
	}

	public void setTipologia(boolean tipologia) {
		ascoltaTipologia = true;
		this.tipologia = tipologia;
	}
	
	public void setFrameTipologia() {        
        pan1.setVisible(true);
        lab1 = new FontResized("Forza 4", 100);
  
        pan2.setVisible(true);
        lab2 = new FontResized("Selezionare tipologia di gioco:", 65);

        frame.setTitle("Forza 4");
        frame.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        
        pan1.add(lab1);
        pan2.add(lab2);
        JPanel pan3 = new JPanel();
        pan3.setLayout(new GridLayout(2, 1));
        pan3.add(pan1);
        pan3.add(pan2);
        frame.add(pan3);
        
        ascoltaTipologia = false;
    	
    	butt.setText("1vs1");
        butt.setFocusPainted(false);
        butt.addActionListener(e -> but1vs1());
        butt.setPreferredSize(new Dimension(100, 50));
        butt.setMaximumSize(new Dimension(1000, 500));
        butt.setMargin(new Insets(0, 0, 0, 0));
        butt.setVisible(true);
        
        butt2.setText("1vsBOT");
        butt2.setFocusPainted(false);
        butt2.addActionListener(e -> butBOT());
        butt2.setPreferredSize(new Dimension(100, 50));
        butt2.setMaximumSize(new Dimension(1000, 500));
        butt2.setMargin(new Insets(0, 0, 0, 0));
        butt2.setVisible(true);
		
        JPanel buttonPane = new JPanel();
        buttonPane.add(butt);
        buttonPane.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPane.add(butt2);
        buttonPane.setAlignmentX(Component.CENTER_ALIGNMENT);
	    frame.add(buttonPane);
        
        frame.addComponentListener(new ComponentAdapter() {
        	public void componentShown(ComponentEvent e) {
        		lab1.ridimensiona(new Dimension(pan1.getWidth(), pan1.getHeight()));
        		lab2.ridimensiona(new Dimension(pan2.getWidth(), pan2.getHeight()));
			}
        	public void componentResized(ComponentEvent e) {
    			lab1.ridimensiona(new Dimension(pan1.getWidth(), pan1.getHeight()));
        		lab2.ridimensiona(new Dimension(pan2.getWidth(), pan2.getHeight()));
        		
        		//pulsanti
        		int h = frame.getHeight();
        		int w = frame.getWidth();
        		//(300 * 500) : (getHeight * getWindt) = 500 : x
        		int areaButton = ((h * w) * 750) / 15000;
        		int baseButton = areaButton / ((50 * h) / 300);
        		int altezzaButton = areaButton / ((100 * w) / 500);
        		buttonPane.revalidate();
        		butt.setPreferredSize(new Dimension(baseButton, altezzaButton));
        		butt2.setPreferredSize(new Dimension(baseButton, altezzaButton));
        		
        		int sizeFont = (frame.getWidth() * frame.getHeight() * 45) / 600000;
        		butt.setFont(new Font("Dialog", Font.BOLD, sizeFont));
        		butt2.setFont(new Font("Dialog", Font.BOLD, sizeFont));
        		buttonPane.repaint();
        		
        		
        		if(!getDefaultSize().equals(frame.getSize()))
        		{
        			if(!getDefaultSize().equals(new Dimension(0, 0)) && !getDefaultSize().equals(new Dimension(500, 430))){
        				setSized(true);
        			}
    				System.err.println("ridimensionato " + getDefaultSize());
    				setDefaultSize(new Dimension(frame.getSize()));
        		}
        	}
        	
        	public void componentMoved(ComponentEvent e) {
        		
        		System.err.println(getDefaultPosition());
        		if(!getDefaultPosition().equals(frame.getLocation()))
        		{
        			if(!getDefaultPosition().equals(new Point(0, 0))) {
        				setMoved(true);
        			}
    				System.out.println("spostatooo " + getDefaultPosition());
    				setDefaultPosition(frame.getLocation());
        		}
        	}
		});
        frame.pack();

        if(!isSized)
        {
        	System.err.println("!sized");
        	frame.setSize(500, 300);
        }else {
        	System.err.println("sized");
        	frame.setSize(getDefaultSize());
        }
        
        if(!isMoved) {
        	System.err.println("!moved");
        	frame.setLocation(a);
        } else {
        	System.err.println("moved");
			frame.setLocation(getDefaultPosition());
		}
	}
	
	public FrameTipologia() {
		frame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    			DataFile d = new DataFile();
    			d.updateData(-1);
    			System.out.println("Uscita");
			}
		});
		
		setFrameTipologia();
	}
	
	private void but1vs1() {
    	System.out.println("1vs1");
    	//tipologia = false;
    	ascoltaTipologia = true;
    	setTipologia(false);
    	mostraFrame(false);
    	super.setTipologia(false);
    }
    private void butBOT() {
    	System.out.println("1vsBOT");
    	ascoltaTipologia = true;
    	setTipologia(true);
    	mostraFrame(false);
    	super.setTipologia(true);
    }
    public int getTipologiaSelezionata() {
    	if(ascoltaTipologia)
    	{
    		System.out.println("289");
    		ascoltaTipologia = false;
    		return getTipologia()? 1 : 0;
    	}
    	else {
    		return -1;
		}
    }
    
    public void mostraFrame(boolean st) {
    	if(st)
    		frame.setVisible(st);
    	else {
    		frame.setVisible(false);
			frame.dispose();
		}
    }
    
    public void resetTipologia() {
    	ascoltaTipologia = false;
    }
}
