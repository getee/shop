package com.myshop.model;

public class PageBean {
    private int nowPage;
    private int previousPage;
    private int nextPage;
    private int lastPage;//最后一页
    private int allCount;
    public int getNowPage() {
        return nowPage;
    }
    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }
    public int getPreviousPage() {
        return previousPage;
    }
    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }
    public int getNextPage() {
        return nextPage;
    }
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
    
    public int getLastPage() {
        return lastPage;
    }
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
    public int getAllCount() {
        return allCount;
    }
    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

}
