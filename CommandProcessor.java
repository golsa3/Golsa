package PointsProject;
/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 * 
 * @author CS Staff
 * 
 * @version 2024-01-22
 */
public class CommandProcessor {



    /**
     * The constructor for the command processor requires a database instance to
     * exist, so the only constructor takes a database class object to feed
     * commands to.
     * 
     * the database object to manipulate
     */
    public CommandProcessor() {
    }



    public void processor(String line) {
        // converts the string of the line into an
        // array of its space (" ") delimited elements
        String[] arr = line.split("\\s{1,}");
        String command = arr[0]; // the command will be the first of these
                                 // elements
        // calls the insert function and passes the correct
        // parameters by converting the string integers into
        // their Integer equivalent, trimming the whitespace
        if (command.equals("insert")) {

            String name = arr[1];
            int xCoord = Integer.parseInt(arr[2]);
            int yCoord = Integer.parseInt(arr[3]);
            
            if (xCoord > 0 && yCoord > 0 && xCoord <= 1024 && yCoord < 1024) {
            // Calls insert on valid coordinates
            }
            else {
                //print  out "Point rejected (xCoord, yCoord)"
            }
            
        }
        // calls the appropriate remove method based on the
        // number of white space delimited strings in the line
        else if (command.equals("remove")) {
            // checks the number of white space delimited strings in the line
            int numParam = arr.length - 1;
            if (numParam == 1) {
                // Calls remove by name
                String name = arr[1].trim();
                //call remove w/param name
            }
            else if (numParam == 2) {
                // Calls remove by coordinate, converting string
                // integers into their Integer equivalent minus whitespace
                int xCoord = Integer.parseInt(arr[1].trim());
                int yCoord = Integer.parseInt(arr[2].trim());

                if (xCoord > 0 && yCoord > 0 && xCoord <= 1024 && yCoord < 1024) {
                    //call remove on valid coordinates
                    //remove will take care of not found coordinates that dont exist
                }else {
                    //print out Points rejected (x, y)
                }

        }
        else if (command.equals("regionsearch")) {
            // calls the regionsearch method for a set of coordinates
            int xCoord = Integer.parseInt(arr[1].trim());
            int yCoord = Integer.parseInt(arr[2].trim());
            int width = Integer.parseInt(arr[3].trim());
            int height = Integer.parseInt(arr[4].trim());

            if (xCoord > 0 && yCoord > 0 && xCoord <= 1024 && yCoord < 1024 && width >= 0 && height >=0) {
                //call regionsearch on valid coordinates
            }else {
                //print out "Rectangle rejected: (x, y, w, h)
            }
        }
        else if (command.equals("duplicates")) {
            //call duplicates method which will report all 
            //points with the same coordinates with no name printed

        }
        else if (command.equals("search")) {

            String name = arr[1];
            //call search method with the name

        }
        else if (command.equals("dump")) {
            //call dump method 
            //will return dump of Skip List and Quadtree
            //for skiplist, will print out each node from left to right
            //and for each node print its value and number of pointers(levels) it contains
            
            //for quadtree should print nodes of quadtree in preorder traversal
            //with one node per line and each line indented by 2 spaces for each level
            
            //in the order NW, NE, SW, SE
        }
        else {
            // the first white space delimited string in the line is not
            // one of the commands which can manipulate the database,
            // a message will be written to the console
            System.out.println("Unrecognized command.");
        }
    }
    }
}