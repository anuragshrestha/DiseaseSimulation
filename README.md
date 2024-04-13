## Welcome to our disease simulation project

Initially for now, I `Anurag Shrestha` will be working on the GUI section. And `suman` 
will be working on the threading and backend section that handles the 
disease spreading among the agents. 

How to Start the Simulation
To get the simulation started, you need to run a JAR file and give me the configuration file in the form of txt. Here's what you should know:

Needed Information: The simulation uses the values you provide in  the configuration file. If some values are missing, it will use defaults values.
Mistakes in the File Name: If you type the wrong name for the configuration file, you'll see an error message.

Main Features
Restart: There's a "Restart" button that lets you start the simulation over again using the same settings.
Initial Immunity: You can choose certain number of agents to be immune from the start. They won't get sick during the simulation.
Knowing When Someone Dies: The simulation keeps track of when each person dies and shows it.
Seeing Changes Over Time: There’s a graph that shows how the situation changes over time with colors that match how agents are doing (like blue for healthy, red for sick).

How it Works
Choosing a Mode: You can choose how people are arranged in the simulation—randomly or in a grid or in a random -grid. If you don't choose, it will set them up randomly with 100 agents.
Colors: The simulation uses colors to show you how people are doing, which makes it easy to see what’s happening.

Limits
Too Many People: If you try to give more than 22 * 22 agents in a grid, the graph might get out of the bound as it have huge difference in the state of the agents. This isn’t a problem when people are placed randomly and at random grid.

Helpful Guides: Provide simple guides or tips within the program to help you know how to use it.