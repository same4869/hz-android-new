package com.renren.wawa.model;


/**
 * 获取 app 版本信息
 */

public class VersionBean extends BaseObject {


    /**
     * data : {"minVersion":"400601","version":"400603","checksum":"fsljfldsf","url":"https://fir.im/dailywawaji"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * minVersion : 400601
         * version : 400603
         * checksum : fsljfldsf
         * url : https://fir.im/dailywawaji
         */

        private String minVersion;
        private String version;
        private String checksum;
        private String url;

        public String getMinVersion() {
            return minVersion;
        }

        public void setMinVersion(String minVersion) {
            this.minVersion = minVersion;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getChecksum() {
            return checksum;
        }

        public void setChecksum(String checksum) {
            this.checksum = checksum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
