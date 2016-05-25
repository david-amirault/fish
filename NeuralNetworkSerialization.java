import java.util.*;
import java.io.*;

public class NeuralNetworkSerialization {
	// public static void main(String[] args) {
	// 	try {
	// 		FileOutputStream f_out = new FileOutputStream("latest.nnt");
	// 		try {
	// 			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
	// 			int inputs = 5;
	// 			List<Integer> sizes = new ArrayList<Integer>(4);
	// 			sizes.add(4);
	// 			sizes.add(3);
	// 			sizes.add(5);
	// 			sizes.add(3);
	// 			NeuralNetwork Network = new NeuralNetwork(inputs, sizes, 0.1);
	// 			Network.printTest();
	// 			obj_out.writeObject(Network);
	// 		}
	// 		catch (IOException io) {}
	// 	}
	// 	catch (FileNotFoundException fnf) {}
	// }
	public static void main(String[] args) {
		try {
			FileInputStream f_in = new FileInputStream("latest.nnt");
			try {
				ObjectInputStream obj_in = new ObjectInputStream(f_in);
				try {
					Object obj = obj_in.readObject();
					NeuralNetwork Network = null;
					if (obj instanceof NeuralNetwork) {
						Network = (NeuralNetwork) obj;
					}
					Network.printTest();
				}
				catch (ClassNotFoundException cnf) {}
			}
			catch (IOException io) {}
		}
		catch (FileNotFoundException fnf) {}
	}
}