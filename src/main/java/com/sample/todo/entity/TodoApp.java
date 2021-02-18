package com.sample.todo.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 * TODO_APPテーブルに該当するエンティティクラス<br>
 * JavaBeansのルールに従っています。
 */
public class TodoApp implements Serializable {
    /**
     * おまじない
     */
    private static final long serialVersionUID = 1L;

    private int todoId;
    private String title;
    private String detail;
    private ArrayList<String> deleteIds;
    private String searchArea;
    private String searchKeyword;
    private Date date;
    private String sortArea;

    public TodoApp() {
    }

    public String getSortArea() {
        return sortArea;
    }

    public void setSortArea(String sortArea) {
        this.sortArea = sortArea;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(String searchArea) {
        this.searchArea = searchArea;
    }

    public ArrayList<String> getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteId(ArrayList<String> deleteIds) {
        this.deleteIds = deleteIds;
    }
    public int getTodoId() {
        return this.todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
