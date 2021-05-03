package nomenclature_piece;

public class Lampe extends PieceDeBase{

	private float puissance;
	
	public Lampe(int reference, float poids, float p_puissance) {
		super(reference, "Lampe", poids);
		puissance = p_puissance;
	}
	
	public String toString() {
		return super.toString() + " puissance: " + puissance + " Lumen";
	}

	public boolean estFragile() {
		return true;
	}
}
