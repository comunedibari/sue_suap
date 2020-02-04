package it.wego.cross.beans;

import it.wego.cross.entity.Utente;

public class SearchBean {

    private Integer offset;
    private Integer limit;
    private String orderColumn;
    private String orderDirection;
    private Utente connectedUser;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Utente getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(Utente connectedUser) {
        this.connectedUser = connectedUser;
    }
}
