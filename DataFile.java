import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataFile {
	String file = "data.txt";
	File f = new File(file);
	
	static Integer[] data = new Integer[3];
	int stato = -1;
	
	public DataFile() {
		
		
		for(int i = 0; i < 3; i++)
			data[i] = 0;
		
		if(f.exists()) {
			System.out.println("File riconosciuto");
			setStato(true);
		}else {
			System.out.println("Errore riconoscimento File");
			creaFile();
		}
		
		getData();
	}
	
	private void creaFile() {
		
		try {
			if(f.createNewFile()) {
				System.out.println("File creato con successo");
				if(setup()) 
					setStato(true);
				else 
					setStato(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERRORE CREAZIONE FILE");
		}
	}
	
	private boolean setup() {
		try {
			FileWriter out = new FileWriter(file);
			out.write("0\n0\n0\n");
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	private void setStato(boolean s) {
		if(s)
			stato = 1;
		else 
			stato = 0;
	}
	
	public int getStato() {
		return stato;
	}
	
	public boolean getRound() {		
		//0: pl1; 1: pl2
		
		if(data[0] % 2 == 0)
			return false;
		else
			return true;
	}
	
	public int getVittPl1() {
		return data[1];
	}
	
	public int getVittPl2() {
		return data[2];
	}
	
	private void getData() {
		try (FileReader in = new FileReader(file)) {
			for(int i = 0; i < 3; i++) {
				char ch = (char)in.read();				
				while(ch != '\n') {
					int num = Character.getNumericValue(ch);
					if(num != -1) {
						data[i] = data[i] * 10 + num;
					}
					
					ch = (char)in.read();
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateData(int vincitore) {
		/*	-1: partita interrotta
			0: vittria pl1
			1: vittoria pl2
			2: pareggio
		*/
		
		if(vincitore == 0) 
			data[1]++;
		else if(vincitore == 1) 
			data[2]++;
		else if(vincitore == 2) 
			data[0]++;
		else if(vincitore == -1) {
			data[0] = 0;
			data[1] = 0;
			data[2] = 0;
		}else if(vincitore == -2) {
			data[0]--;
		}
		
		updateFile();
	}

	private void updateFile() {		
		try {
			FileWriter out = new FileWriter(file, false);
			
			for(int i = 0; i < 3 ; i++) {
				out.write(data[i] + "\n");	
			}			
			out.close();
		} catch (Exception e) {
		}
	}
	
	public void removeMatch() {
		getData();
		
		data[0]--;
		
		updateFile();
	}
	
	public static String getResoconto() {
		return "    Player 1: " + data[1] + " - Player 2: " + data[2];
	}
	
	public static String getResocontoBot() {
		return "    Player 1: " + data[1] + " - BOT: " + data[2];
	}
}
