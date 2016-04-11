import Java.util.*;

public class Neuron {
	private List<float> weights;

	public Neuron() {
		weights = new ArrayList<float>(0);
	}

	public Neuron(List<float> weightList) {
		weights = weightList;
	}

	public List<float> getWeights() {
		return weights;
	}

	public float getWeight(int i) {
		return weights.get(i);
	}

	public void setWeights(List<float> weightList) {
		weights = weightList;
	}

	public void setWeight(int i, float weight) {
		weights.set(i,weights);
	}

	public void changeWeight(int i, float change) {
		weights.set(i, weights.get(i)+change);
	}

	public out(List<float> in) {
		float total = 0;
		for (int i = 0; i < in.size(); i++) {
			total += (in.get(i) * weights.get(i));
		}
		return phi(total);
	}

	private float phi(float x) {
		return 1/(1+Math.exp(-x));
	}

	private float phiprime(float x) {
		return -Math.exp(-x)/Math.pow(1+Math.exp(-x),2);
	}
}