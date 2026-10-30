package it.wego.cross.beans.layout;

/**
 * per il salvataggio dei dati per il paginator di jqgrid
 *
 */
public class JqgridPaginator {

    private String rows;
    private String page;
    private String sidx;
    private String sord;
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getPage() {
        if (page == null) {
            this.page = "1";
        }
        return page;
    }

    public void setPage(String page) {
        if (page == null) {
            page = "1";
        }
        this.page = page;
    }

    public String getRows() {
        if (rows == null) {
            this.rows = "10";
        }
        return rows;
    }

    public void setRows(String rows) {
        if (rows == null) {
            rows = "10";
        }
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }
}
