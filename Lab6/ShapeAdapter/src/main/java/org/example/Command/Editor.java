package org.example.Command;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Editor {

    private String text;

    public String getSelection(){
        return "Selected text";
    }

    public void deleteSelection(){
        System.out.println("Deleted");
    }

    public void replaceSelection(String toReplace){
        System.out.println("replaced");
    }

}
