package pl.michalklecha.sns.treeBuilder.logic.graphVisualisation;

import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import org.apache.commons.collections15.Transformer;
import pl.michalklecha.sns.treeBuilder.charm.model.ItemsWithTids;
import pl.michalklecha.sns.treeBuilder.sns.tree.Node;
import pl.michalklecha.sns.treeBuilder.sns.tree.Tree;

import javax.swing.*;

public class JungImpl implements Visualisator{
    private DelegateTree<ItemsWithTids, Object> g;

    public void show() {
        BasicVisualizationServer<ItemsWithTids, String> vv = new BasicVisualizationServer<>(new TreeLayout(g));


        Transformer<ItemsWithTids, String> toStringTransformer = itemsWithTids -> itemsWithTids.getItems().toString().replaceAll("\\d", "");
        vv.getRenderContext().setVertexLabelTransformer(toStringTransformer);


        JPanel panel = new JPanel();
        panel.add(vv);

        JScrollPane scrollBar = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(scrollBar);

        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public void loadTree(Tree tree) {
        g = new DelegateTree<>();
        Node root = tree.getRoot();
        g.setRoot(root.getItem());
        addChildren(root);

    }

    private void addChildren(Node node) {
        node.getChildren().forEach((child) -> {
            g.addChild(new Object(), node.getItem(), child.getItem());
            addChildren(child);
        });
    }
}
