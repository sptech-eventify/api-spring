package eventify.api_spring.dto.utils.objects;

import eventify.api_spring.dto.buffet.BuffetConsultaDto;

public class Node {
    private BuffetConsultaDto info;
    private Node next;

    public Node(BuffetConsultaDto info) {
        this.info = info;
        this.next = null;
    }

    public BuffetConsultaDto getInfo() {
        return this.info;
    }

    public void setInfo(BuffetConsultaDto info) {
        this.info = info;
    }

    public Node getNext() {
        return this.next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
