import java.awt.*;
import javax.swing.*;

public abstract class GraphGenes extends JPanel {
	private Polygon p = new Polygon();
	static Polygon p1 = new Polygon();
	
	protected GraphGenes() {
		drawFunction();
	}
	
	abstract double f(int x);
	
	public void drawFunction() {
		for(int x = 0; x < 300; x++) {
			p.addPoint(x + 200, 100 - (int) f(x));
		}
	}
	
	
	public void rePainting() {
		for(int i = 0; i < 300; i++) {
			repaint();
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawPolyline(p.xpoints,p.ypoints,p.npoints);
		g.drawLine(100, 0, 100, 650);
		g.drawLine(0, 500, 1000, 500);
		g.drawString("Run(Cycle)", 225, 550);
		g.drawString("Performance(Batteries)", 10, 325);
		drawFunction();
	}
	public static void Draw() {
		for(int x = 0; x < 300; x++) {
			p1.addPoint(x + 200, 100 - (int)f1(x));
		}
	}
	
	public static double f1(double y) {
		return 100 * RobotGene.TotalPerf[(int)y]; 
	}
}