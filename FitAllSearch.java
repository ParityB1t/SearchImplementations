import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by hung on 07/03/15.
 */
public class FitAllSearch {

    private LinkedHashMap<PureCoordinates,Integer> distance;
    private LinkedHashMap<PureCoordinates,Boolean> tight;
    private LinkedHashMap<PureCoordinates,PureCoordinates> predecessor;
    private LinkedHashMap<PureCoordinates,Double> fDistance;

    private HashSet<PureCoordinates> visited;
    private GridMap map = new GridMap();

    private Cons<PureCoordinates> path;

    /**
     * creates an instance of the fit-all search. Based on A*
     * @param map map used to set git-all search up
     */
    public FitAllSearch(GridMap map)
    {
        distance = new LinkedHashMap<PureCoordinates, Integer>();
        tight = new LinkedHashMap<PureCoordinates, Boolean>();
        predecessor = new LinkedHashMap<PureCoordinates, PureCoordinates>();
        fDistance = new LinkedHashMap<PureCoordinates, Double>();

        this.map = map;

        visited = new HashSet<PureCoordinates>();
    }

    /**
     * finds path from a start node to goal node. Generalised for queues, stacks and priority queue
     * for stack and queues, the heuristic is ignored
     * @param start start node
     * @param goal goal node
     * @param frontier container used, could be stack, queue or priority queue
     * @param heuristic heuristic used for priority queue
     * @return path from start to goal
     */
    public Maybe<IList<PureCoordinates>> findPathFrom(PureCoordinates start, PureCoordinates goal, Container frontier, Function2<PureCoordinates,PureCoordinates,Double> heuristic)
    {
        visited.clear();
        predecessor.clear();
        path = new Cons<PureCoordinates>(start,new Nil<PureCoordinates>());
        fDistance.clear();
        tight.clear();
        PureCoordinates parent;

        frontier.push(start);

        for (int i = 0; i < map.getMapSize(); i++)
        {
            distance.put(map.getNode(i),Integer.MAX_VALUE);
            tight.put(map.getNode(i),false);
        }
        distance.put(start,0);
        fDistance.put(start,heuristic.apply(start,goal));


        while (!frontier.isEmpty())
        {
            PureCoordinates n = frontier.poll();

            if (n == goal)
            {
                while (predecessor.keySet().contains(n))
                {
                    path = (Cons<PureCoordinates>) path.append(n);

                    parent = predecessor.get(n);
                    n = parent;
                }

                path = new Cons<PureCoordinates>(path.head(),path.tail().reverse());
                return new Just<IList<PureCoordinates>>(path);
            }
            else
            {
                visited.add(n);
                for (PureCoordinates s : n.getSuccessors())
                {
                    if (!visited.contains(s))
                    {
                        Integer cost = distance.get(n) + 1;
                        if (cost < distance.get(s) || !frontier.contains(s))
                        {
                            predecessor.put(s,n);
                            distance.put(s,cost);
                            fDistance.put(s,distance.get(s) + heuristic.apply(s, goal));
                            frontier.push(s);
                        }
                    }
                }
            }

        }
        return new Nothing<IList<PureCoordinates>>();
    }
}
