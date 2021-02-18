package com.sample.todo.dao;

import java.util.*;
import java.sql.Date;
import com.sample.todo.entity.TodoApp;
import com.sample.todo.entity.TodoAppRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * データアクセスオブジェクト（DataAccessObject=Dao）<br>
 * データアクセス関連を記述するクラス
 */
@Component
public class TodoAppDao {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<TodoApp> getTodoAppList() {
        List<TodoApp> resultList = jdbcTemplate.query("SELECT * FROM TODO_APP", new MapSqlParameterSource(null),
                new TodoAppRowMapper());
        return resultList;
    }

    public int getNextId() {
        int maxTodoId = jdbcTemplate.queryForObject("SELECT MAX(TODO_ID) FROM TODO_APP;",
                new MapSqlParameterSource(null), Integer.class);
        return ++maxTodoId;
    }

    public <T> void insert(int todoId, String title, String detail, Date date ) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("todoId", todoId);
        paramMap.addValue("title", title);
        paramMap.addValue("detail", detail);
        paramMap.addValue("date", date);
        jdbcTemplate.update("INSERT INTO TODO_APP VALUES(:todoId, :title, :detail, :date)", paramMap);
    }

    //ラジオボタンでチェックしたidのタスクをSQL文でデータベースから削除
    public void delete(int deleteId) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("deleteId", deleteId);
        jdbcTemplate.update("DELETE FROM TODO_APP WHERE TODO_ID= :deleteId", paramMap);
    }
    // データベースの行数取得
    public int getRowCount() {
        int rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TODO_APP;",
                new MapSqlParameterSource(null), Integer.class);
        return rowCount;
    }

    public void update(int todoId, String title, String detail, Date date) {
            MapSqlParameterSource paramMap = new MapSqlParameterSource();
            paramMap.addValue("todoId", todoId);
            paramMap.addValue("title", title);
            paramMap.addValue("detail", detail);
            paramMap.addValue("date", date);
            jdbcTemplate.update("UPDATE TODO_APP SET TITLE = :title, DETAIL = :detail, DEADLINE = :date WHERE TODO_ID = :todoId", paramMap);
    }

	public List<TodoApp> searchAll(String searchKeyword) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("searchKeyword", "%"+searchKeyword.toUpperCase()+"%");//大文字小文字区別せずに検索
        List<TodoApp> searchResult = jdbcTemplate.query("SELECT * FROM TODO_APP WHERE UPPER(TODO_ID) LIKE :searchKeyword OR UPPER(TITLE) LIKE :searchKeyword OR UPPER(DETAIL) LIKE :searchKeyword", paramMap,
                new TodoAppRowMapper());
		return searchResult;
	}

	public List<TodoApp> searchId(String searchKeyword) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("searchKeyword", "%"+searchKeyword.toUpperCase()+"%");//大文字小文字区別せずに検索
        List<TodoApp> searchResult = jdbcTemplate.query("SELECT * FROM TODO_APP WHERE UPPER(TODO_ID) LIKE :searchKeyword", paramMap,
                new TodoAppRowMapper());
		return searchResult;
	}

	public List<TodoApp> searchTitle(String searchKeyword) {
	MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("searchKeyword", "%"+searchKeyword.toUpperCase()+"%");//大文字小文字区別せずに検索
        List<TodoApp> searchResult = jdbcTemplate.query("SELECT * FROM TODO_APP WHERE UPPER(TITLE) LIKE :searchKeyword", paramMap,
                new TodoAppRowMapper());
		return searchResult;
	}

	public List<TodoApp> searchDetail(String searchKeyword) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("searchKeyword", "%"+searchKeyword.toUpperCase()+"%");//大文字小文字区別せずに検索
        List<TodoApp> searchResult = jdbcTemplate.query("SELECT * FROM TODO_APP WHERE UPPER(DETAIL) LIKE :searchKeyword", paramMap,
                new TodoAppRowMapper());
		return searchResult;
        }
        
        //並べ替え
        public List<TodoApp> sort(String sortArea) {
                MapSqlParameterSource paramMap = new MapSqlParameterSource();
                if(sortArea.equals("TITLE")){
                        paramMap.addValue("Title", "TITLE");
                        List<TodoApp> sortResult = jdbcTemplate.query("SELECT * FROM TODO_APP ORDER BY TITLE", paramMap,
                        new TodoAppRowMapper());
                        return sortResult;
                }else if(sortArea.equals("DETAIL")){
                        List<TodoApp> sortResult = jdbcTemplate.query("SELECT * FROM TODO_APP ORDER BY DETAIL", paramMap,
                        new TodoAppRowMapper());
                        return sortResult;
                }else{
                        List<TodoApp> sortResult = jdbcTemplate.query("SELECT * FROM TODO_APP ORDER BY TODO_ID", paramMap,
                        new TodoAppRowMapper());
                        return sortResult;

                }
        }


}
