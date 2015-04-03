
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

/**
 * Created by Hung on 04/03/2015.
 */
public class AStar {

    protected LinkedHashMap<PureCoordinates, Integer> distance;
    protected LinkedHashMap<PureCoordinates, Boolean> tight;
    protected LinkedHashMap<PureCoordinates, PureCoordinates> predecessor;
    protected LinkedHashMap<PureCoordinates, Double> fDistance;

    protected GridMap map = new GridMap();

    protected HashSet<PureCoordinates> visited;
    protected PriorityQueue<PureCoordinates> pending;

    /**
     * creates an instance of A* search
     *
     * @param map map required to set up an A* search
     */
    public AStar(GridMap map) {
        distance = new LinkedHashMap<PureCoordinates, Integer>();
        tight = new LinkedHashMap<PureCoordinates, Boolean>();
        predecessor = new LinkedHashMap<PureCoordinates, PureCoordinates>();
        fDistance = new LinkedHashMap<PureCoordinates, Double>();

        visited = new HashSet<PureCoordinates>();
        pending = new PriorityQueue<PureCoordinates>();
        this.map = map;

    }

    /**
     * find a goal node from a start node using A* search
     *
     * @param origin       start node
     * @param destination  goal node
     * @param heuristic    the heuristic function used to estimate shortest path
     * @param distanceFunc the distance funtion to calculate distance between 2 nodes
     * @return the goal node
     */
    public Maybe<PureCoordinates> findNodeFrom(PureCoordinates origin, final PureCoordinates destination, final Function2<PureCoordinates, PureCoordinates, Double> heuristic, Function2<PureCoordinates, PureCoordinates, Integer> distanceFunc) {
        Comparator<PureCoordinates> lowestFPriority = new Comparator<PureCoordinates>() {
            @Override
            public int compare(PureCoordinates o1, PureCoordinates o2) {
                if (heuristic.apply(o1, destination) < heuristic.apply(o2, destination)) {
                    return 1;
                } else if (heuristic.apply(o1, destination) > heuristic.apply(o2, destination)) {
                    return -1;
                }

                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };

        pending = new PriorityQueue<PureCoordinates>(10, lowestFPriority);

        visited.clear();

        pending.add(origin);

        for (int i = 0; i < map.getMapSize(); i++) {
            distance.put(map.getNode(i), Integer.MAX_VALUE);
            tight.put(map.getNode(i), false);
        }
        distance.put(origin, 0);
        fDistance.put(origin, heuristic.apply(origin, destination));

        while (!pending.isEmpty()) {
            PureCoordinates n = pending.poll();

            if (n == destination) {
                return new Just<PureCoordinates>(n);

            } else {
                visited.add(n);
                for (PureCoordinates s : n.getSuccessors()) {
                    if (!visited.contains(s)) {
                        Integer cost = distance.get(n) + distanceFunc.apply(n, s);
                        if (cost < distance.get(s) || !pending.contains(s)) {
                            predecessor.put(s, n);
                            distance.put(s, cost);
                            fDistance.put(s, distance.get(s) + heuristic.apply(s, destination));
                            pending.add(s);
                        }
                    }
                }
            }

        }
        return new Nothing<PureCoordinates>();
    }

    /**
     * find a path from the start node from a goal node using A* search
     * @param origin       start node
     * @param destination  goal node
     * @param heuristic    the heuristic function used to estimate shortest path
     * @param distanceFunc the distance function to calculate distance between 2 nodes
     * @return path to goal node from start node
     */
    public Maybe<IList<PureCoordinates>> findPathFrom(PureCoordinates origin, final PureCoordinates destination, final Function2<PureCoordinates, PureCoordinates, Double> heuristic, Function2<PureCoordinates, PureCoordinates, Integer> distanceFunc) {
        Cons<PureCoordinates> path = new Cons<PureCoordinates>(origin, new Nil<PureCoordinates>());
        PureCoordinates parent;

        Comparator<PureCoordinates> lowestFPriority = new Comparator<PureCoordinates>() {
            @Override
            public int compare(PureCoordinates o1, PureCoordinates o2) {
                if (heuristic.apply(o1, destination) < heuristic.apply(o2, destination)) {
                    return 1;
                } else if (heuristic.apply(o1, destination) > heuristic.apply(o2, destination)) {
                    return -1;
                }

                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };

        pending = new PriorityQueue<PureCoordinates>(10, lowestFPriority);

        visited.clear();
        predecessor.clear();

        pending.add(origin);

        for (int i = 0; i < map.getMapSize(); i++) {
            distance.put(map.getNode(i), Integer.MAX_VALUE);
            tight.put(map.getNode(i), false);
        }
        distance.put(origin, 0);
        fDistance.put(origin, heuristic.apply(origin, destination));


        while (!pending.isEmpty()) {
            PureCoordinates n = pending.poll();

            if (n == destination) {
                while (predecessor.keySet().contains(n)) {
                    path = (Cons<PureCoordinates>) path.append(n);

                    parent = predecessor.get(n);
                    n = parent;
                }

                path = new Cons<PureCoordinates>(path.head(), path.tail().reverse());
                return new Just<IList<PureCoordinates>>(path);
            } else {
                visited.add(n);
                for (PureCoordinates s : n.getSuccessors()) {
                    if (!visited.contains(s)) {
                        Integer cost = distance.get(n) + distanceFunc.apply(n, s);
                        if (cost < distance.get(s) || !pending.contains(s)) {
                            predecessor.put(s, n);
                            distance.put(s, cost);
                            fDistance.put(s, distance.get(s) + heuristic.apply(s, destination));
                            pending.add(s);
                        }
                    }
                }
            }

        }
        return new Nothing<IList<PureCoordinates>>();
    }

}