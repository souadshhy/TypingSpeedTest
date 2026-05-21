package logic;

import java.util.ArrayList; // dynamic size

import model.TestResult;

public class AnalyticsManager {

    public double getAverageWPM(
            ArrayList<TestResult> results) {

        if (results.isEmpty()) {

            return 0;

        }

        double total = 0;

        for (TestResult result : results) {

            total += result.getWpm();

        }

        return total / results.size();

    }
public double getBestWPM(
        ArrayList<TestResult> results) {

    if (results.isEmpty()) {

        return 0;

    }
    
    double best = results.get(0).getWpm();

    for (TestResult result : results) {

        if (result.getWpm() > best) {

            best = result.getWpm();

        }

    }

    return best;

}
public double getAverageAccuracy(
        ArrayList<TestResult> results) {

    if (results.isEmpty()) {

        return 0;

    }

    double total = 0;

    for (TestResult result : results) {

        total += result.getAccuracy();

    }

    return total / results.size();

}
public int getTotalTests(
        ArrayList<TestResult> results) {

    return results.size();

}
}