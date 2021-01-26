# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: My implementation is basically hard coded which is sufficient for the lab; however, for project 3 we need to decide on how generalized certain methods should be
before directly coding them.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: For project 3 we'll probably use a similar method to generate the rooms and hallways. We will first write a method that can generate inidividual
rooms and a method that generates hallways that connect 2 specific rooms. Then another method to call the room and hallway generating methods to create 
the world.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: First write a method to generate a single room given some output, then a method to create multiple rooms using the first method.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: hallways are just one tile wide, but both rooms and hallways are a bunch of empty tiles surrounded by walls
