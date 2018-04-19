package fr.icam.tests;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class DataGen {

	private final Random random = new Random();
	
	public void doGenerate(OutputStream output) throws Exception {
		int length = 10000;
		int size = 5; 
		StringBuilder builder = new StringBuilder(length * size * 5);
		builder.append(length);
		builder.append(",");
		builder.append(size);
		for (int i = 0; i < length; i++) {
			builder.append("\n");
			builder.append(i);
			for (int j = 0; j < size; j++) {
				builder.append(",");
				builder.append(random.nextDouble());
			}
		}
		output.write(builder.toString().getBytes());
	}

	public static void main(String[] args) throws Exception {
		OutputStream output = new FileOutputStream("/home/jerome/Bureau/test.csv");
		new DataGen().doGenerate(output);
		output.close();
	}
	
}
