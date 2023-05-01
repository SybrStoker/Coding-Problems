public class Main{
	public static void main(String[] args) {
		Generator passGen = new Generator();

		passGen.setConfigurations((byte)10, true, true, true, true);
		System.out.println(passGen.getPassword());
	}
}