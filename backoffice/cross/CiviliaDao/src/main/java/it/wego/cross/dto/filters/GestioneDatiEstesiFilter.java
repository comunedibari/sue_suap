package it.wego.cross.dto.filters;

public class GestioneDatiEstesiFilter {

    private Integer limit;
    private Integer offset;
    private Integer totale;
    private String orderDirection;
    private String orderColumn;
    private Integer page;
    private String codTipoOggetto;
    private String idIstanza;
    private String codValue;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotale() {
        return totale;
    }

    public void setTotale(Integer totale) {
        this.totale = totale;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getCodTipoOggetto() {
        return codTipoOggetto;
    }

    public void setCodTipoOggetto(String codTipoOggetto) {
        this.codTipoOggetto = codTipoOggetto;
    }

    public String getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(String idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

}
