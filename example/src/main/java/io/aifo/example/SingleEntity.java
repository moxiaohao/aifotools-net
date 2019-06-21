package io.aifo.example;

import java.util.List;

public class SingleEntity extends BaseEntity {

    public List<DataBean> data;

    public static class DataBean {
        public String title;
        public String content;
        public String hint;
        public List<String> infoList;

        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", hint='" + hint + '\'' +
                    ", infoList=" + infoList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SingleEntity{" +
                "data=" + data +
                ", code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
