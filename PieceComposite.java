package nomenclature_piece;

import java.util.Iterator;
import java.util.LinkedList;

public class PieceComposite extends Piece{
	private float poidsTotal;
	private LinkedList<Paire<Piece, Integer>> composants;
	
	public PieceComposite(int reference, String p_denomination, float p_poids) {
		super(reference, p_denomination, p_poids);
		composants = new LinkedList<Paire<Piece, Integer>>();
		poidsTotal = super.getPoids();
	}
	
	public void augmenterOccurence(Piece p, int nombre) {
		Paire<Piece, Integer> paire1 = new Paire<Piece, Integer>(p, nombre);
		Paire<Piece, Integer> paire2;
		Iterator<Paire<Piece, Integer>> it = composants.iterator();
		boolean continuer = true;
		while(continuer && it.hasNext()) {
			paire2 = it.next();
			if(paire2.equals(paire1)) {
				paire2.augmenterOccurence(nombre);
				continuer = false;
			}
		} if(!continuer)
			poidsTotal += (nombre * p.getPoids());
	}
	
	public void push(Piece p, int nbOccurence) throws IllegalArgumentException, NullPointerException {
		if (p == null)
			throw new NullPointerException();
		if(p == this || p.contains(this))
			throw new IllegalArgumentException("La pièce ne peut pas se contenir!");
		Paire<Piece, Integer> paire = new Paire<Piece, Integer>(p, nbOccurence);
		composants.push(paire);
		poidsTotal += (nbOccurence * p.getPoids());
	}
	
	// Je fais confiance à la nomenclature donc pas de vérification.
	public void pushFromNomenclature(Piece p, int nbOccurence) {
		Paire<Piece, Integer> paire = new Paire<Piece, Integer>(p, nbOccurence);
		composants.push(paire);
		poidsTotal += (nbOccurence * p.getPoids());
	}
	
	public String affiche_tous_les_sous_composants(int decal) {
		Piece p;
		StringBuilder sb = new StringBuilder();
		String ret = "";
		Iterator<Paire<Piece, Integer>> it = composants.iterator();
		
		for(int i = 0; i < decal; i++) {
			ret = " " + ret;
		}
		sb.append(ret + super.toString() + "\n");

		while(it.hasNext()) {
			ret = "";
			p = it.next().getPiece();
			if(p instanceof PieceComposite) {
				ret = ((PieceComposite) p).affiche_tous_les_sous_composants(decal + 5);
				sb.append(ret);
			} else {
				for(int i = 0; i < decal + 5; i++) {
					ret = " " + ret;
				}
				sb.append(ret + p + "\n");
			}
		}
		return sb.toString();
	}
	
	public String toString() {
		return affiche_tous_les_sous_composants(0);
	}

	@Override
	public float getPoids() {
		return poidsTotal;
	}
	
	@Override
	public void afficherPoids() {
		System.out.println(getDenomination() +  " (Poids: " + poidsTotal + " kg)");
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
	
	public boolean contains(Object o) {
		Iterator<Paire<Piece, Integer>> it = composants.iterator();
		Piece piece;
		Piece po;
		boolean ret = false, continuer = true;
		if(!(o instanceof Piece))
			return false;
		po = (Piece) o;
		while(continuer && it.hasNext()) {
			piece = it.next().getPiece();
			if(piece == po) {
				ret = true;
				continuer = false;
			} else if(piece.contains(po)){
				ret = true;
				continuer = false;
			}
		}
		return ret;
	}
	
	public String afficherDescriptionCourteSousComposant(int decal) {
		Paire<Piece, Integer> paire;
		StringBuilder sb = new StringBuilder();
		String espaces = "";
		Iterator<Paire<Piece, Integer>> it = composants.iterator();
		
		for(int i = 0; i < decal; i++)
			espaces = " " + espaces;
		if(!it.hasNext())
			sb.append(espaces + "-1 -1\n");
		else {
			while(it.hasNext()) {
				paire = it.next();
				sb.append(espaces + paire + "\n");
			}
			sb.append(espaces + "-1 -1\n");
		}
		return sb.toString();
	}
	
	@Override
	public String descriptionCourte() {
		return super.toString() + "\n" + afficherDescriptionCourteSousComposant(5);
	}
}
