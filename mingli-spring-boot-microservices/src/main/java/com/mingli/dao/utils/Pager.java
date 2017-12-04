package com.mingli.dao.utils;

import java.util.List;  
  
public class Pager<T> {  
    /** 
     */  
    private boolean hasPrePage;  
    /** 
     */  
    private boolean hasNextPage;  
    /** 
     */  
    private int everyPage;  
    /** 
     */  
    private int totalPage;  
    /** 
     */  
    private int currentPage;   
    /** 
     */  
    private int beginIndex;   
    /** 
     */  
    private int totalCount;   
    /** 
     */  
    private String pageName;   
    /** 
     */  
    private String conString;  
    public String getConString() {  
        return conString;  
    }  
    /** 
     */  
    public void setConString(String conditionString) {  
        this.conString = conditionString;  
    }  
    /** 
     */  
    public String getPageName() {  
        return pageName;  
    }  
  
    public void setPageName(String tableName) {  
        this.pageName = tableName;  
    }  
  
    public int getTotalCount() {  
        return totalCount;  
    }  
    /** 
     */  
    public void setTotalCount(int totalCount) {  
        this.totalCount = totalCount;  
    }  
    // construct the page by everyPage   
    public Pager(int everyPage){  
        this.everyPage = everyPage;  
    }  
    //The whole constructor   
    public Pager(boolean hasPrePage, boolean hasNextPage,   
                    int everyPage, int totalPage,  
                    int currentPage, int beginIndex,int totalCount,  
                    String pageName,String conString) {  
        this.hasPrePage = hasPrePage;  
        this.hasNextPage = hasNextPage;  
        this.everyPage = everyPage;  
        this.totalPage = totalPage;  
        this.currentPage = currentPage;  
        this.beginIndex = beginIndex;  
        this.totalCount = totalCount;  
        this.pageName = pageName;  
        this.conString = conString;  
    }  
    public Pager() {
		// TODO Auto-generated constructor stub
	}
	public int getBeginIndex() {  
        return beginIndex;  
    }  
    /** 
     */  
    public void setBeginIndex(int beginIndex) {  
        this.beginIndex = beginIndex;  
    }  
    public int getCurrentPage() {  
        return currentPage;  
    }  
    /** 
     */  
    public void setCurrentPage(int currentPage) {  
        this.currentPage = currentPage;  
    }  
    public int getEveryPage() {  
        return everyPage;  
    }  
    /** 
     */  
    public void setEveryPage(int everyPage) {  
        this.everyPage = everyPage;  
    }  
    /** 
     */  
    public boolean getHasNextPage() {  
        return hasNextPage;  
    }  
    public void setHasNextPage(boolean hasNextPage) {  
        this.hasNextPage = hasNextPage;  
    }  
    /** 
     */  
    public boolean getHasPrePage() {  
        return hasPrePage;  
    }  
    public void setHasPrePage(boolean hasPrePage) {  
        this.hasPrePage = hasPrePage;  
    }  
    public int getTotalPage() {  
        return totalPage;  
    }  
    /** 
     */  
    public void setTotalPage(int totalPage) {  
        this.totalPage = totalPage;  
    }
    
    
    /////////////////////////////////////////////////////////////
    
	public Object getItemsPerPage() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setList(List<T> list) {
		// TODO Auto-generated method stub
		
	}
	public void setItems(int totalCount2) {
		// TODO Auto-generated method stub
		
	}
	public void setCurPage(Object curPage) {
		// TODO Auto-generated method stub
		
	}
	public void setItemsPerPage(Object itemsPerPage) {
		// TODO Auto-generated method stub
		
	}  
  
}  
