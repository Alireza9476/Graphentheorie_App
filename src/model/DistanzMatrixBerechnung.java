package model;

import java.io.IOException;
import java.util.ArrayList;

import GUI.RootGridPane;


public class DistanzMatrixBerechnung {

	public static ArrayList<Integer> exzentrizitaet = new ArrayList<Integer>();
	public static ArrayList<Integer> zentrum = new ArrayList<Integer>();
	public static int radius;
	public static int durchmesser;
	public static String allResults;
	public static int n;
	
	public static int[][] adjazenz;
	
	public static void DistanzBerechnung() throws IOException {			
		
		allResults = "DistanzMatrix\n";
		Matrix matrix = new Matrix();
		DistanzMatrix dmObj = new DistanzMatrix();
		adjazenz = matrix.getMatrix();
		
		n = RootGridPane.getN();
		
		int [][] m1 = matrix.getMatrix(), m2 = m1.clone(), m3 = m1.clone();	//clone damit die Refernezen der jeweilig andere nicht verändert werden.
		String [][] dm1 = new String[n][n], dm2 = dm1.clone(),dm3 = dm1.clone();
		
		int i = 1;		
		do {
			if (i == 1) {
				//System.out.println(i);
				dm1 = dmObj.distanzMatrix(m1);			//Bei der ersten Durchlauf wird nicht multipliziert.
				m3 = m1.clone(); dm3 = dm1.clone();
				//System.out.println(matrix.getMatrixContent(m3));
				
			}
			else if (i%2 == 0){								//Die Ergebnisse der letzten Durchlauf muss gespeichert werden, deshalb auch m1/2 und dm1/2
				//System.out.println(i + "bin do");
				//System.out.println(matrix.getMatrixContent(m1));
				//System.out.println(matrix.getMatrixContent(m2));
				
				//m2 = m1*m1
				//m3 = m2*m1
				//m4 -> m2*m2 
				//m5 -> m4*m1
				//m6 -> m4*m2 oder m5*m1 (besser)
				
				if (i==2) m2 = matrix.multiply(m1,m1, false);	//Bei der zweiten Durchlauf wird nur der Matrix mit sich multipliziert, gilt nur für's 2.Schritt bzw. Durchlauf
				else{
					//System.out.println("Alles klar");
					m2 = matrix.multiply(m2,m2, false);
					//System.out.println(matrix.getMatrixContent(m2));
				}
				dm2 = dmObj.distanzMatrix(m2,dm1);
				m3 = m2.clone(); dm3 = dm1.clone();
				//System.out.println(matrix.getMatrixContent(m3));
			}
			else{
				//System.out.println(i);
				m1 = matrix.getMatrix();
				//System.out.println(matrix.getMatrixContent(m1));
				//System.out.println(matrix.getMatrixContent(m2));
				m1 = matrix.multiply(m1,m2, false);
				dm1 = dmObj.distanzMatrix(m1,dm2);
				m3 = m1.clone(); dm3 = dm1.clone();
				//System.out.println(matrix.getMatrixContent(m3));
			}
			System.out.println("A" + i + "(G)\n\" + matrix.getMatrixContent(m3)");
			//allResults += "A" + i + "(G)\n" + matrix.getMatrixContent(m3) + "\n";
			//allResults += "D" + i + "(G)\n" + dmObj.getStringArray(dm3) + "-------------------------\n";
			i++;
		}while(dmObj.pruefeDistanz(dm3));	//Wenn keine Unendlichzeichen in der Distanzmatrix zu finden ist.
		
		allResults += "D" + i + "(G)\n" + dmObj.getStringArray(dm3) + "-------------------------\n";
		allResults += "-------------------------\n";
			
		if(dmObj.prufeAufUnendlich(dm3)) {
			exzentrizitaet = dmObj.getExzentrizitaet(dm3);
			zentrum = dmObj.getZentrum(exzentrizitaet);
			radius = dmObj.getRadius(exzentrizitaet);
			durchmesser = dmObj.getDurchmesser(exzentrizitaet);					
		}
		else {
			exzentrizitaet = new ArrayList<Integer>();
		}
	}	
}

