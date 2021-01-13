package com.sample.todo.controller;

import java.util.List;

import com.sample.todo.entity.TodoApp;
import com.sample.todo.service.TodoAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ブラウザからのリクエストはここにくる
 */
@Controller
public class TodoAppController {

    @Autowired
    private TodoAppService service;

    /**
     * valueの部分がURL<br>
     * POSTを許可しているのは、{@code #register(TodoApp, Model)} からredirectしてくるため
     */
    @RequestMapping(value = { "/", "index" }, method = { RequestMethod.GET, RequestMethod.POST })
    String index(Model model) {
        List<TodoApp> todoList = service.getTodoAppList();
        model.addAttribute("todoList", todoList);// ここの"todoList"というキーがindex.htmlで参照されている
        return "index";// resources/index.htmlを指している
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    String add(Model model) {
        return "detail";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String register(@ModelAttribute TodoApp todoApp, Model model) {
        service.register(todoApp.getTitle(), todoApp.getDetail());
        return "redirect:index";// 登録したらindexに移る
    }

    @RequestMapping(value = "/sel", method = RequestMethod.GET)
    String select(Model model) {
        List<TodoApp> todoList = service.getTodoAppList();
        model.addAttribute("todoList", todoList);// ここの"todoList"というキーがselect.htmlで参照されている
        return "select";
    }

    // /selectでdeleteボタンを押した時の処理
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    String delete(@ModelAttribute TodoApp todoApp, Model model) {
        service.delete(todoApp.getSelectId());
        return "redirect:index";// 削除したらindexに移る
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    String edit(@ModelAttribute TodoApp todoApp,Model model) {
        List<TodoApp> todoList = service.getTodoAppList();
        int selectRow = service.findRowFromId(todoApp.getSelectId(), todoList);
        String currentTitle = service.currentTitle(selectRow, todoList);
        String currentDetail = service.currentDetail(selectRow, todoList);
        model.addAttribute("currentTitle", currentTitle);// edit.htmlで編集前のタイトルを参照
        model.addAttribute("currentDetail", currentDetail);// edit.htmlで編集前のタイトルを参照
        return "edit";
    }
}
