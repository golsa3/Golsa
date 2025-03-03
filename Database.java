package PointsProject;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

// import SkipList.SkipListIterator;
// import SkipList.SkipNode;
//
/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * @author CS Staff
 * 
 * @version 2024-01-22
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Rectangle> list;

    // This is an Iterator object over the SkipList to loop through it from
    // outside the class.
    // You will need to define an extra Iterator for the intersections method.
    private Iterator<KVPair<String, Rectangle>> itr1;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * add the KVPair specified into the sorted SkipList appropriately
     * Grid is 1024 by 1024 units in size and has upper left corner at (0, 0).
     * If a
     * rectangle is all or partly out of this box, it should be rejected for
     * insertion.
     * 
     * @param pair
     *            the KVPair to be inserted
     */
    public void insert(KVPair<String, Rectangle> pair) {
        // Delegates the decision mostly to SkipList, only
        // writing the correct message to the console from
        // that
        Rectangle a = pair.getValue();
        if (a.getWidth() > 0 && a.getWidth() + a.getxCoordinate() < 1024 && a
            .getHeight() > 0 && a.getHeight() + a.getyCoordinate() < 1024 && a
                .getxCoordinate() >= 0 && a.getyCoordinate() >= 0) {
            list.insert(pair);
            System.out.println("Rectangle inserted: (" + pair.getKey() + ", "
                + a + ")");
        }

        else {
            System.out.println("Rectangle rejected: (" + pair.getKey() + ", "
                + a + ")");
        }

    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
        KVPair<String, Rectangle> removed = list.remove(name);
        if (removed != null) {
            System.out.println("Rectangle removed: " + removed);
        }
        else {
            System.out.println("Rectangle not removed: " + name);
        }
    }


    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
        Rectangle a = new Rectangle(x, y, w, h);
        KVPair<String, Rectangle> removed = list.removeByValue(a);
        if (removed != null) {
            System.out.println("Rectangle removed: " + removed);
        }
        else {
            System.out.println("Rectangle rejected: " + +x + ", " + y + ", " + w
                + ", " + h);
        }
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region.
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */

    public void regionsearch(int x, int y, int w, int h) {

        itr1 = list.iterator();

        // check if region is valid and print correct header
        if (h <= 0 || w <= 0) {
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", " + w
                + ", " + h + ")");
            return;
        }
        else {
            System.out.println("Rectangles intersecting region (" + x + ", " + y
                + ", " + w + ", " + h + "):");
        }

        // iterate through list
        while (itr1.hasNext()) {

            KVPair<String, Rectangle> pair = itr1.next();
            Rectangle r = pair.getValue();

            int rx = r.getxCoordinate();
            int ry = r.getyCoordinate();

            int rRight = rx + r.getWidth();
            int rBottom = ry + r.getHeight();

            boolean inside = true;

            // check left side bigger than right or right side less than left or
            // bottom above top or top below bottom if so not in region
            if (rx >= x + w || rRight <= x || rBottom <= y || ry >= y + h) {
                inside = false;
            }
            if (inside) {
                System.out.println("(" + pair.getKey() + ", " + r + ")");
            }
        }

    }


    /**
     * Prints out all the rectangles that intersect each other. Note that
     * it is better not to implement an intersections method in the SkipList
     * class
     * as the SkipList needs to be agnostic about the fact that it is storing
     * Rectangles.
     */
    public void intersections() {

        // print header every time
        System.out.println("Intersection pairs:");

        itr1 = list.iterator();

        // first iteration

        int j = 0;
        while (itr1.hasNext()) {

            ArrayList<String> pairs = new ArrayList<>(); // list of pairs
                                                         // already used

            // first pair
            KVPair<String, Rectangle> pair1 = itr1.next();

// set up second iterator

            Iterator<KVPair<String, Rectangle>> itr2 = list.iterator();

            for (int i = 0; i < j; i++) {
                itr2.next();
            }
            Rectangle r1 = pair1.getValue();
            // coordinates pair 1
            int r1x = r1.getxCoordinate();
            int r1y = r1.getyCoordinate();

            int r1Right = r1x + r1.getWidth();
            int r1Bottom = r1y + r1.getHeight();

            while (itr2.hasNext()) {
                // second pair
                KVPair<String, Rectangle> pair2 = itr2.next();

                // if this rectangle is the same one as the first iteration skip
                // it
                if (pair2.getKey().compareTo(pair1.getKey()) == 0) {
                    continue;
                }
                Rectangle r2 = pair2.getValue();

                int r2x = r2.getxCoordinate();
                int r2y = r2.getyCoordinate();

                int r2Right = r2x + r2.getWidth();
                int r2Bottom = r2y + r2.getHeight();

                boolean intercept = true;

                // check left side of r1 bigger than right of r2 or right side
                // r1 less than left r2 or
                // bottom r1 above top r2 or top r1 below bottom r2 if any of
                // those r1 and r2 do not intercept
                if (r1x >= r2Right || r1Right <= r2x || r1Bottom <= r2y
                    || r1y >= r2Bottom) {
                    intercept = false;
                }
                if (intercept) {
                    boolean found = false;
                    String reversed = pair2.getKey() + " " + pair1.getKey();
                    for (String x : pairs) {
                        if (x.equals(reversed)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        String normal = pair1.getKey() + " " + pair2.getKey();
                        pairs.add(normal);

                        System.out.println("(" + pair1.getKey() + ", " + r1
                            + ") | (" + pair2.getKey() + ", " + r2 + ")");
                    }
                }
            }
            j++;
        }
    }


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
        ArrayList<KVPair<String, Rectangle>> result = list.search(name);

        if (result == null) {
            System.out.println("Rectangle not found: (" + name + ")");
        }
        else {
            System.out.println("Rectangles found:");
            for (KVPair<String, Rectangle> curr : result) {
                System.out.println("(" + curr.getKey() + ", " + curr.getValue()
                    + ")");
            }
        }
        list.search(name);

    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
    }


    /**
     * size method
     * 
     * @return size of list
     */
    public int size() {
        return list.size();
    }

}
