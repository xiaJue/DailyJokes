package com.xiajue.dayxiaohua.bean;

import java.util.ArrayList;

/**
 * xiaJue 2018/4/8创建
 */
public class ImageBeans {
    public class Datum {
        private String content;

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        private String hashId;

        public String getHashId() {
            return this.hashId;
        }

        public void setHashId(String hashId) {
            this.hashId = hashId;
        }

        private int unixtime;

        public int getUnixtime() {
            return this.unixtime;
        }

        public void setUnixtime(int unixtime) {
            this.unixtime = unixtime;
        }

        private String updatetime;

        public String getUpdatetime() {
            return this.updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        private String url;

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class Result {
        private ArrayList<Datum> data;

        public ArrayList<Datum> getData() {
            return this.data;
        }

        public void setData(ArrayList<Datum> data) {
            this.data = data;
        }
    }

    public class RootObject {
        private String reason;

        public String getReason() {
            return this.reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        private Result result;

        public Result getResult() {
            return this.result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        private int error_code;

        public int getErrorCode() {
            return this.error_code;
        }

        public void setErrorCode(int error_code) {
            this.error_code = error_code;
        }
    }

}
