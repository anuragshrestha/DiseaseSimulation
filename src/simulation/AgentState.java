package simulation;
import javafx.scene.paint.Color;

/**
 * @author anuragshrestha
 * This class represents the current state of each agent.
 * Each state is indicated by a unique color.
 */
public enum AgentState {
    VULNERABLE {
        public Color getColor(){return Color.BLUE;}
    },
    INCUBATING {
        public Color getColor(){return Color.YELLOW;}
    },
    SICK {
        public Color getColor(){return Color.RED;}
    },
    IMMUNE {
        public Color getColor(){return Color.GREEN;}
    },
    DEAD {
        public Color getColor(){return Color.BLACK;}
    };

    public abstract Color getColor();
}

