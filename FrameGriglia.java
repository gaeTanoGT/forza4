import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FrameGriglia extends Interfaccia implements Runnable{
	
	private static String bianco = "C:\\Users\\gaeta\\Documents\\java\\p1v7\\Immagini\\bianco.png";
	private static String giallo = "C:\\Users\\gaeta\\Documents\\java\\p1v7\\Immagini\\giallo.png";
	private static String rosso = "C:\\Users\\gaeta\\Documents\\java\\p1v7\\Immagini\\rosso.png";
	
	static ImageIcon ic1 = new ImageIcon(bianco);
    final static Image img1 = ic1.getImage(); // transform it 
    final static Image newimg1 = img1.getScaledInstance(45,45, java.awt.Image.SCALE_SMOOTH);
    final static ImageIcon white = new ImageIcon(newimg1);
    
    static ImageIcon ic2 = new ImageIcon(giallo);
    final static Image img2 = ic2.getImage(); // transform it 
    final static Image newimg2 = img2.getScaledInstance(45,45, java.awt.Image.SCALE_SMOOTH);
    final static ImageIcon yellow = new ImageIcon(newimg2);
    
    static ImageIcon ic3 = new ImageIcon(rosso);
    final static Image img3 = ic3.getImage(); // transform it 
    final static Image newimg3 = img3.getScaledInstance(45,45, java.awt.Image.SCALE_SMOOTH); 
    final static ImageIcon red = new ImageIcon(newimg3);
    
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    Point p = new Point(500, 500);
	Point a = new Point(dim.width / 2 - 500, dim.height / 2 - 700 / 2);
	
	Thread th;
	
	JFrame frame = new JFrame();
	
    JPanel pan1 = new JPanel();
    JLabel lab1 = new JLabel();
    JPanel pan2 = new JPanel();
    JLabel lab2 = new JLabel();

    JButton butt = new JButton();
    JButton butt2 = new JButton();
    JButton back = new JButton();

    JRadioButton[] rad = new JRadioButton[4];

    static JButton[][] but = new JButton[6][7];

    ButtonGroup gr = new ButtonGroup();

    JPanel f1 = new JPanel();
    JPanel f2 = new JPanel();
    
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
        
      //MATRICE DI BOTTONI
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                but[i][j] = new JButton();
                //setGrigliaBianca(but[i][j]);
                but[i][j].setIcon(white);
                but[i][j].setBounds(3 + (50 * j), 3 + (50 * i), 50, 50);
                but[i][j].setVisible(false);
                but[i][j].setBorderPainted(false);
                but[i][j].setFocusPainted(false);
                but[i][j].setContentAreaFilled(false);
                but[i][j].setOpaque(true);
                int l = j;
                but[i][j].addActionListener(e -> setCol(l));
            }
        }
        
        pan2.setLayout(null);
        pan2.setVisible(true);
        pan2.setBounds(0,50,500,50);
        lab2.setLayout(null);
        lab2.setHorizontalAlignment(JLabel.CENTER);
        lab2.setFont(new Font("1", Font.ITALIC, 20));
        lab2.setBounds(100,0,300,50);
        
        //back button
        back.setText("Indietro");
        back.setFocusPainted(false);
        back.addActionListener(e -> butBack());
        back.setBounds(30, 0, 80, 35);
        back.setVisible(true);
        pan2.add(back);

        frame.setTitle("Forza 4");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        pan1.add(lab1);
        f1.add(pan1);
        
        pan2.add(lab2);
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
        
        f2.setBounds(65, 120, 357, 307);
        f2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));  

        f2.removeAll();
        
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
            	but[i][j].setVisible(true);
                f2.add(but[i][j]);        
            }
        }
        
        frame.repaint();
        frame.add(f1);
        frame.add(f2);
        
        frame.setSize(500, 500);
        frame.setLocation(a);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    private void setCol(int col){
    	this.col = col;
  
    	if(MatAlg.getEnd(col) != -1) {
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
    		f = MatAlg.getEnd(col);		//ultima riga disponibile
    	}else {
    		f = MatAlg.getVrig();
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
    	MatAlg.setGettone(f, col, pl, tipologia);
    	return true;
    }
    
    public boolean riempiCol(int col, boolean pl) throws InterruptedException{
    	//ottengo coordinate per la transizione
    	int f = 0;
   		f = MatAlg.getEnd(col);		//ultima riga disponibile
    	
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
    	MatAlg.setGettone(f, col, pl, tipologia);
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
    			if(MatAlg.getMat(i, j) == 3 || MatAlg.getMat(i, j) == 4)
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
    
    public static void aggiornaMat() {
    	for(int i = 0; i < 6; i++) {
    		for(int j = 0; j < 7; j++) {
    			if(MatAlg.getMat(i, j) == 1)
    				but[i][j].setIcon(yellow);
    			else if(MatAlg.getMat(i, j) == 2)
    				but[i][j].setIcon(red);
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
		if(MatAlg.getVincitore() != 0) {	//vittoria di qualcuno
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
	    	
	    	System.out.println("VITTORIA QUALCUNO: " + liv);
	    	
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
		}else if(MatAlg.verificaEnd()) {		//pareggio
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
			
			m.stampaMat();
			System.out.println();
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
				System.out.println("LIVELLO DI RUN " + liv);
			    col = m.mossaBot(liv);
			    System.out.println("colllll: " + col);
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
			}else if(pl){		//mossa bot		//fase inizializzazione
			System.out.println("LIVELLO DI RUN " + liv);
			    col = m.mossaBot(liv);
			    System.out.println("colllll: " + col);
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