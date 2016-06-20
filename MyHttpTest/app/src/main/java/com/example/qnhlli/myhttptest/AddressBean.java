package com.example.qnhlli.myhttptest;

/**
 * Created by qnhlli on 2016/6/16.
 */
public class AddressBean {
    private int idMemberInfo;
    private int pageNo;
    private int pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {

        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getIdMemberInfo() {

        return idMemberInfo;
    }

    public void setIdMemberInfo(int idMemberInfo) {
        this.idMemberInfo = idMemberInfo;
    }
}
