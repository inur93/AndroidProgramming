package weeklyprojects.pop.dk.week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by Runi on 08-09-2016.
 */
public class HangmanController {

    private static ArrayList<String> possibleWords = new ArrayList<String>();
    private String word;
    private ArrayList<String> usedLetters = new ArrayList<String>();
    private String visibleWord;
    private int noWrongLetters;
    private boolean lastLetterWasCorrect;
    private boolean hasWon;
    private boolean hasLost;

    public HangmanController(){

        reset();
    }

    public ArrayList<String> getUsedLetters() {
        return usedLetters;
    }

    public String getVisibleWord() {
        return visibleWord;
    }

    public String getWord() {
        return word;
    }

    public int getNumberWrongLetters() {
        return noWrongLetters;
    }

    public boolean isLastLetterCorrect() {
        return lastLetterWasCorrect;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public boolean isGameOver() {
        return hasLost || hasWon;
    }

    public void reset() {
        usedLetters.clear();
        noWrongLetters = 0;
        hasWon = false;
        hasLost = false;
        word = possibleWords.get(new Random().nextInt(possibleWords.size()));
        updateVisibleWord();
    }


    private void updateVisibleWord() {
        visibleWord = "";
        hasWon = true;
        for (int n = 0; n < word.length(); n++) {
            String bogstav = word.substring(n, n + 1);
            if (usedLetters.contains(bogstav)) {
                visibleWord = visibleWord + bogstav;
            } else {
                visibleWord = visibleWord + "*";
                hasWon = false;
            }
        }
    }

    public void guessLetter(String letter) {
        if (letter.length() != 1) return;
        System.out.println("Der gættes på bogstavet: " + letter);
        if (usedLetters.contains(letter)) return;
        if (hasWon || hasLost) return;

        usedLetters.add(letter);

        if (word.contains(letter)) {
            lastLetterWasCorrect = true;
            System.out.println("Bogstavet var korrekt: " + letter);
        } else {
            // Vi gættede på et bogstav der ikke var i word.
            lastLetterWasCorrect = false;
            System.out.println("Bogstavet var IKKE korrekt: " + letter);
            noWrongLetters = noWrongLetters + 1;
            if (noWrongLetters > 6) {
                hasLost = true;
            }
        }
        updateVisibleWord();
    }

    public void logStatus() {
        System.out.println("---------- ");
        System.out.println("- word (skult) = " + word);
        System.out.println("- visibleWord = " + visibleWord);
        System.out.println("- forkerteBogstaver = " + noWrongLetters);
        System.out.println("- brugeBogstaver = " + usedLetters);
        if (hasLost) System.out.println("- SPILLET ER TABT");
        if (hasWon) System.out.println("- SPILLET ER VUNDET");
        System.out.println("---------- ");
    }


    public static String getUrl(String url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

    public static List<String> getPossibleWords(){
        if(possibleWords.isEmpty()){
            possibleWords.add("bil");
            possibleWords.add("computer");
            possibleWords.add("programmering");
            possibleWords.add("motorvej");
            possibleWords.add("busrute");
            possibleWords.add("gangsti");
            possibleWords.add("skovsnegl");
            possibleWords.add("solsort");
        }
        return possibleWords;
    }

    public void getWordFromDR() throws Exception {
        String data = getUrl("http://dr.dk");
        System.out.println("data = " + data);

        data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
        System.out.println("data = " + data);
        possibleWords.clear();
        possibleWords.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

        System.out.println("possibleWords = " + possibleWords);
        reset();
    }
}
