package oit.is.chang6.channel6.controller;

//import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.chang6.channel6.model.Users;
import oit.is.chang6.channel6.model.UsersMapper;
import oit.is.chang6.channel6.model.Rooms;
import oit.is.chang6.channel6.model.RoomsMapper;
import oit.is.chang6.channel6.model.Word;
import oit.is.chang6.channel6.model.WordMapper;

@Controller
@RequestMapping("/ch6")
public class controller {
  @Autowired
  UsersMapper usersMapper;

  @Autowired
  RoomsMapper roomsMapper;

  @Autowired
  WordMapper wordMapper;

  @GetMapping
  public String ch6(ModelMap model) {
    ArrayList<Users> Users1 = usersMapper.selectAll();
    model.addAttribute("users1", Users1);
    return "ch6.html";
  }

  @PostMapping
  public String ch6chat(@RequestParam String word, ModelMap model) {
    Word word1 = new Word();
    word1.setId(1);
    word1.setRoom(1);
    word1.setUser("誰か");
    word1.setWord(word);
    wordMapper.insertWord(word1);

    model.addAttribute("word", word1);
    return "ch6.html";
  }

}
