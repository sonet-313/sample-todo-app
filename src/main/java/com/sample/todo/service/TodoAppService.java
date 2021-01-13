package com.sample.todo.service;

import java.util.List;

import com.sample.todo.dao.TodoAppDao;
import com.sample.todo.entity.TodoApp;

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

    public void register(String title, String detail) {
        int rowCount = dao.getRowCount();
        // todoを全て削除するとNextIdが取得できなくなるエラーを回避するために1を渡す
        int nextId;
        if (rowCount == 0){
            nextId = 1;
        } else{
            nextId = dao.getNextId();
        }
        dao.insert(nextId, title, detail);
    }

    public void delete(int selectId) {
        dao.delete(selectId);
    }

    public int getRowCount(){
        return dao.getRowCount();
    }
    //editの時に選択されたidから行番号を返す関数
    public int findRowFromId(int selectId, List<TodoApp> todoList){
        int selectRow = -1;
        for(int i = 0; i < todoList.size(); i++){
            if(todoList.get(i).getTodoId() == selectId){
                selectRow = i;
                break;
            }
        }
        return selectRow;
    }
    //行番号からTitleを取得
    public String currentTitle(int selectRow, List<TodoApp> todoList){
        return todoList.get(selectRow).getTitle();
    }
    //行番号からdetailを取得
    public String currentDetail(int selectRow, List<TodoApp> todoList){
        return todoList.get(selectRow).getDetail();
    }

    //更新
    public void update(int todoId, String title, String detail) {
        dao.update(todoId, title, detail);
    }
}
