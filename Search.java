/**
 * interface to define different types of search
 */
public interface Search {

    public Maybe<PureCoordinates> search(PureCoordinates startNode, Predicate<PureCoordinates> p);

    public Maybe<IList<PureCoordinates>> findPathFrom(PureCoordinates startNode, Predicate<PureCoordinates> p);
}
