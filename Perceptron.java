import java.util.*;
import java.io.*;

public class Perceptron implements Serializable {
	private List<Double> weights;
	private double output;
	private double errorSignal;

	public Perceptron() {
		weights = new ArrayList<Double>(0);
		double output = 0;
		double errorSignal = 0;
	}

	public Perceptron(ArrayList<Double> weightList) {
		weights = weightList;
		double output = 0;
		double errorSignal = 0;
	}

	public Perceptron(int size) {
		weights = new ArrayList<Double>(size);
		for (int i = 0; i < size; i++) {
			weights.add(1.0/((double)size));
		}
		double output = 0;
		double errorSignal = 0;
	}

	public void setOutput(double ou) {
		output = ou;
	}

	public double getOutput() {
		return output;
	}

	public void setErrorSignal(double es) {
		errorSignal = es;
	}

	public double getErrorSignal() {
		return errorSignal;
	}

	public List<Double> getWeights() {
		return weights;
	}

	public Double getWeight(int i) {
		return weights.get(i);
	}

	public void setWeights(List<Double> weightList) {
		weights = weightList;
	}

	public void setWeight(int i, double weight) {
		weights.set(i,weight);
	}

	public void changeWeight(int i, double change) {
		weights.set(i, weights.get(i)+change);
	}

	public double out(List<Double> in) {
		double total = 0;
		for (int i = 0; i < in.size(); i++) {
			total += (in.get(i) * weights.get(i));
		}
		return phi(total);
	}

	private double phi(double x) {
		return 1.0/(1+Math.exp(-x));
	}
}