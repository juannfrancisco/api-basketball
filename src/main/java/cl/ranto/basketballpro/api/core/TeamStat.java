package cl.ranto.basketballpro.api.core;

import com.google.cloud.firestore.annotation.DocumentId;

public class TeamStat {

    @DocumentId
    private String oid;
    private String oidTeam;
    private Integer PTS;
    private Integer PTS1;
    private Integer PTS2;
    private Integer PTS3;
    private Integer MPT;
    private Integer MPT1;
    private Integer MPT2;
    private Integer MPT3;
    private Integer OR;
    private Integer AST;
    private Integer MAS;
    private Integer DR;
    private Integer STL;
    private Integer BLK;
    private Integer PF;
    private Integer REB;
    private HiStatPlayer oidPlayerHIPoints;
    private HiStatPlayer oidPlayerHIRebounds;
    private HiStatPlayer oidPlayerHIAssists;


    public TeamStat() {
        this.PTS=0;
        this.PTS1=0;
        this.PTS2=0;
        this.PTS3=0;
        this.MPT=0;
        this.MPT1=0;
        this.MPT2=0;
        this.MPT3=0;
        this.OR=0;
        this.AST=0;
        this.MAS=0;
        this.DR=0;
        this.STL=0;
        this.BLK=0;
        this.PF=0;
        this.REB=0;
    }

    public TeamStat(String oidTeam) {
        this.oidTeam = oidTeam;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOidTeam() {
        return oidTeam;
    }

    public void setOidTeam(String oidTeam) {
        this.oidTeam = oidTeam;
    }

    public Integer getPTS() {
        return PTS;
    }

    public void setPTS(Integer PTS) {
        this.PTS = PTS;
    }

    public Integer getPTS1() {
        return PTS1;
    }

    public void setPTS1(Integer PTS1) {
        this.PTS1 = PTS1;
    }

    public Integer getPTS2() {
        return PTS2;
    }

    public void setPTS2(Integer PTS2) {
        this.PTS2 = PTS2;
    }

    public Integer getPTS3() {
        return PTS3;
    }

    public void setPTS3(Integer PTS3) {
        this.PTS3 = PTS3;
    }

    public Integer getMPT() {
        return MPT;
    }

    public void setMPT(Integer MPT) {
        this.MPT = MPT;
    }

    public Integer getMPT1() {
        return MPT1;
    }

    public void setMPT1(Integer MPT1) {
        this.MPT1 = MPT1;
    }

    public Integer getMPT2() {
        return MPT2;
    }

    public void setMPT2(Integer MPT2) {
        this.MPT2 = MPT2;
    }

    public Integer getMPT3() {
        return MPT3;
    }

    public void setMPT3(Integer MPT3) {
        this.MPT3 = MPT3;
    }

    public Integer getOR() {
        return OR;
    }

    public void setOR(Integer OR) {
        this.OR = OR;
    }

    public Integer getAST() {
        return AST;
    }

    public void setAST(Integer AST) {
        this.AST = AST;
    }

    public Integer getMAS() {
        return MAS;
    }

    public void setMAS(Integer MAS) {
        this.MAS = MAS;
    }

    public Integer getDR() {
        return DR;
    }

    public void setDR(Integer DR) {
        this.DR = DR;
    }

    public Integer getSTL() {
        return STL;
    }

    public void setSTL(Integer STL) {
        this.STL = STL;
    }

    public Integer getBLK() {
        return BLK;
    }

    public void setBLK(Integer BLK) {
        this.BLK = BLK;
    }

    public Integer getPF() {
        return PF;
    }

    public void setPF(Integer PF) {
        this.PF = PF;
    }

    public HiStatPlayer getOidPlayerHIPoints() {
        return oidPlayerHIPoints;
    }

    public void setOidPlayerHIPoints(HiStatPlayer oidPlayerHIPoints) {
        this.oidPlayerHIPoints = oidPlayerHIPoints;
    }

    public HiStatPlayer getOidPlayerHIRebounds() {
        return oidPlayerHIRebounds;
    }

    public void setOidPlayerHIRebounds(HiStatPlayer oidPlayerHIRebounds) {
        this.oidPlayerHIRebounds = oidPlayerHIRebounds;
    }

    public HiStatPlayer getOidPlayerHIAssists() {
        return oidPlayerHIAssists;
    }

    public void setOidPlayerHIAssists(HiStatPlayer oidPlayerHIAssists) {
        this.oidPlayerHIAssists = oidPlayerHIAssists;
    }

    public Integer getREB() {
        return REB;
    }

    public void setREB(Integer REB) {
        this.REB = REB;
    }
}
