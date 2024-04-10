package simulation;

public class FormatAgents {
    private Structures formatType;
    private int columns, rows, numberOfAgents;
    private int initialSick = 1;
    private int initialImmune = 0;

    /**
     * Constructor for FormatAgents
     */
    public FormatAgents(){
        formatType = Structures.GRID;
    }

    /**
     * This class sets the format type.
     * @param format formatType
     */
    public void setFormatType(String format){
        if (format.equals("grid")){
            formatType = Structures.GRID;
        }else if (format.equals("randomGrid")){
            formatType = Structures.RANDOMGRID;
        }else if (format.equals("random")){
            formatType = Structures.RANDOM;
        }else{
            formatType = Structures.GRID;
        }
    }

    /**
     *sets the height.
     * @param columns height
     */
    public void setColumns(int columns){
        this.columns = columns;
    }

    /**
     * sets the width.
     * @param rows width
     */
    public void setRows(int rows){
        this.rows = rows;
    }

    /**
     * sets number of agents that will be initialized
     * @param numberOfAgents
     */
    public void setNumberOfAgents(int numberOfAgents){
        this.numberOfAgents = numberOfAgents;
    }

    /**
     * returns the height of the grid.
     * @return height
     */
    public int getColumns() {
        return columns;
    }

    /**
     * returns the width of the grid.
     * @return width
     */
    public int getRows() {
        return rows;
    }

    /**
     * returns the number of agents that was set or initialized
     * @return number of agents.
     */
    public int getNumberOfAgents() {
        return numberOfAgents;
    }




    /**
     * returns the formatType of grid.
     * @return
     */
    public Structures getFormatType(){
        return this.formatType;
    }
}
