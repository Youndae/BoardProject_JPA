package com.board.project.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/board")
public class HierarchicalBoardController {

    @GetMapping("/hierarchicalBoard")
    public void HierarchicalBoardMain(Model model){

    }

}
