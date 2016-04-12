import Java.util.*;

public class NeuralNetwork {
	private List<List<Perceptron>> network;
	private List<Double> input;

	public NeuralNetwork(ArrayList<Integer> sizes) {

	}

	private double phiprime(double x) {
		return -Math.exp(-x)/Math.pow(1+Math.exp(-x),2);
	}
}