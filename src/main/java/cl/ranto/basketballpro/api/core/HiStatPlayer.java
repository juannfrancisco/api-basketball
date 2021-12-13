package cl.ranto.basketballpro.api.core;

public class HiStatPlayer {

    private String oidPlayer;
    private TypeStat type;
    private Integer value;


    public HiStatPlayer() {
    }

    public HiStatPlayer(String oidPlayer, TypeStat type, Integer value) {
        this.oidPlayer = oidPlayer;
        this.type = type;
        this.value = value;
    }

    public String getOidPlayer() {
        return oidPlayer;
    }

    public void setOidPlayer(String oidPlayer) {
        this.oidPlayer = oidPlayer;
    }

    public TypeStat getType() {
        return type;
    }

    public void setType(TypeStat type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
