package nomenclature_piece;

@SuppressWarnings("hiding")
public class Paire<Piece, Integer> {
	private Piece p;
	private int nbOccurence;
	
	public Paire(Piece p, int nbOccurence) {
		this.p = p;
		this.nbOccurence = nbOccurence;
	}
	
	public String toString() {
		int ref;
		if(p instanceof PieceComposite)
			ref = ((PieceComposite) p).getReferenceNumber();
		else
			ref = ((PieceDeBase) p).getReferenceNumber();
		return ref + " " + nbOccurence;
	}

	public Piece getPiece() {
		return p;
	}

	public int getNbOccurence() {
		return nbOccurence;
	}
	
	public void augmenterOccurence(int nombre) {
		nbOccurence += nombre;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Paire))
			return false;
		@SuppressWarnings("unchecked")
		Paire<Piece, Integer> paire = (Paire<Piece, Integer>) o;
		return (p == paire.getPiece());
	}
}
