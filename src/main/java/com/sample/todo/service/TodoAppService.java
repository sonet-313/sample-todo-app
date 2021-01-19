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

    public void delete(int[] deleteId) {
        for (int todoId : deleteId){
            dao.delete(todoId);
        }
    }
    //更新
    public void update(int todoId, String title, String detail) {
        dao.update(todoId, title, detail);
    }
}
