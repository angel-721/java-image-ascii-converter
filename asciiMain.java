import java.util.Scanner;
import java.io.File;
public class asciiMain{
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		System.out.print("Enter a image file: ");
		String fileName = userInput.nextLine();
		asciiImage test = new asciiImage();
		File testFile = new File(fileName);
		test.createAsciiImage(testFile);
		userInput.close();
	}
}
