package cl.ranto.basketballpro.api.core;

import com.google.cloud.firestore.annotation.DocumentId;

public class ScoreboardItem {

    @DocumentId
    private String oid;
    private Integer localPoints;
    private Integer visitorPoints;
    private String quarter;


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
