

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main {
    static int index = 0;

    //constants
    static final int TRAINING_ITERATIONS = 1000000;
    static final String OUTPUT_FILE_PATH = "saved-network.txt";

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Beginning Main Thread");

        ArrayList<Integer> topology = new ArrayList<Integer>();
        ArrayList<Double> resultVals = new ArrayList<Double>();
        topology.add(8);
        topology.add(8);
        topology.add(8);
        topology.add(4);

        Net myNet = new Net(topology);

        ArrayList<Double> inputVals = new ArrayList<Double>();
        inputVals.add(0.0);
        inputVals.add(0.0);
        inputVals.add(0.0);
        inputVals.add(0.0);
        //plus
        inputVals.add(0.0);
        inputVals.add(0.0);
        inputVals.add(0.0);
        inputVals.add(0.0);

        ArrayList<Double> targetVals = new ArrayList<Double>();
        targetVals.add(0.0);
        targetVals.add(0.0);
        targetVals.add(0.0);
        targetVals.add(0.0);

        //initial run
        createBinaryData(inputVals, targetVals, 0);
        myNet.feedForward(inputVals);
        myNet.backProp(targetVals);
        myNet.getResults(resultVals);

        System.out.println(inputVals + " Result: " + resultVals + " Target: " + targetVals);


        trainNetwork(myNet, inputVals, targetVals, TRAINING_ITERATIONS);

        writeOutNetwork(myNet, OUTPUT_FILE_PATH);
    }

    public static void trainNetwork(Net network, ArrayList inputValues, ArrayList targetValues, int iterations) {
        ArrayList<Double> resultVals = new ArrayList<Double>();
        for (int i = 0; i < iterations; i++) {
            createBinaryData(inputValues, targetValues, i);
            network.feedForward(inputValues);
            network.backProp(targetValues);
            network.getResults(resultVals);
            if (i % 1000 == 0) {
                for (int j = 0; j < resultVals.size(); j++) {
                    resultVals.set(j, (double) Math.round(resultVals.get(j)));
                }
                System.out.print(inputValues + " Result: " + resultVals + " Target: " + targetValues);
                if (targetValues.get(0).equals(resultVals.get(0)) && targetValues.get(1).equals(resultVals.get(1))
                        && targetValues.get(2).equals(resultVals.get(2)) && targetValues.get(3).equals(resultVals.get(3))) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("nope :(");
                }
            }
        }
    }

    public static void pruneNetwork(Net network) {
        
    }

    public static void writeOutNetwork(Net network, String outputPath) {
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get(outputPath), StandardCharsets.UTF_8);
            writer.write("4 Bit Binary Addition Neural Network");
            writer.newLine();
            for (int i = 0; i < network.getLayers().size(); i++) {
                for (int n = 0; n < network.getLayers().get(i).getSize(); n++) {
                    for (int j = 0; j < network.getLayers().get(i).getNeuron(n).getOutputWeights().size(); j++) {
                        System.out.println("Layer: " + i + " Neuron: " + n + " Output: " + j + " Connection weight: " + network.getLayers().get(i).getNeuron(n).getOutputWeights().get(j).getWeight());
                        writer.write("Layer: " + i + " Neuron: " + n + " Output: " + j + " Connection weight: " + network.getLayers().get(i).getNeuron(n).getOutputWeights().get(j).getWeight());
                        writer.newLine();
                    }
                }
            }
            writer.close();
            System.out.println("Network written to disk");
        } catch (IOException e) {
            System.out.println("Error writing network to disk");
            e.printStackTrace();
        }
    }

    public static void createBinaryData(ArrayList<Double> inputVals, ArrayList<Double> outputVals, int index) {

        int inputInt1 = (int) (Math.random() * 7);
        String input1 = Integer.toBinaryString(inputInt1);
        int inputInt2 = (int) (Math.random() * 7);
        String input2 = Integer.toBinaryString(inputInt2);
        int outputInt = inputInt1 + inputInt2;
        String output = Integer.toBinaryString(outputInt);
        if (index % 1000 == 0) {
            System.out.println("input data: " + input1 + " + " + input2 + " = " + output);
        }
        for (int i = 0; i < 4; i++) {
            if (input1.length() < 4) {
                input1 = "0" + input1;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (input2.length() < 4) {
                input2 = "0" + input2;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (output.length() < 4) {
                output = "0" + output;
            }
        }
        for (int i = 0; i < 4; i++) {
            inputVals.set(i, (double) (int) input1.charAt(i) - 48);
        }
        for (int i = 4; i < 8; i++) {
            inputVals.set(i, (double) (int) input2.charAt(i - 4) - 48);

        }
        for (int i = 0; i < 4; i++) {
            outputVals.set(i, (double) (int) output.charAt(i) - 48);
        }
    }


}

