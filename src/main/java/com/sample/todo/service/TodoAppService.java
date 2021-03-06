package com.sample.todo.service;

import java.util.*;
import java.sql.Date;
import com.sample.todo.dao.TodoAppDao;
import com.sample.todo.entity.TodoApp;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ロジックを記述するクラス<br>
 *
 * @Componentと書いておくと、他からはは@Autowiredと記述すれば利用できる。Spring Beanという概念。
 */
@Component
public class TodoAppService {

    /**
     * TodoAppDaoは@Componentを持っているので、@Autowiredで利用できる（裏でSpringがこっそりセットしています）
     */
    @Autowired
    private TodoAppDao dao;

    public List<TodoApp> getTodoAppList() {
        return dao.getTodoAppList();
    }

    public void register(String title, String detail, Date deadline) {
        int rowCount = dao.getRowCount();
        // todoを全て削除するとNextIdが取得できなくなるエラーを回避するために1を渡す
        int nextId;
        if (rowCount == 0){
            nextId = 1;
        } else{
            nextId = dao.getNextId();
        }
        dao.insert(nextId, title, detail, deadline);
    }

    public void delete(ArrayList<Integer> deleteIds) {
        for (int deleteId : deleteIds){
            dao.delete(deleteId);
        }
    }
    //今日の日付を取得
    public String getTodaysDate(){
        java.util.Date today = new java.util.Date();
        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
        return todaysDate;
    }
    //更新
    public void update(int todoId, String title, String detail, Date deadline) {
        dao.update(todoId, title, detail,deadline);
    }

    //検索
    public List<TodoApp> search(String searchArea, String searchKeyword){
        if(searchArea.equals("All")){
            return dao.searchAll(searchKeyword);
        }else if(searchArea.equals("#")){
            return dao.searchId(searchKeyword);
        }else if(searchArea.equals("Title")){
            return dao.searchTitle(searchKeyword);
        }else if(searchArea.equals("Detail")){
            return dao.searchDetail(searchKeyword);
        }else if(searchArea.equals("Deadline")){
            return dao.searchDeadline(searchKeyword);
        }else{
            return null;
        }
        
    }

    //並べ替え
    public List<TodoApp> sort(String sortArea){
        return dao.sort(sortArea);
    }
}
