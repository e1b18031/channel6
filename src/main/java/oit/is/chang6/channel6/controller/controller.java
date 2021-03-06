package oit.is.chang6.channel6.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.chang6.channel6.model.Users;
import oit.is.chang6.channel6.model.UsersMapper;
import oit.is.chang6.channel6.model.Rooms;
import oit.is.chang6.channel6.model.RoomsMapper;
import oit.is.chang6.channel6.model.Word;
import oit.is.chang6.channel6.model.WordMapper;
import oit.is.chang6.channel6.service.AsyncChatService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/ch6")
public class controller {
  @Autowired
  UsersMapper usersMapper;

  @Autowired
  RoomsMapper roomsMapper;

  @Autowired
  WordMapper wordMapper;

  @Autowired
  AsyncChatService Chat6;

  @GetMapping
  public String ch6(Principal prin, ModelMap model) {
    ArrayList<Users> Users1 = usersMapper.selectAll();
    String loginUser = prin.getName();
    model.addAttribute("users1", Users1);
    model.addAttribute("loginUser", loginUser);
    return "lobby.html";
  }

  @GetMapping("backlobby")
  public String backlobby(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    Users user1 = usersMapper.selectByUser(prin.getName());
    user1.setRoom(0);
    usersMapper.updateById(user1);
    model.addAttribute("loginUser", loginUser);
    return "lobby.html";
  }

  @GetMapping("lobbystep")
  @Transactional
  public String lobbystep(@RequestParam Integer number, ModelMap model, Principal prin) {
    String loginUser = prin.getName(); // ログインユーザ情報
    Users user1 = usersMapper.selectByUser(loginUser);
    user1.setRoom(number);
    usersMapper.updateById(user1);
    ArrayList<Users> Users1 = usersMapper.selectByRoom(number);
    model.addAttribute("users1", Users1);
    model.addAttribute("room", number);
    final ArrayList<Word> word_list = wordMapper.selectByRoom(user1.getRoom());
    model.addAttribute("word_list", word_list);
    return "ch6.html";
  }

  @GetMapping("chatstep")
  public SseEmitter chatstep() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.Chat6.asyncShowChatList(sseEmitter);
    return sseEmitter;
  }

  @GetMapping("reload")
  public String reload(ModelMap model, Principal prin) {
    Users user1 = usersMapper.selectByUser(prin.getName());
    ArrayList<Users> Users1 = usersMapper.selectByRoom(user1.getRoom());
    model.addAttribute("users1", Users1);
    model.addAttribute("room", user1.getRoom());
    final ArrayList<Word> word_list = wordMapper.selectByRoom(user1.getRoom());
    model.addAttribute("word_list", word_list);
    return "ch6.html";
  }

  @PostMapping
  public String ch6chat(@RequestParam String word, ModelMap model, Principal prin) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
    String str = sdf.format(timestamp);
    // model.addAttribute("time", str);

    Users user1 = usersMapper.selectByUser(prin.getName());
    Word word1 = new Word();
    word1.setId(0);
    word1.setRoom(user1.getRoom());
    word1.setUser(user1.getUser());
    word1.setTime(str);
    word1.setWord(word);
    Chat6.syncChatInsert(word1);

    ArrayList<Users> Users1 = usersMapper.selectByRoom(user1.getRoom());
    model.addAttribute("users1", Users1);
    model.addAttribute("room", user1.getRoom());

    final ArrayList<Word> word_list = wordMapper.selectByRoom(user1.getRoom());
    model.addAttribute("word_list", word_list);

    return "ch6.html";

  }

}
