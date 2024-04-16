/**
 * @author suman
 * This calss is about the agent
 */
package simulation;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;



public class Agent implements Runnable {

    private final Random random;
    private AgentState agentState;
    static int agentNumber = 0;
    private final int rate, num;

    private final Object healthAt, lockedAt, neighbourAt;

    private Point2D location;
    public final int maxX, maxY;
    private final LinkedBlockingDeque<Communication> message;
    private final int incubatingTime, sickTime;
    private final double recoveryProb;
    private final  boolean moveRandomly;
    private final String name;

    
    
    private ArrayList<Agent> neighbors;
    private long lastWander, infectedAgents = 0, lastSeek = 0;


    public Agent(Point2D location, Parameter parameters){

        lockedAt = new Object();
        neighbourAt = new Object();
        healthAt = new Object();
        message = new LinkedBlockingDeque<>();
        agentState = AgentState.VULNERABLE;
        incubatingTime = parameters.getIncubationTime();
        sickTime = parameters.getSicknessTime();
        rate = parameters.getSpeed();
        recoveryProb = parameters.getRecoveryProb();
        moveRandomly = parameters.isMoving();

        this.location = location;
        neighbors = new ArrayList<>();
        this.random = new Random();
        lastWander = System.currentTimeMillis();
        agentNumber += 1;
        num = agentNumber;
        name = "DiseaseSimulation" + agentNumber;
        maxX = parameters.getMaxX();
        maxY = parameters.getMaxY();
    }

    public Point2D getLocation(){

        synchronized (lockedAt){
            return location;
        }
    }


    public AgentState getHealthCondition(){
        synchronized (healthAt){
            return agentState;
        }
    }

    /**
     * sets the list of neighbor of the agents.
     * @param neighbors
     */
    public void setNeighbors(ArrayList<Agent> neighbors){
        synchronized (neighbourAt){
            this.neighbors = neighbors;
        }
    }

    public void setHealthState(AgentState agentState){
        synchronized (healthAt){
            this.agentState = agentState;
        }
    }

    public void diseasesTransmitted() throws InterruptedException {
        synchronized (neighbourAt){
            for (Agent a : neighbors){
                if (a.getHealthCondition() == AgentState.VULNERABLE){
                    a.transmitMessage(new Communication(CommunicationType.MOVING,
                            AgentState.INCUBATING));
                }
            }
        }
    }

    /**
     * sets the message for the agent.
     * @param msg
     */
    public void transmitMessage(Communication msg) throws InterruptedException {
        message.put(msg);
    }

    /**
     * This function makes the agent move around randomly, if they
     * move out of given boundary, it brings back within the given boundary.
     * @param now current time
     * @param rate rate of movement
     */
    private void moveRandomly(long now, int rate){
        double timeLapse = (now - lastWander) / 1000.0;
        double direction = 2 * Math.PI * random.nextDouble();

        // Calculate new position
        double posX = rate * timeLapse * Math.cos(direction);
        double posY = rate * timeLapse * Math.sin(direction);

        synchronized (lockedAt) {
            posX += this.location.getX();
            posY += this.location.getY();

            int margin = 10;
            if (posX > maxX - margin) {
                posX = maxX - margin;
            } else if (posX < margin) {
                posX = margin;
            }

            if (posY > maxY - margin) {
                posY = maxY - margin;
            } else if (posY < margin) {
                posY = margin;
            }

            this.location = new Point2D(posX, posY);
        }
        lastWander = now;
    }

    /**
     * This class begins the agent's life, moving the agents randomly,
     * receiving and transmitting the messages, and infecting neighbors.
     * The thread of the agents end when the health state changes to Dead.
     */
    public void run() {

      Communication communication;

        while (this.getHealthCondition() != AgentState.DEAD) {
            if (moveRandomly && getHealthCondition() != AgentState.DEAD) {
                moveRandomly(System.currentTimeMillis(), rate);
            }
            if (getHealthCondition() == AgentState.SICK) {
                try {
                    diseasesTransmitted();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                communication = message.poll(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                continue;
            }

            if (communication == null) {
                continue;
            }

            switch (communication.getMessageType()) {
                case MOVING:
                    handleTransition(communication);
                    break;
                case STOP:
                    return;
            }
        }
    }


    private void handleTransition(Communication msg) {

        switch (msg.getState()) {
            case INCUBATING:
                if (getHealthCondition() == AgentState.VULNERABLE) {
                    new Thread(new UpdateState(this, AgentState.SICK,
                            incubatingTime * 1000)).start();
                    setHealthState(AgentState.INCUBATING);
                }
                break;
            case SICK:
                if (getHealthCondition() == AgentState.VULNERABLE
                        || getHealthCondition() == AgentState.INCUBATING)
                {
                    if (random.nextDouble() < recoveryProb)
                    {
                        new Thread(new UpdateState(this, AgentState.IMMUNE,
                                sickTime * 1000)).start();
                    } else {
                        // Non-recovery path: SICK -> DEAD
                        new Thread(new UpdateState(this, AgentState.DEAD,
                                sickTime * 1000)).start();
                    }
                    setHealthState(AgentState.SICK);
                }
                break;
            case IMMUNE:
                setHealthState(AgentState.IMMUNE);
                break;
            case DEAD:
                setHealthState(AgentState.DEAD);
                break;
            default:
                break;
        }
    }

    /**
     * This class checks if the agents is moving or stable.
     * @return true if message is empty else false
     */
    public boolean isMoving(){
        return message.isEmpty();
    }

    public void start(){
        new Thread(this).start();
    }
}
