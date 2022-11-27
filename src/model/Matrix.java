package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import GUI.RootGridPane;

public class Matrix {
	
	private int [][] matrix;
	public static String path;
	private int n;
	
	/////////////////////////////////Konstruktor/////////////////////////////////////////
	
	public Matrix() {
		//setN(line_length());
		n = RootGridPane.getN();
		matrix = new int[n][n];
		csv_lesen();
	}
	
	public Matrix(int[][]matrix){
		this.matrix = matrix;
		n = RootGridPane.getN();
	}
	
	//////////////////////////////////Setter////////////////////////////////////////
	
	public void setMatrix(int[][]matrix) {
		this.matrix = matrix;
	}
	
	public static void setPath(String path) {
		Matrix.path = path;
	}
	/////////////////////////////////Getter//////////////////////////////////////////
	
	public int[][] getMatrix(){
		return matrix;
	}
	
	public String getMatrixContent() {
		String str = "";
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] > 9) str += matrix[i][j] + " ";
				else str += matrix[i][j] + "   ";
			}
			str += "\n";
		}
		str += "\n";
		return str;
	}
	
	
	public String getMatrixContent(int [][] m) {
		String str = "";
		int n = m.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (m[i][j] > 9)
					str += m[i][j] + " ";
				else
					str += m[i][j] + "  ";
			}
			str += "\n";
		}
		str += "\n";
		return str;
	}
	
	/////////////////////////////////Hilfsmethoden//////////////////////////////////////
	
	public void csv_lesen(){
		Scanner sc = null;
		try {
			 sc = new Scanner(new BufferedReader(new FileReader(path)));
			 while(sc.hasNextLine()) {
				for (int i = 0; i < matrix.length; i++) {
					String[] line = sc.nextLine().trim().split(";");
				    for (int j = 0; j < line.length; j++) {
				        matrix[i][j] = Integer.parseInt(line[j]);
				           }
				       }
				}
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
	}

	//////////////////////////////////////////////////////////////////////////////////
	
	public int[][] multiply(){
		int [][] c = new int[n][n];
		if (matrix != null) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						 c[i][j] += matrix[i][k] * matrix[k][j];
						}
					}
			}
		}
		return c;
	}
	
	public int[][] multiply(int a[][], int b[][], boolean okey){
		int [][] c = new int[n][n];
		if (matrix != null) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						if(!okey)
							c[i][j] += a[i][k] * b[k][j];
						else
							c[i][j] += matrix[i][k] * matrix[k][j];
					}
				}
			}
		}
		return c;
	}
	
	public void multiply(int a[][]){
		int [][] c = new int[n][n];
		if (matrix != null) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						 c[i][j] += matrix[i][k] * a[k][j]; //Instanzvariable matrix
					}
				}
			}
		}
		matrix = c;
	}

	
	//////////////////////////////////////////////////////////////////////////////////
	
}
