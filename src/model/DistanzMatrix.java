package model;

import java.util.ArrayList;

public class DistanzMatrix {

	static String infinite = "\u221E";
	static int k;
	
	public DistanzMatrix() {
		k = 1;
	}
	
	//////////////distanzMatrix/////////////////////////////////////////////////////

	public String[][] distanzMatrix(int [][] m0, String [][]distanz) {
		//pruefeAufInfinite(m1);
		String[][] c = distanz;
		
		int n = m0.length;
		String [][] matrix = arrIntoString(m0);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
					if (!(i == j)){
						if (distanz[i][j].contains(infinite) && !(matrix[i][j].contains("0"))) {
							c[i][j] = Integer.toString(k);
							System.out.println("k: " + k);
						}
						else c[i][j] = distanz[i][j];
					}
				}
			}
		k++;
		return c;		 
	}
	
	public String[][] distanzMatrix(int [][] m0) {
		int n = m0.length;
		String [][] matrix0 = arrIntoString(m0);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
					if (!(i == j)){
						if (matrix0[i][j].contains("0")) matrix0[i][j] = infinite;
					}
				}
			}
		k++;
		return matrix0;		 
	}
	
	/////////////Hilfsklassen///////////////////////////////////
	
	public String[][] arrIntoString(int [][] matrix) {
		int n = matrix.length;
	    String[][] board = new String[n][n];

	    for(int i = 0 ; i < n ; i++){
	        for(int j = 0 ; j < n ; j++){
	            	board[i][j] = Integer.toString(matrix[i][j]);
	        	}
	    	}
	    	return board;
	    }
	
	
	//dm1 = das Ergebnis, dm2 = das Ergebnis davor
	public boolean pruefeDistanz(String [][]dm1) {
		int n = dm1.length;
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++) {
				//System.out.println(Arrays.deepEquals(dm1, dm2));
				//System.out.println(k == n);
				//System.out.println(!dm1[i][j].contains(infinite));
				
				if(dm1[i][j].contains(infinite) && !(k==n))
					return true;
				//if(Arrays.deepEquals(dm1, dm2) || (k == n))
				//	return false;
			}
		}
		return false;
	}
	
	public boolean prufeAufUnendlich(String [][]dm1) {
		int n = dm1.length;
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++) {
				//System.out.println(Arrays.deepEquals(dm1, dm2));
				//System.out.println(k == n);
				//System.out.println(!dm1[i][j].contains(infinite));
				if(dm1[i][j].contains(infinite))
					return false;
				//if(Arrays.deepEquals(dm1, dm2) || (k == n))
				//	return false;
			}
		}
		return true;
	}
	
	///////////////////////////////////////get()/////////////
	
	public String getStringArray(String[][] matrix) {
		String str = "";
		int n = matrix.length;
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++) {
					if(i == j) 
						str += "(" + matrix[i][j] + ")  ";
					else if(matrix[i][j] == infinite) str += matrix[i][j] + "  ";
					else str += matrix[i][j] + "   ";
					}
				str += "\n";
				}
		str += "\n";
		return str;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<Integer> getExzentrizitaet(String[][] Exzentrizitaet) {
		int n = Exzentrizitaet.length;
		int max = 0;
		ArrayList<Integer> ergebnis = new ArrayList<Integer>();
		int[][] c = new int[n][n];
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++) {
					if(Exzentrizitaet[i][j] == "\\\\u221E")
						c[i][j] = 0;
					else c[i][j] = Integer.parseInt(Exzentrizitaet[i][j]);
					if(c[i][j] > max) max = c[i][j];
				}
				ergebnis.add(max);
				max = 0;
			}
		return ergebnis;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	public int getRadius(ArrayList<Integer> Exzentrizitaet) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < Exzentrizitaet.size(); i++){
				if (Exzentrizitaet.get(i) < min) {
					min = Exzentrizitaet.get(i);
				}
			}
		return min;
	}
		
	public ArrayList<Integer> getZentrum (ArrayList<Integer> Exzentrizitaet) {
		int n = Exzentrizitaet.size();
		int min = getRadius(Exzentrizitaet);
		
		ArrayList<Integer> zentrum = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (Exzentrizitaet.get(i) == min)
				zentrum.add(i + 1);
		}
		
		return zentrum;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	public int getDurchmesser(ArrayList<Integer> Exzentrizitaet) {
		int max = 0;
		for (int i = 0; i < Exzentrizitaet.size(); i++){
				if (Exzentrizitaet.get(i) > max) {
					max = Exzentrizitaet.get(i);
				}
			}
		return max;
	}
	
}
