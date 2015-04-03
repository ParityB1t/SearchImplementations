import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Hung on 07/03/2015.
 */
public class UlSearch extends  AStar{

    /**
     * a cheat search based off A*, just turns any container to priority queue
     */
    PriorityQueue<PureCoordinates> pending;
    public UlSearch(GridMap map) {
        super(map);
    }

    /**
     * cheat search that just turns any container into priority queue
     * @param origin start node
     * @param destination goal node
     * @param rawFrontier container before conversion
     * @param heuristic heuristic used in A* search
     * @param distanceFunc * distance function sued in A* search
     * @return path from start node to goal
     */
    public Maybe<IList<PureCoordinates>> findPathFrom(PureCoordinates origin, final PureCoordinates destination, Collection<PureCoordinates> rawFrontier, final Function2<PureCoordinates,PureCoordinates,Double> heuristic, Function2<PureCoordinates,PureCoordinates,Integer> distanceFunc)
    {

        pending = new PriorityQueue<PureCoordinates>(rawFrontier);

        Cons<PureCoordinates> path = new Cons<PureCoordinates>(origin,new Nil<PureCoordinates>());
        PureCoordinates parent;

        Comparator<PureCoordinates> lowestFPriority = new Comparator<PureCoordinates>() {
            @Override
            public int compare(PureCoordinates o1, PureCoordinates o2) {
                if (heuristic.apply(o1,destination) < heuristic.apply(o2,destination))
                {
                    return 1;
                }
                else if (heuristic.apply(o1,destination) > heuristic.apply(o2,destination))
                {
                    return  -1;
                }

                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };

        pending = new PriorityQueue<PureCoordinates>(10,lowestFPriority);

        visited.clear();
        pending.clear(); //redundant
        predecessor.clear();

        pending.add(origin);

        for (int i = 0; i < map.getMapSize(); i++)
        {
            distance.put(map.getNode(i),Integer.MAX_VALUE);
            tight.put(map.getNode(i),false);
        }
        distance.put(origin,0);
        fDistance.put(origin,heuristic.apply(origin,destination));


        while (!pending.isEmpty())
        {
            PureCoordinates n = pending.poll();

            if (n == destination)
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
                        Integer cost = distance.get(n) + distanceFunc.apply(n,s);
                        if (cost < distance.get(s) || !pending.contains(s))
                        {
                            predecessor.put(s,n);
                            distance.put(s,cost);
                            fDistance.put(s,distance.get(s) + heuristic.apply(s, destination));
                            pending.add(s);
                        }
                    }
                }
            }

        }
        return new Nothing<IList<PureCoordinates>>();
    }
}
