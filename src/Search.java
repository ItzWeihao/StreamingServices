import java.util.*;

// search media based on booleans. 
// Converting to lowercase helps pass lowercase searches to non-lowercase titles and vice versa.

public class Search {
    ArrayList<Media> MediasInSearch;
    String search;
    Domain Domain;
    boolean movies, series;

    public Search(String search, boolean movies, boolean series, Domain Domain) {
        this.search = search.toLowerCase();
        this.movies = movies;
        this.series = series;
        MediasInSearch = new ArrayList<>();
        // necessary to create a datareader while it isnt connected to the rest of the
        // program
        this.Domain = Domain;
    }

    // adds media to arraylist if title contains serach
    public ArrayList<Media> titleSearch() throws NoSuchMediaException {
        if (movies) {
            for (Media Media : Domain.getMovies()) {
                String MovieTitle = Media.getTitle().toLowerCase();
                if (MovieTitle.contains(search)) {
                    MediasInSearch.add(Media);
                }
            }
        }
        if (series) {
            for (Media Media : Domain.getSeries()) {
                String SeriesTitle = Media.getTitle().toLowerCase();
                if (SeriesTitle.contains(search)) {
                    MediasInSearch.add(Media);
                }
            }
        }
        if (MediasInSearch.size() == 0) {
            throw new NoSuchMediaException("Your search gave no results");
        }
        return MediasInSearch;
    }

    // searches for a specific genre and adds to list of medias
    public ArrayList<Media> genreSearch() throws NoSuchMediaException {
        if (movies) {
            for (Media Media : Domain.getMovies()) {
                String Genres = Media.getGenres().toLowerCase();
                if (Genres.contains(search)) {
                    MediasInSearch.add(Media);
                }
            }
        }
        if (series) {
            for (Media Media : Domain.getSeries()) {
                String Genres = Media.getGenres().toLowerCase();
                if (Genres.contains(search)) {
                    MediasInSearch.add(Media);
                }
            }
        }
        if (MediasInSearch.size() == 0)

        {
            throw new NoSuchMediaException("No media has such genres");
        }
        return MediasInSearch;
    }
}
