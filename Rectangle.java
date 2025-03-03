package PointsProject;

public class Rectangle {
    // the x coordinate of the rectangle
    private int xCoordinate;
    // the y coordinate of the rectangle
    private int yCoordinate;
    // the distance from the x coordinate the rectangle spans
    private int width;
    // the distance from the y coordinate the rectangle spans
    private int height;

    /**
     * Creates an object with the values to the parameters given in the
     * xCoordinate, yCoordinate, width, height
     * 
     * @param x
     *            x-coordinate of the rectangle
     * @param y
     *            y-coordinate of the rectangle
     * @param w
     *            width of the rectangle
     * @param h
     *            height of the rectangle
     */
    public Rectangle(int x, int y, int w, int h) {
        xCoordinate = x;
        yCoordinate = y;
        width = w;
        height = h;
    }


    /**
     * Getter for the x coordinate
     *
     * @return the x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }


    /**
     * Getter for the y coordinate
     *
     * @return the y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }


    /**
     * Getter for the width
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }


    /**
     * Getter for the height
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Checks if the invoking rectangle intersects with r2.
     * 
     * @param r2
     *            Rectangle parameter
     * @return true if the rectangle intersects with r2, false if not
     */
    public boolean intersect(Rectangle r2) {

        // Get the coordinates of the two rectangles
        int r2Left = r2.getxCoordinate();
        int r2Right = r2Left + r2.getWidth();
        int r2Top = r2.getyCoordinate();
        int r2Bottom = r2Top + r2.getHeight();

        int thisLeft = this.xCoordinate;
        int thisRight = thisLeft + this.width;
        int thisTop = this.yCoordinate;
        int thisBottom = thisTop + this.height;

        // Check for intersections
        // check if this is to the right
        if (thisLeft >= r2Right) {
            return false;
        }
        // check if this is to the left of r2
        else if (thisRight <= r2Left) {
            return false;
        }
        // check if this is below r2
        else if (thisTop >= r2Bottom) {
            return false;
        }
        // check if this is above r2
        else if (thisBottom <= r2Top) {
            return false;
        }
        else {
            return true;
        }

    }


    /**
     * Checks, if the invoking rectangle has the same coordinates as rec.
     * 
     * @param rec
     *            the rectangle parameter
     * @return true if the rectangle has the same coordinates as rec, false if
     *         not
     */
    public boolean equals(Object rec) {

        Rectangle rec1 = (Rectangle)rec;
        return (this.xCoordinate == rec1.getxCoordinate()
            && this.yCoordinate == rec1.getyCoordinate() && this.width == rec1
                .getWidth() && this.height == rec1.getHeight());

    }


    /**
     * Outputs a human readable string with information about the rectangle
     * which includes the x and y coordinate and its height and width
     * 
     * @return a human readable string containing information about the
     *         rectangle
     */
    public String toString() {
        return xCoordinate + ", " + yCoordinate + ", " + width + ", " + height;
    }


    /**
     * Checks if the rectangle has invalid parameters
     * 
     * @return true if the rectangle has invalid parameters, false if not
     */
    public boolean isInvalid() {
        return (this.height <= 0 || this.width <= 0 || this.xCoordinate
            + this.width > 1024 || this.yCoordinate + this.height > 1024);

    }
}

