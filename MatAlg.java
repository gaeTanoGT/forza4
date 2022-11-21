import java.util.Arrays;

public class MatAlg {

	static int[][] mat = new int[6][7];
	
	private int nF, nK = 0;
	private static int[] v = new int[3]; 	//rig, col, nK
	
	public MatAlg() {
		
	}
	
	public void stampaMat() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				System.out.print(mat[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	public static int getEnd(int col) {
		for(int i = 5; i >= 0; i--) {
			//System.out.println("rigg " + mat[i][col]);
			if(mat[i][col] == 0)
				return i;
		}
		return -1;
	}
	
	public static void setGettone(int rig, int col, boolean pl, int tipologia) {
		if(tipologia == 0) {
			if(!pl)
				mat[rig][col] = 1;
			else
				mat[rig][col] = 2;
		}else if(tipologia == 1) {
			if(!pl)
				mat[rig][col] = 1;
			/*else
				mat[rig][col] = 2;*/
		}
		
	}
	
	public static int getVincitore() {
		//righe
	    for(int i = 0; i < 6; i++)
	        for(int j = 0; j <= 3; j++)
	            if(mat[i][j] == mat[i][j + 1] && mat[i][j] == mat[i][j + 2] && mat[i][j] == mat[i][j + 3] && mat[i][j] != 0)
	            {
	                for(int u = 0; u < 4; u++)
	                    mat[i][j + u] += 2;     //player1 = 3; player2 = 4
	                return mat[i][j];
	            }

	    //colonne
	     for(int j = 0; j < 3; j++)
	        for(int i = 0; i < 7; i++)
	            if(mat[j][i] == mat[j + 1][i] && mat[j][i] == mat[j + 2][i] && mat[j][i] == mat[j + 3][i] && mat[j][i] != 0)
	            {
	                for(int u = 0; u < 4; u++)
	                    mat[j + u][i] += 2;     //player1 = 3; player2 = 4
	                return mat[j][i];
	            }

	    //diagonale ++
	    for(int i = 0; i <= 2; i++)
	        for(int j = 0; j <= 3; j++)
	            if(mat[i][j] == mat[i + 1][j + 1] && mat[i][j] == mat[i + 2][j + 2] && mat[i][j] == mat[i + 3][j + 3] && mat[i][j] != 0)
	            {
	                for(int u = 0; u < 4; u++)
	                    mat[i + u][j + u] += 2;     //player1 = 3; player2 = 4
	                return mat[i][j];
	            }

	    //diagonale -+
	    for(int i = 3; i <= 5; i++)
	        for(int j = 0; j <= 3; j++)
	            if(mat[i][j] == mat[i - 1][j + 1] && mat[i][j] == mat[i - 2][j + 2] && mat[i][j] == mat[i - 3][j + 3] && mat[i][j] != 0)
	            {
	                for(int u = 0; u < 4; u++)
	                    mat[i - u][j + u] += 2;     //player1 = 3; player2 = 4
	                return mat[i][j];
	            }

	    return 0;
	}

	public static boolean verificaEnd() {
		for(int i = 0; i < 6; i++)
	    {
	        for(int j = 0; j < 7; j++)
	        {
	            if(mat[i][j] == 0)
	                return false;
	        }
	    }
	    return true;
	}
	
	public static int getMat(int rig, int col) {
		return mat[rig][col];
	}
	
	public void clearMat() {
		for(int i = 0; i < 6; i++)
	        for(int j = 0; j < 7; j++)
	        	mat[i][j] = 0;
	}
	
	public static void simulaPareggio() {
		//mat[0][0] = 1;
		mat[1][0] = 2;
		mat[2][0] = 2;
		mat[3][0] = 2;
		mat[4][0] = 1;
		mat[5][0] = 1;
		mat[0][1] = 2;
		mat[1][1] = 2;
		mat[2][1] = 1;
		mat[3][1] = 2;
		mat[4][1] = 1;
		mat[5][1] = 2;
		mat[0][2] = 2;
		mat[1][2] = 1;
		mat[2][2] = 2;
		mat[3][2] = 2;
		mat[4][2] = 1;
		mat[5][2] = 2;
		mat[0][3] = 1;
		mat[1][3] = 1;
		mat[2][3] = 2;
		mat[3][3] = 1;
		mat[4][3] = 2;
		mat[5][3] = 1;
		mat[0][4] = 2;
		mat[1][4] = 1;
		mat[2][4] = 2;
		mat[3][4] = 1;
		mat[4][4] = 2;
		mat[5][4] = 1;
		mat[0][5] = 1;
		mat[1][5] = 2;
		mat[2][5] = 1;
		mat[3][5] = 2;
		mat[4][5] = 1;
		mat[5][5] = 2;
		mat[0][6] = 1;
		mat[1][6] = 1;
		mat[2][6] = 1;
		mat[3][6] = 2;
		mat[4][6] = 1;
		mat[5][6] = 2;
		
		FrameGriglia.aggiornaMat();
	}
	
	public int mossaBot(int liv) {
		System.out.println(liv + "\t" + nK + "\t" + nF);
		Jni c = new Jni();
		v = c.getMossaBot(liv, mat, nK, nF);
		
		System.err.println(Arrays.toString(v));
		
		if(v[0] == -1) {
			v[0] = getEnd(v[1]);
			mat[v[0]][v[1]] = 2;
		}else {
			mat[v[0]][v[1]] = 2;
		}
		stampaMat();
		//System.out.println("v2 "+v[2]);
		
		if(v[2] > nK)
			nK = v[2];
		
	    System.out.println("nK: " + nK + "\tnF: " + nF);
		
	    return v[1];
	}
	
	public void setNf(int liv) {
		nK = 0;
		switch (liv) {
		case 0: 
			nF = (int)(Math.random() * 3);		//0 - 2
			break;
		case 1:
			nF = (int)(Math.random() * 3) + 2;	//2 - 4
			break;
		case 2:
			nF = (int)(Math.random() * 3) + 2;
			break;
		case 3:
			nF = -2;
			break;
		}
	}
	
	public static int getVrig(){
		return v[0];
	}
	
}
