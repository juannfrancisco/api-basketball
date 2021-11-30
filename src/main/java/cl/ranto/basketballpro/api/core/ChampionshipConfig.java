package cl.ranto.basketballpro.api.core;

public class ChampionshipConfig {

    private Integer numberFoul;
    private Integer quarterDuration;
    private Integer timeoutMaxPerQuarter;


    public Integer getNumberFoul() {
        return numberFoul;
    }

    public void setNumberFoul(Integer numberFoul) {
        this.numberFoul = numberFoul;
    }

    public Integer getQuarterDuration() {
        return quarterDuration;
    }

    public void setQuarterDuration(Integer quarterDuration) {
        this.quarterDuration = quarterDuration;
    }

    public Integer getTimeoutMaxPerQuarter() {
        return timeoutMaxPerQuarter;
    }

    public void setTimeoutMaxPerQuarter(Integer timeoutMaxPerQuarter) {
        this.timeoutMaxPerQuarter = timeoutMaxPerQuarter;
    }
}
