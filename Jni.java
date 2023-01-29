public class Jni {
	
	static {
		System.load("C:\\Users\\gaeta\\Documents\\java\\p1v8\\bin\\Dll8.dll");
	}
	
	public native int[] getMossaBot(int liv, int mat[][], int nK, int nF);	//rig - col - nK
	public native String stampami();
}
