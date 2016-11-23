package com.wow.learning.utils;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class FileTool {
   public void f(String[] args) throws Exception {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(args[0]));
      List<String> ls = (List<String>) List.class.cast(in.readObject());
   }
}
