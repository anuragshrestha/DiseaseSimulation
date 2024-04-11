package  simulation;

import static java.lang.Thread.sleep;

public class UpdateState implements Runnable{
    private final Agent agent;
    private final AgentState state;
    private final int delayTime;

    public UpdateState(Agent agent, AgentState state, int delayTime){
        this.agent = agent;
        this.state = state;
        this.delayTime = delayTime;
    }

    /**
     * This function pauses the thread for given number of time.
     * After given number of time, it sends a message to the agent
     * requesting that it change its health state.
     */
    @Override
    public void run() {
        try{
            sleep(delayTime);
        }catch (InterruptedException e) {
            return;
        }
        try {
            agent.transmitMessage(new Communication(CommunicationType.MOVING, state));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
