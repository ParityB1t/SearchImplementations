/**
 * Created by hung on 07/03/15.
 */

/**
 * The container interface for stacks, queues and priority queues, used in the fit-all search
 */
public interface Container {

    PureCoordinates poll();

    void push(PureCoordinates c);

    boolean contains(PureCoordinates c);

    boolean isEmpty();

    void clear();

}
