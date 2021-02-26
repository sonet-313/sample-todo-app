package com.sample.todo.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import javax.validation.constraints.Size;
import javax.validation.*;

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
    @Size(min = 1, max = 30)
    private String title;
    @Size(min = 0, max = 100)
    private String detail;
    private ArrayList<String> deleteIds;
    private String searchArea;
    private String searchKeyword;
    @Valid
    private Date deadline;
    private String sortArea;

    public TodoApp() {
    }

    public String getSortArea() {
        return sortArea;
    }

    public void setSortArea(String sortArea) {
        this.sortArea = sortArea;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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

    public void setDeleteIds(ArrayList<String> deleteIds) {
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
