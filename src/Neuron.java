import java.util.ArrayList;


public class Neuron {

	private Double m_outputVal = 0.0;
	private ArrayList<Connection> m_outputWeights = new ArrayList<Connection>();
	private int m_myIndex;
	private Double m_gradient = 0.0;
	private Double eta = 0.15, alpha = 0.2; //both are between 0 and 1
	
	Neuron(int numOutputs, int myIndex){
		m_myIndex = myIndex;
		for(int c = 0; c < numOutputs; c++) {
			m_outputWeights.add(new Connection());
			
		}
		
	}

	public void removeConnection(int index) {
		m_outputWeights.remove(index);
	}
	
	public void setOutputVal(Double val) {
		m_outputVal = val;
	}
	
	public Double getOutputVal() {
		return m_outputVal;
	}
	
	public Double getGradient(){
		return m_gradient;
	}
	
	public ArrayList<Connection> getOutputWeights(){
		return m_outputWeights;
	}
	
	
	
	public void feedForward(Layer prevLayer){
		Double sum = 0.0;
		
		//sum previous layer's outputs that are our inputs
		//sum bias node from previous layer
		
		for(int n = 0; n < prevLayer.getSize(); n++) {
			sum += prevLayer.getNeuron(n).getOutputVal() *
					prevLayer.getNeuron(n).getOutputWeights().get(m_myIndex).getWeight();
		}
		
		m_outputVal = transferFunction(sum);
		
	}
	
	public Double transferFunction(Double x){
		// tanh - output range [-1.0 .. 1.0]
		return Math.tanh(x);
	}
	
	public Double transferFunctionDerivative(Double x) {
		Double num = Math.tanh(x);
		return 1 - num*num;
	}

	public void calcOutputGrads(Double x) {
		//TODO implement this
		Double delta = x - m_outputVal;
		m_gradient = delta * transferFunctionDerivative(m_outputVal);
	}
	
	public void calcHiddenGradients(Layer nextLayer) {
		//TODO implement this
		double dow = sumDOW(nextLayer);
		m_gradient = dow * transferFunctionDerivative(m_outputVal);
	}
	
	public void updateInputWeights(Layer prevLayer) {
		//TODO implement this
		//the weights to be updated are in the Connection class
		//in the neurons in the preceding layer
		for(int n = 0; n < prevLayer.getSize(); n++) {
			Neuron neuron = prevLayer.getNeuron(n);
			Double oldDeltaWeight = neuron.getOutputWeights().get(m_myIndex).getDeltaWeight();
			//new weight composed of learning rate (eta) times
			Double newDeltaWeight = eta * neuron.getOutputVal() * m_gradient + alpha * oldDeltaWeight;
			
			neuron.getOutputWeights().get(m_myIndex).setDeltaWeight(newDeltaWeight);
			Double oldWeight = neuron.getOutputWeights().get(m_myIndex).getWeight();
			neuron.getOutputWeights().get(m_myIndex).setWeight(oldWeight + newDeltaWeight);
		}
	}
	
	public Double sumDOW(Layer nextLayer) {
		double sum = 0.0;
		
		//sum our contributions of the errors at the nodes we feed
		for(int n = 0; n < nextLayer.getSize() - 1; n++) {
			sum += m_outputWeights.get(n).getWeight() * nextLayer.getNeuron(n).getGradient();
		}
		
		return sum;
	}
	
	
}
