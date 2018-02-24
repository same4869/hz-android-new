package com.renren.wawa.model;

import java.io.Serializable;
import java.util.List;

public class Servers extends BaseObject {
    private List<ServersBean> servers;

    public List<ServersBean> getServers() {
        return servers;
    }

    public void setServers(List<ServersBean> servers) {
        this.servers = servers;
    }

    public static class ServersBean implements Serializable{
        /**
         * name : A001
         * address : httsdfdsfds
         */

        private String name;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "ServersBean{" +
                    "name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
