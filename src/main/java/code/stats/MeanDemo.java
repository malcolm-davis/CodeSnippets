package code.stats;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class MeanDemo {

    public static void main(String[] args) {
        System.out.println("Start: MeanDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        MeanDemo test = new MeanDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        Mean mean = new Mean();
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();

        for (double dvalue : testWeightsArray) {
            descriptiveStatistics.addValue(dvalue);
        }

        mean.incrementAll(testWeightsArray);

        System.out.println("descriptiveStatistics.getMean()="+descriptiveStatistics.getMean());
        System.out.println("descriptiveStatistics.getGeometricMean()="+descriptiveStatistics.getGeometricMean());
        System.out.println("descriptiveStatistics.getStandardDeviation()=" + descriptiveStatistics.getStandardDeviation());
        System.out.println("mean.evaluate()=" + mean.evaluate(testWeightsArray));

        double agent1Sum = 50.0d;
        double agent1Avg= 10.0d;
        double agent2Sum = 1.0d;
        double agent2Avg = 1.0d;
        double totalTime = agent1Sum + agent2Sum;

        double value = (agent1Sum/(totalTime)*agent1Avg) + (agent2Sum/(totalTime)*agent2Avg);
        System.out.println(value);

    }

    protected double[] testWeightsArray =
        {  10.0, 10.0, 10.0, 10.0, 10.0, 1.0 };
}
