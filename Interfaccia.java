import java.awt.Dimension;
import java.awt.Point;

public class Interfaccia {
	MatAlg m = new MatAlg();
	
	FrameTipologia frTipo;
	FrameLivelli frLiv;
	FrameGriglia frGri;
	
	int tipologia;
	
	static Dimension defaultSize = new Dimension();
	static boolean isSized = false;
	static Point defaultPosition = new Point();
	static boolean isMoved = false;
	
	public static boolean isSized() {
		return isSized;
	}

	public static void setSized(boolean isSized) {
		Interfaccia.isSized = isSized;
	}

	public static boolean isMoved() {
		return isMoved;
	}

	public static void setMoved(boolean isMoved) {
		Interfaccia.isMoved = isMoved;
	}

	public Point getDefaultPosition() {
		return defaultPosition;
	}

	public void setDefaultPosition(Point defaultPosition) {
		Interfaccia.defaultPosition = defaultPosition;
	}

	public Dimension getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(Dimension defaultSize) {
		Interfaccia.defaultSize = defaultSize;
		//System.err.println(defaultSize);
	}

	public int getDefaultWidth() {
		return (int)defaultSize.getWidth();
	}
	public int getDefaultHeight() {
		return (int)defaultSize.getHeight();
	}
	
	public void setDefaultWidth(int width) {
		Interfaccia.defaultSize.setSize(width, defaultSize.getHeight());
	}
	public void setDefaultHeight(int height) {
		Interfaccia.defaultSize.setSize(defaultSize.getWidth(), height);
	}
	
	public void setTipologia(boolean t) {
		this.tipologia = t? 1 : 0;
		//System.out.println("setTipologia: " + tipologia);
		
		DataFile d = new DataFile();
		d.updateData(-1);
		
		if(tipologia == 0) {
			try {
				showGriglia(0, -1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if(tipologia == 1){
			try {
				showLivelli();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setLivello(int livello) throws InterruptedException {
		showGriglia(1, livello);
	}
	
	public void back(int page, int tipologia) throws InterruptedException {
		System.out.println(page + " " + tipologia);
		if(page == 1 || (page == 2 && tipologia == 0)) {
			showTipologia();
    	}else if(page == 2 && tipologia == 1) {
    		System.err.println("23");
    		showLivelli();
    	}
	}
	
	public void showTipologia() throws InterruptedException {
		frTipo = new FrameTipologia();
		tipologia = 2;
		frTipo.mostraFrame(true);
	}
	public void showLivelli() throws InterruptedException {
		frLiv = new FrameLivelli(); 
	
		frLiv.mostraLivelli();
		frLiv.mostraFrame(true);
	}
	
	public void showGriglia(int tipologia, int liv) throws InterruptedException {
		frGri = new FrameGriglia(tipologia);
		frGri.setLiv(liv);
		frGri.setTipologia(tipologia);
		m.clearMat();
		if(tipologia == 1) {
			showGri(frGri.getLiv(), frGri);
		}else if(tipologia == 0){
			showGri(frGri);
		}
	}
	
	//INTERFACCIA 1v1
	private void showGri(FrameGriglia frGri) throws InterruptedException{
		//MatAlg.simulaPareggio();
		//System.out.println("showGri  1vs1");
		
		DataFile file = new DataFile();
		
		while(file.getStato() == -1);

		boolean pl = file.getRound(); 	//0: pl1; 1: pl2
		frGri.setPl(pl);
		
    	if(file.getStato() == 0) {		//verifica corretto funzionamento del file
    		frGri.showErrore();
    		file.updateData(-1); 	//pulisce a zero il file
       		System.exit(0);
    	}
    	file.updateData(2); 	//incrementa numero partite di 1
		 	
		frGri.showGriglia();
		frGri.mostraFrame(true);
		frGri.showStartPlayer(pl);
	}
	
	//INTERFACCIA 1vsBOT
	private void showGri(int liv, FrameGriglia frGri) throws InterruptedException{
		DataFile file = new DataFile();
		
		m.setNf(liv);
		frGri.showGriglia();
		
		boolean pl = file.getRound(); 	//0: pl1; 1: pl2		//player iniziale
		frGri.setPl(pl);
		
		if(file.getStato() == 0) {		//verifica corretto funzionamento del file
			frGri.showErrore();
    		file.updateData(-1); 	//pulisce a zero il file
       		System.exit(0);
    	}
    	file.updateData(2); 	//incrementa numero partite di 1
		
    	frGri.showStartPlayer(pl? 1 : 0);		//int: bot    	
    	frGri.showGriglia();
		
    	if(pl) {
    		Thread frThread = new Thread(frGri);
        	frThread.start();
    	}		
	}	
}