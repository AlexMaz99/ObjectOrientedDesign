package org.example.Command.Commands;

import org.example.Command.Application;
import org.example.Command.Editor;

public class UndoCommand extends Command {
    public UndoCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    public boolean execute() {
        app.undo();
        return false;

    }
}
