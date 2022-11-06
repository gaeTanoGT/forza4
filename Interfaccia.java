
public class Interfaccia {
	
	static MatAlg m = new MatAlg();
	
	int tipologia;
	int liv = -1;
	
	FrameTipologia frTipo;// = new FrameTipologia();
	FrameLivelli frLiv;// = new FrameLivelli();
	FrameGriglia frGri;// = new FrameGriglia();
	
	DataFile file = new DataFile();
	
	/*public void setGriglia(int liv, FrameLivelli frLiv) throws InterruptedException {
		//if(liv == -1) {
		this.liv = liv;
			showGriglia();
		//}
	}*/
	
	public void setColonna(int col, int tipologia, FrameGriglia frGri) throws InterruptedException {
		if(tipologia == 0) {
			
		}else if(tipologia == 1){
			if(!frGri.getPl()) {
				//int col = getColonna(frGri);
				
				if(frGri.riempiColBot(col, frGri.getPl())) {
					if(!verificaEnd(frGri.getPl(), file, false, liv, frGri))
					{
						frGri.setPl(!frGri.getPl());
						//pl = !pl;
					}
					/*else {
						b = false;
					}*/
				}
				frGri.cambiaLab2Bot(frGri.getPl());
			}else if(frGri.getPl()){		//mossa bot
			    //int col = m.mossaBot(liv);
			    
			    frGri.riempiColBot(col, frGri.getPl());
			    
			    if(!verificaEnd(frGri.getPl(), file, true, liv, frGri))
			    {
					frGri.setPl(!frGri.getPl());
					//pl = !pl;
				}
			    /*else {
					b = false;
				}*/
			    
			    frGri.cambiaLab2Bot(frGri.getPl());
			}
		}
	}
	
	public void setTipologia(boolean t) {
		this.tipologia = t? 1 : 0;
		System.out.println("Bella sono stato pigiato. " + tipologia);
		
		//frTipo.mostraFrame(false);
		if(tipologia == 0) {
			try {
				showGriglia(0);
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
		liv = livello;
		//frLiv.mostraFrame(false);
		//System.out.println("W" + livello + tipologia);
		showGriglia(1);/*
		try {
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void back(int page) throws InterruptedException {
		//TODO:passare tipologia come parametro
		if(page == 1 || (page == 2 && tipologia == 0)) {
			System.out.println("15");
			//showTipologia();
			//frTipo.showTipologia();
			//frTipo.mostraFrame(true);
			//frLiv.mostraFrame(false);
			showTipologia();
			//System.exit(0);
    	}else if(page == 2 && tipologia == 1) {
    		System.out.println("23");
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
	public void showGriglia(int tipologia) throws InterruptedException {
		frGri = new FrameGriglia(tipologia);
		//frGri.mostraFrame(true);

		//System.out.println("showGriglia"+tipologia);
		if(tipologia == 1) {
			//frLiv.mostraFrame(false);
			//System.out.println("showGriLiv");
			showGri(liv, frGri);
		}else if(tipologia == 0){
			showGri(frGri);
		}
	}
	
	//sqsqINTERFACCIA 1v1
		public void showGri(FrameGriglia frGri) throws InterruptedException{
			//MatAlg.simulaPareggio();
			
			DataFile file = new DataFile();
			
			while(file.getStato() == -1);
			
			boolean b = true;
			boolean pl = file.getRound(); 	//0: pl1; 1: pl2
			
	    	if(file.getStato() == 0) {		//verifica corretto funzionamento del file
	    		frGri.showErrore();
	    		file.updateData(-1); 	//pulisce a zero il file
	       		System.exit(0);
	    	}
	    	file.updateData(2); 	//incrementa numero partite di 1
			 	
	    	frGri.showGriglia();
	    	frGri.showStartPlayer(pl);
			//System.out.println("IL PLAYER INIZIALE: " + pl);
			while(b) {
				int col = getColonna(frGri);
				
				if(frGri.riempiCol(col, pl)) {		//se assegnazione è avvenuta con successo
					if(!verificaEnd(pl, file, false, -1, frGri))
						pl = !pl;	
				}
				frGri.cambiaLab2(pl);
			}
		}
		
		//sqqsINTERFACCIA 1vsBOT
		public void showGri(int liv, FrameGriglia frGri) throws InterruptedException{
			DataFile file = new DataFile();
			
			System.out.println("show gri is done");
			
			m.setNf(liv);
			frGri.showGriglia();
			
			boolean b = true;
			boolean pl = file.getRound(); 	//0: pl1; 1: pl2
			
			if(file.getStato() == 0) {		//verifica corretto funzionamento del file
				frGri.showErrore();
	    		file.updateData(-1); 	//pulisce a zero il file
	       		System.exit(0);
	    	}
	    	file.updateData(2); 	//incrementa numero partite di 1
			
	    	frGri.showStartPlayer(pl? 1 : 0);		//int: bot    	
			frGri.showGriglia();
			//System.out.println("IL PLAYER INIZIALE: " + pl);
			
			while(b) {
				if(!pl) {
					int col = getColonna(frGri);
					
					if(frGri.riempiColBot(col, pl)) {
						if(!verificaEnd(pl, file, false, liv, frGri))
							pl = !pl;
					}
					frGri.cambiaLab2Bot(pl);
				}else if(pl){		//mossa bot
				    int col = m.mossaBot(liv);
				    
				    frGri.riempiColBot(col, pl);
				    
				    if(!verificaEnd(pl, file, true, liv, frGri))
				    	pl = !pl;
				    
				    frGri.cambiaLab2Bot(pl);
				}	
			}		
		}
	
	/*//INTERFACCIA 1v1
	private void showGri(FrameGriglia frGri) throws InterruptedException{
		//MatAlg.simulaPareggio();
		System.out.println("showGri  1vs1");
		
		//DataFile file = new DataFile();
		
		while(file.getStato() == -1);
		
		boolean b = true;
		boolean pl = file.getRound(); 	//0: pl1; 1: pl2
		
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
		while(b) {
			int col = getColonna(frGri);
			
			if(frGri.riempiCol(col, pl)) {		//se assegnazione è avvenuta con successo
				if(!verificaEnd(pl, file, false, -1, frGri))
					pl = !pl;	
			}
			frGri.cambiaLab2(pl);
		}
	}
	
	//INTERFACCIA 1vsBOT
	private void showGri(int liv, FrameGriglia frGri) throws InterruptedException{
		
		System.out.println("showGri  BOTTTT  " + liv);
		//DataFile file = new DataFile();
		
		m.setNf(liv);
		frGri.showGriglia();
		frGri.mostraFrame(true);
		//frTipo.mostraFrame(false);
		//frLiv.mostraFrame(false);
		
		boolean b = true;
		boolean pl = file.getRound(); 	//0: pl1; 1: pl2
		
		if(file.getStato() == 0) {		//verifica corretto funzionamento del file
    		frGri.showErrore();
    		file.updateData(-1); 	//pulisce a zero il file
       		System.exit(0);
    	}
    	file.updateData(2); 	//incrementa numero partite di 1
		
		frGri.showStartPlayer(pl? 1 : 0);		//int: bot    	
//		frGri.showGriglia();
		//System.out.println("IL PLAYER INIZIALE: " + pl);
		
		//TODO: provare a metterlo in un thread a parte....
		/*do {
			if(!pl) {
				int col = getColonna(frGri);
				
				if(frGri.riempiColBot(col, pl)) {
					if(!verificaEnd(pl, file, false, liv, frGri))
						pl = !pl;
					else {
						b = false;
					}
				}
				frGri.cambiaLab2Bot(pl);
			}else if(pl){		//mossa bot
			    int col = m.mossaBot(liv);
			    
			    frGri.riempiColBot(col, pl);
			    
			    if(!verificaEnd(pl, file, true, liv, frGri))
			    	pl = !pl;
			    else {
					b = false;
				}
			    
			    frGri.cambiaLab2Bot(pl);
			}
		}	while(b);*/
	//}
	
	private int getColonna(FrameGriglia frGri) throws InterruptedException {
		int k = frGri.getCol();
		while(k == -1) {
			Thread.sleep(10);
			k = frGri.getCol();
		}
		
		return k;
	}
	
	private boolean verificaEnd(Boolean pl, DataFile file, boolean bot, int liv, FrameGriglia frGri) throws InterruptedException {
		if(MatAlg.getVincitore() != 0) {	//vittoria di qualcuno
			//b = false;
			frGri.aggiornaMat(pl? 1 : 0, liv);
			
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
	    	
	    	frGri.setGrigliaBianca();
	    	MatAlg.clearMat();
	    	
	    	System.out.println("VITTORIA QUALCUNO: " + liv);
	    	
	    	if(liv == -1)
	    	{
	    		frGri.mostraFrame(false);
	    		showGriglia(0);
	    	}
	    	else {
	    		frGri.mostraFrame(false);
	    		showLivelli();
	    		showGri(liv, frGri);
	    	}
	    	
	    	return true;
		}else if(MatAlg.verificaEnd()) {		//pareggio
			//b = false;
			frGri.aggiornaMat(2, liv);
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
	    	
	    	frGri.setGrigliaBianca();
	    	MatAlg.clearMat();

	    	if(liv == -1)
	    	{
	    		frGri.mostraFrame(false);
	    		showGriglia(0);
	    	}
	    	else
	    	{
	    		frGri.mostraFrame(false);
	    		showLivelli();
	    		showGri(liv, frGri);
	    	}
	    	
	    	return true;
		}
		
		return false;
	}
	
	
}