package nomenclature_piece;

public class PieceDeBase extends Piece{
	
	public PieceDeBase(int reference, String p_denomination, float p_poids) {
		super(reference, p_denomination, p_poids);
	}
	
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public int compareTo(Piece arg0) {
		// TODO Auto-generated method stub
		if(super.getReferenceNumber() == arg0.getReferenceNumber())
			return 0;
		if(super.getReferenceNumber() < arg0.getReferenceNumber())
			return -1;
		return 1;
	}

	@Override
	public String descriptionCourte() {
		return super.toString() + "\n     -1 -1\n";
	}

}
