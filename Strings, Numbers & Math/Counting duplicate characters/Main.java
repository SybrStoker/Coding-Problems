/*BRIEF
Write a programm that counts duplicate chatacters from a given string*/

/*Solution explained

Since we working with strings then it mustn't always be a word.
It can be a phrase of any size or even a text. It can have any amount of characters
because amount of characters isn't limited to amount of letters in the alphabet.
A charcter can be a letter, a special symbol or an emoji.

Im gonna make a mirror collections. First ArrayList will hold the characters
and the second one will hold amount of the charcters in the string.
Collection suites this task pretty well in my opinion.
First of all collections are dynamic, so i can change values of second ArrayList
in real time. Also im "not limited" with amount of potential characters.*/

import java.util.*;

public class Main{
	public static void main(String[] args) {
		if(args[0].isBlank()){
			System.out.println("No argument was passed");
			System.exit(0);
		}

		ArrayList<Character> characterList = getEveryCharacter(args[0]);
		List<Integer> amountOfDuplicateslist = countDuplicatesOf(characterList, args[0]);

		printDuplicates(characterList, amountOfDuplicateslist);
		
	}

	public static ArrayList<Character> getEveryCharacter(String text){
		ArrayList<Character> allCharacters = new ArrayList<>();
		char letter;

		for(int i = 0; i < text.length(); i++){
			letter = text.charAt(i);
			if(!allCharacters.contains(letter)){
				allCharacters.add(letter);
			}
		}
		return allCharacters;
	}

	public static List<Integer> countDuplicatesOf(ArrayList<Character> charctersOfText, String text){
		List<Integer> amountOfDuplicates = new ArrayList<Integer>(Collections.nCopies(charctersOfText.size(), 0));
		char letter;

		for(int i = 0; i < text.length(); i++){
			letter = text.charAt(i);
			amountOfDuplicates.set(charctersOfText.indexOf(letter), amountOfDuplicates.get(charctersOfText.indexOf(letter)) + 1);
		}

		return amountOfDuplicates;
	}

	public static void printDuplicates(ArrayList<Character> charctersOfText, List<Integer> amountOfDuplicates){
		for(int i = 0; i < charctersOfText.size(); i++){
			if(charctersOfText.get(i) == ' '){
				continue;
			}

			if(amountOfDuplicates.get(i) == 1){
				continue;
			}

			System.out.println("\"" + charctersOfText.get(i) + "\"" + " - " + amountOfDuplicates.get(i));
		}
	}
}