package raditya.labs.sqlitedatabase.model;

/**
 * Created by raditya on 3/23/2015.
 */
public class InvoiceModel {

    private int id;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
