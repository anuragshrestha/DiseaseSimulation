package simulation;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import java.util.EnumMap;

/**
 * This class creates the simulation plot.
 */
public class Plot extends Parent {

    private final Manage mg;
    private Pane graphPlot;

    /**
     * @param mg manager
     */
    public Plot(Manage mg) {
        this.mg = mg;
        resetPlot();
    }

    /**
     * Updates the simulation plot
     * @param ticks each step of simulation
     */
    public void updatePlot(int ticks, int num) {
        EnumMap<AgentState, Integer> currentAgentsCount = mg.getAgentCounts();

        for (AgentState state : AgentState.values()) {
            if (currentAgentsCount.containsKey(state)) {
                Circle c = new Circle(2, state.getColor());
                c.setTranslateX(ticks/(num*10.0));
                c.setTranslateY(200 - currentAgentsCount.get(state));
                graphPlot.getChildren().add(c);
            }
        }
    }

    /**
     * Resets the simulation plot
     */
    public void resetPlot() {
        getChildren().clear();
        graphPlot = new Pane();
        getChildren().add(graphPlot);
    }
}
