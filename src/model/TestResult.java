package model;

public class TestResult {

    private String username;
    
    private double wpm;

    private double accuracy;

    private int mistakes;

    private int timeInSeconds;

    public TestResult(String username, double wpm,
                  double accuracy,
                  int mistakes,
                  int timeInSeconds) {

    this.username = username;
    
    this.wpm = wpm;

    this.accuracy = accuracy;

    this.mistakes = mistakes;

    this.timeInSeconds = timeInSeconds;

}
    public double getWpm() {
    return wpm;
}

    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
public void setWpm(double wpm) {
    this.wpm = wpm;
}

public double getAccuracy() {
    return accuracy;
}

public void setAccuracy(double accuracy) {
    this.accuracy = accuracy;
}

public int getMistakes() {
    return mistakes;
}

public void setMistakes(int mistakes) {
    this.mistakes = mistakes;
}

public int getTimeInSeconds() {
    return timeInSeconds;
}

public void setTimeInSeconds(int timeInSeconds) {
    this.timeInSeconds = timeInSeconds;
}
}