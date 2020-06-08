/**
 * Copyright (C) 2015-2016  Magno Labs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cl.ranto.basketballpro.api.core;


import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@Document(collectionName = "courts")
public class Court {


	@DocumentId
	private String oid;
	private String name;
	private String description;
	private int spectators;
	private GeoPoint location;
	
	public Court(){
	}
	
	public Court(String oid){
		this.oid = oid;
	}
	
	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the location
	 */
	public GeoPoint getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(GeoPoint location) {
		this.location = location;
	}

	/**
	 * @return the spectators
	 */
	public int getSpectators() {
		return spectators;
	}

	/**
	 * @param spectators the spectators to set
	 */
	public void setSpectators(int spectators) {
		this.spectators = spectators;
	}

}
