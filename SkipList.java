package PointsProject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
//import student.TestableRandom;

/**
 * 8
 * This class implements SkipList data structure and contains an inner SkipNode
 * 9
 * class which the SkipList will make an array of to store data.
 * 10
 * 
 * 11
 * 
 * @author CS Staff
 *         12
 * 
 *         13
 * @version 2024-01-22
 *          14
 * @param <K>
 *            15
 *            Key
 *            16
 * @param <V>
 *            17
 *            Value
 *            18
 */

public class SkipList<K extends Comparable<? super K>, V>
    implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element (Sentinel Node)
    private int size; // number of entries in the Skip List
    private Random rng;

    /**
     * 26
     * Initializes the fields head, size and level
     * 27
     */

    public SkipList() {
        setHead(new SkipNode(null, 0));
        size = 0;
        // Change back??????
        //this.rng = new TestableRandom();
        // use seeded time instead of testablerandom because easier;
         this.rng = new Random(10);
    }


    /**
     * 39
     * returns a random level (using geometric distribution), minimum of 1
     * 40
     * 
     * 41
     * 
     * @return random level
     *         42
     */
    // keep this method private. Since, we do not have any methods to call
    // this method at this time, we keep this publicly accessible and testable.

    public int randomLevel() {

        int level = 0;

        while (rng.nextBoolean())

            level++;
        return level;
    }


    /**
     * 54
     * returns a random level (using geometric distribution), minimum of 1
     * 55
     * 
     * 56
     * 
     * @return head level
     *         57
     */

    public SkipNode getHead() {

        return head;

    }


    /**
     * 64
     * setHead method
     * 65
     * 
     * 66
     * 
     * @param head
     *            head of the SkipNode
     *            68
     *            the beginning of the list
     *            69
     * @returns a random level (using geometric distribution), minimum of 1
     *          70
     * 
     *          71
     */

    public void setHead(SkipNode head) {

        this.head = head;
    }


    /**
     * 78
     * Searches for the KVPair using the key which is a Comparable object.
     * 79
     * 
     * 80
     * 
     * @param key
     *            81
     *            key to be searched for
     *            82
     * @return the value that we found
     *         83
     */

    public ArrayList<KVPair<K, V>> search(K key) {

        ArrayList<KVPair<K, V>> result = new ArrayList<KVPair<K, V>>();

        SkipNode curr = getHead();

        int level = getHead().level;

        // go through the list starting from highest level
        for (int i = level; i >= 0; i--) {
            while (curr.getForward()[i] != null && curr.getForward()[i]
                .element().getKey().compareTo(key) < 0) {
                curr = curr.getForward()[i];
            }
        }

        curr = curr.getForward()[0];

        // if one is found loop printing out the element until they do not

        // match

        if (curr != null && curr.element() != null)

            if (curr.element().getKey().compareTo(key) == 0) {

                while (curr != null && curr.element().getKey().compareTo(

                    key) == 0) {

                    result.add(curr.element());

                    curr = curr.getForward()[0];

                }

                return result;

            }

        return null;

    }


    /**
     * 117
     * 
     * @return the size of the SkipList
     *         118
     */

    public int size() {
        return size;
    }


    /**
     * 125
     * Inserts the KVPair in the SkipList at its appropriate spot as
     * designated
     * 126
     * by its lexicoragraphical order.
     * 127
     * 
     * 128
     * 
     * @param it
     *            129
     *            the KVPair to be inserted
     *            130
     */

    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
        int level = randomLevel();

        // make sure head has enough levels
        if (level > getHead().level) {
            adjustHead(level);
        }

        SkipNode curr = getHead();
        SkipNode[] update = (SkipNode[])Array.newInstance(SkipNode.class, level
            + 1);

        // traverses all levels from highest to lowest filling in update array
        for (int i = level; i >= 0; i--) {
            while (curr.getForward()[i] != null && curr.getForward()[i]
                .element().getKey().compareTo(it.getKey()) < 0) {

                curr = curr.getForward()[i];

            }

            update[i] = curr; // store update points

        }

        // new node to insert

        SkipNode node = new SkipNode(it, level);

        // insert node and fill in its forward pointers

        for (int i = 0; i <= level; i++) {

            node.getForward()[i] = update[i].getForward()[i];

            update[i].getForward()[i] = node;

        }
        size++;
    }


    /**
     * 166
     * Increases the number of levels in head so that no element has more
     * 167
     * indices than the head.
     * 168
     * 
     * 169
     * 
     * @param newLevel
     *            170
     *            the number of levels to be added to head
     *            171
     */

    @SuppressWarnings("unchecked")
    public void adjustHead(int newLevel) {
        SkipNode[] forward = (SkipNode[])Array.newInstance(SkipNode.class,
            newLevel + 1);
        System.arraycopy(getHead().getForward(), 0, forward, 0, getHead().level

            + 1);
        getHead().setForward(forward);
        getHead().level = newLevel;
    }


    /**
     * 184
     * Removes the KVPair that is passed in as a parameter and returns true
     * if
     * 185
     * the pair was valid and false if not.
     * 186
     * 
     * 187
     * 
     * @param key
     *            188
     *            the KVPair to be removed
     *            189
     * @return returns the removed pair if the pair was valid and null if not
     *         190
     */

    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {

        SkipNode curr = getHead();
        SkipNode[] update = (SkipNode[])Array.newInstance(SkipNode.class,
            getHead().level + 1);

        // start from the highest level and look for the item to be removed
        for (int i = getHead().level; i >= 0; i--) {
            // while the next node is smaller keep going
            // while (curr.forward[i] != null && curr.forward[i].element() !=
            // null
            // && curr.forward[i].element().getKey().compareTo(key) < 0) {
            while (curr.getForward()[i] != null && curr.getForward()[i]
                .element().getKey().compareTo(key) < 0) {

                curr = curr.getForward()[i];
            }
            update[i] = curr;

        }

        curr = curr.getForward()[0];

        // if (curr != null && curr.element() != null && curr.element().getKey()

        // .compareTo(key) == 0) {

        if (curr != null && curr.element().getKey().compareTo(key) == 0) {

            for (int i = 0; i <= curr.level; i++) {

                update[i].getForward()[i] = curr.getForward()[i];

            }

            size--;

            for (int i = 0; i <= curr.level; i++) {

                curr.getForward()[i] = null;

            }

            // change head level if needed

            while (getHead().level > 0 && getHead()

                .getForward()[getHead().level] == null) {

                getHead().level--;

            }

            return curr.element();

        }

        return null;

    }


    /**
     * 236
     * Removes a KVPair with the specified value.
     * 237
     * 
     * 238
     * 
     * @param val
     *            239
     *            the value of the KVPair to be removed
     *            240
     * @return returns true if the removal was successful
     *         241
     */

    public KVPair<K, V> removeByValue(V val) {

        SkipListIterator iter = new SkipListIterator();

        while (iter.hasNext()) {
            KVPair<K, V> curr = iter.next();

            if (curr.getValue().equals(val)) {
                return remove(curr.getKey());
            }

        }
        return null;
    }


    /**
     * 259
     * Prints out the SkipList in a human readable format to the console.
     * 260
     */

    public void dump() {

        System.out.println("SkipList dump:");

        SkipNode current = getHead();

        while (current != null) {
            System.out.println("Node with depth " + current.getForward().length
                + ", value " + current.element());
            current = current.getForward()[0];
        }

        System.out.println("SkipList size is: " + size);
    }

    /**
     * 277
     * This class implements a SkipNode for the SkipList data structure.
     * 278
     * 
     * 279
     * 
     * @author CS Staff
     *         280
     * 
     *         281
     * @version 2016-01-30
     *          282
     */

    class SkipNode {

        // the KVPair to hold

        private KVPair<K, V> pair;
        // An array of pointers to subsequent nodes

        private SkipNode[] forward;
        // the level of the node

        private int level;

        /**
         * 293
         * Initializes the fields with the required KVPair and the number of
         * 294
         * levels from the random level method in the SkipList.
         * 295
         * 
         * 296
         * 
         * @param tempPair
         *            297
         *            the KVPair to be inserted
         *            298
         * @param level
         *            299
         *            the number of levels that the SkipNode should have
         *            300
         */

        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            setForward((SkipNode[])Array.newInstance(SkipNode.class, level
                + 1));
            this.level = level;
        }


        /**
         * 311
         * getter method for level
         * 312
         * 
         * 313
         * 
         * @return level
         *         314
         */

        public int getLevel() {

            return level;

        }


        /**
         * 321
         * Returns the KVPair stored in the SkipList.
         * 322
         * 
         * 323
         * 
         * @return the KVPair
         *         324
         */

        public KVPair<K, V> element() {

            return pair;

        }


        /**
         * method getForward
         * 
         * @return the forward in the list
         */
        public SkipNode[] getForward() {

            return forward;
        }


        /**
         * method setForward
         * 
         * @param forward the parameter to be passed
         */
        public void setForward(SkipNode[] forward) {

            this.forward = forward;

        }

    }


    private class SkipListIterator implements Iterator<KVPair<K, V>> {

        private SkipNode current;

        public SkipListIterator() {

            current = getHead();

        }


        @Override
        public boolean hasNext() {
            return current.getForward()[0] != null;
        }


        @Override
        public KVPair<K, V> next() {
            KVPair<K, V> elem = current.getForward()[0].element();
            current = current.getForward()[0];
            return elem;
        }

    }

    @Override

    public Iterator<KVPair<K, V>> iterator() {

        return new SkipListIterator();

    }

}

