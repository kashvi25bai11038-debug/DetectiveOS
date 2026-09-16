package com.detectiveos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/** Thread-safe LIFO action history using the Java Stack collection. */
public class InvestigationHistory {
    private final Stack<String> actions = new Stack<>();
    public synchronized void push(String action){actions.push(action);}
    public synchronized String pop(){return actions.empty()?"No previous action.":actions.pop();}
    public synchronized String latest(){return actions.empty()?"No actions recorded.":actions.peek();}
    public synchronized int size(){return actions.size();}
    public synchronized String dump(){List<String> copy=new ArrayList<>(actions);StringBuilder b=new StringBuilder();for(int i=copy.size()-1;i>=0;i--)b.append("• ").append(copy.get(i)).append('\n');return b.length()==0?"No investigation actions recorded.":b.toString();}
}
