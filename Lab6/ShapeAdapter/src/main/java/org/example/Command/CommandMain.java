package org.example.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandMain {
    public static void main(String[] args) {

        CommandHistory commandHistory = new CommandHistory();
        Editor editor1 = new Editor("text1");
        Editor editor2 = new Editor("text2");
        List<Editor> editors = new ArrayList<Editor>();
        editors.add(editor1);
        editors.add(editor2);
        Application application = new Application("clipboard", editors, editor1, commandHistory);
        application.createUI();
    }
}
