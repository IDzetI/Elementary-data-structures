public class Map_AVLTree<K extends Comparable, V> {

    /**
     * element for map
     */
    class Element {
        K key;
        V value;

        /**
         * create new element
         * @param key
         * @param value
         */
        public Element(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * nods in AVL Tree
     */
    class Node {
        int height;
        Element item;
        boolean isLeaf;
        Node leftSubtree;
        Node rightSubTree;
        Node parent;

        /**
         * Create new node
         * @param key
         * @param value
         * @param parent
         * @param isLeaf
         */
        public Node(K key, V value, Node parent, boolean isLeaf) {
            this.item = new Element(key, value);

            height = 0;

            this.isLeaf = isLeaf;
            this.parent = parent;
            leftSubtree = null;
            rightSubTree = null;
        }

        /**
         * method for taller node
         * @return node
         */
        public Node tallerSubTree() {
            if (this.leftSubtree.height > this.rightSubTree.height) {
                return this.leftSubtree;
            } else {
                return this.rightSubTree;
            }
        }
    }

    /**
     * root node
     */
    Node root;

    /**
     * create AVL Map tree
     */
    public Map_AVLTree() {
        this.root = new Node(null, null, null, true);
    }

    /**
     * add new Element
     * @param key
     * @param value
     */
    public void add(K key, V value) {
        
        if (root.item.key == null) {
            Node newNode = new Node(key, value, null, false);
            Node right = new Node(null, null, newNode, true);
            Node left = new Node(null, null, newNode, true);
            newNode.leftSubtree = left;
            newNode.rightSubTree = right;
            root = newNode;
            reHeight(root);
            return;
        }

        Node destNode = root;
        
        while (!destNode.isLeaf) {
            if (destNode.item.key.compareTo(key) < 0) {
                destNode = destNode.rightSubTree;
            } else {
                destNode = destNode.leftSubtree;
            }
        }

        Node buf = new Node(key, value, destNode.parent, false);

        if (destNode == destNode.parent.leftSubtree) {
            destNode.parent.leftSubtree = buf;
        } else {
            destNode.parent.rightSubTree = buf;
        }
        Node leftLeafNode = new Node(null, null, buf, true);
        Node rightLeafNode = new Node(null, null, buf, true);
        buf.leftSubtree = leftLeafNode;
        buf.rightSubTree = rightLeafNode;

        reHeight(buf);

        if (!hasBalance(buf)) {
            Node topNode, middleNode, bottomNode;
            topNode = findImbalancedNode(buf);
            middleNode = topNode.tallerSubTree();
            bottomNode = middleNode.tallerSubTree();
            rebuild(topNode, middleNode, bottomNode);
        }
    }

    /**
     * find unbalanced node
     * @param node
     * @return
     */
    private Node findImbalancedNode(Node node) {
        Node buf = node;
        while (buf != null) {
            if (Math.abs(buf.leftSubtree.height - buf.rightSubTree.height) == 2) {
                return buf;
            }
            buf = buf.parent;
        }
        return null;
    }

    /**
     * recompute height
     * @param node
     */
    private void reHeight(Node node) {
        Node bufNode = node;
        while (bufNode != null) {
            bufNode.height = 1 + Math.max(bufNode.leftSubtree.height, bufNode.rightSubTree.height);
            bufNode = bufNode.parent;
        }
    }



    /**
     * check Balance
     * @param node
     * @return
     */
    private boolean hasBalance(Node node) {
        Node buf = node;
        while (buf != null) {
            if (Math.abs(buf.leftSubtree.height - buf.rightSubTree.height) == 2) {
                return false;
            }
            buf = buf.parent;
        }
        return true;
    }


    /**
     * get Value from map
     * @param key
     * @return
     */
    public V getValue(K key) {
        Node bufNode = root;

        while (!bufNode.isLeaf) {
            K bufKey = bufNode.item.key;
            if (key.compareTo(bufKey) > 0) {
                bufNode = bufNode.rightSubTree;
            } else if (key.compareTo(bufKey) < 0) {
                bufNode = bufNode.leftSubtree;
            } else {
                return bufNode.item.value;
            }
        }
        return null;
    }


    /**
     * 
     * @param firstNode
     * @param secondNode
     * @param bottomNode
     */
    private void rebuild(Node firstNode, Node secondNode, Node bottomNode) {

        Node Tree1, Tree2, Tree3, Tree4, node1, node2, node3;

        // ALL THREE IN FULL FOUR
        if (secondNode == firstNode.leftSubtree && bottomNode == secondNode.leftSubtree) {
            node1 = bottomNode;
            node2 = secondNode;
            node3 = firstNode;
            Tree1 = node1.leftSubtree;
            Tree2 = node1.rightSubTree;
            Tree3 = node2.rightSubTree;
            Tree4 = node3.rightSubTree;
        } else if (secondNode == firstNode.leftSubtree && bottomNode == secondNode.rightSubTree) {
            node1 = secondNode;
            node2 = bottomNode;
            node3 = firstNode;
            Tree1 = node1.leftSubtree;
            Tree2 = node2.leftSubtree;
            Tree3 = node2.rightSubTree;
            Tree4 = node3.rightSubTree;
        } else if (secondNode == firstNode.rightSubTree && bottomNode == secondNode.leftSubtree) {
            node1 = firstNode;
            node2 = bottomNode;
            node3 = secondNode;
            Tree1 = node1.leftSubtree;
            Tree2 = node2.leftSubtree;
            Tree3 = node2.rightSubTree;
            Tree4 = node3.rightSubTree;
        } else {
            node1 = firstNode;
            node2 = secondNode;
            node3 = bottomNode;
            Tree1 = node1.leftSubtree;
            Tree2 = node2.leftSubtree;
            Tree3 = node3.leftSubtree;
            Tree4 = node3.rightSubTree;
        }

        if (root == firstNode) {
            node2.parent = null;
            root = node2;
        } else {Node prentFirstNode = firstNode.parent;

        if (prentFirstNode.leftSubtree == firstNode) {
                prentFirstNode.leftSubtree = node2;
            } else {
                prentFirstNode.rightSubTree = node2;
            }

            node2.parent = prentFirstNode;
        }

        node2.leftSubtree = node1;
        node2.rightSubTree = node3;
        node1.parent = node2;
        node3.parent = node2;

        node1.leftSubtree = Tree1;
        node1.rightSubTree = Tree2;
        if (Tree1 != null) {
            Tree1.parent = node1;
        }
        if (Tree2 != null) {
            Tree2.parent = node1;
        }
        node3.leftSubtree = Tree3;
        node3.rightSubTree = Tree4;
        if (Tree3 != null) {
            Tree3.parent = node3;
        }
        if (Tree4 != null) {
            Tree4.parent = node3;
        }
        reHeight(node1);
        reHeight(node3);
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean hasKey(K key) {
        Node bufNode = root;

        while (!bufNode.isLeaf) {
            K bufKey = bufNode.item.key;
            if (key.compareTo(bufKey) > 0) {
                bufNode = bufNode.rightSubTree;
            } else if (key.compareTo(bufKey) < 0) {
                bufNode = bufNode.leftSubtree;
            } else {
                return true;
            }
        }

        return false;
    }

}

