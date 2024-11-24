import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Movie extends Media {
    String timeOfRelease;
    Path path;

    public Movie(String title, String timeOfRelease, String genres, String imageName, double rating) {
        super(title, rating, genres, imageName);
        this.timeOfRelease = timeOfRelease;
        // this.fileLocation = "/filmplakater/" + imageName;
        path = Paths.get("filmplakater/" + imageName);
        this.fileLocation = path;
    }

    @Override
    public void display() {
        super.display();
        System.out.println(" Year: " + timeOfRelease);
    }

    public String getTimeOfRelease() {
        return timeOfRelease;
    }

    public ArrayList<Integer> getSeasonsAndEpisodes() {
        return null;
    }
}