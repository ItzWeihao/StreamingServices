import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataReader {
    // create arraylists containing the entire strings of data
    ArrayList<String> movieData, seriesData, movieTitles, movieTimeOfReleases, movieGenres, seriesTitles,
            seriesTimeOfProduction, seriesGenres, seriesSeasons;

    ArrayList<Double> movieRatings, seriesRatings;

    ArrayList<Media> movies;

    ArrayList<Media> series;

    public DataReader() {

        // initialize arraylists
        movieData = new ArrayList<String>();
        seriesData = new ArrayList<String>();
        movieTitles = new ArrayList<>();
        movieTimeOfReleases = new ArrayList<>();
        movieGenres = new ArrayList<>();
        movieRatings = new ArrayList<>();
        seriesTitles = new ArrayList<>();
        seriesTimeOfProduction = new ArrayList<>();
        seriesGenres = new ArrayList<>();
        seriesRatings = new ArrayList<>();
        seriesSeasons = new ArrayList<>();
        movies = new ArrayList<Media>();
        series = new ArrayList<Media>();

        // call own methods
        readMovieData();
        readSeriesData();
        movieModifier();
        seriesModifier();
        createSeries();
        createMovies();

    }

    // method for reading data from movie file
    public void readMovieData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("film.txt"));
            String line = br.readLine();

            // while the next line isnt empty, add it to the arraylist
            while (line != null) {
                movieData.add(line);
                line = br.readLine();
            }
            br.close();
            // handle exceptions
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }

        // Test printout
        // System.out.println(movieData.get(0));
    }

    // method for reading data from movie series
    public void readSeriesData() {
        try {
            // variable for reading
            BufferedReader br = new BufferedReader(new FileReader("serier.txt"));
            String line = br.readLine();

            // while line isnt empty, add it to the arraylist
            while (line != null) {
                seriesData.add(line);
                line = br.readLine();
            }
            br.close();
            // handle exceptions
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }

        // test printout
        // System.out.println(seriesData.get(0));
    }

    // return movieData
    public ArrayList<String> getMovieData() {
        return (movieData);
    }

    // return seriesData
    public ArrayList<String> getSeriesData() {
        return (seriesData);
    }

    // add moviedata to designated arraylists
    public void movieModifier() {
        for (String s : movieData) {
            String[] parts = s.split(";");
            movieTitles.add(parts[0]);
            movieTimeOfReleases.add(parts[1]);
            movieGenres.add(parts[2]);
            parts[3] = parts[3].trim();
            parts[3] = parts[3].replaceAll(",", ".");
            double rating = Double.parseDouble(parts[3]);
            movieRatings.add(rating);
        }
        // test printout
        // System.out.println(movieTitles.get(0));
    }

    // add seriesdata to designated arraylists
    public void seriesModifier() {
        for (String s : seriesData) {
            String[] parts = s.split(";");
            seriesTitles.add(parts[0]);
            seriesTimeOfProduction.add(parts[1]);
            seriesGenres.add(parts[2]);
            parts[3] = parts[3].trim();
            parts[3] = parts[3].replaceAll(",", ".");
            double rating = Double.parseDouble(parts[3]);
            seriesRatings.add(rating);
            seriesSeasons.add(parts[4]);
        }
        // test printout
        // System.out.println(seriesTitles.get(0));
    }

    // creates Movie object and uses addMovie method
    public void createMovies() {
        for (int i = 0; i < movieTitles.size(); i++) {
            Movie m = new Movie(movieTitles.get(i), movieTimeOfReleases.get(i),
                    movieGenres.get(i), movieTitles.get(i) + ".jpg", movieRatings.get(i));
            addMovie(m);
        }
        // test printout
        // movies.get(0).display();
    }

    // adds Movie to arraylist movies
    public void addMovie(Movie Movie) {
        movies.add(Movie);
    }

    // creates Series object and uses addSeries method
    public void createSeries() {
        for (int i = 0; i < seriesTitles.size(); i++) {
            Series s = new Series(seriesTitles.get(i), seriesRatings.get(i), seriesGenres.get(i),
                    seriesTitles.get(i) + ".jpg", seriesSeasons.get(i), seriesTimeOfProduction.get(i));
            addSeries(s);
        }
        // test printout
        // series.get(0).display();
    }

    // adds Series to arraylist series
    public void addSeries(Series Series) {
        series.add(Series);
    }

    public ArrayList<Media> getMovies() {
        return (movies);
    }

    public ArrayList<Media> getSeries() {
        return (series);
    }
}
