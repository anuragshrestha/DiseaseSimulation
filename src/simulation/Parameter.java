
package simulation;

/**
 * @author anuragshrestha
 * This method sets all the parameter needed for the agents.
 */
public class Parameter {
    private int incubationTime, sicknessTime;
    private int infectionDistance;
    private double recoveryProb;
    private int maxX, maxY;
    private int initialSick, initialImmune;
    private int speed;
    private boolean move;

    /**
     * Constructor for this class.
     */
    public Parameter(){
        this.incubationTime = 5;
        this.sicknessTime = 10;
        this.recoveryProb = 0.95;
        this.speed = 100;
        this.maxX = 200;
        this.maxY = 200;
        this.move = false;
    }


    /**
     * returns the incubation time
     * @return incubation time
     */
    public int getIncubationTime() {
        return incubationTime;
    }

    /**
     * This method sets the incubation time period
     * @param incubationTime  the time interval between exposure
     * to the virus
     */
    public void setIncubationTime(int incubationTime) {
        this.incubationTime = incubationTime;
    }

    /**
     * This method returns the sickness time.
     * @return sickness time
     */
    public int getSicknessTime() {
        return sicknessTime;
    }

    /**
     * This method sets the sickness time
     * @param sicknessTime the time to get sick
     */
    public void setSicknessTime(int sicknessTime) {
        this.sicknessTime = sicknessTime;
    }

    /**
     * This method returns the infection distance.
     * @return the minimum distance to get infected.
     */
    public int getInfectionDistance() {
        return infectionDistance;
    }

    /**
     * this method set the infection distance
     * @param infectionDistance the distance to get the virus
     */
    public void setInfectionDistance(int infectionDistance) {
        this.infectionDistance = infectionDistance;
    }

    /**
     * This method returns the recovery probability of an agent.
     * @return the probability to recover after being infected
     */
    public double getRecoveryProb() {
        return recoveryProb;
    }

    /**
     * This method sets the recovery probability of an agent
     * @param recoveryProb the probability to recover after
     * being infected
     */
    public void setRecoveryProb(double recoveryProb) {
        this.recoveryProb = recoveryProb;
    }

    /**
     * This method returns of maximum width of simulation
     * @return the max width
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * This method sets the maximum width of simulation
     * @param maxX the maximum width
     */
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    /**
     * @return maximum height of simulation
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * @param maxY maximum width of simulation
     */
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    /**
     * @return number of sick agents initialized
     */
    public int getInitialSick() {
        return initialSick;
    }

    /**
     * @param initialSick number of sick agents
     */
    public void setInitialSick(int initialSick) {
        this.initialSick = initialSick;
    }

    /**
     * @return number of immune agents
     */
    public int getInitialImmune() {
        return initialImmune;
    }

    /**
     * @param initialImmune number of immune agents
     */
    public void setInitialImmune(int initialImmune) {
        this.initialImmune = initialImmune;
    }

    /**
     * @return returns the rate of movement
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed rate of movement.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return is stable or moving.
     */
    public boolean isMoving() {
        return move;
    }

    /**
     * @param move boolean value of move
     */
    public void setMove(boolean move) {
        this.move = move;
    }
}
