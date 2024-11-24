import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class Presentation implements ActionListener {
    private ArrayList<Media> showsearch;
    private Search s;
    private JTextField search = new JTextField("Search for titles", 20);
    private String searchString;
    private Domain domain = new Domain();
    private User admin;

    // Hashmap for storing users
    private HashMap<String, String> UserMap = new HashMap<String, String>();

    // initializing variables used in creating images and popups
    public ImageIcon image;
    public JLabel generalMoviesLabel = new JLabel("Home");
    private JButton label;

    // Initializing of frame
    private JFrame frame = new JFrame("Medialog");

    // Initializing of Panels
    private JPanel genreBar = new JPanel(), showMovies = new JPanel(), movieScroll = new JPanel(),
            menu = new JPanel(new FlowLayout(3)), generalMovies = new JPanel(new FlowLayout(0));

    // Initializing of Scrollpane
    private JScrollPane scrollImages = new JScrollPane(movieScroll);

    // Initializing of Booleans
    boolean movieBoolean = true, seriesBoolean = true, loggedIn = false;

    // Initialize genre array
    private String[] genres = { "Choose Genre", "Talk-show", "Documentary", "Crime", "Drama", "Action", "Adventure",
            "Comedy", "Fantasy", "Animation", "Horror", "Sci-fi", "War", "Thriller", "Mystery", "Biography", "History",
            "Family", "Western", "Romance", "Sport", "Film-Noir", "Musical", "Music" };
    // Genre label above dropdown
    private JComboBox<String> genreDropdown = new JComboBox<String>(genres);

    // ComboBox for seasons and episodes
    private JComboBox<String> seasonDropdown, episodeDropdown;

    // Initializing of Buttons
    private JButton homeButton = new JButton("Home"),
            myList = new JButton("My List"), movies = new JButton("Movies"), series = new JButton("Series"),
            logout = new JButton("Log out");

    private User currentUser;
    private ArrayList<User> UserList = new ArrayList<User>();

    public Presentation() {
        // initializing BoxLayout
        LayoutManager showmoviebox = new BoxLayout(showMovies, BoxLayout.Y_AXIS);
        showMovies.setLayout(showmoviebox);

        // User
        admin = new User("admin", "admin");
        UserMap.put("admin", "admin");
        UserList.add(admin);

        GridLayout layout = new GridLayout(0, 10);
        layout.setHgap(1);
        layout.setVgap(20);
        layout.preferredLayoutSize(showMovies);
        movieScroll.setLayout(layout);
    }

    // Creating a frame for a program
    public void makeFrame() {
        // Coloring the different panels
        // Genrebar color
        genreBar.setBackground(Color.BLACK);
        // Show Movie color
        showMovies.setBackground(Color.BLACK);
        // Menu color
        menu.setBackground(new Color(128, 128, 128));

        // Runs addButton Method
        addButton();

        // Adding to frame
        frame.getContentPane().add(genreBar, "West");
        frame.getContentPane().add(showMovies, "Center");

        scrollImages.getVerticalScrollBar().setUnitIncrement(6);

        // adding movie title pane and series title pane

        // Adding to panel (show movies)
        menu.setMaximumSize(new Dimension(frame.getComponentCount() * 2000, 50));
        generalMovies.setMaximumSize(new Dimension(frame.getComponentCount() * 2000, 50));
        showMovies.add(menu, "North");
        generalMovies.add(generalMoviesLabel, "West");
        showMovies.add(generalMovies, "North");
        showMovies.add(scrollImages);

        // Ending frame reading so it does not wait for more input
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Runs display method
        display();
    }

    // Adds buttons
    public void addButton() {
        // Adding buttons to Genrebar
        genreDropdown.setVisible(true);
        genreBar.add(genreDropdown);

        // Adding buttons to menu
        menu.add(homeButton);
        menu.add(myList);
        menu.add(movies);
        menu.add(series);
        menu.add(search);
        menu.add(logout);

        // Add genrebox action
        genreDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!genreDropdown.getSelectedItem().equals("Choose Genre")) {
                    genreSearchThrough();
                    // System.out.println(genreDropdown.getSelectedItem());
                }
            }
        });

        // Movie button action
        movies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movieScroll.removeAll();
                movieBoolean = true;
                seriesBoolean = false;
                mediaRunthrough(domain.getMovies());
                generalMoviesLabel.setText("Movies");
            }

        });

        // add searchbar
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                movieScroll.removeAll();
                searchString = search.getText();
                s = new Search(searchString, movieBoolean, seriesBoolean, domain);
                try {
                    showsearch = s.titleSearch();
                    mediaRunthrough(showsearch);
                    generalMoviesLabel.setText("Searching for : " + searchString);
                } catch (NoSuchMediaException e) {
                    generalMoviesLabel.setText(e.getMessage());
                }

            }
        });
        // Home button action
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movieScroll.removeAll();
                label.removeAll();
                movieBoolean = true;
                seriesBoolean = true;
                mediaRunthrough(domain.getMovies());
                mediaRunthrough(domain.getSeries());
                generalMoviesLabel.setText("Home");
            }
        });

        // Series button action
        series.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movieScroll.removeAll();
                movieBoolean = false;
                seriesBoolean = true;
                mediaRunthrough(domain.getSeries());
                generalMoviesLabel.setText("Series");
            }
        });

        // My list button action
        myList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movieScroll.removeAll();
                movieBoolean = true;
                seriesBoolean = true;
                myListRunthrough();
                generalMoviesLabel.setText("My List");
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loggedIn = false;
                movieScroll.removeAll();
                frame.dispose();
                loginScreen();
            }
        });
    }

    public void myListRunthrough() {
        for (Media media : currentUser.getList()) {
            String title = media.getTitle();
            createPosters(media);
            JFrame ramme = new JFrame(title);
            label.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Media media : currentUser.getList()) {
                        if (title == media.getTitle()) {
                            JButton addToList = new JButton("Remove from my list");
                            popUp(media, addToList, ramme);
                            addToList.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ramme.setVisible(false);
                                    currentUser.removeFromList(media);
                                    movieScroll.removeAll();
                                    myListRunthrough();
                                    movieScroll.setVisible(false);
                                    movieScroll.setVisible(true);
                                }
                            });
                        }
                        ;
                    }
                }
            });
            movieScroll.add(label);
        }
    }

    public void genreSearchThrough() {
        movieScroll.removeAll();
        s = new Search(genreDropdown.getSelectedItem().toString(), movieBoolean, seriesBoolean, domain);
        try {
            showsearch = s.genreSearch();
            mediaRunthrough(showsearch);
            generalMoviesLabel.setText(genreDropdown.getSelectedItem().toString());
        } catch (NoSuchMediaException e) {
            generalMoviesLabel.setText(e.getMessage());
        }
        // System.out.println(media.getTitle());
    }

    // Displays the program
    public void display() {
        // Combines all elements to frame
        frame.pack();

        // Set a initiative size on frame
        frame.setSize(1000, 600);

        // Setting where the frame will pop-up (null meaning in the middle of the
        // screen)
        frame.setLocationRelativeTo(null);

        // Making frame visible
        frame.setVisible(true);
    }

    public void addMediaToList(Media media, JButton addToList) {
        for (Media Media : currentUser.getList()) {
            if (Media.getTitle().equals(media.getTitle())) {
                currentUser.removeFromList(Media);
            }
        }
        if (currentUser.getList().size() != 0) {
            if (currentUser.getList().contains(media) == false) {
                // System.out.println("inside if statement");
                currentUser.addToList(media);
                addToList.setText("Added to my list");
                // System.out.println(currentUser.getList().get(0).getTitle());
            } else {
                JLabel errorMessage = new JLabel();
                errorMessage.setText("Media is already in your list");
            }
        } else {
            // System.out.println("in else statement");
            currentUser.addToList(media);
            addToList.setText("Added to my list");
            // System.out.println(currentUser.getList().get(0).getTitle());
        }
    }

    public void popUp(Media media, JButton addToList, JFrame ramme) {

        JPanel popup = new JPanel(new BorderLayout());
        JPanel textArea = new JPanel(new GridLayout(0, 1));
        ImageIcon billede = new ImageIcon(media.getFileLocation());
        JLabel popupbillede = new JLabel();
        JLabel popupRating = new JLabel("IMDB Rating:" + media.getRating());
        JLabel popupTitle = new JLabel("Title: " + media.getTitle());
        JLabel popupGenre = new JLabel("Genre: " + media.getGenres());
        JPanel seasonsAndEpisodesPanel = new JPanel(new FlowLayout(0));

        if (media.getSeasonsAndEpisodes() != null) {
            String[] seasonArray = new String[media.getSeasonsAndEpisodes().size()];
            for (int i = 0; i < media.getSeasonsAndEpisodes().size(); i++) {
                seasonArray[i] = "Season " + (i+1);
            }
            String[] episodesArray = new String[media.getSeasonsAndEpisodes().get(0)];
            for (int j = 0; j < media.getSeasonsAndEpisodes().get(0); j++) {
                episodesArray[j] = "Episode " + (j+1);
            }
            seasonDropdown = new JComboBox<String>(seasonArray);
            episodeDropdown = new JComboBox<String>(episodesArray);
            seasonDropdown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int key = seasonDropdown.getSelectedIndex();
                    episodeDropdown.removeAllItems();
                    for (int i = 0; i < media.getSeasonsAndEpisodes().get(key); i++) {
                        episodeDropdown.addItem("Episode " + (i+1));
                    }
                }
            });
            seasonsAndEpisodesPanel.add(seasonDropdown);
            seasonsAndEpisodesPanel.add(episodeDropdown);
        }
        JButton playButton = new JButton("Play");

        popupbillede.setIcon(billede);
        popup.add(popupbillede, "West");
        textArea.add(popupTitle);
        textArea.add(popupGenre);
        textArea.add(popupRating);
        textArea.add(seasonsAndEpisodesPanel);
        textArea.add(addToList);
        textArea.add(playButton);
        popup.add(textArea, "Center");

        ramme.add(popup);
        ramme.pack();
        ramme.setSize(500, 250);
        ramme.setLocationRelativeTo(null);
        ramme.setVisible(true);
    }

    public void createPosters(Media media) {
        image = new ImageIcon(media.getFileLocation());
        label = new JButton(image);
        label.setFocusPainted(false);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void mediaRunthrough(ArrayList<Media> medias) {
        for (Media media : medias) {
            String title = media.getTitle();
            createPosters(media);
            JFrame ramme = new JFrame(title);
            // button on image
            label.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Media media : medias) {
                        if (title == media.getTitle()) {
                            JButton addToList = new JButton("Add to my list");
                            popUp(media, addToList, ramme);
                            addToList.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent f) {
                                    addMediaToList(media, addToList);
                                }
                            });
                        }
                    }
                }
            });
            movieScroll.add(label);
            movieScroll.setVisible(false);
            movieScroll.setVisible(true);
        }
    }

    public void loginScreen() {

        JFrame loginFrame = new JFrame("Login");
        JPanel displayPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new FlowLayout(0));
        JPanel usernamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        JLabel errorLabel = new JLabel();

        LayoutManager loginBox = new BoxLayout(displayPanel, BoxLayout.Y_AXIS);
        JTextArea username = new JTextArea();
        JTextArea password = new JTextArea();
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JButton login = new JButton("Login");
        JButton createUser = new JButton("Create User");
        Dimension d = new Dimension(300, 50);
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        username.setMaximumSize(d);
        password.setMaximumSize(d);
        displayPanel.setLayout(loginBox);
        usernamePanel.add(usernameLabel);
        displayPanel.add(usernamePanel);
        displayPanel.add(username);
        passwordPanel.add(passwordLabel);
        displayPanel.add(passwordPanel);
        displayPanel.add(password);
        buttonPanel.add(login);
        buttonPanel.add(createUser);
        displayPanel.add(buttonPanel);
        displayPanel.add(errorLabel);
        loginFrame.add(displayPanel);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userEntry = username.getText();
                String passwordEntry = password.getText();
                try {
                    if (UserMap.get(userEntry).equals(passwordEntry)) {
                        loggedIn = true;
                        loginFrame.dispose();
                        for (User User : UserList) {
                            if (User.getUsername().equals(userEntry)) {
                                currentUser = User;
                                break;
                            }
                        }
                        initializeProgram();
                    } else {
                        errorLabel.setText("Wrong password");
                    }
                } catch (NullPointerException f) {
                    errorLabel.setText("User not found");
                }
            }
        });
        createUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userEntry = username.getText();
                String passwordEntry = password.getText();
                try {
                    if (UserMap.get(userEntry) != null) {
                        throw new UserAlreadyExistsException("User already exists");
                    } else {
                        UserMap.put(userEntry, passwordEntry);
                        User user = new User(userEntry, passwordEntry);
                        UserList.add(user);
                    }
                } catch (UserAlreadyExistsException f) {
                    errorLabel.setText(f.getMessage());
                }
            }
        });

        loginFrame.pack();
        loginFrame.setSize(500, 200);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void initializeProgram() {
        mediaRunthrough(domain.getMovies());
        mediaRunthrough(domain.getSeries());
        makeFrame();
    }
}
