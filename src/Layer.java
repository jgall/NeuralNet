import java.util.ArrayList;


public class Layer {

	public ArrayList<Neuron> neurons = new ArrayList<Neuron>();
	
	Layer() {
		
	}
	
	public int getSize() {
		return neurons.size();
	}
	
	public void addNeuron(Neuron n) {
		neurons.add(n);
	}
	
	public Neuron getNeuron(int i) {
		return neurons.get(i);
	}

	public void deleteNeuron(int index) {
		neurons.remove(index);
	}

	public void deleteConnectionsToNeuron(int index) {
		for(Neuron neuron : neurons) {
			neuron.removeConnection(index);
		}
	}
}
