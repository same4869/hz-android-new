package com.renren.wawa.model;


public class AppUploadBean extends BaseObject {


    /**
     * data : {"token":"WpDE2kaUkmA87QnKEeEQ-ZFuH3Jjx4Rrp7qOC2d1:kOlfPsNk4RlnaqBhL_tSaCvcm9k=:eyJjYWxsYmFja1VybCI6Imh0dHA6XC9cL3d3ai5kYXlkYXlnby50b3BcL2FwaVwvdjFcL3Fpbml1XC9jYWxsYmFja1ZpZGVvIiwiY2FsbGJhY2tCb2R5IjoiZmlsZU5hbWU9cm9vbSUyRnAxMzByNDFzNDgwNDAxLm1wNCZnYW1lX3Nlc3Npb25faWQ9NDgwNDAxIiwic2NvcGUiOiJ3YXdhamk6cm9vbVwvcDEzMHI0MXM0ODA0MDEubXA0IiwiZGVhZGxpbmUiOjE1MDU4NzkwMjEsInVwSG9zdHMiOlsiaHR0cDpcL1wvdXAucWluaXUuY29tIiwiaHR0cDpcL1wvdXBsb2FkLnFpbml1LmNvbSIsIi1IIHVwLnFpbml1LmNvbSBodHRwOlwvXC8xODMuMTMxLjcuMTgiXX0=","expired_at":1505879021,"base_url":"http://wwj.same.com","file_name":"room/p130r41s480401.mp4"}
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
         * token : WpDE2kaUkmA87QnKEeEQ-ZFuH3Jjx4Rrp7qOC2d1:kOlfPsNk4RlnaqBhL_tSaCvcm9k=:eyJjYWxsYmFja1VybCI6Imh0dHA6XC9cL3d3ai5kYXlkYXlnby50b3BcL2FwaVwvdjFcL3Fpbml1XC9jYWxsYmFja1ZpZGVvIiwiY2FsbGJhY2tCb2R5IjoiZmlsZU5hbWU9cm9vbSUyRnAxMzByNDFzNDgwNDAxLm1wNCZnYW1lX3Nlc3Npb25faWQ9NDgwNDAxIiwic2NvcGUiOiJ3YXdhamk6cm9vbVwvcDEzMHI0MXM0ODA0MDEubXA0IiwiZGVhZGxpbmUiOjE1MDU4NzkwMjEsInVwSG9zdHMiOlsiaHR0cDpcL1wvdXAucWluaXUuY29tIiwiaHR0cDpcL1wvdXBsb2FkLnFpbml1LmNvbSIsIi1IIHVwLnFpbml1LmNvbSBodHRwOlwvXC8xODMuMTMxLjcuMTgiXX0=
         * expired_at : 1505879021
         * base_url : http://wwj.same.com
         * file_name : room/p130r41s480401.mp4
         */

        private String token;
        private int expired_at;
        private String base_url;
        private String file_name;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(int expired_at) {
            this.expired_at = expired_at;
        }

        public String getBase_url() {
            return base_url;
        }

        public void setBase_url(String base_url) {
            this.base_url = base_url;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }
    }
}
