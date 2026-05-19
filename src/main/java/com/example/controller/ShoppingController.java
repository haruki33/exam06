package com.example.controller;

import com.example.domain.Item;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private HttpSession session;
    @Autowired
    private ServletContext application;

    @GetMapping("")
    public String index() {
        if (application.getAttribute("items") == null) {
            List<Item> items = new LinkedList<>();
            items.add(Item.builder().name("手帳ノート").price(1000).build());
            items.add(Item.builder().name("文房具セット").price(1500).build());
            items.add(Item.builder().name("ファイル").price(2000).build());
            application.setAttribute("items", items);
        }

        if (session.getAttribute("shoppingCart") == null) {
            List<Item> shoppingCart = new ArrayList<>();
            session.setAttribute("shoppingCart", shoppingCart);
        }

        return "item-and-cart";
    }

    @PostMapping("/in-cart")
    public String inCart(String id) {
        List<Item> items = (List<Item>) application.getAttribute("items");
        //　Integer.valueOf : Integerクラスを取得する
        //  Integer.parseInt : intを取得する
        Item purchacedItem = items.get(Integer.parseInt(id));

        List<Item> shoppingCart = (List<Item>) session.getAttribute("shoppingCart");
        shoppingCart.add(purchacedItem);

        return "redirect:/shopping";
    }

    @PostMapping("/delete")
    public String delete(int id) {
        List<Item> shoppingCart = (List<Item>) session.getAttribute("shoppingCart");
        shoppingCart.remove(id);
        return "redirect:/shopping" + index();
    }
}
