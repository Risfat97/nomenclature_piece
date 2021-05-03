package nomenclature_piece;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainPiece {
	
	static Nomenclature nomenclature = null;
	static String tab[] = {
	    "Exit", 
	    "Nouvelle nomenclature",
	    "Ajouter une pièce sans ces composants",
	    "Afficher la nomenclature",
	    "Pièces contenant un même composant",
	    "Ajouter un composant à une pièce",
	    "Afficher tous les détails d'une pièce",
	    "Supprimer une pièce",
	    "Trier",
	    "Afficher pièce de base",
	    "Sauvegarder la nomenclature",
	    "Lire une nomenclature"
	};
	
	static Scanner sc = new Scanner(System.in);
	
	public static int saisieInt() {
		String rep;
		boolean continuer;
		int ret = 0;
		do {
			rep = sc.next();
			try {
				ret = Integer.parseInt(rep);
				continuer = false;
			} catch(NumberFormatException e) {
				System.out.println("Vous devez saisir un entier.");
				continuer = true;
			}
		} while(continuer);
		return ret;
	}
	
	public static float saisieFloat() {
		String rep;
		boolean continuer = false;
		float ret = 0.0f;
		do {
			rep = sc.next();
			try {
				ret = Float.parseFloat(rep);
				continuer = false;
			} catch(NumberFormatException e) {
				System.out.println("Vous devez saisir un flottant.");
				continuer = true;
			}
		} while(continuer);
		return ret;
	}
	
	public static int menu() {
		int ret;
		System.out.println("Veuillez faire votre choix:");
		for(int i = 0; i < tab.length; i++)
			System.out.println("\t" + i + "-" + tab[i]);
		ret = saisieInt();
		return ret;
	}
	
	public static int menuTypeDePiece(String opt1, String opt2) {
		int ret;
		System.out.println("Veuillez choisir le type de pièce:");
		System.out.println("1-"+opt1);
		System.out.println("2-"+opt2);
		ret = saisieInt();
		return ret;
	}
	
	public static boolean confirmer(String question) {
		int ret;
		do{
			System.out.println(question);
			System.out.println("1-oui");
			System.out.println("2-non");
			ret = saisieInt();
		} while(ret != 1 && ret != 2);
		return ret == 1;
	}
	
	public static void nouvelleNomenclature() {
		System.out.print("Saisir le nom: ");
		String nom = sc.next();
		nomenclature = new Nomenclature(nom);
		System.out.println("Nomenclature créé!\n");
	}
	
	public static void ajouterPieceSansCesComposants() {
		Piece p;
		String denom;
		int ref;
		float poids;
		System.out.println("Saisir le numéro de référence: ");
		ref = saisieInt();
		System.out.println("Saisir le poids: ");
		poids = saisieFloat();
		if(confirmer("Est-ce une pièce fragile?")) {
			p = new Cadres(ref, poids);
		} else {
			System.out.println("Saisir la dénomination: ");
			denom = sc.next();
			p = new PieceComposite(ref, denom, poids);
		}
		try {
			nomenclature.push(p);
			System.out.println("Composant ajouté!\n");
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch(NullPointerException e) {
			System.out.println("Chargement nomenclature: " + e.getMessage());
		}
	}
	
	public static void listePieceMemeComposant() {
		int ref;
		System.out.println("Saisir la référence du composant: ");
		ref = saisieInt();
		nomenclature.afficherPieceAvecMemeComposant(nomenclature.get(ref));
	}
	
	public static void ajouterComposantAUnePiece() {
		Piece piece, pieceAAjoute;
		String denom;
		int ref, choix, puissance;
		float poids;
		System.out.println("Saisir le numéro de référence de la pièce que vous voulez  ajouter un composant: ");
		ref = saisieInt();
		piece = nomenclature.get(ref);
		if(piece == null)
				System.out.println("Pièce non présente dans la nomenclature!");			
		else if(piece instanceof PieceDeBase) {
			System.out.println("La pièce choisie n'est pas une pièce composite");
			System.out.println("Impossible d'y ajouter un composant");
		}
		else {
			System.out.println("Saisir le numéro de référence du composant: ");
			ref = saisieInt();
			System.out.println("Saisir le poids du composant: ");
			poids = saisieFloat();
			
			do {
				choix = menuTypeDePiece("Pièce de base", "Pièce composite");
			}while(choix != 1 && choix != 2);
			if(choix == 1) {
				if(confirmer("Est-ce une pièce fragile")) {
					System.out.println("Saisir la puissance en Lumen de la lampe: ");
					puissance = saisieInt();
					pieceAAjoute = new Lampe(ref, poids, puissance);
				} else {
					System.out.println("Saisir la dénomination du composant: ");
					denom = sc.next();
					pieceAAjoute = new PieceDeBase(ref, denom, poids);
				}
			}else {
				if(confirmer("Est-ce une pièce fragile")) {
					System.out.println("Saisir la puissance en Lumen de la lampe: ");
					pieceAAjoute = new Cadres(ref, poids);
				} else {
					System.out.println("Saisir la dénomination du composant: ");
					denom = sc.next();
					pieceAAjoute = new PieceComposite(ref, denom, poids);
				}
			}
			try {
				((PieceComposite) piece).push(pieceAAjoute, 1);
				System.out.println("Composant ajouté!\n");
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch(NullPointerException e) {
				System.out.println("Chargement nomenclature: " + e.getMessage());
			}
		}
	}
	
	public static void descriptionPiece() {
		int ref;
		Piece p;
		System.out.println("Saisir le numéro de référence de la pièce: ");
		ref = saisieInt();
		p = nomenclature.get(ref);
		if(p == null)
			System.out.println("Pièce non présente dans la nomenclature!");
		else
			System.out.println(p);
	}
	
	public static void supprimerPiece() {
		System.out.println("Saisir le numéro de référence de la pièce: ");
		int ref = saisieInt();
		Piece p = nomenclature.get(ref);
		if(p == null)
			System.out.println("Pièce non présente dans la nomenclature!");
		else  if(!nomenclature.remove(p))
			System.out.println("Suppression non autorisée!");
		else
			System.out.println("Pièce supprimée!");
	}
	
	public static void sauvegarder() {
		long debut = 0L, fin = 0L;
		System.out.println("Saisir le nom du fichier: ");
		String nomFic = sc.next();
		System.out.println("Debut sauvegarde nomenclature...");
		debut = System.currentTimeMillis();
		try {
			nomenclature.sauvegarder(nomFic);
			fin = System.currentTimeMillis();
			System.out.println("Fin sauvegarde nomenclature dans " + nomFic);
			System.out.println("Temps de sauvegarde du fichier: " + (((float) (fin - debut)) / 1000) + "s");
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void charger(String nomFichier) throws FileNotFoundException{
		File fic = new File(nomFichier);
		Scanner scn = new Scanner(fic), scnAgain;
		System.out.println("Début chargement du fichier " + nomFichier + "...");
		String tab[];
		String denom = "";
		int ref = 0, nbOcc = 0;
		float poids = 0.0f;
		Piece p = null, p1 = null;
		long debut = 0L, fin = 0L;
		
		if(scn.hasNextLine()) {
			debut = System.currentTimeMillis();
			tab = scn.nextLine().split("[ \t]+");
			if(tab.length == 2) {
				nomenclature = new Nomenclature(tab[0]);
			}
			while(scn.hasNextLine()) {
				tab = scn.nextLine().split("[ \t]+");
				if(tab[0].length() != 0) {
					try {
						ref = Integer.parseInt(tab[0]);
					}catch(NumberFormatException e) {
						System.out.println("Chargement nomenclature (lecture référence): " + e.getMessage());
						ref = -1;
					}
					denom = tab[1];
					try {
						poids = Float.parseFloat(tab[2]);
					}catch(NumberFormatException e) {
						System.out.println("Chargement nomenclature (lecture poids): " + e.getMessage());
						poids = -1.0f;
					}
					tab = scn.nextLine().split("[ \t]+");
					if(tab[1].equals("-1")) {
						try {
							p = new PieceDeBase(ref, denom, poids);
						} catch(IllegalArgumentException e) {
							p = null;
						}
					} else {
						try {
							p = new PieceComposite(ref, denom, poids);
						} catch(IllegalArgumentException e) {
							p = null;
						}
					}
					try {
						nomenclature.pushFromNomenclature(p);
					} catch(NullPointerException e) {
						System.out.println("Chargement nomenclature (ajouter poids): " + e.getMessage());
					}
				}
			}
			scn.close();
			scnAgain = new Scanner(fic);
			scnAgain.nextLine();
			while(scnAgain.hasNextLine()) {
				tab = scnAgain.nextLine().split("[ \t]+");
				try {
					ref = Integer.parseInt(tab[0]);
					p = nomenclature.get(ref);
				}catch(NumberFormatException e) {
					System.out.println("Chargement nomenclature (lecture ref): " + e.getMessage());
				}
				do {
					tab = scnAgain.nextLine().split("[ \t]+");
					try {
						ref = Integer.parseInt(tab[1]);
					}catch(NumberFormatException e) {
						System.out.println("Chargement nomenclature (lecture ref): " + e.getMessage());
						ref = -1;
					}
					if(ref != -1) {
						p1 = nomenclature.get(ref);
						try {
							nbOcc = Integer.parseInt(tab[2]);
							((PieceComposite) p).pushFromNomenclature(p1, nbOcc);
						} catch(NumberFormatException e) {
							System.out.println("Chargement nomenclature (lecture nbOcc): " + e.getMessage());
						}
					}
				} while(ref != -1);
			}
			fin = System.currentTimeMillis();
			scnAgain.close();
		} else
			scn.close();
		System.out.println("Fin chargement du fichier " + nomFichier + ".");
		System.out.println("Temps de chargement du fichier: " + (((float) (fin - debut)) / 1000) + "s");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int choix;
		boolean continuer = true;
		
		while(continuer) {
			choix = menu();
			switch(choix) {
				case 0: {
					continuer = false; 
					System.out.println("A bientôt!");
					break;
				} case 1: {
					nouvelleNomenclature();
					break;
				} case 2: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else 
						ajouterPieceSansCesComposants();
					break;
				} case 3: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else
						System.out.println(nomenclature);
					break;
				} case 4: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else 
						listePieceMemeComposant();
					break;
				} case 5: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else 
						ajouterComposantAUnePiece();
					break;
				} case 6: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else 
						descriptionPiece();
					break;
				} case 7: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else 
						supprimerPiece();
					break;
				} case 8: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else 
						nomenclature.trier();
					break;
				} case 9: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else 
						nomenclature.afficherPieceDeBase();
					break;
				} case 10: {
					if(nomenclature == null)
						System.out.println("Veuillez créer d'abord la nomenclature!");
					else
						sauvegarder();
					break;
				} case 11: {
					System.out.println("Saisir le nom du fichier: ");
					String nomFic = sc.next();
					try {
						charger(nomFic);
					} catch(FileNotFoundException e) {
						System.out.println(e.getMessage());
					}
					break;
				}
				default: {
					System.out.println("Choix non disponible!");
				}
			}
		}
	}
}
