package simulation;

/**
 * This class is responsible for transmitting the message between
 * agents.
 * @author anuragShrestha
 */
public class Communication {
    private AgentState state = AgentState.VULNERABLE;
    private CommunicationType communicationType;

    /**
     * Constructor class. sets the communication type
     * @param type message type
     */
    public Communication(CommunicationType type){
        this.communicationType= type;
    }

    /**
     * Constructor class that sets the both type and the agent
     * state.
     * @param type the type of communication
     * @param state the current state of the agent
     */
    public Communication(CommunicationType type, AgentState state){
        this.communicationType = type;
        this.state = state;
    }

    /**
     * returns the state of the agents
     * @return State
     */
    public AgentState getState(){
        return state;
    }

    /**
     * returns the message type of the agent.
     * @return type
     */
    public CommunicationType getMessageType(){
        return communicationType;
    }
}
