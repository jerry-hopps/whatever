package net.nemo.whatever.entity;

/**
 * Created by tonyshi on 2016/12/13.
 */
public class Tag {
    private Integer id;
    private Message message;
    private String name;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
