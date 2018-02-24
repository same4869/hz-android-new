package com.renren.wawa.model;

import java.util.List;

public class LiveUserResult extends BaseObject {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
