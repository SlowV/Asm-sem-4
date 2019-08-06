package entity;

import com.google.gson.Gson;

public class ResponseJson {
    private int stauts;
    private String message;
    private Object obj;

    public int getStauts() {
        return stauts;
    }

    public void setStauts(int stauts) {
        this.stauts = stauts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String parserToJson(){
        return new Gson().toJson(this);
    }

    public static final class Builder {
        private int stauts;
        private String message;
        private Object obj;

        private Builder() {
        }

        public static Builder aResponseJson() {
            return new Builder();
        }

        public Builder setStauts(int stauts) {
            this.stauts = stauts;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setObj(Object obj) {
            this.obj = obj;
            return this;
        }

        public ResponseJson build() {
            ResponseJson responseJson = new ResponseJson();
            responseJson.setStauts(stauts);
            responseJson.setMessage(message);
            responseJson.setObj(obj);
            return responseJson;
        }
    }
}
