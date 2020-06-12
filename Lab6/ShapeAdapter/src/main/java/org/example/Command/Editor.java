package org.example.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Editor {

    private String text;

    public String getSelection(){
        return text;
    }

    public void deleteSelection(){
        System.out.println(text + " is deleted");
        text = "";
    }

    public void replaceSelection(String toReplace){
        System.out.println("Replaced " + text + " to " + toReplace);
        text = toReplace;
    }

}
