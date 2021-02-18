package com.sample.todo.controller;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
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

    @RequestMapping(value = "/register", method = { RequestMethod.GET, RequestMethod.POST })
    String register(@ModelAttribute TodoApp todoApp, Model model) {
        service.register(todoApp.getTitle(), todoApp.getDetail(), todoApp.getDate());
        return "redirect:index";// 登録したらindexに移る
    }

    @RequestMapping(value = "/sel", method = RequestMethod.GET)
    String select(Model model) {
        List<TodoApp> todoList = service.getTodoAppList();
        model.addAttribute("todoList", todoList);// ここの"todoList"というキーがselect.htmlで参照されている
        return "select";
    }

    // /selectでdeleteボタンを押した時の処理
    @RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
    String delete(@ModelAttribute TodoApp todoApp, Model model) {
        ArrayList<String> strDeleteIds = todoApp.getDeleteIds();
        if (strDeleteIds != null){
            ArrayList<Integer> deleteIds = new ArrayList<Integer>();
            for(int i = 0;i < strDeleteIds.size();i++){
                try{
                    int deleteId = Integer.parseInt(strDeleteIds.get(i));
                    deleteIds.add(deleteId);
                }
                catch(NumberFormatException e){
                    //何もしない
                    System.out.println("tinko");
                }
            }
            service.delete(deleteIds);
        }else{
            System.out.println("unko");
        }
        return "redirect:index";// 削除したらindexに移る
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
    String edit(@ModelAttribute TodoApp todoApp,Model model) {
        int Id = todoApp.getTodoId();
        String currentTitle = todoApp.getTitle();
        String currentDetail = todoApp.getDetail();
        Date currentDate = todoApp.getDate();
        model.addAttribute("Id", Id);
        model.addAttribute("currentTitle", currentTitle);// edit.htmlで編集前のタイトルを参照
        model.addAttribute("currentDetail", currentDetail);
        model.addAttribute("currentDate", currentDate);
        return "edit";
    }

    @RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
    String update(@ModelAttribute TodoApp todoApp, Model model) {
        service.update(todoApp.getTodoId(),todoApp.getTitle(),todoApp.getDetail(),todoApp.getDate());
        return "redirect:index";// 登録したらindexに移る
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    String search(@ModelAttribute TodoApp todoApp, Model model) {
        List<TodoApp> searchResult =service.search(todoApp.getSearchArea(),todoApp.getSearchKeyword());
        model.addAttribute("todoList", searchResult);// ここの"todoList"というキーがindex.htmlで参照されている
        return "index";// 登録したらindexに移る
    }

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    String sort(@ModelAttribute TodoApp todoApp, Model model) {
        if(todoApp.getSortArea() != null){
            List<TodoApp> sortResult =service.sort(todoApp.getSortArea());
        model.addAttribute("todoList", sortResult);// ここの"todoList"というキーがindex.htmlで参照されている
        return "index";// 登録したらindexに移る
        }else{
            //処理なし
        }
        return "redirect:index";// 登録したらindexに移る
        
    }
}
