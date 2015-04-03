import java.util.Stack;

/**
 * Created by hung on 07/03/15.
 */
public class NewStack implements Container {

    Stack<PureCoordinates> frontier;

    /**
     * creates a stack to use in generalised search, act just like standard stack
     */
    public NewStack()
    {
        frontier = new Stack<PureCoordinates>();
    }

    /**
     * pop node from top of stack
     * @return
     */
    @Override
    public PureCoordinates poll() {
        return frontier.pop();
    }

    /**
     * push node to top of stack
     * @param c node to push
     */
    @Override
    public void push(PureCoordinates c) {
       frontier.push(c);
    }

    /**
     * check if stack contain a node
     * @param c node to check
     * @return whether stack contains node
     */
    @Override
    public boolean contains(PureCoordinates c) {
        return frontier.contains(c);
    }

    /**
     * checks if stack is empty
     * @return whether stack is empty
     */
    @Override
    public boolean isEmpty() {
        return frontier.isEmpty();
    }

    /**
     * clears stack
     */
    @Override
    public void clear() {
        frontier.clear();
    }


}
