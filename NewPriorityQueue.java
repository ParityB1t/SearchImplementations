import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by hung on 07/03/15.
 */
public class NewPriorityQueue implements Container {

    PriorityQueue<PureCoordinates> frontier;

    /**
     * creates a priority queue which can be used a generalised search
     * @param comparator the comparator used to order items in the priority queue
     */
    public NewPriorityQueue(Comparator<PureCoordinates> comparator)
    {
        frontier = new PriorityQueue<PureCoordinates>(10, comparator);
    }

    /**
     * gets node with highest priority
     * @return node with highest priority
     */
    @Override
    public PureCoordinates poll() {
        return frontier.poll();
    }

    /**
     * push node to priority queue and bubbles it to correct priority ordering
     * @param c node to push
     */
    @Override
    public void push(PureCoordinates c) {
        frontier.add(c);
    }

    /**
     * check if priority queue contain a node
     * @param c node to check
     * @return whether stack contains node
     */
    @Override
    public boolean contains(PureCoordinates c) {
        return frontier.contains(c);
    }

    /**
     * checks if priority queue is empty
     * @return whether priority queue is empty
     */
    @Override
    public boolean isEmpty() {
        return frontier.isEmpty();
    }

    /**
     * clears priority queue
     */
    @Override
    public void clear() {
        frontier.clear();
    }
}
