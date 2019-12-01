package app.entities;

import javax.persistence.*;

@Entity
public class Copies {
    public Copies() {
    }

    public Copies(int count, int rate) {
        this.count = count;
        this.rate = rate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int count;
    private int rate;


    @OneToOne(mappedBy = "copies")
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}
