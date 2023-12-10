import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FrameGriglia extends Interfaccia implements Runnable{
	
	private static final String bianco = "..\\img\\bianco.png";
	private static final String giallo = "..\\img\\giallo.png";
	private static final String rosso = "C:..\\img\\rosso.png";
	
	static ImageIcon ic1 = new ImageIcon(bianco);
	static  Image img1 = ic1.getImage(); // transform it 
    static Image newimg1 = img1.getScaledInstance(45,45, Image.SCALE_SMOOTH);
    static ImageIcon white = new ImageIcon(newimg1);
    
    static  ImageIcon ic2 = new ImageIcon(giallo);
    static Image img2 = ic2.getImage(); // transform it 
    static Image newimg2 = img2.getScaledInstance(45,45, Image.SCALE_SMOOTH);
    static ImageIcon yellow = new ImageIcon(newimg2);
    
    static ImageIcon ic3 = new ImageIcon(rosso);
    static Image img3 = ic3.getImage(); // transform it 
    static Image newimg3 = img3.getScaledInstance(45,45, Image.SCALE_SMOOTH); 
    static ImageIcon red = new ImageIcon(newimg3);
    
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    Point p = new Point(500, 500);
	Point a = new Point(dim.width / 2 - 500, dim.height / 2 - 700 / 2);
	
	Thread th;
	
	JFrame frame = new JFrame();
	
    JPanel pan1 = new JPanel();
    FontResized lab1;
    JPanel pan2 = new JPanel();
    FontResized lab2;

    JButton butt = new JButton();
    JButton butt2 = new JButton();
    JButton back = new JButton();

    JRadioButton[] rad = new JRadioButton[4];

    static JButton[][] but = new JButton[6][7];

    ButtonGroup gr = new ButtonGroup();

    JPanel f1 = new JPanel();
    JPanel f2 = new JPanel();
    JPanel f2JPanel = new JPanel();
    
    private boolean pl;		//player in gioco
    private int liv;
    private int tipologia;		//1: BOT; 	0: 1vs1
	protected boolean mostraErrore = true;
	private int col = -1;
	
    public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public void setLiv(int liv) {
		this.liv = liv;
	}
	
	public int getLiv() {
		return liv;
	}

    public boolean getPl() {
		return pl;
	}
	public void setPl(boolean pl) {
		this.pl = pl;
	}

	public FrameGriglia(int tipologia) {
    	this.tipologia = tipologia;
    	
    	frame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    			DataFile d = new DataFile();
    			d.updateData(-1);
    			System.out.println("Uscita");
			}
		});
    	
    	setFrameGriglia();
    }
	
	private void setFrameGriglia() {
        f1.setVisible(true);
        f1.setLayout(new BoxLayout(f1, BoxLayout.Y_AXIS));

        f2.setVisible(true);
        f2.setLayout(new BorderLayout(100, 100));

        pan1.setVisible(true);
        lab1 = new FontResized("Forza 4", 70);
        
      //MATRICE DI BOTTONI
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                but[i][j] = new JButton();
                but[i][j].setIcon(white);
                but[i][j].setVisible(false);
                but[i][j].setBorderPainted(false);
                but[i][j].setFocusPainted(false);
                but[i][j].setContentAreaFilled(false);
                but[i][j].setOpaque(true);
                int l = j;
                but[i][j].addActionListener(e -> setCol(l));
            }
        }
        
        pan2.setVisible(true);
        pan2.setLayout(new BorderLayout(10, 10));
        lab2 = new FontResized("", 30);
        lab2.setHorizontalAlignment(JLabel.CENTER);
        
        //back button
        back.setText("Indietro");
        back.setFocusPainted(false);
        back.addActionListener(e -> butBack());
        back.setPreferredSize(new Dimension(80, 35));
        back.setMaximumSize(new Dimension(800, 350));
        back.setMargin(new Insets(0, 0, 0, 0));
        back.setVisible(true);
        JPanel butJPanel = new JPanel();
        butJPanel.add(back);
        pan2.add(butJPanel, BorderLayout.WEST);

        frame.setTitle("Forza 4");
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        
        pan1.add(lab1);
        f1.add(pan1);
        
        pan2.add(lab2, BorderLayout.CENTER);
        f1.add(pan2);
	}
    
	public void showGriglia(){

    	mostraErrore = true;
    	
        butt.setVisible(false);
        try {
        	for(JRadioButton b : rad){
                b.setVisible(false);
            }	
		}catch (Exception e) {}        
        
        f2.setLayout(new GridLayout(6, 7));
        f2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));  

        f2.removeAll();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
            	but[i][j].setVisible(true);
            	
                f2.add(but[i][j]);
            }
        }
        
        frame.repaint();
        frame.add(f1, BorderLayout.BEFORE_FIRST_LINE);
        f2JPanel.add(f2);
        frame.add(f2JPanel, BorderLayout.CENTER);
        
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
        		
        		back.revalidate();
        		back.setPreferredSize(new Dimension((int)(baseButton * 0.8), (int)(altezzaButton * 0.7)));
        		back.setMaximumSize(back.getPreferredSize());
        		int sizeFont = (frame.getWidth() * 10) / 500;
        		back.setFont(new Font("Dialog", Font.BOLD, sizeFont));
        		back.repaint();
        		
        		int xPanelF2 = (int)(w * 0.8);
        		int yPanelF2 = (int)(h * 0.60);
        		f2.setPreferredSize(new Dimension(xPanelF2, yPanelF2));
        		
        		int lato = (45 * yPanelF2) / 325;
        		
        	    newimg1 = img1.getScaledInstance(lato, lato, Image.SCALE_SMOOTH);
        	    white = new ImageIcon(newimg1);
        	    
        	    newimg2 = img2.getScaledInstance(lato, lato, Image.SCALE_SMOOTH);
        	    yellow = new ImageIcon(newimg2);
        	    
        	    newimg3 = img3.getScaledInstance(lato, lato, Image.SCALE_SMOOTH); 
        	    red = new ImageIcon(newimg3);
        	    
        	    f2.revalidate();
                aggiornaMat();
                f2.repaint();
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

        		back.revalidate();
        		back.setPreferredSize(new Dimension((int)(baseButton * 0.8), (int)(altezzaButton * 0.7)));
        		back.setMaximumSize(back.getPreferredSize());
        		int sizeFont = (frame.getWidth() * 10) / 500;
        		back.setFont(new Font("Dialog", Font.BOLD, (int)(sizeFont)));
        		back.repaint();
        		
        		//ridimensionamento bottoni
        		//0.6 y
        		//0.8 x
        		int xPanelF2 = (int)(w * 0.8);
        		int yPanelF2 = (int)(h * 0.6);
        		f2.setPreferredSize(new Dimension(xPanelF2, yPanelF2));
                
        		//45 : 400 = x : xPanelF2;
        		int lato = (45 * yPanelF2) / 325;
        		
        	    newimg1 = img1.getScaledInstance(lato, lato, Image.SCALE_SMOOTH);
        	    white = new ImageIcon(newimg1);
        	    
        	    newimg2 = img2.getScaledInstance(lato, lato, Image.SCALE_SMOOTH);
        	    yellow = new ImageIcon(newimg2);
        	    
        	    newimg3 = img3.getScaledInstance(lato, lato, Image.SCALE_SMOOTH); 
        	    red = new ImageIcon(newimg3);
        	    
        	    f2JPanel.revalidate();
        	    f2.revalidate();
                
                aggiornaMat();
                f2.repaint();
                f2JPanel.repaint();
        		
        		//reimposto default size di interfaccia
        		if(!getDefaultSize().equals(frame.getSize()))
        		{
        			if(!getDefaultSize().equals(new Dimension(0, 0)) && (!getDefaultSize().equals(new Dimension(500, 300)) && !getDefaultSize().equals(new Dimension(500, 430)))){
        				setSized(true);
        			}
    				setDefaultSize(new Dimension(frame.getSize()));
        		}
        	}
        	public void componentMoved(ComponentEvent e) {
        		if(!getDefaultPosition().equals(frame.getLocation()))
        		{
        			if(!getDefaultPosition().equals(new Point(0, 0))) {
        				setMoved(true);
        			}
    				setDefaultPosition(frame.getLocation());
        		}
        	}
		});
        
        //frame.pack();
        
        if(!isSized) {
        	frame.setSize(500, 500);
        }else {
        	frame.setSize(getDefaultSize());
        }
        
        if(!isMoved) {
        	frame.setLocation(a);
        } else {
			frame.setLocation(getDefaultPosition());
		}
        frame.setVisible(true);
    }
    
    private void setCol(int col){
    	this.col = col;
  
    	if(m.getEnd(col) != -1) {
    		try {
    			if(!th.isAlive()) {
        			th = new Thread(this);
                	th.start();
        		}
			} catch (NullPointerException e) {
				th = new Thread(this);
            	th.start();
			}	
    	}
    }
    
    public int getCol() {
    	if(col != -1) {
    		int k = col;
    		col = -1;
    		return k;
    	}
    	return col;
    }
    
    public boolean riempiColBot(int col, boolean pl) throws InterruptedException {
    	this.col = col;
    	this.pl = pl;
    	
    	//ottengo coordinate per la transizione
    	int f = 0;
    	if(!pl) {
    		f = m.getEnd(col);		//ultima riga disponibile
    	}else {
    		f = m.getVrig();
    	}
    	if(f == -1)
    		return false;
    	
    	//eseguio transizione
    	for(int i = 0; i <= f; i++) {
    		if(!pl)
    			but[i][col].setIcon(yellow);
    		else
    			but[i][col].setIcon(red);
    		
    		Thread.sleep(100);
    		
    		if(i != f)
    			but[i][col].setIcon(white);
    	}
    	m.setGettone(f, col, pl, tipologia);
    	return true;
    }
    
    public boolean riempiCol(int col, boolean pl) throws InterruptedException{
    	//ottengo coordinate per la transizione
    	int f = 0;
   		f = m.getEnd(col);		//ultima riga disponibile
    	
    	if(f == -1)
    		return false;
    	
    	//eseguio transizione
    	for(int i = 0; i <= f; i++) {
    		if(!pl)
    			but[i][col].setIcon(yellow);
    		else
    			but[i][col].setIcon(red);
    		
			Thread.sleep(100);
    		
    		if(i != f)
    			but[i][col].setIcon(white);
    	}
    	m.setGettone(f, col, pl, tipologia);
    	return true;
    }
    
    public void cambiaLab2(boolean pl) {
    	if(!pl) {
    		lab2.setText("Mossa giocatore 1:");
    	}else {
    		lab2.setText("Mossa giocatore 2:");
		}
    }
    
    public void cambiaLab2Bot(boolean pl) {
    	if(!pl) {
    		lab2.setText("Mossa giocatore 1:");
    	}else {
    		lab2.setText("Mossa BOT:");
		}
    }
    
    public void aggiornaMat(int pl, int liv) {
    	for(int i = 0; i < 6; i++)
    		for(int j = 0; j < 7; j++)
    			if(m.getMat(i, j) == 3 || m.getMat(i, j) == 4)
    				but[i][j].setEnabled(false);
    	
    	if(pl == 0) 
    		lab2.setText("Ha vinto giocatore 1!");
    	else if(pl == 2)
       		lab2.setText("Gioco terminato in pareggio!");
    	
    	if(pl == 1 && liv == -1) {
       		lab2.setText("Ha vinto giocatore 2!");
    	}else if(pl == 1 && liv != -1){
       		lab2.setText("Ha vinto BOT!");
		}
    }
    
    public void showErrore() {
    	JOptionPane.showMessageDialog(frame, "Errore data cache");
	}
    
    public void showStartPlayer(boolean pl) {
    	lab1.setText("Forza 4 1vs1");
    	if(!pl) {
    		lab2.setText("Inizia giocatore 1:");
    	}else {
    		lab2.setText("Inizia giocatore 2:");
		}
    }
    
    public void showStartPlayer(int pl) {
    	lab1.setText("Forza 4 1vsBOT");
    	if(pl == 0) {
    		lab2.setText("Inizia giocatore 1:");
    	}else {
    		lab2.setText("Inizia BOT:");
		}
    }
    
    public void setGrigliaBianca() {
    	for(JButton[] bb : but)
    		for(JButton b : bb) {
    			b.setIcon(white);
    			b.setEnabled(true);
    		}
    }
    
    public void aggiornaMat() {
    	for(int i = 0; i < 6; i++) {
    		for(int j = 0; j < 7; j++) {
    			if(m.getMat(i, j) == 1)
    				but[i][j].setIcon(yellow);
    			else if(m.getMat(i, j) == 2)
    				but[i][j].setIcon(red);
    			else if(m.getMat(i, j) == 0 || m.getMat(i, j) == 4)
    				but[i][j].setIcon(white);
    		}
    	}
    }
    
    public void mostraFrame(boolean st) {
    	frame.setVisible(st);
    }
    
    public void butBack() {
    	mostraFrame(false);
    	
    	DataFile d = new DataFile();
    	d.updateData(-2);	//rimuove la partita iniziata
		
		m.clearMat();
    	
    	try {
			super.back(2, tipologia);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
    //liv pl si possono toglire
    protected boolean verificaEnd(DataFile file, boolean bot) throws InterruptedException {
		if(m.getVincitore() != 0) {	//vittoria di qualcuno
			aggiornaMat(pl? 1 : 0, liv);
			
			file.updateData(pl? 1 : 0);
	    	
			JFrame2 fr2 = new JFrame2(pl? 1 : 0, liv);		//restituisce valore intero tra i due valori possibili
	    	int k = fr2.getBut();
	    	while(k == -1) {
	    		Thread.sleep(10);
	    		k = fr2.getBut();
	    	}
	    	
	    	if(k == 0) {	//se selezione NO
	    		file.updateData(-1);
	    		System.exit(0);
	    	}
	    	
	    	setGrigliaBianca();
	    	m.clearMat();
	    	
	    	if(liv == -1)
	    	{
	    		mostraFrame(false);
	    		super.showGriglia(0, liv);
	    	}
	    	else {
	    		mostraFrame(false);
	    		super.showLivelli();
	    	}
	    	
	    	return true;
		}else if(m.verificaEnd()) {		//pareggio
			aggiornaMat(2, liv);
	    	JFrame2 fr2 = new JFrame2(3, liv);		//restituisce valore intero tra i due valori possibili
	    	
	    	int k = fr2.getBut();
	    	while(k == -1) {
	    		Thread.sleep(10);
	    		k = fr2.getBut();
	    	}
	    	
	    	if(k == 0) {	//se selezione NO
	    		file.updateData(-1);
	    		System.exit(0);
	    	}
	    	
	    	setGrigliaBianca();
	    	m.clearMat();

	    	if(liv == -1)
	    	{
	    		mostraFrame(false);
	    		showGriglia(0, liv);
	    	}
	    	else
	    	{
	    		mostraFrame(false);
	    		showLivelli();
	    	}
	    	return true;
		}
		return false;
	}

	@Override
	public void run() {
		DataFile file = new DataFile();
		
		if(tipologia == 0) {
			int col = getCol();
			
			try {
				if(riempiCol(col, pl)) {		//se assegnazione è avvenuta con successo
					if(!verificaEnd(file, false))
						pl = !pl;	
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			cambiaLab2(pl);
			
		}else if(tipologia == 1) {
			if(!pl) {
				int col = getCol();
				
				try {
					if(riempiColBot(col, pl)) {
						if(!verificaEnd(file, false))
							pl = !pl;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cambiaLab2Bot(pl);
				
				//if(pl){		//mossa bot
			    col = m.mossaBot(liv);
			    try {
					riempiColBot(col, pl);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			    
			    try {
					if(!verificaEnd(file, true))
						pl = !pl;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			    
			    cambiaLab2Bot(pl);
			}else if(pl){		//mossa bot		//fase inizializzazione/
			    col = m.mossaBot(liv);
			    try {
					riempiColBot(col, pl);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			    
			    try {
					if(!verificaEnd(file, true))
						pl = !pl;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			    
			    cambiaLab2Bot(pl);
			}
		}	
	}
}