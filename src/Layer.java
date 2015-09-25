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

	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}

	public void deleteNeuron(int index) {
		neurons.remove(index);
		for (int i = 0; i < neurons.size(); i++) {
			neurons.get(i).setIndex(i);
		}
	}

	public void deleteConnectionsToNeuron(int index) {
		for(Neuron neuron : neurons) {
			neuron.removeConnection(index);
		}
	}
}
