package pl.agh.edu.dp.labirynth.MazeElements.Walls;

import pl.agh.edu.dp.labirynth.MazeElements.MapSite;

public class Wall extends MapSite {

    public Wall(){

    }


    @Override
    public void Enter(){
        System.out.println("You hit normal wall");

    }
}
