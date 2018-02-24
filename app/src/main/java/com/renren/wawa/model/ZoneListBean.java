package com.renren.wawa.model;

import java.util.List;

public class ZoneListBean extends BaseObject {

    /**
     * data : {"page":1,"size":100,"data":[{"zone_id":1,"zone_name":"大区1"}]}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * page : 1
         * size : 100
         * data : [{"zone_id":1,"zone_name":"大区1"}]
         */

        private int page;
        private int size;
        private List<DataBean> data;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * zone_id : 1
             * zone_name : 大区1
             */

            private int zone_id;
            private String zone_name;

            public int getZone_id() {
                return zone_id;
            }

            public void setZone_id(int zone_id) {
                this.zone_id = zone_id;
            }

            public String getZone_name() {
                return zone_name;
            }

            public void setZone_name(String zone_name) {
                this.zone_name = zone_name;
            }
        }
    }
}
