package entity;

import com.google.gson.Gson;

public class ResponseJson {
    private int status;
    private String message;
    private Object obj;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        private int status;
        private String message;
        private Object obj;

        private Builder() {
        }

        public static Builder aResponseJson() {
            return new Builder();
        }

        public Builder setStatus(int status) {
            this.status = status;
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
            responseJson.setStatus(status);
            responseJson.setMessage(message);
            responseJson.setObj(obj);
            return responseJson;
        }
    }
}
