
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String x = "3,4*demo";
		
		String[] parts = x.split("\\*");
		String ids = parts[0]; 
		String project= parts[1]; 
		System.out.println("if ids :" + ids);
		System.out.println("if project :" + project);
	}

}
