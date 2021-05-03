package nomenclature_piece;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Nomenclature  implements Sauvable{

	private String nom;
	private LinkedList<Piece> listePieces;
	
	public Nomenclature(String p_nom){
		nom = p_nom;
		listePieces = new LinkedList<Piece>();
	}
	
	public void push(Piece p) throws IllegalArgumentException, NullPointerException{
		if (p == null)
			throw new NullPointerException();
		if(hasSameReferenceNumber(p))
			throw new IllegalArgumentException("Pièce ayant même numéro de référence");
		listePieces.push(p);
	}
	
	// Je fais confiance à la nomenclature donc pas de vérification.
	public void pushFromNomenclature(Piece p) throws NullPointerException{
		if(p == null)
			throw new NullPointerException();
		listePieces.push(p);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nom + " " + listePieces.size() + "\n");
		for(Piece p: listePieces) {
			sb.append(p.descriptionCourte());
		}
		return sb.toString();
	}
	public boolean hasSameReferenceNumber(Piece p) {
		boolean ret = false;
		boolean continuer = true;
		Piece piece;
		if(p == null)
			return false;
		Iterator<Piece> it = listePieces.iterator();
		while(continuer && it.hasNext()) {
			piece = it.next();
			if(piece.getReferenceNumber() == p.getReferenceNumber()) {
				ret = true;
				continuer = false;
			}
		}
		return ret;
	}
	
	public Piece get(int reference) {
		Piece ret = null;
		Iterator<Piece> it = listePieces.iterator();
		while(it.hasNext()) {
			ret = it.next();
			if(ret.getReferenceNumber() == reference)
				return ret;
		}
		return null;
	}
	
	public void printPieceContainingThisPieceInParams(Piece p) {
		for(Piece piece: listePieces) {
			if(piece.contains(p))
				System.out.println(piece);
		}
	}
	
	public boolean remove(Piece p) {
		Piece piece = null;
		boolean continuer = true;
		Iterator<Piece> it;
		if(p == null)
			return false;
		it = listePieces.iterator();
		while(continuer && it.hasNext()) {
			piece = it.next();
			if(piece.contains(p))
				continuer = false;
		}
		if(!continuer)
			return false;
		return listePieces.remove(p);
	}
	
	public void afficherPieceAvecMemeComposant(Piece p) {
		for(Piece piece: listePieces) {
			if(piece.contains(p))
				System.out.println(piece);
		}
	}
	
	public void afficherPieceDeBase() {
		for(Piece p: listePieces) {
			if(p instanceof PieceDeBase)
				System.out.println(p);
		}
	}
	
	public void trier() {
		Collections.sort(listePieces);
	}

	@Override
	public void sauvegarder(String nomFichier) throws IOException{
		PrintWriter writer = new PrintWriter(nomFichier);
		writer.println(nom + " " + listePieces.size());
		for(Piece p: listePieces)
			writer.print(p.descriptionCourte());
		writer.close();
	}
	
}
