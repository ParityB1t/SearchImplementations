import java.lang.Override;


import java.util.LinkedHashSet;



public class PureCoordinates implements Comparable<PureCoordinates> {

    
    private LinkedHashSet<PureCoordinates> succesors;
    private int xCoord;
    private int yCoord;

    /**
     * Creates a node, which in this case also includes the content
     * @param x the x coordinates of the node
     * @param y the y coordinates of the node
     */
    public PureCoordinates(int x, int y)
    {
        this.xCoord = x;
        this.yCoord = y;
        succesors = new LinkedHashSet<PureCoordinates>();

    }

    /**
     * adds successors the node
     * @param n the successor node
     */
    public  void addSuccessor(PureCoordinates n)
    {
        succesors.add(n);
    }

    /**
     * gets the successors of this node
     * @return sucessors of this node
     */
    public LinkedHashSet<PureCoordinates> getSuccessors()
    {
        return succesors;
    }

    /**
     * implements comparable, checks whether another node is the same node as this node
     * @param node the other node
     * @return 0 if same node, -1 otherwise
     */
    @Override
    public int compareTo(PureCoordinates node)
    {
         if (xCoord == node.getXCoord() && yCoord ==  node.getYCoord())
         {
             return 0;
         }
        else
         {
             return -1;
         }
    }

    /**
     * gets x coordinates
     * @return x coordinates
     */
     public int getXCoord()
    {
        return xCoord;
    }

    /**
     * gets y coordinates
     * @return y coordinates
     */
    public int getYCoord()
    {
        return yCoord;
    }

    /**
     * prints out the node's content
     * @return the node's content in string
     */
    public String toString()
    {
        return  ("(" + getXCoord() + ", " + getYCoord() + ")");
    }
}
