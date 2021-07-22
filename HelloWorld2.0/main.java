import edu.duke.*;

public class main {
	public static void main(String[] args) {
		FileResource res = new FileResource("hello_unicode.txt");
		for (String line : res.lines()) {
			System.out.println(line);
		}
	}
}
