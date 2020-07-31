package zaibacu.graphql.wrappers;

import java.io.Serializable;
import java.util.List;

public class NodeArrayWrapper<T extends Serializable> {
    private class NodeWrapper<U extends Serializable>{
        private U node;
    }

    private List<NodeWrapper<T>> edges;
}
