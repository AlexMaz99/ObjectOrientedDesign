package pl.edu.agh.dronka.shop.model.ItemTypes.utils;

public enum Genre {
    ROCK("ROCK"), JAZZ("JAZZ"), DUBSTEP("DUBSTEP"), TECHNO("TECHNO"), BLUES("BLUES"), COUNTRY("COUNTRY"), POP("POP");

    private String displayName;

    Genre(String genreName) {
        this.displayName = genreName;
    }

    public static Genre parser(String genreName){
        switch(genreName.toUpperCase()){
            case "ROCK":
                return Genre.ROCK;
            case "JAZZ":
                return Genre.JAZZ;
            case "DUBSTEP":
                return Genre.DUBSTEP;
            case "TECHNO":
                return Genre.TECHNO;
            case "BLUES":
                return Genre.BLUES;
            case "COUNTRY":
                return Genre.COUNTRY;
            case "POP":
                return Genre.POP;
            default:
                throw new IllegalArgumentException("No such genre: "+  genreName.toUpperCase());
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
