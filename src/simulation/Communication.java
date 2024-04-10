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
     * Constructor class of the diseaseSimulation.Message class.
     * @param type message type
     */
    public Communication(CommunicationType type){
        this.communicationType= type;
    }

    public Communication(CommunicationType type, AgentState state){
        this.communicationType = type;
        this.state = state;
    }

    /**
     * returns the state of the agents
     * @return diseaseSimulation.State
     */
    public AgentState getState(){
        return state;
    }

    /**
     * returns the message type of the agent.
     * @return diseaseSimulation.MessageType
     */
    public CommunicationType getMessageType(){
        return communicationType;
    }
}
