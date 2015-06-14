
public class Connection {

	private Double weight = 0.0, deltaWeight = 0.0;
	
	Connection(){
		weight = Math.random();
	}

	public Double getWeight() {
		return weight;
	}
	
	public Double getDeltaWeight() {
		return deltaWeight;
	}
	
	public void setWeight(Double w) {
		weight = w;
	}
	public void setDeltaWeight(Double w) {
		deltaWeight = w;
	}
}
