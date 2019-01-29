package io.aifo.example;

import java.util.List;

public class SingleEntity extends BaseEntity {

    public List<DataBean> data;
    public static class DataBean {
        public int alertType;
        public String title;
        public String contentHint;
        public String downHint;
        public List<String> infoList;
    }
}
