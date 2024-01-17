import java.util.function.Predicate;
import tester.Tester;

//to represent a node
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  //counts the number of nodes
  abstract int sizeHelper();

  //EFFECT:removes the first node from this Deque
  abstract T removeFromHelper();

  //finds the first node that the given predicate returns true for
  abstract ANode<T> findHelper(Predicate<T> pred);

  //helper function for removeNode
  abstract void removeNodeHelper();
}

//to represent a node
class Node<T> extends ANode<T> {
  T data;

  //constructor that creates a node
  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  //constructor to create a node in a list
  Node(T data, ANode<T> next, ANode<T> prev) {
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Invalid null node");
    }
    this.data = data;
    this.next = next;
    this.prev = prev;
    prev.next = this;
    next.prev = this;
  }

  //counts the number of nodes in a list Deque
  int size() {
    return this.sizeHelper();
  }

  //counts the number of nodes
  int sizeHelper() {
    return 1 + this.next.sizeHelper();
  }

  //EFFECT:removes the node from this Deque
  T removeFromHelper() {
    prev.next = this.next;
    next.prev = this.prev;
    return this.data;
  }

  //finds the first node that the given predicate returns true for
  ANode<T> findHelper(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.findHelper(pred);
    }
  }

  //helper function for removeNode
  void removeNodeHelper() {
    this.removeFromHelper();
  }
}

//to represent a sentinel
class Sentinel<T> extends ANode<T> {

  //constructor that takes zero args. for sentinels, the next=head prev=tail of the list
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  //counts the number of nodes in a list Deque
  int size() {
    return this.next.sizeHelper();
  }

  //returns the count of nodes in a sentinel
  int sizeHelper() {
    return 0;
  }

  //consumes a value of type T and inserts it at the front of the list
  void addAtHead(T given) {
    new Node<T>(given, this.next, this);
  }

  //consumes a value of type T and inserts it at the front of the list
  void addAtTail(T given) {
    new Node<T>(given, this, this.prev);
  }

  //EFFECT:removes the first node from this Deque
  T removeFromHead() {
    return this.next.removeFromHelper();
  }

  //EFFECT:removes the last node from this Deque
  T removeFromTail() {
    return this.prev.removeFromHelper();
  }

  //EFFECT:removes the node from a sentinel
  T removeFromHelper() {
    throw new RuntimeException("Can't remove on an empty list");
  }

  //produces the first node in this Deque for which the given predicate returns true
  ANode<T> find(Predicate<T> pred) {
    return this.next.findHelper(pred);
  }

  //finds the first node that the given predicate returns true for
  ANode<T> findHelper(Predicate<T> pred) {
    return this;
  }

  //helper function for removeNode
  void removeNodeHelper() {
    return;
  }
}

//to represent a deque
class Deque<T> {
  Sentinel<T> header;

  //constructor with no args
  Deque() {
    this.header = new Sentinel<T>();
  }

  //constructor that takes in a sentinel
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  //counts the number of nodes in a list Deque
  int size() {
    return this.header.size();
  }

  //EFFECT: consumes a value of type T and inserts it at the front of the list
  void addAtHead(T given) {
    this.header.addAtHead(given);
  }

  //EFFECT: consumes a value of type T and inserts it at the tail of this list
  void addAtTail(T given) {
    this.header.addAtTail(given);
  }

  //EFFECT:removes the first node from this Deque
  T removeFromHead() {
    return this.header.removeFromHead();
  }

  //EFFECT:removes the last node from this Deque
  }

  //produces the first node in this Deque for which the given predicate returns true
  ANode<T> find(Predicate<T> pred) {
    return this.header.find(pred);
  }

  //removes the given node from this Deque
  void removeNode(ANode<T> rmvdNode) {
    rmvdNode.removeNodeHelper();
  }
}

//class for predicate test
class NodeSame<T> implements Predicate<T> {
  T type;

  NodeSame(T type) {
    this.type = type;
  }

  //boolean method for nodesame
  public boolean test(T other) {
    return this.type.equals(other);
  }
}

//examples and tests for the classes and methods in Deque
class ExamplesDeque {
  Sentinel<String> sent0 = new Sentinel<String>();
  Deque<String> deque1 = new Deque<String>();
  Sentinel<String> sent2 = new Sentinel<String>();
  Node<String> node2n1 = new Node<String>("abc", sent2, sent2);
  Node<String> node2n2 = new Node<String>("bcd", sent2, node2n1);
  Node<String> node2n3 = new Node<String>("cde", sent2, node2n2);
  Node<String> node2n4 = new Node<String>("def", sent2, node2n3);
  Deque<String> deque2 = new Deque<String>(sent2);
  Sentinel<Integer> sent3 = new Sentinel<Integer>();
  Node<Integer> node3n1 = new Node<Integer>(44, sent3, sent3);
  Node<Integer> node3n2 = new Node<Integer>(33, sent3, node3n1);
  Node<Integer> node3n3 = new Node<Integer>(37, sent3, node3n2);
  Node<Integer> node3n4 = new Node<Integer>(12, sent3, node3n3);
  Deque<Integer> deque3 = new Deque<Integer>(sent3);

  //resets to initial conditions
  void iniData() {
    sent0 = new Sentinel<String>();
    deque1 = new Deque<String>(sent0);
    sent2 = new Sentinel<String>();
    node2n1 = new Node<String>("abc", sent2, sent2);
    node2n2 = new Node<String>("bcd", sent2, node2n1);
    node2n3 = new Node<String>("cde", sent2, node2n2);
    node2n4 = new Node<String>("def", sent2, node2n3);
    deque2 = new Deque<String>(sent2);
    sent3 = new Sentinel<Integer>();
    node3n1 = new Node<Integer>(44, sent3, sent3);
    node3n2 = new Node<Integer>(33, sent3, node3n1);
    node3n3 = new Node<Integer>(37, sent3, node3n2);
    node3n4 = new Node<Integer>(12, sent3, node3n3);
    deque3 = new Deque<Integer>(sent3);
  }

  //tests the method size
  boolean testSize(Tester t) {
    iniData();
    return t.checkExpect(deque1.size(), 0)
        && t.checkExpect(deque2.size(), 4)
        && t.checkExpect(deque3.size(), 4);
  }

  //tests the helper func sizeHelper
  boolean testSizeHelper(Tester t) {
    iniData();
    return t.checkExpect(sent0.sizeHelper(), 0)
        && t.checkExpect(sent2.sizeHelper(), 0)
        && t.checkExpect(sent3.sizeHelper(), 0)
        && t.checkExpect(node2n1.sizeHelper(), 4)
        && t.checkExpect(node3n2.sizeHelper(), 3);
  }

  //tests the method addAtHead
  void testAddAtHead(Tester t) {
    iniData();
    t.checkExpect(deque1.header.next, sent0);
    this.deque1.addAtHead("hello");
    t.checkExpect(deque1.header.next, new Node<String>("hello",sent0,sent0));

    t.checkExpect(deque2.header.next, node2n1);
    this.deque2.addAtHead("new");
    t.checkExpect(deque2.header.next, new Node<String>("new",node2n1,sent2));

    t.checkExpect(deque3.header.next, node3n1);
    this.deque3.addAtHead(5);
    t.checkExpect(deque3.header.next, new Node<Integer>(5,node3n1,sent3));
  }

  //tests the method addAtTail
  void testAddAtTail(Tester t) {
    iniData();
    t.checkExpect(deque1.header.prev, sent0);
    this.deque1.addAtTail("bye");
    t.checkExpect(deque1.header.prev, new Node<String>("bye",sent0,sent0));

    t.checkExpect(deque2.header.prev, node2n4);
    this.deque2.addAtTail("new");
    t.checkExpect(deque2.header.prev, new Node<String>("new",sent2,node2n4));

    t.checkExpect(deque3.header.prev, node3n4);
    this.deque3.addAtTail(5);
    t.checkExpect(deque3.header.prev, new Node<Integer>(5,sent3,node3n4));
  }

  //tests the helper function removeFromHelper
  boolean testRemoveFromHelper(Tester t) {
    iniData();
    return t.checkException(new RuntimeException("Can't remove on an empty list"), 
        new Sentinel<String>(), "removeFromHelper")
        && t.checkException(new RuntimeException("Can't remove on an empty list"), 
            sent2, "removeFromHelper")
        && t.checkException(new RuntimeException("Can't remove on an empty list"), 
            sent3, "removeFromHelper")
        && t.checkExpect(node2n2.removeFromHelper(), "bcd")
        && t.checkExpect(node3n4.removeFromHelper(), 12);
  }

  //tests the method removeFromHead
  void testRemoveFromHead(Tester t) {
    iniData();
    t.checkExpect(deque1.header.next, sent0);
    t.checkException(new RuntimeException("Can't remove on an empty list"), 
        sent0, "removeFromHead");

    t.checkExpect(deque2.header.next, node2n1);
    this.deque2.removeFromHead();
    t.checkExpect(deque2.header.next, node2n2);

    t.checkExpect(deque3.header.next, node3n1);
    this.deque3.removeFromHead();
    t.checkExpect(deque3.header.next, node3n2);
  }

  //tests the method removeFromTail
  void testRemoveFromTail(Tester t) {
    iniData();
    t.checkExpect(deque1.header.prev, sent0);
    t.checkException(new RuntimeException("Can't remove on an empty list"), 
        sent0, "removeFromTail");

    t.checkExpect(deque2.header.prev, node2n4);
    this.deque2.removeFromTail();
    t.checkExpect(deque2.header.prev, node2n3);

    t.checkExpect(deque3.header.prev, node3n4);
    this.deque3.removeFromTail();
    t.checkExpect(deque3.header.prev, node3n3);
  }

  //tests the method find
  boolean testFind(Tester t) {
    iniData();
    return t.checkExpect(deque1.find(new NodeSame<String>("bcd")), sent0)
        && t.checkExpect(deque2.find(new NodeSame<String>("bcd")), node2n2)
            && t.checkExpect(deque2.find(new NodeSame<String>("juy")), sent2)
                && t.checkExpect(deque3.find(new NodeSame<Integer>(4)), sent3)
                    && t.checkExpect(deque3.find(new NodeSame<Integer>(44)), node3n1);
  }

  //tests the helper function findHelper
  boolean testFindHelper(Tester t) {
    iniData();
    return t.checkExpect(sent0.findHelper(new NodeSame<String>("bcd")), sent0)
        && t.checkExpect(sent2.findHelper(new NodeSame<String>("bcd")), sent2)
        && t.checkExpect(sent3.findHelper(new NodeSame<Integer>(69)), sent3)
        && t.checkExpect(node2n2.findHelper(new NodeSame<String>("bcd")), node2n2)
        && t.checkExpect(node2n2.findHelper(new NodeSame<String>("thy")), sent2)
        && t.checkExpect(node3n1.findHelper(new NodeSame<Integer>(44)), node3n1)
        && t.checkExpect(node3n4.findHelper(new NodeSame<Integer>(59)), sent3);
  }

  //tests the method removeNode
  void testRemoveNode(Tester t) {
    iniData();
    t.checkExpect(deque1.header.next, sent0);
    deque1.removeNode(node2n1);
    t.checkExpect(deque1.header.next, sent0);
    iniData();
    t.checkExpect(node2n1.next, node2n2);
    deque2.removeNode(node2n2);
    t.checkExpect(node2n1.next, node2n3);
    iniData();
    t.checkExpect(deque3.header.next.next, node3n2);
    deque3.removeNode(node3n2);
    t.checkExpect(deque3.header.next.next, node3n3);
  }

  //tests the helper function removeNodeHelper
  void testRemoveNodeHelper(Tester t) {
    iniData();
    t.checkExpect(sent0, sent0);
    sent0.removeNodeHelper();
    t.checkExpect(sent0, sent0);
    
    t.checkExpect(sent2, sent2);
    sent2.removeNodeHelper();
    t.checkExpect(sent2, sent2);
    
    t.checkExpect(sent3, sent3);
    sent3.removeNodeHelper();
    t.checkExpect(sent3, sent3);
    
    t.checkExpect(sent2.next.next, node2n2);
    node2n2.removeNodeHelper();
    t.checkExpect(sent2.next.next, node2n3);
    
    t.checkExpect(sent3.prev, node3n4);
    node3n4.removeNodeHelper();
    t.checkExpect(sent3.prev, node3n3);
  }
}