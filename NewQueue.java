import java.util.LinkedList;

/**
 * Created by hung on 07/03/15.
 */
public class NewQueue implements Container {


    LinkedList<PureCoordinates> frontier;

    /**
     * creates a queue to use in the generalise search, acts just like standard queue
     */
    public NewQueue()
    {
        frontier = new LinkedList<PureCoordinates>();
    }

    /**
     * get node in front of queue
     * @return node in front of queue
     */
    @Override
    public PureCoordinates poll() {
        return frontier.poll();
    }

    /**
     * push node to back of queue
     * @param c ndoe to push
     */
    @Override
    public void push(PureCoordinates c) {
        frontier.add(c);
    }

    /**
     * check if queue contain a node
     * @param c node to check
     * @return whether stack contains node
     */
    @Override
    public boolean contains(PureCoordinates c) {
        return frontier.contains(c);
    }

    /**
     * checks if queue is empty
     * @return whether queue is empty
     */
    @Override
    public boolean isEmpty() {
        return frontier.isEmpty();
    }

    /**
     * clears queue
     */
    @Override
    public void clear() {
        frontier.clear();
    }


}
