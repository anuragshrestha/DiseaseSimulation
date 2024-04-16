

/**
 * @author suman
 * This calss is to manage the diseases and it's spreading
 */
package simulation;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

public class Manage implements Runnable{
    private int width, height;
    private int transmitDistance;
    private final ArrayList<Agent> agents;
    private int ticks;

    /**
     * Constructor
     * @param width width of simulation in pixel size
     * @param height height of simulation in pixel size
     */
    public Manage(int width, int height){
        this.width = width;
        this.height = height;
        this.agents = new ArrayList<>();
    }

    public Manage(){
        this(200,200);
    }



    /**
     * This function returns list of all agents.
     * @return list of all agents
     */
    public ArrayList<Agent> getAgents(){
        return new ArrayList<>(agents);
    }

    /**
     * This function counts current agents on simulation.
     * @return counts of current agent of particular state of simulation
     */
    public EnumMap<AgentState, Integer> getAgentCounts() {
        EnumMap<AgentState, Integer> currentAgent = new EnumMap<>(AgentState.class);
        for (Agent agent : agents) {
            if (!currentAgent.containsKey(agent.getHealthCondition())) {
                currentAgent.put(agent.getHealthCondition(), 0);
            }
            currentAgent.put(agent.getHealthCondition(), 1 +
                    currentAgent.get(agent.getHealthCondition()));
        }
        return currentAgent;
    }

    /**
     * This function is responsible for instantiating the given number
     * of agents with the given parameters.
     * @param formats
     * @param parameters
     */
    public void makeAgents(FormatAgents formats, Parameter parameters) throws InterruptedException {
        int columns = formats.getColumns();
        int rows = formats.getRows();
        transmitDistance = parameters.getInfectionDistance();

        if (!agents.isEmpty()){
            agents.clear();
        }

        switch(formats.getFormatType()){
            case GRID -> makeGrid(columns, rows, parameters);
            case RANDOMGRID -> makeRandomGrid(columns, rows,
                    formats.getNumberOfAgents(), parameters);
            case RANDOM -> makeRandom(formats.getNumberOfAgents(), parameters);
        }

        int i;

        for (i = 0; i < parameters.getInitialSick() && i < formats.getNumberOfAgents(); i++){
            Communication msg = new Communication(CommunicationType.MOVING, AgentState.SICK);
            agents.get(i).transmitMessage(msg);
        }

        for (; i < parameters.getInitialSick() + parameters.getInitialImmune() &&
                i < formats.getNumberOfAgents(); i++){
            Communication msg = new Communication(CommunicationType.MOVING, AgentState.IMMUNE);
            agents.get(i).transmitMessage(msg);
        }
    }

    /**
     * This function makes grid of agents with specified number of
     * rows, and columns.
     * @param columns number of columns
     * @param rows number of rows
     * @param parameters collection of agent's parameter.
     */
    private void makeGrid(int columns, int rows, Parameter parameters){
        int posX, posY;
        parameters.setMove(false);

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                posX = width / (columns + 1) * (j + 1);
                posY = height / (rows + 1) * (i + 1);
                agents.add(new Agent(new Point2D(posX, posY), parameters));
            }
        }
    }

    /**
     * This function makes random grid of agents with specified number of rows
     * and columns. It is a helper function for makeAgents function.
     * @param columns
     * @param rows
     * @param numberOfAgents
     * @param parameters
     */
    private void makeRandomGrid(int columns, int rows, int numberOfAgents, Parameter parameters){
        Random random = new Random();
        int posX, posY;
        ArrayList<Agent> otherAgents = new ArrayList<>();

        parameters.setMove(false);

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                posX = width / (columns + 1) * (j + 1);
                posY = height / (rows + 1) * (i + 1);
               otherAgents.add(new Agent(new Point2D(posX, posY), parameters));
            }
        }

        int index;
        for (int i = 0; i < numberOfAgents; i++){
            index = random.nextInt(otherAgents.size());
            agents.add(otherAgents.get(index));
            otherAgents.remove(index);
        }
    }

    /**
     * It makes the set of number of agents, each of which moves
     * randomly.
     * @param numberOfAgents
     * @param parameters
     */
    private void makeRandom(int numberOfAgents, Parameter parameters){
        Random random = new Random();
        int posX, posY;
        parameters.setMove(true);

        for (int i = 0; i < numberOfAgents; i++){
            posX = (int) (random.nextDouble() * width);
            posY = (int) (random.nextDouble() * height);
           agents.add(new Agent(new Point2D(posX, posY), parameters));
        }
    }

    /**
     * This function returns the highest number of agents in any state.
     * @return The highest count of agents in a single state.
     */
    public int getHighestAgentCount() {
        EnumMap<AgentState, Integer> agentCounts = getAgentCounts();
        return agentCounts.values().stream().max(Integer::compare).orElse(0);
    }



    /**
     * It sets the dimension of the grid.
     * @param width width of grid
     * @param height height of grid
     */
    public void setDimension(int width, int height){
        this.width = width;
        this.height = height;
    }

    /**
     * This function starts the diseaseSimulation.Manager's thread which starts the
     * simulation.
     */
    public void start(){
        new Thread(this).start();
        for (Agent agent : agents){
            agent.start();
        }
    }

    /**
     * This functions checks if the simulation has reached a stable state.
     * It is used to check if the simulation has ended.
     * @return
     */
    public boolean isSteady(){
        AgentState state;

        for (Agent agent : agents){
            state = agent.getHealthCondition();
            if (!(state == AgentState.DEAD || state == AgentState.IMMUNE)){
                return false;
            }else {
                if (state == AgentState.VULNERABLE) {
                    agent.isMoving();
                }
            }
        }
        return true;
    }

    /**
     * This function checks for neighbors, and sets the neighbors, when
     * some agents come within the infection distance.
     */
    @Override
    public void run() {

        Point2D position;
        ArrayList<Agent> neighbour;
        while (!isSteady()){
            for (Agent agent : agents){
                position = agent.getLocation();
                neighbour = new ArrayList<>();

                for (Agent a : agents){
                    if (!agent.equals(a) && position.distance(a.getLocation()) < transmitDistance){
                        neighbour.add(a);
                    }
                }
               agent.setNeighbors(neighbour);
            }
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException exception){}
        }
        for (Agent ag : agents){
            try {
                ag.transmitMessage(new Communication(CommunicationType.STOP));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @return ticks
     */
    public int getTicks() {
        return this.ticks;
    }

    /**
     * Increment of ticks
     */
    public void incTicks() {
        this.ticks++;
    }

    /**
     * Reset the simulation step
     */
    public void resetTicks() {
        this.ticks = 0;
    }
}

