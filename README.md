## Welcome to our disease simulation project


## Introduction

This is a Disease simulation program that is designed to simulate the disease between the agents. Here agents can
be human, animals, birds and many more. Initially there will be certain agents and among them few of the agents will 
be sick at first. Those sick agents when comes in contact with non-sick agents - agents need to be certain distance close- 
to transmit the disease. After being sick, the agent might die or recover depending on the recover probability. All the 
data such as exposureDistance, sickness time, incubation time will be provided in a configuration file. 

## Instructions to run the program

1) At first, run the Jar file named `DiseaseSimulation_ashrestha_skafle.jar`. It will ask for a configuration file which
can be found inside the resource folder. Pass the path using `resources/config.txt` or we can give it a different config
file also.
2) After entering the config file path, we need to press the start button.
3) Once we start the program, it will show all the data use to run the program such as sickness time, recovery probability,
exposure distance on the left side of the window. This data will be taken from the config file we used. If some or all
of the data are missing in the config file, then it will use the default value.
4) In the center, it will show the disease simulation where the invulnerable agents will get the disease and become sick. 
Then after the provided sickness time, it will either die or recover as an immune. The probability of dying or recovery 
depends on the `recovery probabilty` provided on the config file or the default value.
5) On the right side, it will show all the events such as when the agents got sick, when the agent died and which agent 
it was. It will also show a graph of agents over the time.
6) The program will stop when all the agents will die or get recover after being sick. Some of the agents may remain
vulnerable if the exposure distance was not enough.
7) We can then restart the whole program again and again using the same information we used before.


## Additional four features in the program

1) `Restart`: There is a "Restart" button that lets you start the simulation over again using the same information
we used before.
2) `Initial Immunity`: You can choose certain number of agents to be immune from the start. They won't get sick during
the simulation till the end of the program.
3) `Displaying a history of the simulation in the GUI`: The simulation keeps track of each agent and displays when each 
agent gets sick, and when they die in a separate pane on the top right side.
4) `Seeing Changes Over Time using graph`: There’s a graph in the bottom right pane that shows how the agent states
changes over time were each states is represented by a unique color that match how agents are doing (such as blue color
for healthy, red for sick).



## Important Things to know

1) If you provide more than one agent location in the configuration file, then it will use the one that is
provided at the last. For example : if you give `grid 30 30` at first then `randomgrid 30 30 100` then it will use 
the last one.
2) Too Many People: If you try to give more than 22 * 22 agents in a grid, then the graph might get out of the bound as 
it have huge difference in the state of the agents. This problem will not happen when people are placed randomly and at
randomgrid.
3) If you type the wrong name for the configuration file, you'll get an error message.
4) If you don't provide any initial agents location then it will use the default random location with 100 agents.


## Which student worked in which part of the program

I `Anurag Shrestha` mainly worked on the GUI section of the program. I design and developed the user interface.
I developed the communication class that helps to communicate between the agents over the time. I coded the parameter
class that sets the parameter of the disease simulation. I also did the update state part that is responsible for 
the update of the agents. `Suman kafle` mostly did the remaining backend sections such as agent handling. He coded 
the sections that handles the agents and formats the agents. He managed all the disease simulation process that starta
the simulation process, tracks it and then stops it. 

