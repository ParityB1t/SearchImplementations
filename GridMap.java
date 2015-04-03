import java.util.*;


public class GridMap {

    private ArrayList<PureCoordinates> map;

    public GridMap()
    {

    }

    /**
     *Creates a map out of received graph. If node already exist, ensures they're the same object
     * @param receivedGraph graph receive to create the map, should be of int[][] type
     */
    public GridMap(int[][] receivedGraph)
    {
        map = new ArrayList<PureCoordinates>();

        for (int i = 0; i < receivedGraph.length; i++)
        {
            PureCoordinates coordinates = (new PureCoordinates(receivedGraph[i][0], receivedGraph[i][1]));
            PureCoordinates node = nodeWith(coordinates);


            for (int j = 2; j < receivedGraph[i].length; j +=2)
            {
                PureCoordinates successorCoord = (new PureCoordinates(receivedGraph[i][j], receivedGraph[i][j + 1]));
                PureCoordinates successorNode = nodeWith(successorCoord);

                node.addSuccessor(successorNode);
            }
        }


    }

    /**
     * Checks if node already exists in the map, if so ensures they're the same object, else creates a new node
     * and adds it to the map
     * @param n the node to check for if it already exists in map
     * @return a node in subject
     */
    private PureCoordinates nodeWith(PureCoordinates n)
    {
        PureCoordinates node = null;
        boolean nodeFound = false;
        int i = 0;

        while (nodeFound == false && i < map.size())
        {
            if (map.get(i).getXCoord() == n.getXCoord() && map.get(i).getYCoord() == n.getYCoord()) {
                node = map.get(i);
                nodeFound = true;
            }
            else
                i++;
        }

        if (nodeFound == false)
        {
            node = n;
            map.add(node);
        }

        return node;
    }


    /**
     * gets the node at specified index
     * @param index the index
     * @return node at index
     */
    public PureCoordinates getNode(int index)
    {
        return map.get(index);
    }

    /**
     * gets size of map / number of nodes
     * @return number of nodes in map
     */
    public  int getMapSize()
    {
        return map.size();
    }
}
