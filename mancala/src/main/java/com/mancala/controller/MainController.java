package com.mancala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mancala.utils.Constants;


@Controller
public class MainController {
	
	/**
	 * return the main view of the game
	 * 
	 * @param html model
	 * 
	 * @return the main view of the game
	 */
    @GetMapping("/")
    public String main(Model model) {
        return Constants.MAIN_TEMPLATE;
    }

}
