package connect;
import java.util.Scanner;
import StdLib.StdOut;
import StdLib.StdIn;
import java.io.File;
import java.io.FileWriter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class connect4 {
	// Scanner for inputing text from the terminal
	static Scanner terminalInput = new Scanner(System.in);
	
    //Board size
    static int X = 6;
    static int Y = 7;
    //This variable can be used to turn your debugging output on and off. Preferably use 
    static boolean DEBUG = true;
    public static void main (String[] args) {
        //TODO: Read in a comamnd line argument that states whether the program will be in either terminal
        //      mode (T) or in GUI mode (G) [Hint: use args and the variable below]
    	
    	
        //Sets whether the game is in terminal mode or GUI mode
        boolean gui = false;


        int input = 0;
        int pos = -1;
        //The 6-by-7 grid that represents the gameboard, it is initially empty
        int [][] grid = new int [X][Y];
        for(int i = 0; i < X; i++)
        {
        	for(int j = 0; j < Y; j++)
        	{
        		grid[i][j] = '*';
        	}
        }
        //Shows which player's turn it is, true for player 1 and false for player 2
        boolean player1 = true;
        //Shows the number of special tokens a player has left
        int [] p1powers = {2, 2, 2};
        int [] p2powers = {2, 2, 2};      
        
        if (!gui) {
            //Terminal mode

            //TODO: Display 10 line title

            //Array for current player's number of power storage
            int [] curppowers = new int[3];
            while (true) {
                if (player1) {
                    StdOut.println("Player 1's turn (You are Red):");
                    curppowers = p1powers;
                } else {
                    StdOut.println("Player 2's turn (You are Yellow):");
                    curppowers = p2powers;
                }
                StdOut.println("Choose your move: \n 1. Play Normal \n 2. Play Bomb ("+curppowers[0]+" left) \n 3. Play Teleportation ("+curppowers[1]+" left) \n 4. Play Colour Changer ("+curppowers[2]+" left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");
                input = StdIn.readInt();
                switch(input) {
                    case 0: Exit();
                            break;
                    case 1: StdOut.println("Choose a column to play in:\n");                   			
	                    	int column = StdIn.readInt();
	                    	ValidateColumn(column);
	                    	Play(column, grid, player1);
	                    	if(player1)
	                    	{
	                    		player1 = false;
	                    	}
	                    	else
	                    	{
	                    		player1 = true;
	                    	}
                            break;

                    case 2: StdOut.println("Choose a column to play in:");
							//TODO: Read in chosen column
							//TODO: Check that value is within the given bounds, check that the data is numeric
							//TODO: Play bomb disc in chosen column and reduce the number of bombs left
							//TODO: Check that player has bomb discs left to play, otherwise print out an error message
                            break;

                    case 3: StdOut.println("Choose a column to play in:");
							//TODO: Read in chosen column
							//TODO: Check that value is within the given bounds, check that the data is numeric
							//TODO: Play teleport disc in chosen column and reduce the number of teleporters left
							//TODO: Check that player has teleport discs left to play, otherwise print out an error message
                            break;

                    case 4: StdOut.println("Choose a column to play in:");
							//TODO: Read in chosen column
							//TODO: Check that value is within the given bounds, check that the data is numeric
							//TODO: Play Colour Change disc in chosen column and reduce the number of colour changers left
							//TODO: Check that player has Colour Change discs left to play, otherwise print out an error message
                            break;

					//This part will be used during testing, please DO NOT change it. This will result in loss of marks
                    case 5: Display(grid);
                    		//Displays the current gameboard again, doesn't count as a move, so the player stays the same
                            player1 = !player1;
                            break;

					//This part will be used during testing, please DO NOT change it. This will result in loss of marks
                    case 6: grid = Test(StdIn.readString());
                    		//Reads in a test file and loads the gameboard from it.
                            player1 = !player1;
                            break;
                    case 7: Save(StdIn.readString(), grid);
		            player1 = !player1;
		            break;

					default: 
							StdOut.println("Please enter a valid option");
							continue;
           
                }
				//Displays the grid after a new move was played
                Display(grid);
				//Checks whether a winning condition was met
                int win = Check_Win(grid);

                //TODO: Switch the players turns

            }
        } else {
            //Graphics mode

        }
        
    }
    
    /** 
     * Validates the column number which the user enters and returns true if the value is both an integer and between 0 and 6 (inclusive)
     */
    public static void ValidateColumn(int column)
    {
    	boolean validSelection = false;
    	while(!validSelection)
    	{
    		if(column == (int)column)
        	{
        		if(column >= 0 && column < 7)
        		{
        			validSelection = true;
        		}
        		else
        		{
        				StdOut.println("Please make a valid selection\n");
        				StdOut.println("Choose a column to play in:\n");
        				column = terminalInput.nextInt();
        		}	
        	}
    		else
    		{
    			StdOut.println("Please make a valid selection");
    			StdOut.print("Choose a column to play in:");
				column = terminalInput.nextInt();                    			
        	}
    	}

    }
    
    /**
     * Displays the grid, empty spots as *, player 1 as R and player 2 as Y. Shows column and row numbers at the top.
     * @param grid  The current board state
     */
    public static void Display (int [][] grid) {
    	
    	for(int i = 0; i < X; i++)
    	{
    		for(int j = 0; j < Y; j++)
    		{
    			StdOut.print((char)grid[i][j]);
    			StdOut.print(" ");
    		}
    		StdOut.println("\n");	
    	}
    	
        //TODO: Display the given board state with empty spots as *, player 1 as R and player 2 as Y. Shows column and row numbers at the top.
    }

    /**
     * Exits the current game state
     */
    public static void Exit() {
        System.exit(0);
    }

    /**
     * Plays a normal disc in the specified position (i) in the colour of the player given. Returns the modified grid or
     * prints out an error message if the column is full.
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Play (int i, int [][] grid, boolean player1) {
    	//int letter = ( player1 ? 'R' : 'Y'  );
    	/*
    	 * TODO: ASK Lecturer/Tutor if above can be used 
    	 */
    	
    	int letter;
    	if(player1)
    	{
    		letter = 'R';
    	}
    	else
    	{
    		letter = 'Y';
    	}
    	
    	boolean columnFull = true;
    	int column = i;
    	while(columnFull)
    	{
    		columnFull = true;
    		for(int row = X - 1; row >= 0; row--)
    		{
    			if(grid[row][column] == 42)
    			{
    				columnFull = false;
    				grid[row][column] = letter;
    				break;
    			}
    		}
    		if(columnFull)
    		{
				StdOut.print("The column you chose is full, please choose another one:");
				column = StdIn.readInt();   			
    		}

    	}
        //TODO: Play a normal disc of the given colour, if the column is full, print out the message: "Column is full."
        return grid;
    }

    /**
     *Checks whether a player has 4 tokens in a row or if the game is a draw.
     * @param grid The current board state in a 2D array of integers
     */
    public static int Check_Win (int [][] grid) {
        int winner = 0;
        //TODO: Check for all the possible win conditions as well as for a possible draw.
        return winner;
    }

    /**
     * Plays a bomb disc that blows up the surrounding tokens and drops down tokens above it. Should not affect the
     * board state if there's no space in the column. In that case, print the error message: "Column is full."
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Bomb (int i, int [][] grid, boolean player1) {
        //TODO: Play a bomb in the given column and make an explosion take place. Discs should drop down afterwards. Should not affect the
        //      board state if there's no space in the column. In that case, print the error message: "Column is full."
        //      Leaves behind a normal disc of the player's colour
        return grid;
    }

    /**
     * Plays a teleporter disc that moves the targeted disc 3 columns to the right. If this is outside of the board
     * boundaries, it should wrap back around to the left side. If the column where the targeted disc lands is full,
     * destroy that disc. If the column where the teleporter disc falls is full, play as normal, with the teleporter
     * disc replacing the top disc.
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Teleport (int i, int [][] grid, boolean player1) {
        //TODO: Play a teleporter disc that moves the targeted disc 3 columns to the right. If this is outside of the board
        //      boundaries, it should wrap back around to the left side. If the column where the targeted disc lands is full,
        //      destroy that disc. If the column where the teleporter disc falls is full, play as normal, with the teleporter
        //      disc replacing the top disc.
        //      No error message is required.
        //      If the colour change disc lands on the bottom row, it must leave a disc of the current player’s colour.
        return grid;
    }

    /**
     * Plays the colour changer disc that changes the affected disc's colour to the opposite colour
     * @param i         Column that the disc is going to be played in
     * @param grid      The current board state
     * @param player1   The current player
     * @return grid     The modified board state
     */
    public static int [][] Colour_Changer (int i, int [][] grid, boolean player) {
        //TODO: Colour Change: If the colour change disc lands on top of another disc, it changes the colour of that
        //      disc to the opposite colour. The power disc does not remain.
        //      If the colour change disc lands on the bottom row, it must leave a disc of the current player’s colour.
        return grid;
    }

    /**
     * Reads in a board from a text file.
     * @param name  The name of the given file
     * @return
     */
    //Reads in a game state from a text file, assumes the file is a txt file
    public static int [][] Test(String name) {
        int [][] grid = new int[6][7];
        try{
            File file = new File(name+".txt");
            Scanner sc = new Scanner(file);

            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return grid;
    }

    /**
     * Debugging tool, preferably use this since we can then turn off your debugging output if you forgot to remove it.
     * Only prints out if the DEBUG variable is set to true.
     * @param line  The String you would like to print out.
     */
    public static void Debug(String line) {
        if (DEBUG)
            StdOut.println(line);
    }
    

public static int [][] Save(String name, int [][] grid) {
    	try{
    		FileWriter fileWriter = new FileWriter(name+".txt");
	    	for (int i = 0; i < X; i++) {
	    		for (int j = 0; j < Y; j++) {
		    		fileWriter.write(Integer.toString(grid[i][j]) + " ");
		    	}
		    	fileWriter.write("\n");
		    }
		    fileWriter.close();
	    } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return grid;
    }
}

