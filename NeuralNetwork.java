import java.util.*;

public class NeuralNetwork {
	private List<List<Perceptron>> network;

	public NeuralNetwork(int inputs, List<Integer> sizes) {
		network = new ArrayList<List<Perceptron>>(sizes.size());
		for (int i = 0; i < sizes.size(); i++) {
			List<Perceptron> col = new ArrayList<Perceptron>(sizes.get(i));
			if (i==0) {
				for (int j = 0; j < sizes.get(i); j++) {
					col.add(new Perceptron(inputs));
				}			
			}
			else {
				for (int j = 0; j < sizes.get(i); j++) {
					col.add(new Perceptron(sizes.get(i-1));
				}				
			}
			network.add(col);
		}
	}

	public List<Double> out(List<Double> init) {
		List<Double> outArray = init;
		List<Double> dummyArray;
		for (int col = 0; col < network.size(); col++) {
			dummyArray = new ArrayList<Double>(network.get(col).size());
			//remove recursion
		}
		if (col == 0) {
			for (int i = 0; i < outArray.size(); i++) {
				outArray.add(network.get(col).get(i).out(out(col-1)));
			}
		}
		else {
			for (int i = 0; i < outArray.size(); i++) {
				outArray.add(network.get(col).get(i).out(input));
			}
		}
	}

	public List<Double> out(List<Double> init) {
		input = init;
		return out(network.size()-1);
	}

	private double error(int nodeId, List<Double> init, List<Double> expected) {
		return out(init).get(nodeId)-expected.get(nodeId);
	}

	public double totalError(List<Double> init, List<Double> expected) {
		double total = 0;
		List<Double> res = out(init);
		for (int i = 0; i < res.size(); i++) {
			total += Math.pow(res.get(i) - expected.get(i),2);
		}
		return total/2;
	}

	public void trainStep(List<Double> init, List<Double> expected) {

	}

	private double phiprime(double x) {
		return -Math.exp(-x)/Math.pow(1+Math.exp(-x),2);
	}
}