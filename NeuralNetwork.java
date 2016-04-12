import Java.util.*;

public class NeuralNetwork {
	


	private float phiprime(float x) {
		return -Math.exp(-x)/Math.pow(1+Math.exp(-x),2);
	}
}