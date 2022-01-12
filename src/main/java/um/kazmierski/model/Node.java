package um.kazmierski.model;

import um.kazmierski.Main;

import java.util.List;

public class Node {
    public Node left;
    public Node right;
    public Criterion criterion;
    public List<FullMovieInfo> movies;
    public int scoreLabel;
    public int depth;

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public Node(Node left, Node right, Criterion criterion, List<FullMovieInfo> movies, int scoreLabel, int depth) {
        this.left = left;
        this.right = right;
        this.criterion = criterion;
        this.movies = movies;
        this.scoreLabel = scoreLabel;
        this.depth = depth;
    }

    public static Node makeLeaf(List<FullMovieInfo> movies, int depth) {
        return new Node(null, null, null, movies, Main.getMajorityScoreLabel(movies), depth);
    }
}
