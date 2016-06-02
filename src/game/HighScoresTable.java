package game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 01/06/2016.
 */
public class HighScoresTable {
    private List <ScoreInfo> table;
    private int tableCapacity;

    // Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        table = new ArrayList<ScoreInfo>();
        tableCapacity = size;
    }

    // Add a high-score.
    public void add(ScoreInfo score) {
        if (table.size() == 0) {
            table.add(score);
        } else {
            List<ScoreInfo> newScoreInfo = new ArrayList<ScoreInfo>();
            int i = getRank(score.getScore());
            if (i < size()) {
                for (int j = 0; j < i; ++j) {
                    newScoreInfo.add(table.get(j));
                }
                newScoreInfo.add(score);
                for (int j = i; j < table.size(); ++j) {
                    newScoreInfo.add(table.get(j));
                }
                table = newScoreInfo;
            }
        }
    }

    // Add a high-scores list.
    public void add(List<ScoreInfo> scores) {
        for (ScoreInfo score : scores) {
            this.add(score);
        }
    }

    public int currentSize() {
        return table.size();
    }

    // Return table size.
    public int size() {
        return this.tableCapacity;
    }

    // Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
        return table;
    }

    // return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {
        int i = 0;
        while (i < this.size() && i < table.size() && table.get(i).getScore() > score) {
            i++;
        }
        return i;

    }

    // Clears the table
    public void clear() {
        table.clear();
    }

    // Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            while (filename.canRead()){
                add((ScoreInfo) ois.readObject());
            }
        } catch (IOException e) {
            System.out.println("Error loading file");
        } catch (ClassNotFoundException e) {
            System.out.println("Class from file does not exist");
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("Error closing file");
                }
            }
        }

    }

    // Save table data to the specified file.
    public void save(File filename) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            for (ScoreInfo score : table) {
                oos.writeObject(score);
            }
        } catch (IOException e) {
            System.out.println("Error saving file");
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.println("Error closing file");
                }
            }
        }
    }

    // Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File filename) {
        List<ScoreInfo> ds = new ArrayList<ScoreInfo>();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            ds.add((ScoreInfo) ois.readObject());
            ois.close();
        } catch (IOException e) {
            System.out.println("Error loading from file");
        } catch (ClassNotFoundException e) {
            System.out.println("Class from file does not exist");
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("Error closing file");
                }
            }
        }
        HighScoresTable newScores = new HighScoresTable(ds.size());
        newScores.add(ds);
        return newScores;
    }

    public ScoreInfo getScore(int index) {
        return this.table.get(index);
    }
}
