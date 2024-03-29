/**
 * Copyright (C) 2015-2016  Ranto
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

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.Date;
import java.util.List;

/**
 * @author Juan Francisco Maldonado León - juan@ranto.cl
 * Ranto - Santiago de Chile
 * Estadisticas de Deportes - Basketball
 */
@Document(collectionName = "games")
public class Game {

	@DocumentId
	private String oid;
	private Date date;
	private DocumentReference visitor;
	private DocumentReference local;
	private int visitorScore;
	private int localScore;
	private DocumentReference court;
	private Referee referee;
	private DocumentReference championship;
	private GameState state;
	private List<GameStat> stats;
	private List<GameStatPlayer> statsPlayer;
	private List<ScoreboardItem> scoreboard;


	public Game(){
	}

	public Game(String oid) {
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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the visitor
	 */
	public DocumentReference getVisitor() {
		return visitor;
	}

	/**
	 * @param visitor the visitor to set
	 */
	public void setVisitor(DocumentReference visitor) {
		this.visitor = visitor;
	}

	/**
	 * @return the local
	 */
	public DocumentReference getLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(DocumentReference local) {
		this.local = local;
	}

	public int getVisitorScore() {
		return visitorScore;
	}

	public void setVisitorScore(int visitorScore) {
		this.visitorScore = visitorScore;
	}

	public int getLocalScore() {
		return localScore;
	}

	public void setLocalScore(int localScore) {
		this.localScore = localScore;
	}

	/**
	 * @return the court
	 */
	public DocumentReference getCourt() {
		return court;
	}

	/**
	 * @param court the court to set
	 */
	public void setCourt(DocumentReference court) {
		this.court = court;
	}

	/**
	 * @return the referee
	 */
	public Referee getReferee() {
		return referee;
	}

	/**
	 * @param referee the referee to set
	 */
	public void setReferee(Referee referee) {
		this.referee = referee;
	}
	
	/**
	 * @return the state
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(GameState state) {
		this.state = state;
	}

	public DocumentReference getChampionship() {
		return championship;
	}

	public void setChampionship(DocumentReference championship) {
		this.championship = championship;
	}

	public List<GameStat> getStats() {
		return stats;
	}

	public void setStats(List<GameStat> stats) {
		this.stats = stats;
	}


	public List<ScoreboardItem> getScoreboard() {
		return scoreboard;
	}

	public void setScoreboard(List<ScoreboardItem> scoreboard) {
		this.scoreboard = scoreboard;
	}

	public List<GameStatPlayer> getStatsPlayer() {
		return statsPlayer;
	}

	public void setStatsPlayer(List<GameStatPlayer> statsPlayer) {
		this.statsPlayer = statsPlayer;
	}

	@Override
	public boolean equals(Object object) {
		if( object instanceof Game)
			return ((Game)object).getOid().equals( this.getOid() );
		return super.equals(object);
	}


	@Override
	public int hashCode() {
		return this.getOid().hashCode();
	}

}
