package negabur.app.f1team.formula1team.model;

import java.io.Serializable;

/**
 * Created by Ruben on 5/2/15.
 */
public class Team implements Serializable {

    private String name, location, pilotOne, pilotSecond;
    private int championships, born, wins, codi;
    private byte[] imageID;

    public Team(int codi, String name, String location, String pilotOne, String pilotSecond, int championships, int born, int wins, byte[] imageID) {
        this.codi = codi;
        this.name = name;
        this.location = location;
        this.pilotOne = pilotOne;
        this.pilotSecond = pilotSecond;
        this.championships = championships;
        this.born = born;
        this.wins = wins;
        this.imageID = imageID;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setHistory(String location) {
        this.location = location;
    }

    public String getPilotOne() {
        return pilotOne;
    }

    public void setPilotOne(String pilotOne) {
        this.pilotOne = pilotOne;
    }

    public String getPilotSecond() {
        return pilotSecond;
    }

    public void setPilotSecond(String pilotSecond) {
        this.pilotSecond = pilotSecond;
    }

    public int getChampionships() {
        return championships;
    }

    public void setChampionships(int championships) {
        this.championships = championships;
    }

    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public byte[] getImageID() {
        return imageID;
    }

    public void setImageID(byte[] imageID) {
        this.imageID = imageID;
    }
}
