package com.dinfo.bdms.response;

/**
 * Created by Carlos on 2017/12/1.
 */
public class RequestResponse {

    private String msg;
    private Object data;
    private boolean ret;

    public RequestResponse(String msg, Object data, boolean ret) {
        this.msg = msg;
        this.data = data;
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public boolean isRet() {
        return ret;
    }
    public void setRet(boolean ret) {
        this.ret = ret;
    }

    @Override
    public String toString() {
        return "{\"msg\":\""+msg+"\",\"data\":"+data+",\"ret\":"+ret+"}";
    }
}
