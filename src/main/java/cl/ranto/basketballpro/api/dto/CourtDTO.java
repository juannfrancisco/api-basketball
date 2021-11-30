package cl.ranto.basketballpro.api.dto;

import cl.ranto.basketballpro.api.core.Court;
import cl.ranto.basketballpro.api.core.Location;

public class CourtDTO {

    private String oid;
    private String name;
    private String description;
    private int spectators;
    private Location location;

    public CourtDTO(){

    }

    public CourtDTO(String oid) {
        this.oid = oid;
    }

    public CourtDTO(Court court){
        this.setOid( court.getOid() );
        this.setName( court.getName() );
        this.setDescription(court.getDescription());
        this.setSpectators(court.getSpectators() );
        this.setLocation( new Location() );
        this.getLocation().setLatitude( court.getLocation().getLatitude()  );
        this.getLocation().setLongitude( court.getLocation().getLongitude() );
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpectators() {
        return spectators;
    }

    public void setSpectators(int spectators) {
        this.spectators = spectators;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
