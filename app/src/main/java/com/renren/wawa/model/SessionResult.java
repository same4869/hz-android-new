package com.renren.wawa.model;

public class SessionResult extends BaseObject {

    private Room room;

    private Session session;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

