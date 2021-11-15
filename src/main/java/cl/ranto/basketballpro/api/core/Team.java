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

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.List;
import java.util.Objects;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@Document(collectionName = "teams")
public class Team {

	@DocumentId
	private String oid;
	private String nameURL;
	private String name;
	private String bio;
	private String oidChampionship;
	private Gender gender;
	private Coach coach;
	private TeamCategory category;
	private Contact contact;

	@Exclude
	private List<Player> players;

	/**
	 * 
	 */
	public Team() {
		this.name ="";
		this.players = null;
	}
	
	public Team(String oid) {
		this.oid = oid;
	}
	
	
	/**
	 * @param name
	 * @param players
	 */
	public Team(String name, List<Player> players) {
		this.name = name;
		this.players = players;
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
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * @param bio the bio to set
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * @return the coach
	 */
	public Coach getCoach() {
		return coach;
	}

	/**
	 * @param coach the coach to set
	 */
	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	/**
	 * @return the category
	 */
	public TeamCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(TeamCategory category) {
		this.category = category;
	}


	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getNameURL() {
		return nameURL;
	}

	public void setNameURL(String nameURL) {
		this.nameURL = nameURL;
	}

	public String getOidChampionship() {
		return oidChampionship;
	}

	public void setOidChampionship(String oidChampionship) {
		this.oidChampionship = oidChampionship;
	}

	@Override
	public boolean equals(Object obj) {
		if( obj instanceof Team ){
			return ((Team)obj).getOid().equals( this.getOid() );
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(oid);
	}
}
