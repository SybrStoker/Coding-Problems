import java.util.Random;
import java.util.ArrayList;

public class Generator{
	private byte passLength;
	private boolean letters;
	private boolean capLetters;
	private boolean numbers;
	private boolean sChars; //speacial characters

	private char[] lettersA = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
					   				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
					   				's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	private char[] numbersA = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private char[] sCharsA = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-',
									'_', '=', '+', '/', '\\', '|', '[', ']', '{', '}', '\'',
									'\"', ';', ':', ',', '.', '<', '>', '?', '`', '~'};

	public void setConfigurations(byte passLength, boolean letters,
			boolean capLetters, boolean numbers, boolean sChars){
		this.passLength = passLength;
		this.letters = letters;
		this.capLetters = capLetters;
		this.numbers = numbers;
		this.sChars = sChars;
	}

	private char pickAcharacter(){
		Random genNum = new Random();
		byte choice = (byte) genNum.nextInt(4);
		char character = ' '; //cause all assingments are in switch statment compiler doesn't allow me to leave the var unsigned.

		switch(choice){
			case (byte) 0:
				character = this.lettersA[genNum.nextInt(this.lettersA.length)];
				break;
			case (byte) 1:
				character = Character.toUpperCase(character = this.lettersA[genNum.nextInt(this.lettersA.length)]);
				break;
			case (byte) 2:
				character = this.numbersA[genNum.nextInt(this.numbersA.length)];
				break;
			case (byte) 3:
				character = this.sCharsA[genNum.nextInt(this.sCharsA.length)];
				break;
			default:
				System.out.println("Failed to pick a character.");
		}

		return character;
	}


	public String getPassword(){
		StringBuilder password = new StringBuilder();

		for(int i = 0; i < passLength; i++){
			password.append(pickAcharacter());
		}

		return password.toString();
	}
	



}