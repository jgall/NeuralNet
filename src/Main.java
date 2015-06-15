

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;




public class Main {
	static int index = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//create window for visuals
		MainFrame ex = new MainFrame();
        ex.setVisible(true);
            
			System.out.println("test");
			
			ArrayList<Integer> topology = new ArrayList<Integer>();
			ArrayList<Double> resultVals = new ArrayList<Double>();
			topology.add(2);
			topology.add(8);
			topology.add(8);
			topology.add(8);
			topology.add(8);
			topology.add(1);
			
			Net myNet = new Net(topology);
			
			ArrayList<Double> inputVals = new ArrayList<Double>();
			inputVals.add(0.0);
			inputVals.add(0.0);
			ArrayList<Double> targetVals = new ArrayList<Double>();
			targetVals.add(0.0);
			
			//initial run
			createXORData(inputVals, targetVals);
			myNet.feedForward(inputVals);
			myNet.backProp(targetVals);
			myNet.getResults(resultVals);
			
			System.out.println(inputVals + " Result: " + resultVals.get(0) + " Target: " + targetVals);
			
			
			for(int i = 0; i < 100000; i++) {
				createXORData(inputVals, targetVals);
				myNet.feedForward(inputVals);
				myNet.backProp(targetVals);
				myNet.getResults(resultVals);
				System.out.println(inputVals + " Result: " + resultVals.get(0) + " Target: " + targetVals);
			}
			
			for(int i = 0; i < myNet.getLayers().size(); i++) {
				for(int n = 0; n < myNet.getLayers().get(i).getSize(); n++) {
					for(int j = 0; j < myNet.getLayers().get(i).getNeuron(n).getOutputWeights().size(); j++) {
						System.out.println(myNet.getLayers().get(i).getNeuron(n).getOutputWeights().get(j).getWeight());
					}
				}
			}
	}
	
	public static void createXORData(ArrayList<Double> inputVals, ArrayList<Double> outputVals){
		
		Double random1 = Math.random();
		random1 = random1/2;
		Double random2 = Math.random();
		random2 = random2/2;
		inputVals.set(0, random1);
		inputVals.set(1, random2);
		outputVals.set(0, random1+random2);
	}
	
	
}

