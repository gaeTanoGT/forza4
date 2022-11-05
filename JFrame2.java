import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFrame2 extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private int but = -1;

	public JFrame2(int pl, int liv) {
		JPanel[] pan = new JPanel[4];
		JLabel[] lab = new JLabel[4];
		
		JButton si = new JButton();
		JButton no = new JButton();
		
		si.setVisible(true);
		si.setLayout(null);
		si.setBounds(75, 105, 60, 30);
		si.setVisible(true);
		si.setFocusPainted(false);
		si.setText("SI");
		
		no.setVisible(true);
		no.setLayout(null);
		no.setBounds(165, 105, 60, 30);
		no.setVisible(true);
		no.setFocusPainted(false);
		no.setText("NO");
		
		for(int i = 0; i < 4; i++) {
			pan[i] = new JPanel();
			lab[i] = new JLabel();
			
			if(i != 1)
				lab[i].setHorizontalTextPosition(JLabel.LEFT);
			pan[i].add(lab[i]);
			this.add(pan[i]);
		}
		lab[0].setFont(new Font("MV Boli", Font.PLAIN, 15));
		lab[1].setFont(new Font("MV Boli", Font.PLAIN, 10));
		lab[2].setFont(new Font("MV Boli", Font.PLAIN, 10));
		lab[3].setFont(new Font("MV Boli", Font.PLAIN, 10));
		
		if(pl == 1 && liv == -1)
			lab[0].setText("Ha vinto GIOCATORE 2! ");
		else if(pl == 1 && liv != -1)
			lab[0].setText("Ha vinto BOT");
		else if(pl == 0)
			lab[0].setText("Ha vinto GIOCATORE 1! ");
		else
			lab[0].setText("Gioco terminato in pareggio! ");
		
		lab[1].setText("Resoconto punti:");
		
		if(liv == -1)		//se la partita è tra due giocatori
			lab[2].setText(DataFile.getResoconto());
		else
			lab[2].setText(DataFile.getResocontoBot());
		
		lab[3].setText("Vuoi fare un'altra partita?");
		
		pan[0].setBounds(0, 0, 300, 30);
		pan[1].setBounds(0, 30, 250, 20);
		pan[2].setBounds(0, 50, 280, 20);
		pan[3].setBounds(0, 80, 250, 20);
		
		si.addActionListener(e -> setBut(1));
		no.addActionListener(e -> setBut(0));
		
		this.setSize(300, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Point p = new Point(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		this.add(si);
		this.add(no);
		
		this.setTitle("Partita terminata");
		this.setLocation(p);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
        this.setVisible(true);
	}
	
	private void setBut(int b) {
		but = b;
		
		setVisible(false);	//rimuove tendina alert
		dispose();
	}
	
	public int getBut() {		//1: si - 0: no - -1: null
		int b = but;
		but = -1;
		return b;
	}

}
