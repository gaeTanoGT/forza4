import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

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
    
    JButton back = new JButton();

    JRadioButton[] rad = new JRadioButton[4];
    
    ButtonGroup gr = new ButtonGroup();
    
    protected boolean mostraErrore = true;
    private int liv = -1;

	public FrameLivelli() {
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
        
        //back button
        back.setText("Indietro");
        back.setFocusPainted(false);
        back.addActionListener(e -> {
			System.out.println("BACKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
			butBack();
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
	
	public void mostraLivelli(){
        f2.removeAll();
        
    	mostraErrore = true;
    	
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
        
        lab1.setText("Forza 4 1vsBOT");
    	lab2.setText("Selezionare Livello...");
    	
    	//frame.removeAll();
        frame.repaint();
        frame.add(f1);
        frame.add(f2);

        frame.setSize(500, 430);
        frame.setLocation(a);
        frame.setLayout(null);
        //frame.setVisible(true);
    }

	private void giocaBut() throws InterruptedException{    
        for(int i = 0; i < 4; i++){
            if(rad[i].isSelected()){
            	setLiv(i);
                //liv = i;
            }
        }
        if(mostraErrore && liv == -1)		// && b == false
        {
        	//b = true;
        	mostraErrore = false;
        	JOptionPane.showMessageDialog(frame, "Selezionare un livello...");
        	try {
        		wait();
			} catch (Exception e) {
			} 
        }
        
        //super.setGriglia(getLiv(), this);
        
        mostraFrame(false);
        super.setLivello(liv);
    }
	
	public void setLiv(int liv) {
		this.liv = liv;
		/*try {
			//super.setLivello(liv, this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
			super.back(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
