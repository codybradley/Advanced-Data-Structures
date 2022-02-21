// Cody Bradley
// Comp 282, MoWe 2:00PM - 3:15PM
// Programming Assignment #2
// 7 October 2020
// This file implements splay binary search tree
// that consists of nodes whose keys are strings

class StringNode {

    private String word;
    private StringNode left, right;

    // The only constructor you will need
    public StringNode(String w) {
        word = w;
        left = null;
        right = null;
    }

    public String getString() {
        return word;
    }

    public StringNode getLeft() {
        return left;
    }

    public void setLeft(StringNode pt) {
        left = pt;
    }

    public StringNode getRight() {
        return right;
    }

    public void setRight(StringNode pt) {
        right = pt;
    }
} // StringNode

// So that a String can change. There is nothing you need to add
// to this class
class WrapString {
    // Yes, I am allowing (and encouraging) direct access to the String
    public String string;

    public WrapString(String str) {
        this.string = str;
    }
}

class SplayBST {

    // member variable pointing to the root of the splay tree
    // It really should be private but I need access to it for the test program
    StringNode root;

    // default constructor
    public SplayBST() {
        root = null;
    }

    // copy constructor
    // Be sure to make a copy of the entire tree
    // Do not make two pointers point to the same tree
    public SplayBST(SplayBST t) {
        if(t.root == null)//original tree was empty
            this.root = null;
        else
            this.root = copySubtree(t.root);
    }

    //helper function for the copy constructor
    private static StringNode copySubtree(StringNode originalNode){
        StringNode copyOfNode;
        if(originalNode==null)
            copyOfNode=null;
        else {
            //copy data in current node
            copyOfNode = new StringNode(originalNode.getString());
            //copy left subtree
            copyOfNode.setLeft(copySubtree(originalNode.getLeft()));
            //copy right subtree
            copyOfNode.setRight(copySubtree(originalNode.getRight()));
        }
        return copyOfNode;
    }

    // like last time
    public static String myName() {
        return "Cody Bradley";
    }

    // This is the driver method. You should also check for and perform
    // a final zig here
    // You will also have to write the 2-parameter recursive insert method
    public void insert(String s) {
        root = insert(s, root);

        if (!root.getString().equals(s)) {//not done splaying if string isn't at root
            if (root.getLeft() != null && root.getLeft().getString().equals(s))
                root = rotateRight(root);//need to splay left child
            else if (root.getRight() != null && root.getRight().getString().equals(s))
                root = rotateLeft(root);//need to splay right child
            else {
                //shouldn't happen if program works properly, just for testing
                System.out.println("\nError: Could not perform final zig.");
            }
        }
    }

    private static StringNode insert(String s, StringNode t) {

        if (t == null)//this is where the string s belongs
            t = new StringNode(s);
        else {
            //probably faster to store the compare value than call compareTo twice
            int compareValue = s.compareTo(t.getString());
            boolean rotationDone = false;
            //rotationDone used to prevent looking at left or right subtree of a null node
            if (compareValue < 0) {//s<t
                t.setLeft(insert(s, t.getLeft()));//insert s into left subtree
                //backtracking, splay node if it is a grandchild
                if (t.getLeft().getLeft() != null)//check if left left grandchild exists
                    if (t.getLeft().getLeft().getString().equals(s)) {
                        //left left grandchild needs to be splayed
                        t = rotateRight(t);//t is now parent of node to splay
                        t = rotateRight(t);//after rotation, t is node to splay
                        rotationDone = true;
                    }
                if (!rotationDone && t.getLeft().getRight() != null)//check if left right grandchild exists
                    if (t.getLeft().getRight().getString().equals(s)) {
                        //left right grandchild needs to be splayed
                        t.setLeft(rotateLeft(t.getLeft()));
                        t = rotateRight(t);//after rotation, t is node to splay
                    }
            } else if (compareValue > 0) {//s>t
                t.setRight(insert(s, t.getRight()));//insert s into right subtree
                //backtracking, splay node if it is a grandchild
                if (t.getRight().getRight() != null)//check if right right grandchild exists
                    if (t.getRight().getRight().getString().equals(s)) {
                        //right right grandchild needs to be splayed
                        t = rotateLeft(t);//after rotation, t is parent of node to splay
                        t = rotateLeft(t);//after rotation, t is node to splay
                        rotationDone = true;
                    }
                if (!rotationDone && t.getRight().getLeft() != null)//check if left right grandchild exists
                    if (t.getRight().getLeft().getString().equals(s)) {
                        //left right grandchild needs to be splayed
                        t.setRight(rotateRight(t.getRight()));//t is now parent of node to splay
                        t = rotateLeft(t);//after rotation, t is node to splay
                    }
            }
            //else string s is already in the tree, located at the current node
        }

        return t;
    }

    // if s is not in the tree, splay the last node visited
    // final zig, if needed, is done here
    // Return null if the string is not found
    public StringNode search(String s) {
        WrapString str = new WrapString(s);
        root = search(str, root);

        //final zig if string isn't at root
        if (root != null && !root.getString().equals(str.string)) {
            if (root.getLeft() != null && root.getLeft().getString().equals(str.string))
                root = rotateRight(root);//need to splay left child
            else if (root.getRight() != null && root.getRight().getString().equals(str.string))
                root = rotateLeft(root);//need to splay right child
            else {
                //shouldn't happen if program works properly, just for testing
                System.out.println("\nError: Could not perform final zig.");
            }
        }

        StringNode retNode;
        if(!str.string.equals(s))
            retNode = null;//if str is no longer equal to s, s was not found
        else
            retNode = root;
        return retNode;
    }

    // recursive search method
    // if str not in the tree str backtracks with value of last node visited
    public StringNode search(WrapString str, StringNode t) {
        if (t == null || t.getString().equals(str.string))
            ;//return the current node (null or the node we are looking for)
        else {
            //probably faster to store the compare value than call compareTo twice
            int compareValue = str.string.compareTo(t.getString());
            boolean rotationDone = false;
            if (compareValue < 0) {//s<t
                if (t.getLeft() == null)//if this node has no left child
                    str.string = t.getString();//begin backtracking with this node's data value
                else
                    t.setLeft(search(str, t.getLeft()));//search left subtree

                //backtracking, splay node if it is a grandchild
                if (t.getLeft() != null) {
                    if (t.getLeft().getLeft() != null)//check if left left grandchild exists
                        if (t.getLeft().getLeft().getString().equals(str.string)) {
                            //left left grandchild needs to be splayed
                            t = rotateRight(t);//t is now parent of node to splay
                            t = rotateRight(t);//after rotation, t is node to splay
                            rotationDone = true;
                        }
                    if (!rotationDone && t.getLeft().getRight() != null)//check if left right grandchild exists
                        if (t.getLeft().getRight().getString().equals(str.string)) {
                            //left right grandchild needs to be splayed
                            t.setLeft(rotateLeft(t.getLeft()));//after rotation, t is parent of node to splay
                            t = rotateRight(t);//after rotation, t is node to splay
                        }
                }
            } else if (compareValue > 0) {//s>t
                if (t.getRight() == null)//if this node has no right child
                    str.string = t.getString();//begin backtracking with this node's data value
                else
                    t.setRight(search(str, t.getRight()));//search right subtree

                //backtracking, splay node if it is a grandchild
                if (t.getRight() != null) {
                    if (t.getRight().getRight() != null)//check if right right grandchild exists
                        if (t.getRight().getRight().getString().equals(str.string)) {
                            //right right grandchild needs to be splayed
                            t = rotateLeft(t);//after rotation, t is parent of node to splay
                            t = rotateLeft(t);//after rotation, t is node to splay
                            rotationDone = true;
                        }
                    if (!rotationDone && t.getRight().getLeft() != null)//check if left right grandchild exists
                        if (t.getRight().getLeft().getString().equals(str.string)) {
                            //left right grandchild needs to be splayed
                            t.setRight(rotateRight(t.getRight()));//after rotation, t is parent of node to splay
                            t = rotateLeft(t);//after rotation, t is node to splay
                        }
                }
            }
        }

        return t;
    }

    public static StringNode rotateLeft(StringNode t) {
        StringNode tempNode = t;
        if (t.getRight() == null) {
            //shouldn't happen if program works properly, just for testing
            System.out.println("\nError. Cannot rotate left when right child is null");
        }
        else {
            tempNode = t.getRight();
            t.setRight(tempNode.getLeft());
            tempNode.setLeft(t);
        }
        return tempNode;//new root of the tree that originally had the root t
    }

    public static StringNode rotateRight(StringNode t) {
        StringNode tempNode = t;
        if (t.getLeft() == null) {
            //shouldn't happen if program works properly, just for testing
            System.out.println("\nError. Cannot rotate right when left child is null");
        }
        else {
            tempNode = t.getLeft();
            t.setLeft(tempNode.getRight());
            tempNode.setRight(t);
        }
        return tempNode;//new root of the tree that originally had the root t
    }

    // How many leaves in the splay tree?
    public int leafCt() {
        int count = 0;
        if (root != null)//if root is null, no leaves, return 0
            count = leafCt(root);
        return count;
    }

    private static int leafCt(StringNode node) {
        int count = 0;
        if (node.getLeft() == null && node.getRight() == null)
            count = 1;//both left and right are null, this node is a leaf
        else {//either left, right, or both subtrees are not null
            if (node.getLeft() != null)
                count += leafCt(node.getLeft());
            if (node.getRight() != null)
                count += leafCt(node.getRight());
        }

        return count;
    }

    // What is the height the splay tree?
    public int height() {
        int highest = 0;
        if (root != null)// if root is null, empty tree, height is 0
            highest = height(root);
        return highest;
    }

    private static int height(StringNode node) {
        int highest, leftHighest = 0, rightHighest = 0;
        if (node.getLeft() == null && node.getRight() == null)
            highest = 1;
        else {//left or right or both subtrees are not null
            if (node.getLeft() != null)
                leftHighest = height(node.getLeft());
            if (node.getRight() != null)
                rightHighest = height(node.getRight());
            if (leftHighest > rightHighest)
                highest = 1 + leftHighest;
            else//leftHighest<=rightHighest
                highest = 1 + rightHighest;
        }

        return highest;
    }

    // How many nodes have exactly 1 non-null children
    public int stickCt() {
        int count = 0;
        if (root != null)//if root is null, no sticks, return 0
            count = stickCt(root);
        return count;
    }

    private static int stickCt(StringNode node){
        int count = 0;
        if(node.getLeft()!=null&&node.getRight()!=null)
            count = stickCt(node.getLeft())+stickCt(node.getRight());
        else if(node.getLeft()!=null&&node.getRight()==null)
            count = 1 + stickCt(node.getLeft());
        else if(node.getLeft()==null&&node.getRight()!=null)
            count = 1 + stickCt(node.getRight());
        //else current node is a leaf, return 0

        return count;
    }
}
