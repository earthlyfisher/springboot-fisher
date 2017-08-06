package com.wyp.boot.earthlyfisher.common;

import java.io.Serializable;

public class ResponseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -356415843328207840L;

    private String flag;

    private String errCode;

    private Object resDate;

    public ResponseEntity() {
        super();
    }

    public ResponseEntity(String flag, Object resDate) {
        super();
        this.flag = flag;
        this.resDate = resDate;
    }

    public ResponseEntity(String flag, String errCode, Object resDate) {
        super();
        this.flag = flag;
        this.errCode = errCode;
        this.resDate = resDate;
    }

    /**
     * success response
     *
     * @param resDate
     * @return
     */
    public static ResponseEntity getSuccessResponse(Object resDate) {
        return new ResponseEntity("true", resDate);
    }

    /**
     * error response
     *
     * @param resDate
     * @return
     */
    public static ResponseEntity getFailResponse(String errCode, Object resDate) {
        return new ResponseEntity("false", errCode, resDate);
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Object getResDate() {
        return resDate;
    }

    public void setResDate(Object resDate) {
        this.resDate = resDate;
    }

}
