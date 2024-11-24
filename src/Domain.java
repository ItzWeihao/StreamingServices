import java.util.*;;

public class Domain {

    private ArrayList<Media> series;
    private ArrayList<Media> movies;

    public Domain() {
        DataReader DataReader = new DataReader();
        series = DataReader.getSeries();
        movies = DataReader.getMovies();
    }

    public ArrayList<Media> getMovies() {
        return movies;
    }

    public ArrayList<Media> getSeries() {
        return series;
    }

}
