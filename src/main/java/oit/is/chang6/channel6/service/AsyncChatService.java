package oit.is.chang6.channel6.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.chang6.channel6.model.Word;
import oit.is.chang6.channel6.model.WordMapper;

@Service
public class AsyncChatService {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncChatService.class);

  @Autowired
  WordMapper wordMapper;

  public ArrayList<Word> syncShowChatList() {
    return wordMapper.selectByRoom(6);
  }

  @Transactional
  public void syncChatInsert(Word word){

    wordMapper.insertWord(word);

    this.dbUpdated = true;

  }

  @Async
  public void asyncShowChatList(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        TimeUnit.MILLISECONDS.sleep(500);
        if (false == dbUpdated) {
          continue;
        }
        ArrayList<Word> Chat6 = this.syncShowChatList();
        emitter.send(Chat6);
        dbUpdated = false;
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowChatList complete");
  }

}
