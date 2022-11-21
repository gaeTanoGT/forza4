

public class Interfaccia {


	MatAlg m = new MatAlg();
	
	int tipologia;
	//int liv = -1;
	
	FrameTipologia frTipo;// = new FrameTipologia();
	FrameLivelli frLiv;// = new FrameLivelli();
	FrameGriglia frGri;// = new FrameGriglia();
	
	DataFile file = new DataFile();
	
	
	public void setTipologia(boolean t) {
		this.tipologia = t? 1 : 0;
		System.out.println("Bella sono stato pigiato. " + tipologia);
		
		//frTipo.mostraFrame(false);
		if(tipologia == 0) {
			try {
				showGriglia(0, -1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tipologia == 1){
			try {
				showLivelli();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public void setLivello(int livello) throws InterruptedException {
		//liv = livello;
		//frGri.setLiv(livello);
		//frLiv.mostraFrame(false);
		//System.out.println("W" + livello + tipologia);
		showGriglia(1, livello);/*
		try {
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void back(int page, int tipologia) throws InterruptedException {
		//TODO:passare tipologia come parametro
		System.out.println(page + " " + tipologia);
		if(page == 1 || (page == 2 && tipologia == 0)) {
			System.err.println("15");
			//showTipologia();
			//frTipo.showTipologia();
			//frTipo.mostraFrame(true);
			//frLiv.mostraFrame(false);
			showTipologia();
			//System.exit(0);
    	}else if(page == 2 && tipologia == 1) {
    		System.err.println("23");
    		showLivelli();
    		//liv = -1;
    		//frLiv.mostraFrame(true);
    		//frGri.mostraFrame(false);
    	}
	}
	
	public void showTipologia() throws InterruptedException {
		frTipo = new FrameTipologia();
		//Thread tread1 = new Thread(frTipo);
		//tread1.start();
		//frGri.setShowTipologia();
		//frTipo.setFrameTipologia();
		tipologia = 2;
		frTipo.mostraFrame(true);

		//Thread.sleep(3000);
		System.out.println("211111111111111111111");


		/*int t = frTipo.getTipologiaSelezionata();
		
		while(t == -1) {
			//Thread.sleep(10);
			t = frTipo.getTipologiaSelezionata();
			System.out.println("25" + t);
		}
		System.out.println("schermata: "+t);
		//frTipo.resetTipologia();
		
		//Thread.sleep(3000);
		 
		if(t == 0)
			tipologia = false;
		else 
			tipologia = true;
		
		frTipo.mostraFrame(false);
		System.out.println("Tipologia: " + tipologia);*/
	}
	public void showLivelli() throws InterruptedException {
		frLiv = new FrameLivelli(); 
		
		/*if(tipologia == 1)
		{*/
			frLiv.mostraLivelli();
			frLiv.mostraFrame(true);
	

		    //frLiv.resetLiv();
			//frTipo.mostraFrame(false);
			//showLiv();
			
			//showGriglia();
		//}/*else if(tipologia == 0){/
			/*
			System.out.println("125");
			showGriglia(tipologia);
		}*/
	}
	
	/*private int showLiv() throws InterruptedException {
		
		frLiv.mostraLivelli();
		frLiv.mostraFrame(true);
		//frTipo.mostraFrame(false);
		//frGri.mostraFrame(false);
		
		/*liv = frLiv.getLiv();

	    while(liv == -1)
	    {
	    	Thread.sleep(10);
	    	liv = frLiv.getLiv();
	    }*/
/*
	    frLiv.resetLiv();
	    //frLiv.mostraFrame(false);
	    System.out.println("Livello selezionato: " + liv);
	    return liv;
	}*/
	public void showGriglia(int tipologia, int liv) throws InterruptedException {
		frGri = new FrameGriglia(tipologia);
		frGri.setLiv(liv);
		frGri.setTipologia(tipologia);
		m.clearMat();
		//frGri.addWindowListener(new MyWindowListener());
		//addWindowListener(this);
		//frGri.mostraFrame(true);

		//System.out.println("showGriglia"+tipologia);
		if(tipologia == 1) {
			//frLiv.mostraFrame(false);
			//System.out.println("showGriLiv");
			showGri(frGri.getLiv(), frGri);
		}else if(tipologia == 0){
			showGri(frGri);
		}
	}
	
	//INTERFACCIA 1v1
	private void showGri(FrameGriglia frGri) throws InterruptedException{
		//MatAlg.simulaPareggio();
		System.out.println("showGri  1vs1");
		
		//DataFile file = new DataFile();
		
		while(file.getStato() == -1);
		
		//boolean b = true;
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
		//frTipo.mostraFrame(false);
		//frLiv.mostraFrame(false);
		frGri.showStartPlayer(pl);
		//System.out.println("IL PLAYER INIZIALE: " + pl);
		/*while(b) {
			int col = getColonna(frGri);
			
			if(frGri.riempiCol(col, pl)) {		//se assegnazione è avvenuta con successo
				if(!verificaEnd(pl, file, false, -1, frGri))
					pl = !pl;	
			}
			frGri.cambiaLab2(pl);
		}*/
	}
	
	//INTERFACCIA 1vsBOT
	private void showGri(int liv, FrameGriglia frGri) throws InterruptedException{
		//non so spiegare perchè
		//DataFile file = new DataFile();
		
		m.setNf(liv);
		frGri.showGriglia();
		
		//boolean b = true;
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
		//System.out.println("IL PLAYER INIZIALE: " + pl);
    	
    	//this.liv = liv;
    	//frGri.setLiv(liv);
		
    	if(pl) {
    		Thread frThread = new Thread(frGri);
        	frThread.start();
        	System.out.println("BOT FATTP");	
    	}		
	}	
}