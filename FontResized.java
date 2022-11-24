import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;

public class FontResized extends JLabel {
	private static final long serialVersionUID = 1L;
    private static final int MIN_FONT_SIZE = 3;
    private static final int MAX_FONT_SIZE = 240;
    private Graphics g;
    int percent;

	public FontResized(String text, int percent) {
        super(text);
    	this.percent = percent;   
    }

    private Dimension getTextSize(JLabel l, Font font) {
        Dimension size = new Dimension();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        size.width = fm.stringWidth(l.getText());
        size.height = fm.getHeight();
        
        return size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
    }
    
    public void ridimensiona(Dimension d) {
    	if (g == null) {
            return;
        }
    	
        Rectangle r = new Rectangle(d);
        int fontSize = MIN_FONT_SIZE;
        Font f = FontResized.this.getFont();

        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle();
        
        while (fontSize < MAX_FONT_SIZE) {
        	
            r1.setSize(getTextSize(FontResized.this, f.deriveFont(f.getStyle(), fontSize)));
            r2.setSize(getTextSize(FontResized.this, f.deriveFont(f.getStyle(), fontSize + 1)));
            if (r.contains(r1) && !r.contains(r2)) {
                break;
            }
            fontSize++;
            
        }
       
        double size = ((fontSize) * ((double)percent / 100));
        setFont(f.deriveFont(f.getStyle(), (int)size));
        setHorizontalAlignment(JLabel.CENTER);
        repaint();
    }
}