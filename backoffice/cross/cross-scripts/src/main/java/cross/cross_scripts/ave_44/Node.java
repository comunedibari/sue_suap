package cross.cross_scripts.ave_44;

public class Node {
	private int idPraticaEvento;
	private int counting;
	
	
	public Node(int idPraticaEvento, int counting) {
		super();
		this.idPraticaEvento = idPraticaEvento;
		this.counting = counting;
	}
	public int getIdPraticaEvento() {
		return idPraticaEvento;
	}
	public void setIdPraticaEvento(int idPratica) {
		this.idPraticaEvento = idPratica;
	}
	public int getCounting() {
		return counting;
	}
	public void setCounting(int counting) {
		this.counting = counting;
	}
	@Override
	public String toString() {
		return "Node [idPraticaEvento=" + idPraticaEvento + ", counting=" + counting + "]";
	}
	
	
}
