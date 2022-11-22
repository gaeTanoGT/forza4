import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameTipologia extends Interfaccia{

	JFrame frame = new JFrame();

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    Point p = new Point(500, 500);
	Point a = new Point(dim.width / 2 - 500, dim.height / 2 - 700 / 2);
	
	JPanel pan1 = new JPanel();
    JLabel lab1 = new JLabel();
    JPanel pan2 = new JPanel();
    JLabel lab2 = new JLabel();

    JButton butt = new JButton();
    JButton butt2 = new JButton();
    
    JPanel f1 = new JPanel();
    JPanel f2 = new JPanel();
	
	private boolean ascoltaTipologia = false;
	private boolean tipologia = false;
	
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
		f1.setLayout(null);
        f1.setVisible(true);
        f1.setBounds(0,0,500,100);

        f2.setLayout(null);
        f2.setVisible(true);
        f2.setBounds(0,0,500,500);

        pan1.setLayout(null);
        pan1.setVisible(true);
        pan1.setBounds(0,0,500,50);
        lab1.setLayout(null);
        lab1.setHorizontalAlignment(JLabel.CENTER);
        lab1.setFont(new Font("1", Font.ITALIC, 30));
        lab1.setBounds(0,0,500,50);
        
        pan2.setLayout(null);
        pan2.setVisible(true);
        pan2.setBounds(0,50,500,50);
        lab2.setLayout(null);
        lab2.setHorizontalAlignment(JLabel.CENTER);
        lab2.setFont(new Font("1", Font.ITALIC, 20));
        lab2.setBounds(100,0,300,50);

        frame.setTitle("Forza 4");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        pan1.add(lab1);
        f1.add(pan1);
        
        pan2.add(lab2);
        f1.add(pan2);
        
        ascoltaTipologia = false;
    	
    	f1.setBounds(0,0,500,150);
        pan2.setBounds(0,70,500,50);
        lab2.setBounds(0,0,500,50);
    	
    	lab1.setText("Forza 4111");
    	lab2.setText("Selezionare tipologia di gioco:");
    	
    	butt.setText("1vs1");
        butt.setFocusPainted(false);
        butt.addActionListener(e -> but1vs1());
        butt.setBounds(100, 150, 100, 50);
        butt.setVisible(true);
        
        butt2.setText("1vsBOT");
        butt2.setFocusPainted(false);
        butt2.addActionListener(e -> butBOT());
        butt2.setBounds(300, 150, 100, 50);
        butt2.setVisible(true);
        
        f2.add(butt);
        f2.add(butt2);
        
        frame.repaint();
        frame.add(f1);
        frame.add(f2);

        frame.setSize(500, 300);
        frame.setLocation(a);
        frame.setLayout(null);
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
