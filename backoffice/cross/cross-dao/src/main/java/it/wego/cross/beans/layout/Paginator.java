/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.layout;

/**
 *
 * @author CS
 */
public class Paginator {
    private int count;
    private int page;
    private int limit;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    public String getQuery()
    {
        
        return " LIMIT 0 , 2";
        //return "  LIMIT "+(this.limit * this.page )+" , "+this.limit;
    }
    
}
