import java.util.ArrayList;


public class Net {
	
	private int numLayers;
	private ArrayList<Layer> mLayers = new ArrayList<Layer>();
	private Double m_error;
	
	Net(ArrayList<Integer> topology) {
		numLayers = topology.size();
		for(int layerNum = 0; layerNum < numLayers; layerNum++) {
			//create a new layer
			mLayers.add(new Layer());
			
			//determine number of outputs for this layer (number of neurons in the next layer)
			int numOutputs;
			if(layerNum == topology.size() - 1) {
				numOutputs = 0;
			} else {
				numOutputs = topology.get(layerNum + 1);
			}
			
			//add neurons plus bias neuron per layer
			for(int neuronNum = 0; neuronNum <= topology.get(layerNum); ++neuronNum) {
				mLayers.get(mLayers.size()-1).addNeuron(new Neuron(numOutputs, neuronNum));
				System.out.println("made a neuron");
			}
			//force bias nodes to 1.0
			mLayers.get(mLayers.size() - 1).getNeuron(mLayers.get(mLayers.size() - 1).getSize() - 1).setOutputVal(1.0);
		}
	}
	
	public void feedForward(ArrayList<Double> inputVals) {
		//make sure we have necessary conditions for this to run
		assert(inputVals.size() == mLayers.get(0).getSize() - 1);
		
		for(int i = 0; i < inputVals.size(); i++) {
			mLayers.get(0).getNeuron(i).setOutputVal(inputVals.get(i));
		}
		
		//forward propagate
		for(int layerNum = 1; layerNum < mLayers.size(); layerNum++) {
			Layer prevLayer = mLayers.get(layerNum - 1);
			for(int n = 0; n < mLayers.get(layerNum).getSize() - 1; n++) {
				mLayers.get(layerNum).getNeuron(n).feedForward(prevLayer);
			}
		}
	}
	
	public void backProp(ArrayList<Double> targetVals) {
		//calculate net error to minimize (using Root Mean Square Error)
		Layer outputLayer = mLayers.get(mLayers.size() - 1);
		m_error = 0.0;
		for(int n = 0; n < outputLayer.getSize() - 1; n++) {
			double delta = targetVals.get(n) - outputLayer.getNeuron(n).getOutputVal();
			m_error += delta * delta;
		}
		m_error /= outputLayer.getSize() - 1;
		m_error = Math.sqrt(m_error); //RMS
		
		
		
		//calculate output layer gradients
		for(int n = 0; n < outputLayer.getSize() - 1; n++) {
			outputLayer.getNeuron(n).calcOutputGrads(targetVals.get(n));
		}
		
		// calculate gradients on hidden layers
		for(int layerNum = mLayers.size() - 2; layerNum > 0; layerNum--) {
			Layer hiddenLayer = mLayers.get(layerNum);
			Layer nextLayer = mLayers.get(layerNum + 1);
			
			for(int n = 0; n < hiddenLayer.getSize(); n++) {
				hiddenLayer.getNeuron(n).calcHiddenGradients(nextLayer);
			}
		}
		
		//for all layers from outputs to first hidden layer, update connection weights
		for(int layerNum = mLayers.size() - 1; layerNum > 0; layerNum--) {
			Layer layer = mLayers.get(layerNum);
			Layer prevLayer = mLayers.get(layerNum - 1);
		
			for(int n = 0; n < layer.getSize() - 1; n++) {
				layer.getNeuron(n).updateInputWeights(prevLayer);
			}
		}
	}
	
	public void getResults(ArrayList<Double> resultVals) {
		resultVals.clear();
		
		for(int n = 0; n < mLayers.get(mLayers.size() - 1).getSize() - 1; n++) {
			resultVals.add(mLayers.get(mLayers.size() - 1).getNeuron(n).getOutputVal());
		}
	}
	
	public ArrayList<Layer> getLayers(){
		return mLayers;
	}

}
