import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Comparator;

public class Test
{
    public static void main(String args[])
    {

        int [] [] nicksGraph = {
        {0,0,1,0,0,1}, 
        {0,1,0,0,1,1,0,2}, 
        {0,2,0,3,0,1}, 
        {0,3,0,2,0,4}, 
        {0,4,0,3,0,5}, 
        {0,5,0,6,1,5,0,4}, 
        {0,6,1,6,0,5}, 
        {1,0,0,0,1,1,2,0}, 
        {1,1,1,2,2,1,1,0,0,1}, 
        {1,2,2,2,1,1,1,3}, 
        {1,3,1,2,1,4,2,3}, 
        {1,4,2,4,1,5,1,3}, 
        {1,5,1,4,2,5,1,6,0,5}, 
        {1,6,0,6,1,5,2,6}, 
        {2,0,3,0,2,1,1,0}, 
        {2,1,2,2,1,1,2,0,3,1}, 
        {2,2,1,2,2,1,2,3,3,2}, 
        {2,3,2,2,2,4,3,3,1,3}, 
        {2,4,1,4,2,5,2,3,3,4}, 
        {2,5,2,4,1,5,2,6,3,5}, 
        {2,6,3,6,2,5,1,6}, 
        {3,0,2,0,3,1}, 
        {3,1,3,0,4,1,2,1,3,2}, 
        {3,2,2,2,4,2,3,1}, 
        {3,3,2,3,3,4}, 
        {3,4,2,4,3,3}, 
        {3,5,3,6,2,5,4,5}, 
        {3,6,2,6,3,5}, 
        {4,0}, 
        {4,1,4,2,5,1,3,1}, 
        {4,2,4,1,5,2,3,2}, 
        {4,3}, 
        {4,4}, 
        {4,5,5,5,3,5}, 
        {4,6}, 
        {5,0}, 
        {5,1,4,1,5,2,6,1}, 
        {5,2,4,2,5,1,6,2}, 
        {5,3}, 
        {5,4}, 
        {5,5,4,5,6,5}, 
        {5,6}, 
        {6,0,7,0,6,1}, 
        {6,1,6,0,5,1,6,2,7,1}, 
        {6,2,5,2,6,1,7,2}, 
        {6,3,7,3,6,4}, 
        {6,4,6,3,7,4}, 
        {6,5,5,5,6,6,7,5}, 
        {6,6,7,6,6,5}, 
        {7,0,6,0,7,1,8,0}, 
        {7,1,8,1,7,0,6,1,7,2}, 
        {7,2,7,3,8,2,6,2,7,1}, 
        {7,3,6,3,7,2,7,4,8,3}, 
        {7,4,7,3,8,4,6,4,7,5}, 
        {7,5,8,5,7,6,7,4,6,5}, 
        {7,6,6,6,7,5,8,6}, 
        {8,0,8,1,7,0,9,0}, 
        {8,1,8,2,9,1,7,1,8,0}, 
        {8,2,8,1,7,2,8,3}, 
        {8,3,8,2,7,3,8,4}, 
        {8,4,8,5,8,3,7,4}, 
        {8,5,9,5,8,4,7,5,8,6}, 
        {8,6,8,5,7,6,9,6}, 
        {9,0,9,1,8,0}, 
        {9,1,8,1,9,2,9,0}, 
        {9,2,9,1,9,3}, 
        {9,3,9,2,9,4}, 
        {9,4,9,5,9,3}, 
        {9,5,8,5,9,4,9,6}, 
        {9,6,9,5,8,6} 
        };
    
        GridMap map = new GridMap(nicksGraph);

        //print the map out

        for (int i = 0; i < map.getMapSize(); i ++)
        {
            System.out.println(map.getNode(i) + " : " + map.getNode(i).getSuccessors().toString());
        }


        DepthFirstSearch dfs = new DepthFirstSearch();
        BreadthFirstSearch bfs = new BreadthFirstSearch();

        final PureCoordinates goalNode = map.getNode(66);


        // the predicate the holds true if node being compared is goal node

        Predicate<PureCoordinates> goalTest = new Predicate<PureCoordinates>() {
            public boolean holds(PureCoordinates a) {
                return (a.compareTo(goalNode) == 0);
            }
        };

        System.out.println();

        long startTime = System.nanoTime();
        System.out.println(dfs.search(map.getNode(0), goalTest));
        long stopTime = System.nanoTime();
        long elapsed = stopTime - startTime;                            //to check the time it takes to complete the search

        System.out.println("Depth first search took: " + elapsed);

        startTime = System.nanoTime();
        System.out.println(bfs.search(map.getNode(0), goalTest));
        stopTime = System.nanoTime();
        elapsed = stopTime - startTime;

        System.out.println("Breadth first search took: " + elapsed);

        //due to using an ArrayList to store the nodes, ordering is not preserved, hence sometimes Depth First Search may perform better.
        //However, most of the time, breath first search does perform better as expected

        System.out.println();
        System.out.println("Path from " + map.getNode(0) + " to " + goalNode + " using DFS");
        System.out.println(dfs.findPathFrom(map.getNode(0),goalTest));

        System.out.println();
        System.out.println("Path from " + map.getNode(0) + " to " + goalNode + " using BFS");
        System.out.println(bfs.findPathFrom(map.getNode(0),goalTest));


        Function2<PureCoordinates,PureCoordinates,Double> heuristic = new Function2<PureCoordinates, PureCoordinates, Double>() {
            @Override
            public Double apply(PureCoordinates start, PureCoordinates goal) {
                return Math.sqrt((goal.getXCoord() - start.getXCoord()) * (goal.getXCoord() - start.getXCoord()) + (goal.getYCoord() - start.getYCoord()) * (goal.getYCoord() - start.getYCoord()));
            }
        };

        Function2<PureCoordinates,PureCoordinates,Integer> distance = new Function2<PureCoordinates, PureCoordinates, Integer>() {
            @Override
            public Integer apply(PureCoordinates pureCoordinates, PureCoordinates pureCoordinates2) {
                return 1;
            }
        };


        AStar aStar = new AStar(map);
        System.out.println();
        System.out.println("This is A* node search");
        System.out.println(aStar.findNodeFrom(map.getNode(0), map.getNode(66), heuristic, distance));
        System.out.println();
        System.out.println("This is A* path search");
        System.out.println(aStar.findPathFrom(map.getNode(0), map.getNode(66), heuristic, distance));

 	Comparator<PureCoordinates> lowestFPriority = new Comparator<PureCoordinates>() {
            @Override
            public int compare(PureCoordinates o1, PureCoordinates o2) {
                if (heuristic.apply(o1, map.getNode(66)) < heuristic.apply(o2,  map.getNode(66))) {
                    return 1;
                } else if (heuristic.apply(o1, map.getNode(66)) > heuristic.apply(o2, map.getNode(66))) {
                    return -1;
                }

                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };

        FitAllSearch fitAll = new FitAllSearch(map);
        System.out.println();
        System.out.println("This is a fit all container search");
        System.out.println(fitAll.findPathFrom(map.getNode(0), map.getNode(66), new NewPriorityQueue(lowestFPriority),heuristic)); //must define comparator for priority queue and pass it in

        //Just an experiment

        /*UlSearch ultimate = new UlSearch(map);
        System.out.println();
        System.out.println("This is the ultimate path search");
        System.out.println(ultimate.findPathFrom(map.getNode(0), map.getNode(66), new Stack<PureCoordinates>(), heuristic, distance));*/

        //Just an experiment
   }
}
