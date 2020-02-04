package it.sirac.admin.faces;

public class Paging
{
  private int rowsCount = 0;
  private int pageCount = 0;
  private int rowsPerPage = 10;
  private int firstRowIndex = 0;
  private int pageIndex = 1;
  
  public int getPageIndex()
  {
    return this.firstRowIndex / this.rowsPerPage + 1;
  }
  
  public void setPageIndex(int pageIndex)
  {
    if (pageIndex <= 0) {
      return;
    }
    if (pageIndex > this.pageCount) {
      return;
    }
    this.pageIndex = (pageIndex - 1);
    this.firstRowIndex = (this.rowsPerPage * this.pageIndex);
    if (this.firstRowIndex >= this.rowsCount)
    {
      this.firstRowIndex = (this.rowsCount - this.rowsPerPage);
      if (this.firstRowIndex < 0) {
        this.firstRowIndex = 0;
      }
    }
  }
  
  public void setPageCount(int pageCount)
  {
    if (pageCount <= 0) {
      return;
    }
    this.pageCount = pageCount;
  }
  
  public int getPageCount()
  {
    this.pageCount = new Double(Math.floor(this.rowsCount / this.rowsPerPage)).intValue();
    if (this.rowsCount % this.rowsPerPage > 0) {
      this.pageCount += 1;
    }
    return this.pageCount;
  }
  
  public int getrowsPerPage()
  {
    return this.rowsPerPage;
  }
  
  public void setrowsPerPage(int rowsPerPage)
  {
    if (rowsPerPage <= 0) {
      return;
    }
    this.rowsPerPage = rowsPerPage;
  }
  
  public int getFirstRowIndex()
  {
    return this.firstRowIndex;
  }
  
  public String scrollFirst()
  {
    this.firstRowIndex = 0;
    return "success";
  }
  
  public String scrollPrevious()
  {
    this.firstRowIndex -= this.rowsPerPage;
    if (this.firstRowIndex < 0) {
      this.firstRowIndex = 0;
    }
    return "success";
  }
  
  public String scrollNext()
  {
    this.firstRowIndex += this.rowsPerPage;
    if (this.firstRowIndex >= this.rowsCount)
    {
      this.firstRowIndex = (this.rowsCount - this.rowsPerPage);
      if (this.firstRowIndex < 0) {
        this.firstRowIndex = 0;
      }
    }
    return "success";
  }
  
  public String scrollLast()
  {
    this.firstRowIndex = (this.rowsCount - this.rowsPerPage);
    if (this.firstRowIndex < 0) {
      this.firstRowIndex = 0;
    }
    return "success";
  }
  
  public boolean isScrollFirstDisabled()
  {
    return this.firstRowIndex == 0;
  }
  
  public boolean isScrollLastDisabled()
  {
    return this.firstRowIndex >= this.rowsCount - this.rowsPerPage;
  }
  
  public int getRowsCount()
  {
    return this.rowsCount;
  }
  
  public void setRowsCount(int rowsCount)
  {
    this.rowsCount = rowsCount;
  }
}
