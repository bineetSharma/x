package com.example.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;

public class MainClass {

  public static void main(String[] args) throws Exception {
    URLName server = new URLName("imaps://sunnyrealsunny@gmail.com/inbox");

    Session session = Session.getDefaultInstance(new Properties(), new MailAuthenticator());
    Store store = session.getStore("imaps");
    store.connect("smtp.gmail.com", "sunnyrealsunny@gmail.com", "123Waterfall");
    Folder folder = session.getFolder(server);
    if (folder == null) {
      System.out.println("Folder " + server.getFile() + " not found.");
      System.exit(1);
    }
    folder.open(Folder.READ_ONLY);

    Message[] messages = folder.getMessages();
    for (int i = 0; i < messages.length; i++) {
      System.out.println(messages[i].getSize() + " bytes long.");
      System.out.println(messages[i].getLineCount() + " lines.");
      String disposition = messages[i].getDisposition();
      if (disposition == null){
        ; // do nothing
      }else if (disposition.equals(Part.INLINE)) {
        System.out.println("This part should be displayed inline");
      } else if (disposition.equals(Part.ATTACHMENT)) {
        System.out.println("This part is an attachment");
        String fileName = messages[i].getFileName();
        System.out.println("The file name of this attachment is " + fileName);
      }
      String description = messages[i].getDescription();
      if (description != null) {
        System.out.println("The description of this message is " + description);
      }
    }
    folder.close(false);
  }
}

class MailAuthenticator extends Authenticator {

  public MailAuthenticator() {
  }

  public PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication("sunnyrealsunny@gmail.com", "123Waterfall");
  }
}

