package com.intesim.entity;

import java.io.Serializable;

public class DataMsg  implements Serializable {

    private static final long serialVersionUID = 1L;

    public DataMsg(){

    }

    public DataMsg(Object data){
        this();
        this.data = data;
    }

    public DataMsg(Object data, Page page){
        this();
        this.data = data;
        this.page = page;
    }

    /**
     * 返回数据
     */
    private Object data;

    private Page page;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Page getPage() {
        if (page == null) {
            page = new Page();
            page.setPages(1);
            page.setSize(20);
        }
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
