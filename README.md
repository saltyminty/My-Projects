# Build Your Own World Design Document

**Partner 1:**
Wilson Wu

**Partner 2:**
Mingyang Wang

## Classes and Data Structures
Due to the relatively small size of the world, for most purposes, simply iterating over an array of objects or iterating over the whole grid should be fast enough so complicated algorithms and data structures should be avoided.

### Point
The point class represents a single tile on the 2d world.

#### Variables
    1) int x - x coordinate(column of world array)
    2) int y - y coordinate(row of world array)
    3) int hashCode()
#### Methods
    1) int x() - gets x
    2) int y() - gets y

### Room 
The room represents a rectangular room on the 2d grid.

#### Variables
    1) Point bottomLeft - the point representing the top left(small x, big y) of the rectangular room
    2) Point topRight - the point representing the bottom right(big x, small y) of the rectangular room
    3) TETile tile - tile of the room; default floor

#### Methods
    1) boolean intersects(Room other) - returns true if the current room intersects with another room;
    2) boolean contains(Point pt) - returns true if the given point is in the room
    3) boolean isAbove(Room other) - returns true if this is above other (compare this.bottomLeft to other.topRight)
    4) boolean isRight(Room other) - returns true if this is to the right of other (compare this.bottomLeft to other.topRight)
    5) int hashCode()
    6) Top()
    7) Bottom()
    8) Left()
    9) Right()
    10) tile()

### UnionFind
has the standard union find methods, just use the one from the percolation hw
#### Methods
    1) void union(int a, int b) - joins 2 rooms by their indices
       void union (Room a, Room b) - joins 2 rooms
    2) int find(int a) - finds root of index
       room find (room a) - finds root of a room
    3) boolean isConnected (int a, int b) - returns true if index a and index b are connected
       boolean isConnected (Room a, Room b) - returns true if room a and room b are connected
    4) boolean allConnected() - returns true if all nodes have same parent
    
### WorldGenerator
The WorldGenerator class can be used to generate a new world given a seed.

#### Variables
    1) private List<Room> roomsList - stores a list of rooms that have been added
    2) private Random random - a java.util.Random object used to generate random numbers
    3) static final int lowerBound - minimum width/height of a room
    4) static final int upperBound - maximum width/height of a room
    5) private UnionFindRooms rooms - UnionFind Object of Rooms
    6) private static final minRooms - minimum number of rooms
    7) private static final maxRooms - maximum number of rooms
    

#### Methods
    1) TETile[][] generateWorld(TETile[][] world, long seed) - fills the 2d grid with appropriate tiles
    2) void setTile(TETile[][] world, Room room) - adds tiles to the rectangular section of the world denoted by the Room
    3) void drawRooms(TETile[][] world, int numRooms) - draws numRooms number of nonintersecting rooms
    4) void drawHallway(TETile[][] world) - draws all the hallways
    4.1) void drawHallway Helper(TETile[][] world, int start, int target) - connects start to target
    
## Algorithms

### WorldGenerator - generateWorld
    1) initialize the Random object using the seed
    2) use the random object to get the number of rooms to generate (make sure to bound it)
    3) call drawRooms()
    4) while not allConnected
        5) keep randomly selecting 2 indices until u get an unconnected pair
        6) drawHallway() between the 2 rooms
        7) if successful, connect the 2, else nothing
    8) generate a random number between 1 and (numRooms)(numRooms - 1)/2 - (numRooms - 1)
    9) while fewer hallways have been drawn
        10) drawHallway()
        
      
### WorldGenerator - drawHallway
In essence this just brute forces to find a path between 2 rooms. It treats hallways as 1 tile wide (or 1 tile tall) rooms.
    
    1) if its theoretically possible to draw a straight line hallway (the second room isn't both above or to the right)
        2) keep trying to draw straightline hallways
        3) if the hallway doesn't reach the other room or it intersects some irrelavent room, it's invalid
        4) else the hallway is valid and return true
    5) treat L shaped hallways as 2 intersecting 1 tile width/tall rooms
    6) try all valid combinations of hallways(same criteria as above) 
    7) if successful, draw the hallway and return true
    8) return false
    




# Phase 2
### interactwithKeyboard(String s)
    1) StdDraw - menu screen: New World, Load, Quit
    2) check for keyboard inputs (Lab 13)
        Ask for seed if making a new world
    3) for each valid keyboard inputs, carry out interacthelper until :Q

### interactwithInputString(String s)
    1) filter for new world/load, seed, :Q, yadda yadda
    2) call interacthelper for each individual input letter

### interacthelper(char s or String s)
    1) peform move i guess; if illegal then do nothing
    
    :Q handling:
        boolean to keep track of whether : is called
        when : is called, then set to true; whenever anything else is called, set to false
        if q is called with when true, then save/quit
        
### mouse thingymajig
    1) check where mouse is all the time, display in the hud thingymajiger
    
### EngineUtils
This class takes cares of movement and loading and saving.

#### Variables
    
    1) Point loc - this is the current position of the avatar
    2) String inputString - this is the entire input string to be saved
    3) boolean simulate - this tells us if we need to draw the world or not
    4) HashMap<Character, int> dx
    5) HashMap<Character, int> dy
    
#### Methods
    
    1) TETile[][] loadGame() 
    2) void saveGame()
    3) TETile[][] interact(TETile[][] world, char curr)
    4) TETile[][] beginGame(String inputString)

#### Algorithms

##### EngineUtils - beginGame()
    
    1) if interactWithKeyboard:
        2) display main menu
    3) using inputsource if first char is L, call loadGame()
    4) if first char is N, read the sead and generate a world and append it to inputString
        5) if simulate, display the string as the user types it
    6) if first char is Q, then exit game
    7) return the world
    
##### EngineUtils - loadGame(TETile[][] world) 

    1) open the file and read N#S
    2) generate the world and avatar
    3) while not done parsing the string
        4) if simulate - draw the world
        5) interact(world, curr)
    6) append everything to inputString
    7) if simulate - draw the world
    8) return the world

##### EngineUtils - interact(TETile[][] world, char curr)

    1) if curr == ':': do nothing
    2) if curr == 'Q': saveGame()
    3) if curr + dx.get(curr) and curr + dy.get(curr) are valid - move and update world 
    4) if simulate - draw the world
    5) return world

##### EngineUtils - void saveGame()

    1) open the file
    2) write the string into it
    