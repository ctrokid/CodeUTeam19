package com.google.codeu.data;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

  @Test
  public void testCreateMessage() {
    String user = "test_user";
    String text = "test text";
    String recipient = "test_recipient";

    Message message = new Message(user, text, recipient);

    Assert.assertNotEquals(0, message.getId());
    Assert.assertEquals(text, message.getText());
    Assert.assertEquals(user, message.getUser());
    Assert.assertEquals(recipient, message.getRecipient());
  }
}
