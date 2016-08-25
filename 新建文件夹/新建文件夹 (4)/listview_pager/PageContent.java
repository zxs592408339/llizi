package com.ittx.android1601.ui.listview;

import java.util.List;

public class PageContent {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MsgBean> getContent() {
        return content;
    }

    public void setContent(List<MsgBean> content) {
        this.content = content;
    }

    private int pagecount;
    private String message;
    private List<MsgBean> content;
}
