package simulation;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * CS 351L
 * @author anuragshrestha
 * This class is the main GUI class for the Disease simulation
 * that display all the user interface related things such as
 * the disease simulation, option to input the config file and
 * start the program. It uses a Border pane to set all the
 * features.
 */

public class GUI extends Application {


    private static int distance;
    private static int incubation;
    private static int sickTime;
    private static double recovery;
    private static int initialSick;
    private static int height;
    private static int width;
    private static int row;
    private static int col;
    private static int num;
    private static int begImmuneNo;
    private static long begTimer;
    private  Canvas canvas;
    private  GraphicsContext graphicsContext;
    private BorderPane border;
    private TextField configFile;
    private Font font;
    private final Pane plotChart = new Pane();


    public static void main(String[] args) {
        launch(args);
    }


    /**
     * This method starts the program.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Disease Simulation");
        font = Font.font("Times New Roman", FontWeight.BOLD, 18);
        border = new BorderPane();
        border.setPadding(new Insets(5,5,5,5));
        border.setBackground(Background.fill(Color.PURPLE));
        setLeftPane(); // Set up the left pane GUI elements
        setRightPane();
        Scene scene = new Scene(border, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * This method creates a text field and buttons where
     * the user can input the file name and start the program.
     * It sets all the GUI features that will be added to the
     * left pane of the main Border Pane.
     */
    private void setLeftPane(){

        // canvas for the disease simulation
        canvas = new Canvas(200,200);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Label configLabel = new Label("Enter Configuration File");
        configLabel.setFont(font);
        configFile = new TextField();
        configFile.setPromptText("Add a configuration file");
        configFile.setMaxHeight(50);
        configFile.setMaxWidth(150);
        Button startButton = new Button("Start");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED,readFile());
        startButton.setMaxWidth(100);

        //Additional features to Rerun the program without restarting.
        Button rerunButton= new Button("Rerun");
        rerunButton.setMaxWidth(100);

        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(70,0,0,0));
        leftPane.setPrefSize(220, 600);
        BorderPane.setMargin(leftPane, new Insets(20,10,20,20));
        CornerRadii radii = new CornerRadii(2);
        Insets insets = new Insets(2,2,2,2);
        BackgroundFill fill = new BackgroundFill(Color.LIGHTSEAGREEN, radii, insets);
        Background backGround = new Background(fill);
        leftPane.setBackground(backGround);
        leftPane.setAlignment(Pos.TOP_CENTER);
        leftPane.getChildren().addAll(
               configLabel, configFile, startButton, rerunButton
        );

        border.setLeft(leftPane);
    }


    /**
     * This method creates a TextArea that shows all the events that
     * happened. It also has a section that displays the graph
     * of the agents over the time. It includes all the necessary
     * GUI features that will be added to the right side of the main
     * Border Pane.
     */
    private void setRightPane(){


        TextArea eventLogs = new TextArea();
        eventLogs.setPrefSize(300, 400);
        eventLogs.setEditable(false);

        Label text = new Label("Simulation disease Message");
        text.setTextFill(Color.SKYBLUE);
        text.setFont(font);
        text.setAlignment(Pos.TOP_CENTER);
        text.setPrefSize(300,20);

        Label text1 = new Label("Agents graph over time");
        text1.setTextFill(Color.SKYBLUE);
        text1.setFont(font);
        text1.setAlignment(Pos.TOP_CENTER);
        text1.setPadding(new Insets(10));
        text1.setPrefSize(300,20);

        VBox plotBox = new VBox();
        plotBox.setPrefSize(300,350);
        plotBox.setAlignment(Pos.TOP_CENTER);
        plotBox.getChildren().addAll(plotChart);
        plotBox.setBackground(Background.fill(Color.WHITE));
        plotBox.setPadding(new Insets(10,5,5,5));

        VBox stateBox = new VBox();
        stateBox.setPrefSize(300, 700);
        stateBox.setBackground(Background.fill(Color.BLACK));
        stateBox.setAlignment(Pos.TOP_CENTER);
        stateBox.getChildren().addAll(text, eventLogs, text1, plotBox);

        // Create circles and labels for the hBox
        Circle vulnerable = new Circle(8, Color.BLUE);
        Circle incubating = new Circle(8, Color.YELLOW);
        Circle sick = new Circle(8, Color.RED);
        Circle immune = new Circle(8, Color.GREEN);
        Circle dead= new Circle(8, Color.BLACK);
        Label vulnerableLabel = new Label("Vulnerable");
        vulnerableLabel.setFont(font);
        Label incubatingLabel = new Label("Incubating");
        incubatingLabel.setFont(font);
        Label sickLabel = new Label("Sick");
        sickLabel.setFont(font);
        Label immuneLabel = new Label("Immune");
        immuneLabel.setFont(font);
        Label deadLabel = new Label("Dead");
        deadLabel.setFont(font);


        HBox vulnerableHBox = new HBox(5);
        HBox incubatingHBox = new HBox(5);
        HBox sickHBox = new HBox(5);
        HBox immuneHBox = new HBox(5);
        HBox deadHBox = new HBox(5);

        vulnerableHBox.getChildren().addAll( vulnerable, vulnerableLabel);
        incubatingHBox.getChildren().addAll( incubating,incubatingLabel);
        sickHBox.getChildren().addAll( sick, sickLabel);
        immuneHBox.getChildren().addAll( immune, immuneLabel);
        deadHBox.getChildren().addAll(dead, deadLabel);

        HBox states = new HBox(10);
        states.setMinHeight(25);
        states.setMaxHeight(25);
        states.setAlignment(Pos.CENTER);
        states.getChildren().addAll(vulnerableHBox, incubatingHBox,
                sickHBox, immuneHBox, deadHBox);

        border.setCenter(canvas);
        border.setRight(stateBox);
        border.setBottom(states);
        BorderPane.setMargin(canvas, new Insets(20,10,20,20));
        BorderPane.setMargin(stateBox, new Insets(20,0,20,10));
        BorderPane.setMargin(states, new Insets(0,0,20,0));
    }


    /**
     * This method will be called when the user clicks the submit
     * button. It reads the config file and then stores all the necessary
     * data from the file.
     * @return the disease simulation process.
     */
    private EventHandler<MouseEvent> readFile(){

        return _ -> {

            final Parameter PARAM = new Parameter();
            String configName = configFile.getText();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(configName));
                String line = reader.readLine();
                while (line != null) {
                    if (line.contains("randomgrid")) {
                        String[] random = line.split(" ");
                        row = Integer.parseInt(random[1]);
                        col = Integer.parseInt(random[2]);
                        num = Integer.parseInt(random[3]);
                    }
                    else if (line.contains("random")) {
                        num = Integer.parseInt(line.split(" ")[1]);
                    }
                    else if (line.contains("grid")) {
                        String[] grid = line.split(" ");
                        row = Integer.parseInt(grid[1]);
                        col = Integer.parseInt(grid[2]);
                        num = row * col;
                    }
                    else if (line.contains("move")) {
                        int speed = Integer.parseInt(
                                line.split(" ")[1]
                        );
                        PARAM.setSpeed(speed);
                    }
                    else if (line.contains("initialsick")) {
                        String[] initSick = line.split(" ");
                        initialSick = Integer.parseInt(initSick[1]);
                        PARAM.setInitialSick(initialSick);
                    }
                    else if (line.contains("dimensions")) {
                        String[] dim = line.split(" ");
                        width = Integer.parseInt(dim[1]);
                        height = Integer.parseInt(dim[2]);
                        PARAM.setMaxX(width);
                        PARAM.setMaxY(height);
                        canvas.setHeight(height);
                        canvas.setWidth(width);
                    }
                    else if (line.contains("exposuredistance")) {
                        String[] expoDistance = line.split(" ");
                        distance = Integer.parseInt(expoDistance[1]);
                        PARAM.setInfectionDistance(distance);
                    }
                    else if (line.contains("incubation")) {
                        String[] incub = line.split(" ");
                        incubation = Integer.parseInt(incub[1]);
                        PARAM.setIncubationTime(incubation);
                    }
                    else if (line.contains("sickness")) {
                        String[] sick = line.split(" ");
                        sickTime = Integer.parseInt(sick[1]);
                        PARAM.setSicknessTime(sickTime);
                    }
                    else if (line.contains("recover")) {
                        String[] recover = line.split(" ");
                        recovery = Double.parseDouble(recover[1]);
                        PARAM.setRecoveryProb(recovery);
                    }
                    else if (line.contains("initialimmune")) {
                        String[] immune = line.split(" ");
                        begImmuneNo = Integer.parseInt(immune[1]);
                        PARAM.setInitialImmune(begImmuneNo);
                    }
                    line = reader.readLine();
                }
                reader.close();

                int diffHorizontal  = (int) ((canvas.getWidth() - width) / 2);
                int diffVertical = (int) ((canvas.getHeight() - height) / 2);
                graphicsContext.clearRect(0, 0, canvas.getWidth(),
                        canvas.getHeight());
                graphicsContext.setFill(Color.LIGHTGRAY);
                graphicsContext.fillRect(diffHorizontal, diffVertical, width + 10,
                        height + 10);
            } catch (IOException e) {
                System.out.println("Error: File not found");
            }
        };
    }
}




