 package simulation;

/*
 * @author anurag shrestha
 * This class is responsible to plot the graph of the agents
 * over the time
 */

 import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.util.EnumMap;
import java.util.Map;

public class Plot extends Parent {

    private final Manage manage;
    private Pane graphPane;
    private Map<AgentState, double[]> stateLastPositions;
    private boolean simulationRunning = true;

    public Plot(Manage manage) {
        this.manage = manage;
        this.stateLastPositions = new EnumMap<>(AgentState.class);
        initializePlot();
    }

    private void drawLine(AgentState state, double x1, double y1, double x2, double y2) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(state.getColor());
        graphPane.getChildren().add(line);
    }

    public void updatePlot(int ticks, int num) {
        if (!simulationRunning) {
            return;
        }

        double plotHeight = graphPane.getHeight();
        double plotWidth = graphPane.getWidth();

        EnumMap<AgentState, Integer> currentAgentCounts = manage.getAgentCounts();

        currentAgentCounts.forEach((state, count) -> {

            double newX = ticks / (num * 10.0);
            double newY = plotHeight - (count / (double) num) * plotHeight;
            double[] lastPos = stateLastPositions.get(state);
            if (lastPos != null) {
                drawLine(state, lastPos[0], lastPos[1], newX, newY);
            }

            stateLastPositions.put(state, new double[]{newX, newY});
        });
    }



    private void initializePlot() {
        getChildren().clear();
        graphPane = new Pane();
        getChildren().add(graphPane);
        stateLastPositions.clear();
    }

    public void resetPlot() {
        initializePlot();
        simulationRunning = true;
    }
}