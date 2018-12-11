package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.data.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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
    public String add(@ModelAttribute @Valid Category category,
                      Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "category/add";
        }

        categoryDAO.save(category);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCategoryForm(Model model) {
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("title", "Remove Category");
        return "category/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCategoryForm(@RequestParam int[] categoryIds) {

        for (int categoryId : categoryIds) {
            categoryDAO.delete(categoryId);
        }

        return "redirect:";
    }
}
