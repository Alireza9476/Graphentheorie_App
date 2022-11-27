package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WegMatrix {
	
    public static ArrayList<int[]> komponenten;
    public static ArrayList<int[]> komponentenEinzeln;
    
	private int[][] wegMatrix;
	private int n;
	
	static int k;
	static int v;
	static int count;
	int[][] adjazentMatrix;
	
	public static String komponente;
	public static String artikulationen;
	public static String bruecken;
	
	public static String allResults;
	  
	////////////////////////////////Konstruktor/////////////////////////////////
	
	public WegMatrix(int n) {
		allResults = "";
		bruecken = "";
	    this.n = DistanzMatrixBerechnung.n;
	    
	    k = 1;
		wegMatrix = new int[n][n];
	}
	
	////////////////////////////////getter//////////////////////////////////////
	
	public int[][] getWegMatrix() {
		return wegMatrix.clone();		
	}
	
	public String getWegMatrixContent(int [][] m) {
		String str = "";
		int n = m.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(i == j) 
					str += "(" + m[i][j] + ")  ";
				else if (m[i][j] > 9)
					str += m[i][j] + " ";
				else
					str += m[i][j] + "  ";
			}
			str += "\n";
		}
		str += "\n";
		return str;
	}

	////////////////////////////////berechneWegMatrix///////////////////////////////
	
	public void berechneWegMatrix(int [][] matrix, boolean setDiagonale1) {	
		int[][] temp = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
					if(setDiagonale1) {
							if (i == j) wegMatrix[i][j] = 1;
							else wegMatrix[i][j] = matrix[i][j];
						}
					else {
						if (!(i==j) && matrix[i][j] != 0) temp[i][j] = 1;
						else temp[i][j] = wegMatrix[i][j];
					}
				}
		}
		if(!setDiagonale1) {		//temp soll nicht beim Initialisierung der Diagonale eingreifen
			wegMatrix = temp;
			k++;
		}
	}
	
	
	public static boolean pruefeAufWegMatrix1(int [][] wegMatrix) {
		int n = wegMatrix.length;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(wegMatrix[i][j] == 0)
					return true;
			}
		}
		return false;
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	public String Komponente() {
		komponenten = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			int[] reihe = getReihe(wegMatrix, i);
			//for(int l = 0; l < wegMatrix.length; l++) {
            //    System.out.println("l : " + l + "wegMatrixRow " + reihe[l]);
            //}
			for(int j = 0; j < n; j++) {
				if(reihe[j] == 1)							//Wenn eine Zahl gefunden wurde, dann ist bekannt, dass es sich hierbei um eine Verbindung handelt
					reihe[j] = j + 1;						//+1, weil Startknoten 1
			}
			//bevor die 2.Schleife endet, muss überprüft werden, ob die Reihe bereits in der erstellten Komponente existiert, wenn nicht, adde ich sie zu meiner Komponente hinzu 
			if(!containsInList(komponenten,reihe)) {
				komponenten.add(reihe);
				//komponentenZaehler++;		//Wichtig für Artikkulationen und Brücken
			}
		}
		
        String result = "";
        for (int i = 0; i < komponenten.size(); i++) {
            int komponente = i + 1;
            result = result + komponente + " " + getRowToString(komponenten.get(i)).replace(", ]", " ]") + "\n";
        }
		return result;
	}
	
    public static String getRowToString(int[] row) {
        String result = "[ ";
        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0)
                result = result + row[i] + ", ";
        }
        result = result + "]";
        return result;
    }
	
    public boolean containsInList(List<int[]> list, int[] rowParam) {
        for (int[] row : list) {
            if (Arrays.equals(row, rowParam)) {
                return true;
            }
        }
        return false;
    }

    public int[] getReihe(int[][] wegMatrix, int row) {
        int n = wegMatrix.length;
    	int[] extractRow = new int[n];
        for (int col = 0; col < n; col++) {
            extractRow[col] = wegMatrix[row][col];
        }
        return extractRow;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    public void KomponenteEinzeln() {
    	komponentenEinzeln = new ArrayList<>();
    	int[][] adjazenz = DistanzMatrixBerechnung.adjazenz;

    	int k = 0;
    	int[] reihenArray = new int[1000];
    	
    	for(int i = 0; i < n; i++) {
    		int[] reihe = getReihe(adjazenz, i);	
    		for(int j = 0; j < n; j++) {
    			if(reihe[j] == 1) {
    				reihenArray[k] = i+1;
    				reihenArray[k+1] = j+1;
    				komponentenEinzeln.add(reihenArray);
    				k = k + 2;
    			}
    			reihenArray = new int[1000];
    		}
    	}
    	String result = "";
        for (int i = 0; i < komponentenEinzeln.size(); i++) {
            result = result + " " + getRowToString(komponentenEinzeln.get(i)).replace(", ]", " ]") + "\n";
        }
        System.out.println(result);
    }
    
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void getArtikulation() {
    	int edgeLength = 0;
        for(int i = 0;  i < komponentenEinzeln.size(); i++) {
			for(int j = 0; j < komponentenEinzeln.get(i).length - 1; j++) {
				if(komponentenEinzeln.get(i)[j] != 0 && komponentenEinzeln.get(i)[j+1] != 0)
					edgeLength++;
			}
		}
        
    	Artikulation g1 = new Artikulation(edgeLength);
    	System.out.println("komponentenEinzeln.size(): " + komponentenEinzeln.size());
    	for(int i = 0;  i < komponentenEinzeln.size(); i++) {
			for(int j = 0; j < komponentenEinzeln.get(i).length - 1; j++) {
				if(komponentenEinzeln.get(i)[j] != 0 && komponentenEinzeln.get(i)[j+1] != 0)
					g1.addEdge(komponentenEinzeln.get(i)[j], komponentenEinzeln.get(i)[j+1]);
					//System.out.println("Added1: " + komponentenEinzeln.get(i)[j]);
					//System.out.println("Added2: " + komponentenEinzeln.get(i)[j+1]);
			}
			//System.out.println("\n");
		}
    	
    	artikulationen = g1.getArtikulationen();
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void getBruecken() {
    	Bruecken solver = new Bruecken(komponentenEinzeln.size());
    	
    	for(int i = 0;  i < komponentenEinzeln.size(); i++) {
			for(int j = 0; j < komponentenEinzeln.get(i).length - 1; j++) {
				if(komponentenEinzeln.get(i)[j] != 0 && komponentenEinzeln.get(i)[j+1] != 0)
					solver.addEdge(komponentenEinzeln.get(i)[j], komponentenEinzeln.get(i)[j+1]);
					//System.out.println("Added1: " + komponentenEinzeln.get(i)[j]);
					//System.out.println("Added2: " + komponentenEinzeln.get(i)[j+1]);
			}
			//System.out.println("\n");
		}
    	
        List<Integer> bridges = solver.findBridges();

        System.out.println("BridgeSize:" + bridges.size());
        System.out.println(bridges.toString());
        for (int i = 0; i < bridges.size() / 2; i++) {
          int node1 = bridges.get(2 * i);
          System.out.println("2*i: " + 2*i);
          int node2 = bridges.get(2 * i + 1);
          //System.out.print("{"+ node1 + "," + node2 + "}");
          bruecken += "{"+ node1 + "," + node2 + "}";
          if(i < bridges.size() / 2 - 1)
        	  bruecken += ",";
        	  
        }
    }
	///////////////////////////////////////////////////////////////////////////////
	
    //Artikulationen erkennt man durch ihre Fähigeit, die Komponentenanzahl zu ändern, wenn ich ein Knoten lösche, und sie dadurch
    //zwei Komponenten erzeugt, wo davor nur eine war, ist das eine Artikulation
    //Wenn ich das ausführe, ändert sich meine Adjzazentmatrix
	
    //Ob diese Methode verbessert werde kann, ist eine Frage für sich.
    //Solange die Methode einwandfrei funktioniert und diese einigermaßen zu verstehen ist, gibt es kein Verbesserungsbedarf.
    public void wegMatrix(boolean artikulation) {
		Matrix adjazenz;
    	if(artikulation)
    		adjazenz = new Matrix(adjazentMatrix);
		else {
			adjazenz = new Matrix();		
		}
		Matrix matrix1 = new Matrix();
		WegMatrix wegMatrix = new WegMatrix(n);
		
		allResults += "Wegmatrix\n";
		//allResults += "A1(G)\n" + adjazenz.getMatrixContent() + "\n";
		wegMatrix.berechneWegMatrix(adjazenz.getMatrix(), true);
		//allResults += "W1(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()) + "\n\n";
		
		Matrix matrix2 = new Matrix(adjazenz.getMatrix());
		matrix2.multiply(matrix1.getMatrix());	
		//allResults += "A2(G)\n" + matrix2.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix2.getMatrix(), false);
		//allResults += "W2(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
				
		Matrix matrix3 = new Matrix(matrix2.getMatrix());
		matrix3.multiply(matrix1.getMatrix());	
		//allResults += "A3(G)\n" + matrix3.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix3.getMatrix(), false);
		//allResults += "W3(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
		
		Matrix matrix4 = new Matrix(matrix2.getMatrix());
		matrix4.multiply(matrix2.getMatrix());
		//allResults += "A4(G)\n" + matrix4.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix4.getMatrix(), false);
		//allResults += "W4(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
		
		Matrix matrix5 = new Matrix(matrix1.getMatrix());
		matrix5.multiply(matrix4.getMatrix());
		//allResults += "A5(G)\n" + matrix5.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix5.getMatrix(), false);
		//allResults += "W5(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
	
		Matrix matrix6 = new Matrix(matrix1.getMatrix());
		matrix6.multiply(matrix5.getMatrix());
		//allResults += "A6(G)\n" + matrix6.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix6.getMatrix(), false);
		//allResults += "W6(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
		
		Matrix matrix7 = new Matrix(matrix1.getMatrix());
		matrix7.multiply(matrix6.getMatrix());
		//allResults += "A7(G)\n" + matrix7.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix7.getMatrix(), false);
		//allResults += "W7(WWG)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
		
		Matrix matrix8 = new Matrix(matrix1.getMatrix());
		matrix8.multiply(matrix7.getMatrix());
		//allResults += "A8(G)\n" + matrix8.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix8.getMatrix(), false);
		//allResults += "W8(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
	
		Matrix matrix9 = new Matrix(matrix1.getMatrix());
		matrix9.multiply(matrix8.getMatrix());
		//allResults += "A9(G)\n" + matrix9.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix9.getMatrix(), false);
		//allResults += "W9(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
		
		Matrix matrix10 = new Matrix(matrix1.getMatrix());
		matrix10.multiply(matrix10.getMatrix());
		//allResults += "A10(G)\n" + matrix10.getMatrixContent();
		wegMatrix.berechneWegMatrix(matrix10.getMatrix(), false);
		allResults += "W10(G)\n" + wegMatrix.getWegMatrixContent(wegMatrix.getWegMatrix()); 
		
		this.wegMatrix = wegMatrix.getWegMatrix();

		komponente = wegMatrix.Komponente();
		KomponenteEinzeln();
		getArtikulation();
		getBruecken();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /*
	public boolean pruefeWeg(int [][]wg1, int[][] wg2) {
		int n = wg1.length;
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++) {	//deepEquals = vergleicht, ob beide die gleichen Werte enthalten
				//System.out.println(Arrays.deepEquals(dm1, dm2));
				//System.out.println(k == n);
				//System.out.println(!dm1[i][j].contains(infinite));
				
				if(wg1[i][j] == 0 && !(k==n) && !Arrays.deepEquals(wg1, wg2))
					return true;
				//if(Arrays.deepEquals(dm1, dm2) || (k == n))
				//	return false;
			}
		}
		return false;
	}
	*/
    
	
	
	
}
