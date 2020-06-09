package org.example.Command;

import lombok.Getter;
import lombok.Setter;
import org.example.Command.Commands.Command;

import java.util.List;
@Getter
@Setter
public class Application {

    private String clipboard;
    private List<Editor> editors;
    private Editor activeEditor;
    private CommandHistory history;



    public void createUI() {



    }

    public void executeCommand(Command command) {

        if (command.execute())
            history.push(command);
    }


    public void undo() {

        Command command = history.pop();
        if (command != null)
            command.undo();
    }

}
