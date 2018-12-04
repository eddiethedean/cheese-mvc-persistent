package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "category")
public class CategoryController {
    @Autowired
    private CategoryDAO categoryDAO;

    @RequestMapping(value = "")
    public String index(Model viewBag) {

        viewBag.addAttribute("title", "All Categories");
        viewBag.addAttribute("categories", categoryDAO.findAll());
        return "category/index";
    }

    @RequestMapping(value = "add")
    public String add(Model viewBag) {
        viewBag.addAttribute("title", "Add Category");
        viewBag.addAttribute("category", new Category());
        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add() {
        return "";
    }
}
