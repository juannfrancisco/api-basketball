package cl.ranto.basketballpro.api.core;

import com.google.cloud.firestore.annotation.DocumentId;

public class ScoreboardItem {

    @DocumentId
    private String oid;
    private Integer localPoints;
    private Integer visitorPoints;
    private String quarter;


    public ScoreboardItem() {
        this.setLocalPoints(0);
        this.setVisitorPoints(0);
        this.setQuarter("");
    }

    public ScoreboardItem(Integer localPoints, Integer visitorPoints, String quarter) {
        this.localPoints = localPoints;
        this.visitorPoints = visitorPoints;
        this.quarter = quarter;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getLocalPoints() {
        return localPoints;
    }

    public void setLocalPoints(Integer localPoints) {
        this.localPoints = localPoints;
    }

    public Integer getVisitorPoints() {
        return visitorPoints;
    }

    public void setVisitorPoints(Integer visitorPoints) {
        this.visitorPoints = visitorPoints;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }
}
