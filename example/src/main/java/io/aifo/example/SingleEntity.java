package io.aifo.example;

import java.util.List;

public class SingleEntity extends BaseEntity {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * alertType : 3
         * title : 尚未通过资料审核
         * contentHint : 未完成三证审核，审核通过后，可正常接货承运：
         * infoList : ["身份证照片&nbsp;&nbsp;&nbsp;&nbsp;<font color='#02c7c8'>审核中<\/font>","驾驶证照片&nbsp;&nbsp;&nbsp;&nbsp;<font color='#02c7c8'>审核中<\/font>","行驶证照片&nbsp;&nbsp;&nbsp;&nbsp;<font color='#02c7c8'>审核中<\/font>"]
         * downHint : 13312345678
         */

        public int alertType;
        public String title;
        public String contentHint;
        public String downHint;
        public List<String> infoList;
    }
}
