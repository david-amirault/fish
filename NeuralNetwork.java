import java.util.*;

public class NeuralNetwork {
	private List<List<Perceptron>> network;
	private double learningRate;

	public NeuralNetwork(int inputs, List<Integer> sizes, double lR) {
		learningRate=lR;
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
					col.add(new Perceptron(sizes.get(i-1)));
				}				
			}
			network.add(col);
		}
	}

	public void setLR(double lR) {
		learningRate = lR;
	}

	public double getLR() {
		return learningRate;
	}

	public List<Double> doOut(List<Double> init) {
		List<Double> outArray = init;
		List<Double> dummyArray;
		double pass = 0;
		for (int col = 0; col < network.size(); col++) {
			dummyArray = new ArrayList<Double>(network.get(col).size());
			for (int i = 0; i < network.get(col).size(); i++) {
				dummyArray.add(network.get(col).get(i).out(outArray));
			}
			outArray = dummyArray;
		}
		return outArray;
	}

	public void out(List<Double> init) {
		List<Double> outArray = init;
		List<Double> dummyArray;
		double pass = 0;
		for (int col = 0; col < network.size(); col++) {
			dummyArray = new ArrayList<Double>(network.get(col).size());
			for (int i = 0; i < network.get(col).size(); i++) {
				pass = network.get(col).get(i).out(outArray);
				dummyArray.add(pass);
				network.get(col).get(i).setOutput(pass);
			}
			outArray = dummyArray;
		}
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
		int col = network.size()-1;
		int i = 0;
		int j = 0;
		int input = 0;
		double pass = 0;
		double totalPass = 0;
		if (col>0) {
			for (i=0; i<network.get(col).size(); i++) {
				pass = network.get(col).get(i).getOutput();
				network.get(col).get(i).setErrorSignal((pass-expected.get(i))*pass*(1-pass));
				for (j=0; j < network.get(col-1).size(); j++) {
					network.get(col).get(i).changeWeight(j,-learningRate*network.get(col).get(i).getErrorSignal()*network.get(col-1).get(j).getOutput());
				}
			}

			for (col--; col>0; col--) {
				for (i=0; i<network.get(col).size(); i++) {
					input = network.get(col).get(i).getWeights().size();
					pass = network.get(col).get(i).getOutput();
					totalPass = 0;
					for (j=0; j < network.get(col+1).size(); j++) {
						totalPass += network.get(col+1).get(j).getErrorSignal()*network.get(col+1).get(j).getWeight(i);
					}
					network.get(col).get(i).setErrorSignal(totalPass*pass*(1-pass));
					for (j=0; j < input; j++) {
						network.get(col).get(i).changeWeight(j,-learningRate*network.get(col).get(i).getErrorSignal()*network.get(col-1).get(j).getOutput());
					}
				}
			}
		}
		
		for (i=0; i < network.get(0).size(); i++) {
			input = network.get(0).get(i).getWeights().size();
			pass = network.get(col).get(i).getOutput();
			totalPass = 0;
			for (j=0; j < network.get(1).size(); j++) {
				totalPass += network.get(1).get(j).getErrorSignal()*network.get(1).getWeight(i);
			}
			network.get(0).get(i).setErrorSignal(totalPass*pass*(1-pass));
			for (j=0; j<input; j++) {
				network.get(0).get(i).changeWeight(j,-learningRate*network.get(0).get(i).getErrorSignal()*init.get(j));
			}
		}
	}

	public void train(List<List<Double>> inputs, List<List<Double>> expected) {
		for (int i = 0; i < inputs.size(); i++) {
			out(inputs.get(i));
			trainStep(inputs.get(i), expected.get(i));
		}
	}

	private double phiprime(double x) {
		return -Math.exp(-x)/Math.pow(1+Math.exp(-x),2);
	}
}