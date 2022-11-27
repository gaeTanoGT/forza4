import java.awt.BorderLayout;
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
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FrameLivelli extends Interfaccia{
	JFrame frame = new JFrame();

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    //Point p = new Point(500, 500);
	Point a = new Point(dim.width / 2 - 500, dim.height / 2 - 700 / 2);
	
	JPanel pan1 = new JPanel();		//lab1
    FontResized lab1;				//forza 4
    JPanel pan2 = new JPanel();		//lab2
    FontResized lab2;				//selezionare livello
    JPanel westJPanel;

    JButton butt = new JButton();	//gioca

    JPanel f1 = new JPanel();	//pan1 e pan2
    JPanel f2 = new JPanel();	//radiobutton e giocaButton
    
    JButton back = new JButton();

    JRadioButton[] rad = new JRadioButton[4];
    ButtonGroup gr = new ButtonGroup();			//necessario per far selezionare una sola tipologia
    
    protected boolean mostraErrore = true;
    private int liv = -1;

	public FrameLivelli() {
		frame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    			DataFile d = new DataFile();
    			d.updateData(-1);
    			System.out.println("Uscita");
			}
		});
		
		setFrameLivello();
	}
	
	private void setFrameLivello() {
		f1.setVisible(true);
		f1.setLayout(new BoxLayout(f1, BoxLayout.Y_AXIS));
        f2.setVisible(true);
        f2.setLayout(new BorderLayout(10, 10));
        pan1.setVisible(true);
        lab1 = new FontResized("Forza 4 1vsBOT", 70);

        pan2.setLayout(new BorderLayout(10, 10));
        pan2.setVisible(true);
        lab2 = new FontResized("Selezionare Livello...", 40);
        lab2.setHorizontalAlignment(JLabel.CENTER);

        //back button
        back.setText("Indietro");
        back.setFocusPainted(false);
        back.addActionListener(e ->butBack());
        back.setPreferredSize(new Dimension(80, 35));
        back.setMaximumSize(new Dimension(800, 350));
        back.setMargin(new Insets(0, 0, 0, 0));
        //back.setBounds(30, 0, 80, 35);
        back.setVisible(true);
        JPanel butJPanel = new JPanel();
        butJPanel.add(back);
        pan2.add(butJPanel, BorderLayout.WEST);

        frame.setTitle("Forza 4");
        frame.setLayout(new GridLayout(3, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        
        pan1.add(lab1);
        f1.add(pan1);
        
        pan2.add(lab2, BorderLayout.CENTER);
        f1.add(pan2);
	}
	
	public void mostraLivelli(){
        f2.removeAll();
        
    	mostraErrore = true;
    	
        butt.setText("Gioca");
        butt.setFocusPainted(false);
        butt.addActionListener(e -> {
			try {
				giocaBut();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		});
        butt.setPreferredSize(new Dimension(100, 50));
        butt.setMaximumSize(new Dimension(100, 50));
        butt.setMargin(new Insets(0, 0, 0, 0));
        butt.setVisible(true);
        
        int widthPanel = 75;
        JPanel radJPanel = new JPanel();
        radJPanel.setLayout(new BoxLayout(radJPanel, BoxLayout.Y_AXIS));
        for(int i = 0; i < 4; i++){
            rad[i] = new JRadioButton();
            rad[i].setFocusPainted(false);
            //rad[i].setBounds(75, 100 + (50 * i), 100, 30);
            rad[i].setPreferredSize(new Dimension(100, 30));
            rad[i].setMaximumSize(new Dimension(1000, 300));
            rad[i].setFocusPainted(false);
            rad[i].setFont(new Font("Dialog", Font.PLAIN, 12));
            gr.add(rad[i]);
            radJPanel.add(rad[i]);
        }
        westJPanel = new JPanel();
        westJPanel.add(Box.createRigidArea(new Dimension(widthPanel, 0)));
        f2.add(westJPanel, BorderLayout.WEST);
        f2.add(radJPanel, BorderLayout.CENTER);

        rad[0].setText("Easy");
        rad[1].setText("Medium");
        rad[2].setText("Hard");
        rad[3].setText("Impossible");		
    	
        frame.repaint();
        frame.add(f1);
        frame.add(f2);
        JPanel buttonPane = new JPanel();
        buttonPane.add(butt);
        frame.add(buttonPane);     //bottone gioca
        
        frame.addComponentListener(new ComponentAdapter() {
        	public void componentShown(ComponentEvent e) {
        		lab1.ridimensiona(new Dimension(f1.getWidth(), f1.getHeight()));
        		lab2.ridimensiona(new Dimension(f1.getWidth(), f1.getHeight()));
        		
        		//pulsanti
        		int h = frame.getHeight();
        		int w = frame.getWidth();
        		//(300 * 500) : (getHeight * getWindt) = 500 : x
        		int areaButton = ((h * w) * 500) / 15000;
        		int baseButton = areaButton / ((50 * h) / 300);
        		int altezzaButton = areaButton / ((100 * w) / 400);
        		
        		//radiobutton
        		buttonPane.revalidate();
        		f2.revalidate();
        		westJPanel.revalidate();
        		f2.removeAll();
        		
        		butt.setPreferredSize(new Dimension(baseButton, altezzaButton));
        		back.setPreferredSize(new Dimension((int)(baseButton * 0.8), (int)(altezzaButton * 0.7)));
        		
        		int sizeFont = (frame.getWidth() * frame.getHeight() * 45) / 800000;
        		butt.setFont(new Font("Dialog", Font.BOLD, sizeFont));
        		back.setFont(new Font("Dialog", Font.BOLD, (int)(sizeFont * 0.6)));
        		buttonPane.repaint();
        		
        		int sizeFontRad = (frame.getWidth() * frame.getHeight() * 45) / 800000;
        		for(JRadioButton r : rad) {
        			r.setFont(new Font("Dialog", Font.BOLD, sizeFontRad));
        		}
        		
        		//spaziatura radiobutton
        		int widthPanel = 75 * frame.getWidth() / 500;
        		
        		westJPanel = new JPanel();
                westJPanel.add(Box.createRigidArea(new Dimension(widthPanel, 0)));
                f2.add(westJPanel, BorderLayout.WEST);
        		f2.add(radJPanel, BorderLayout.CENTER);
        		
        		westJPanel.repaint();
        		f2.repaint();
        		radJPanel.repaint();
        	}
        	public void componentResized(ComponentEvent e) {
        		lab1.ridimensiona(new Dimension(f1.getWidth(), f1.getHeight()));
        		lab2.ridimensiona(new Dimension(f1.getWidth(), f1.getHeight()));
                
        		//pulsanti
        		int h = frame.getHeight();
        		int w = frame.getWidth();
        		//(300 * 500) : (getHeight * getWindt) = 500 : x
        		int areaButton = ((h * w) * 500) / 15000;
        		int baseButton = areaButton / ((50 * h) / 300);
        		int altezzaButton = areaButton / ((100 * w) / 400);
        		
        		//radiobutton
        		buttonPane.revalidate();
        		f2.revalidate();
        		westJPanel.revalidate();
        		f2.removeAll();
        		
        		butt.setPreferredSize(new Dimension(baseButton, altezzaButton));
        		back.setPreferredSize(new Dimension((int)(baseButton * 0.8), (int)(altezzaButton * 0.7)));
        		
        		int sizeFont = (frame.getWidth() * frame.getHeight() * 45) / 800000;
        		butt.setFont(new Font("Dialog", Font.BOLD, sizeFont));
        		back.setFont(new Font("Dialog", Font.BOLD, (int)(sizeFont * 0.6)));
        		buttonPane.repaint();
        		
        		int sizeFontRad = (frame.getWidth() * frame.getHeight() * 45) / 800000;
        		for(JRadioButton r : rad) {
        			r.setFont(new Font("Dialog", Font.PLAIN, sizeFontRad));
        		}
        		
        		//spaziatura radiobutton
        		int widthPanel = 75 * frame.getWidth() / 500;
        		
        		westJPanel = new JPanel();
                westJPanel.add(Box.createRigidArea(new Dimension(widthPanel, 0)));
                f2.add(westJPanel, BorderLayout.WEST);
        		f2.add(radJPanel, BorderLayout.CENTER);
        		
        		westJPanel.repaint();
        		f2.repaint();
        		radJPanel.repaint();
        		
        		//reimposto default size di interfaccia
        		if(!getDefaultSize().equals(frame.getSize()))
        		{
        			if(!getDefaultSize().equals(new Dimension(0, 0)) && !getDefaultSize().equals(new Dimension(500, 300))){
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
        
        //se non hai cambiato la dimensione
        if(!isSized)
        {
        	System.err.println("!sized");
        	frame.setSize(500, 430);
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

	private void giocaBut() throws InterruptedException{    
        for(int i = 0; i < 4; i++){
            if(rad[i].isSelected()){
            	setLiv(i);
            }
        }
        if(mostraErrore && liv == -1)
        {
        	mostraErrore = false;
        	JOptionPane.showMessageDialog(frame, "Selezionare un livello...");
        	try {
        		wait();
			} catch (Exception e) {
			} 
        }
        
        if(liv != -1) {
        	mostraFrame(false);
            super.setLivello(liv);
        }
    }
	
	public void setLiv(int liv) {
		this.liv = liv;
	}
	public int getLiv(){
        return liv;
    }
	public void resetLiv() {
		liv = -1;
	}
	
	public void butBack() {
		mostraFrame(false);
		
		try {
			super.back(1, tipologia);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void mostraFrame(boolean st) {
		if(st)
    		frame.setVisible(st);
    	else {
			frame.dispose();
		}
    }
}
