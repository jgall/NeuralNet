import javax.swing.*;
import java.awt.*;

class DrawPanel extends JPanel {

	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawLine(1,1,10,10);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }
}



public class MainFrame extends JFrame{
	
	public MainFrame() {
		initUI();
	}
	
	
	public final void initUI() {
		
        DrawPanel dpnl = new DrawPanel();
        add(dpnl);

        setSize(500, 350);
        setTitle("Neural Network");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	public void drawNetwork(Net net) {
		for(int i = 0; i < net.getLayers().size(); i++) {
			for(int n = 0; n < net.getLayers().get(i).getSize(); n++) {
				for(int j = 0; j < net.getLayers().get(i).getNeuron(n).getOutputWeights().size(); j++) {
					System.out.println(net.getLayers().get(i).getNeuron(n).getOutputWeights().get(j).getWeight());
				}
			}
		}
	}
//	public static void main(String[] args) {
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                MainFrame ex = new MainFrame();
//                ex.setVisible(true);
//            }
//        });
//    }

}
