package pl.edu.agh.dronka.shop.model.ItemTypes;

import pl.edu.agh.dronka.shop.model.Category;
import pl.edu.agh.dronka.shop.model.ItemTypes.utils.Genre;

public class Music extends Item {

    private Genre genre;
    private boolean hasVideo;

    public Music(String name, Category category, int price, int quantity, String genre, boolean hasVideo) {
        super(name, category, price, quantity);
        this.genre = Genre.parser(genre);
        this.hasVideo = hasVideo;
    }

    public Music(){}

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }
}
