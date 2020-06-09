package org.example.Command.Commands;

import org.example.Command.Application;
import org.example.Command.Editor;

public abstract class Command {

    protected Application app;
    protected Editor editor;
    protected String backup;


    public Command(Application app, Editor editor) {
        this.app = app;
        this.editor = editor;
    }

    public void undo(){
        editor.setText(backup);
    }
    public void saveBackup(){
        backup=editor.getText();
    }

    public abstract boolean execute();

}
