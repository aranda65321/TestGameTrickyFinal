package Model;

public class Game {
	private String namePlayer1;
    private String namePlayer2;
    private String winner;
    private int idGame;
    private String date;
    
	public Game() {
	}
	
	public Game(String namePlayer1, String namePlayer2, String winner, int idGame, String date) {
		super();
		this.namePlayer1 = namePlayer1;
		this.namePlayer2 = namePlayer2;
		this.winner = winner;
		this.idGame = idGame;
		this.date = date;
	}	   
	
	public String getNamePlayer1() {
		return namePlayer1;
	}

	public void setNamePlayer1(String namePlayer1) {
		this.namePlayer1 = namePlayer1;
	}

	public String getNamePlayer2() {
		return namePlayer2;
	}

	public void setNamePlayer2(String namePlayer2) {
		this.namePlayer2 = namePlayer2;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
