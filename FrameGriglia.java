import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FrameGriglia extends Interfaccia implements WindowListener{
	
	JFrame frame = new JFrame();


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
	
	
	protected boolean ascoltaTipologia = false;
	protected boolean mostraErrore = true;

	//protected boolean tipologia = false;		//false: 1vs1; 	true: BOT
	private int col = -1;
    
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
    
    private int tipologia;
    private boolean pl;
    

    public boolean getPl() {
		return pl;
	}


	public void setPl(boolean pl) {
		this.pl = pl;
	}


	public FrameGriglia(int tipologia) {
    	this.tipologia = tipologia;
    	
    	
    	//addWindowListener(frame);	//necessario per l'implementazione di windowListener
    	
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
        back.addActionListener(e -> {
			System.out.println("BACKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
			//butBack();
		});
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
    
    
    /*public void setShowTipologia() {
    	//f.showTipologia();
    }
    /*@SuppressWarnings("deprecation")
	public void setShowTipologia() {
    	//frame.removeAll();
    	f1.hide();
    	page = 0;
    	ascoltaTipologia = false;
    	
    	
    	//frame.repaint();
    	
    	/*try {
        	for(JRadioButton b : rad){
                b.setVisible(false);
                f2.remove(b);
            }	
		}catch (Exception e) {}*
    	System.out.println("922222222222222222222222222222222222");
    	
    	
    	setShowLiv();
        pan2.remove(back);
    	//f1.removeAll();
    	//f2.removeAll();
    	
    	
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
       
        
    	
        try {
        	for(JRadioButton b : rad){
        		f2.remove(b);
                b.setVisible(false);
                
            }	
		}catch (Exception e) {}
        f2.removeAll();
        
        f2.add(butt);
        f2.add(butt2);
        
        
        frame.repaint();
        frame.add(f1);
        frame.add(f2);

        frame.setSize(500, 300);
        frame.setLocation(a);
        frame.setLayout(null);
        frame.setVisible(true);
    }*/
    
   /* public void setShowLiv() {
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
        back.addActionListener(e -> {
			System.out.println("BACKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
			//butBack();
		});
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
    }*/

   /* public void showLiv(){
        f2.removeAll();
        
        page = 1;
    	z = -1;
    	
        butt.setText("Gioca");
        butt.setFocusPainted(false);
        butt.addActionListener(e -> {
			try {
				giocaBut();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        butt.setBounds(200, 300, 100, 50);
        butt.setVisible(true);

        for(int i = 0; i < 4; i++){
            rad[i] = new JRadioButton();
            rad[i].setFocusPainted(false);
            rad[i].setBounds(75, 100 + (50 * i), 100, 30);
            rad[i].setFocusPainted(false);
            gr.add(rad[i]);
        }

        rad[0].setText("Easy");
        rad[1].setText("Medium");
        rad[2].setText("Hard");
        rad[3].setText("Impossible");
        
        for(JRadioButton b : rad){
            f2.add(b);
        }
       
        f2.add(butt);     //bottone gioca
        
        setShowLiv();
        lab1.setText("Forza 4 1vsBOT");
    	lab2.setText("Selezionare Livello...");
    	
    	//frame.removeAll();
        frame.repaint();
        frame.add(f1);
        frame.add(f2);

        frame.setSize(500, 430);
        frame.setLocation(a);
        frame.setLayout(null);
        frame.setVisible(true);
    }*/

    /*private void giocaBut() throws InterruptedException{    
        for(int i = 0; i < 4; i++){
            if(rad[i].isSelected()){
                z = i;
            }
        }
        if(z == -1 && mostraErrore)		// && b == false
        {
        	//b = true;
        	mostraErrore = false;
        	JOptionPane.showMessageDialog(frame, "Selezionare un livello...");
        	try {
        		wait();
			} catch (Exception e) {
			} 
        }
    }*/
    
    /*private void but1vs1() {
    	System.out.println("1vs1");
    	tipologia = false;
    	ascoltaTipologia = true;
    	//b = true;
    }
    private void butBOT() {
    	System.out.println("1vsBOT");
    	tipologia = true;
    	ascoltaTipologia = true;
    	//b = true;
    }*/
    //private void butBack() throws InterruptedException {
    	//frame.removeAll();
    	//System.out.println("butBack: " + h + b);
    	//if(!h) {
    		//System.out.println("indietro");
    		//Interfaccia.back(page);
    		//b = false;
    		//h = true;
    		//System.out.println("h::: " + h + " " + b);
    		//System.exit(0);
    	//}
    	
    	/*if(page == 1 || (page == 2 && !tipologia)) {
    		//TODO: deve chiamare App per far ripartire l'interfaccia
    		//setShowTipologia();
    	}else if(page == 2 && tipologia) {
    		//setShowLiv();
    	}*/
    /*}
    
    public int getTipologiaSelezionata() {
    	//System.out.println("h: " + h + " " +b);
    	
    	/*if(ascoltaTipologia)
    	{
    		System.out.println("289");
    		ascoltaTipologia = false;
    		//h = false;
    		return f.getTipologia()? 1 : 0;
    	}
    	else*//* {
    		return -1;
		}
    }

    public int getLiv(){
        return z;
    }*/

    public void showGriglia(){

    	mostraErrore = true;
    	
        butt.setVisible(false);
        try {
        	for(JRadioButton b : rad){
                b.setVisible(false);
            }	
		}catch (Exception e) {}        
        

        //setShowLiv();
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
        //frame.setVisible(true);
    }
    
    private void setCol(int col) {
    	this.col = col;
    	
    	try {
			super.setColonna(col, tipologia, this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    	MatAlg.setGettone(f, col, pl);
    	return true;
    }
    
    public boolean riempiCol(int col, boolean pl) throws InterruptedException {
    	//ottengo coordinate per la transizione
    	int f = 0;
   		f = MatAlg.getEnd(col);		//ultima riga disponibile
    	//System.out.println("F: " + f);
    	
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
    	MatAlg.setGettone(f, col, pl);
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
	}

	@Override
	public void windowClosing(WindowEvent e) {
		DataFile d = new DataFile();
		d.updateData(-1);
		System.out.println("Uscita");
	}
	public void windowOpened(WindowEvent e) {
		
	}
	public void windowClosed(WindowEvent e) {
		
	}
	public void windowIconified(WindowEvent e) {
		
	}
	public void windowDeiconified(WindowEvent e) {
		
	}
	public void windowActivated(WindowEvent e) {
		
	}
	public void windowDeactivated(WindowEvent e) {
		
	}
	
}
