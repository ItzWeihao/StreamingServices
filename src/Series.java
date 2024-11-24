import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Series extends Media {
    String seasons, timeOfProduction;

    ArrayList<Integer> seasonsAndEpisodes;

    Path path;

    public Series(String title, double rating, String genres, String imageName, String seasons,
            String timeOfProduction) {
        super(title, rating, genres, imageName);
        this.seasons = seasons;
        this.timeOfProduction = timeOfProduction;
        seasonsAndEpisodes = new ArrayList<Integer>();
        // this.fileLocation = "/serieforsider/" + imageName;
        path = Paths.get("serieforsider/" + imageName);
        this.fileLocation = path;
        String[] info = seasons.split("-|\\,");
        for (int i = 0; i < info.length; i++) {
            info[i].trim();
            if (i % 2 != 0) {
                seasonsAndEpisodes.add(Integer.parseInt(info[i]));
            }
        }
    }

    @Override
    public void display() {
        super.display();
        System.out.println(" Seasons: " + seasons + " TimeOfProduction: " + timeOfProduction);
    }

    public String getTimeOfProduction() {
        return timeOfProduction;
    }

    // returns arraylist of integers representing episodes in a season
    // first number is number of episodes in 1st season etc.
    @Override
    public ArrayList<Integer> getSeasonsAndEpisodes() {
        return (seasonsAndEpisodes);
    }

}