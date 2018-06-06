package pl.michalklecha.sns.treeBuilder.logic.graphVisualisation;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;
import pl.michalklecha.sns.treeBuilder.logic.sns.tree.Tree;

public class GraphstreamImpl implements Visualisator {
    private Graph graph;

    public GraphstreamImpl() {
        graph = new SingleGraph("Tree");
    }

    @Override
    public void show() {
        Viewer viewer = graph.display();
    }

    @Override
    public void loadTree(Tree tree) {
        Node root = graph.addNode(tree.getRoot().getLabel());
        root.addAttribute("label", tree.getRoot().getLabel());
        tree.getRoot().getChildren().forEach(child -> addChild(tree.getRoot(), child));
    }

    private void addChild(pl.michalklecha.sns.treeBuilder.logic.sns.tree.Node parent, pl.michalklecha.sns.treeBuilder.logic.sns.tree.Node node) {
        Node addedNode = graph.addNode(node.getLabel());
        addedNode.addAttribute("label", node.getLabel());
        graph.addEdge(parent.getLabel() + node.getLabel(), parent.getLabel(), node.getLabel());
        node.getChildren().forEach(child -> addChild(node, child));
    }
}
