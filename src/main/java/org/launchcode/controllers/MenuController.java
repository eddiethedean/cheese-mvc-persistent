package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDAO;
import org.launchcode.models.data.MenuDAO;
import org.launchcode.models.data.forms.addMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    private CheeseDAO cheeseDAO;

    @Autowired
    private MenuDAO menuDAO;

    @RequestMapping(value = "")
    public String index(Model templateVariables) {
        templateVariables.addAttribute("title", "Menus");
        templateVariables.addAttribute("menus", menuDAO.findAll());
        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model templateViables) {
        templateViables.addAttribute("title", "Add Menu");
        templateViables.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu newMenu,
                                       Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }
        //newMenu.setCategory(categoryId, categoryDAO);
        menuDAO.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public String displayMenu(Model model, @PathVariable int menuId) {
        Menu menu = menuDAO.findOne(menuId);
        model.addAttribute("title", menu.getName());
        model.addAttribute("cheeses", menu.getCheeses());
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId) {
        Menu menu = menuDAO.findOne(menuId);
        addMenuItemForm form = new addMenuItemForm(cheeseDAO.findAll(), menu);
        model.addAttribute("title", "Add item to menu: " + menu.getName());
        model.addAttribute("form", form);
        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model,
                    @ModelAttribute @Valid addMenuItemForm form, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            return "menu/add-item";
        }

        Cheese theCheese = cheeseDAO.findOne(form.getCheeseId());
        Menu theMenu = menuDAO.findOne(form.getMenuId());
        theMenu.addItem(theCheese);
        menuDAO.save(theMenu);
        return "redirect:/menu/view/" + theMenu.getId();
    }

}
