package com.example.bean;

import java.util.List;

public class NumberBean {


    /**
     * error_code : 0
     * error_msg : Successful.
     * data : [{"num":77,"color":"FF7F00","shape":4,"x":200,"y":300,"radius":50},{"num":17,"color":"FF0000","shape":8,"x":150,"y":450,"radius":50},{"num":11,"color":"2E2B5F","shape":5,"x":150,"y":700,"radius":50},{"num":79,"color":"00FF00","shape":3,"x":400,"y":850,"radius":50},{"num":48,"color":"FF7F00","shape":6,"x":350,"y":450,"radius":50},{"num":16,"color":"FFFF00","shape":6,"x":50,"y":550,"radius":50}]
     */

    private int error_code;
    private String error_msg;
    private List<DataBean> data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        public DataBean(int num, String color, int shape, int x, int y, int radius) {
            this.num = num;
            this.color = color;
            this.shape = shape;
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        /**
         * num : 77
         * color : FF7F00
         * shape : 4
         * x : 200
         * y : 300
         * radius : 50
         */

        private int num;
        private String color;
        private int shape;
        private int x;
        private int y;
        private int radius;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getShape() {
            return shape;
        }

        public void setShape(int shape) {
            this.shape = shape;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "num=" + num +
                    ", color='" + color + '\'' +
                    ", shape=" + shape +
                    ", x=" + x +
                    ", y=" + y +
                    ", radius=" + radius +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NumberBean{" +
                "error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
    }
}
