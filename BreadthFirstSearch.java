import java.util.LinkedList;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class BreadthFirstSearch implements Search{

    LinkedList<PureCoordinates> frontier;
    TreeSet<PureCoordinates> visited;
    LinkedHashMap<PureCoordinates,PureCoordinates> possiblePath;

    public  BreadthFirstSearch()
    {
        frontier = new LinkedList<PureCoordinates>();
        visited = new TreeSet<PureCoordinates>();
	possiblePath = new LinkedHashMap<PureCoordinates,PureCoordinates>();
    }

    /**
     * performs breadth first search
     * @param startNode node search starts at
     * @param p predicate that hold true for goal node
     * @return Maybe the node exist, maybe it doesn't, in which case Nothing is returned
     */
    @Override
    public Maybe<PureCoordinates> search(PureCoordinates startNode, Predicate<PureCoordinates> p) {

        PureCoordinates current;

        frontier.add(startNode);

        while (!frontier.isEmpty())
        {
            current = frontier.poll();

            if (!visited.contains(current))
            {
                if (p.holds(current))
                {
                    visited.clear();                              //clears visited list for next search
                    return new Just<PureCoordinates>(current);
                }
                else
                {
                    visited.add(current);

                    frontier.addAll(current.getSuccessors());
                }
            }
        }

        visited.clear();
        return new Nothing<PureCoordinates>();
    }

    /**
     * finding a path from given node to target in predicate using Breadth frist search, uses hash map method
     * @param x the given starting node
     * @param p predicate returns true if goal node found
     * @return path form start node to goal
     */
    public Maybe<IList<PureCoordinates>> findPathFrom(PureCoordinates x, Predicate<PureCoordinates> p)
    {
        PureCoordinates current;
        Cons<PureCoordinates> path = new Cons<PureCoordinates>(x,new Nil<PureCoordinates>());
        PureCoordinates parent;
        frontier.clear();
        visited.clear();
        possiblePath.clear();

        frontier.add(x);


        while (!frontier.isEmpty()) {

            current = frontier.poll();

            if (!visited.contains(current)) {

                if (p.holds(current))
                {

                    while (possiblePath.keySet().contains(current)) {

                        path = (Cons<PureCoordinates>) path.append(current);

                        parent = possiblePath.get(current);
                        current = parent;

                    }

                    path = new Cons<PureCoordinates>(path.head(),path.tail().reverse());
                    return new Just<IList<PureCoordinates>>(path);
                } else
                {

                    visited.add(current);
                    frontier.addAll(current.getSuccessors());

                    for (PureCoordinates successor : current.getSuccessors()) {
                        if (!possiblePath.keySet().contains(successor) && !possiblePath.containsValue(successor))
                            possiblePath.put(successor, current);
                    }


                }
            }
        }


        return new Nothing<IList<PureCoordinates>>();
    }




}

