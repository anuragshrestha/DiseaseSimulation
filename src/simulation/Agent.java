package simulation;

import java.util.ArrayList;
import java.util.Random;

public class Agent {

    private final Random random;
    private AgentState agentState;
    static int agentNumber = 0;

    private final Object  lockedAt;
    private final Object  neighbourAt;

    private final Object  healthAt;

    private Point2D location;
    public final int maxX, maxY;

    
    
    private ArrayList<Agent> neighbors;
    private long lastWander, infectedAgents = 0, lastSeek = 0;


    public Agent(Point2D location, Parameter parameters, Object lockedAt, Object neighbourAt, Object healthAt, int maxX){
        this.lockedAt = lockedAt;
        this.neighbourAt = neighbourAt;
        this.healthAt = healthAt;
        this.maxX = maxX;
        agentState = AgentState.VULNERABLE;
        this.location = location;


        neighbors = new ArrayList<>();
        this.random = new Random();
        lastWander = System.currentTimeMillis();

        agentNumber += 1;
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

    public void setHealthState(AgentState agentState){
        synchronized (healthAt){
            this.agentState = agentState;
        }
    }

    public void diseadesTransmitted() throws InterruptedException {
        synchronized (neighbourAt){
            for (Agent a : neighbors){
                if (a.getHealthCondition() == AgentState.VULNERABLE){
                    System.out.println("Vulunerable");
                }
            }
        }
    }
   


    public void start(){

    }
}
