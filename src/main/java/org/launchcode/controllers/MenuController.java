package org.launchcode.controllers;

import org.launchcode.models.data.CheeseDAO;
import org.launchcode.models.data.MenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    private CheeseDAO cheeseDAO;

    @Autowired
    private MenuDAO menuDAO;



}