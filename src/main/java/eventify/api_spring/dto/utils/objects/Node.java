package eventify.api_spring.dto.utils.objects;

import eventify.api_spring.dto.buffet.BuffetResumoDto;

public class Node {
    private BuffetResumoDto info;
    private Node next;

    public Node(BuffetResumoDto info) {
        this.info = info;
        this.next = null;
    }

    public BuffetResumoDto getInfo() {
        return this.info;
    }

    public void setInfo(BuffetResumoDto info) {
        this.info = info;
    }

    public Node getNext() {
        return this.next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
