package org.example.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.Command.Commands.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class Application {

    private String clipboard;
    private List<Editor> editors;
    private Editor activeEditor;
    private CommandHistory history;



    public void createUI() {

        executeCommand(new CopyCommand(this, activeEditor));

        executeCommand(new CutCommand(this, activeEditor));

        executeCommand(new PasteCommand(this, activeEditor));

        executeCommand(new UndoCommand(this, activeEditor));

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
