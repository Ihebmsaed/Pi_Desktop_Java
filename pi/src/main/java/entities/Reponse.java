package entities;

import java.util.Date;

public class Reponse {
    private int rep_id;
    private int rec_id;
    private int admin_id;
    private String rep_desc;
    private Date date_rep;

    public Reponse() {
    }

    public Reponse(int rep_id, int rec_id, String rep_desc) {
        this.rep_id = rep_id;
        this.rec_id = rec_id;
        this.rep_desc = rep_desc;
        Date daterep = new Date();
        this.date_rep = daterep;
    }
    public Reponse(int rep_id, int rec_id, int admin_id, String rep_desc) {
        this.rep_id = rep_id;
        this.rec_id = rec_id;

        this.admin_id = admin_id;
        this.rep_desc = rep_desc;
        Date daterep = new Date();
        this.date_rep = daterep;
    }




    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }


    public int getRep_id() {
        return rep_id;
    }

    public void setRep_id(int rep_id) {
        this.rep_id = rep_id;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }





    public String getRep_desc() {
        return rep_desc;
    }

    public void setRep_desc(String rep_desc) {
        this.rep_desc = rep_desc;
    }

    public Date getDate_rep() {
        return date_rep;
    }

    public void setDate_rep(Date date_rep) {
        this.date_rep = date_rep;
    }

    @Override
    public String toString() {
        return "Reponses{" + "rep_id=" + rep_id + ", rec_id=" + rec_id +  ", rep_desc=" + rep_desc + ", date_rep=" + date_rep + '}';
    }




}
