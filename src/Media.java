import java.nio.file.Path;
import java.util.ArrayList;

public abstract class Media {
    String title, imageName, genres;
    Path fileLocation;

    double rating;

    // constructor
    public Media(String title, double rating, String genres, String imageName) {
        this.genres = genres;
        this.title = title;
        this.rating = rating;
        this.imageName = imageName;
    }

    // returns title
    public String getTitle() {
        return title;
    }

    // returns rating
    public double getRating() {
        return rating;
    }

    // returns genres
    public String getGenres() {
        return genres;
    }

    // returns imageName
    public String getImageName() {
        return imageName;
    }

    public String getFileLocation() {
        return fileLocation.toString();
    }

    // Prints attributes
    public void display() {
        System.out
                .println("Title: " + title + " Genres: " + genres + " Rating: " + rating + " Imagename: " + imageName);
    }

    public abstract ArrayList<Integer> getSeasonsAndEpisodes();
}