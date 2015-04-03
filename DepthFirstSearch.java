
import java.util.Stack;
import java.util.TreeSet;
import java.util.LinkedList;


public class DepthFirstSearch implements Search{

    Stack<PureCoordinates> frontier;
    TreeSet<PureCoordinates> visited;


    public DepthFirstSearch()
    {
        frontier = new Stack<PureCoordinates>();
        visited = new TreeSet<PureCoordinates>();
    }

    /**
     * performs depth first search
     * @param startNode node search starts at
     * @param p predicate that hold true for goal node
     * @return Maybe the node exist, maybe it doesn't, in which case Nothing is returned
     */
    @Override
    public Maybe<PureCoordinates> search(PureCoordinates startNode,Predicate<PureCoordinates> p) {

        PureCoordinates current;

        frontier.push(startNode);

        while (!frontier.isEmpty())
        {
            current = frontier.pop();

            System.out.println(current.toString());  // to show what the node popped is

            if (!visited.contains(current))
            {
                if (p.holds(current))
                {
                    visited.clear();
                    return new Just<PureCoordinates>(current);
                }
                else
                {
                    visited.add(current);
                    System.out.println("visited now has" + visited.toString());
                    System.out.println();

                    for (PureCoordinates n : current.getSuccessors())
                    {
                        frontier.push(n);
                        System.out.println("frontier now has " + frontier.toString());
                        System.out.println();
                    }
                }
            }
        }

        visited.clear();
        return new Nothing<PureCoordinates>();
    }

    /**
     *finding a path from given node to target in predicate, uses different method to hash map
     * @param startNode the given starting node
     * @param p predicate returns true if goal node found
     * @return path form start node to goal
     */
    @Override
    public Maybe<IList<PureCoordinates>> findPathFrom(PureCoordinates startNode,Predicate<PureCoordinates> p) {

        PureCoordinates current;
        visited.clear();
        frontier.clear();

        frontier.push(startNode);

        IList<PureCoordinates> path = new Cons<PureCoordinates>(startNode, new Nil<PureCoordinates>());

        while (!path.isEmpty())
        {
            while(!frontier.isEmpty())
            {
                current = frontier.pop();

                if (!visited.contains(current))
                {
                    if (p.holds(current))
                    {
                        path = path.append(current);
                        return new Just<IList<PureCoordinates>>(path.tail());
                    }
                    else
                    {
                        path = path.append(current);
                        visited.add(current);
                        frontier.clear();

                        for (PureCoordinates n : current.getSuccessors())
                        {
                            if(!visited.contains(n))
                            {
                                frontier.push(n);
                            }
                        }
                    }
                }
            }

            path = path.tail();
            if(!path.isEmpty())
            {
                current = path.head();
                for (PureCoordinates n : current.getSuccessors())
                {
                    if(!visited.contains(n))
                    {
                        frontier.push(n);
                        System.out.println("frontier now has " + frontier.toString());
                        System.out.println();
                    }
                }
            }
        }


        visited.clear();
        return new Nothing<IList<PureCoordinates>>();
    }


}
