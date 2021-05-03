package nomenclature_piece;

public abstract class Piece implements Comparable<Piece>{

	private int numRef;
	private float poids;
	private String denomination;
	
	protected Piece(int reference, String p_denomination, float p_poids) throws IllegalArgumentException{
		if(reference == -1 || p_poids == -1.0f)
			throw new IllegalArgumentException();
		numRef = reference;
		denomination = p_denomination;
		poids = p_poids;
	}
	
	public String toString() {
		return numRef + " " + denomination + " " + poids;
	}
	
	public float getPoids() {
		return poids;
	}
	
	public int getReferenceNumber() {
		return numRef;
	}
	
	public String getDenomination() {
		return denomination;
	}
	
	public void afficherPoids() {
		System.out.println(getDenomination() +  " (Poids: " + getPoids() + " kg)");
	}
	
	public boolean estFragile() {
		return false;
	}
	
	public abstract String descriptionCourte();
	
	public abstract boolean contains(Object o);
	
}
