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
import org.springframework.cloud.gcp.data.firestore.Document;

/**
 * @author Juan Francisco Maldonado León - juan.maldonado.leon@gmail.com
 * Magno Labs - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@Document(collectionName = "players")
public class Player extends Person{

	@DocumentId
	private String oid;

	//private Team currentTeam;
	private int number;
	private Position position;
	
	/**
	 * 
	 */
	public Player(){
		this.number = 0;
		this.position = null;
	}
	
	/**
	 * @param number
	 * @param position
	 */
	public Player(int number, Position position) {
		super();
		this.number = number;
		this.position = position;
	}


	public Player(String oid) {
		this.setOid(oid);
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
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


	@Override
	public boolean equals(Object object) {
		if( object instanceof Player )
			return ((Player)object).getOid().equals( this.getOid() );
		return super.equals(object);
	}

}
