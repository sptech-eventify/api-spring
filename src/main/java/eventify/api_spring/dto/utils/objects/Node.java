package eventify.api_spring.dto.utils.objects;

public class Node {
    private int info;
    private Node next;

    public Node(int info) {
        this.info = info;
        this.next = null;
    }

    public int getInfo() {
        return this.info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public Node getNext() {
        return this.next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
