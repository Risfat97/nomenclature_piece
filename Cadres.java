package nomenclature_piece;

public class Cadres extends PieceComposite{

	public Cadres(int reference, float p_poids) {
		super(reference, "Cadres", p_poids);
	}
	
	public boolean estFragile() {
		return true;
	}
}
