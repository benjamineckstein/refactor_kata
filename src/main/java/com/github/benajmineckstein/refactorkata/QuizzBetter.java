package com.github.benajmineckstein.refactorkata;

import java.util.ArrayList;
import java.util.List;


class Player{
    private final String name;

    private final int place;

    public Player(String playerName) {
        this.name = playerName;
        place = 0;
    }

    public String getName() {
        return name;
    }
}
/**
 * Want to make this refactoring more interesting? Add one or more challenges:
 * 1. Speed Challenge: Do not use your mouse at all
 * 2. Surgeon Challenge: No breaking changes. Only baby steps are allowed and tests must pass after each step.
 * 3. Focus Challenge: Explain what your goal is and why this is good before doing it.
 * 4. Precision Challenge: You are only allowed to speak, someone else has to do it.
 * 5. Delegate Challenge: Let your IDE handle most refactorings. Changing variable names, extracting or inlining methods, inverting ifs, etc ...
 * 6. Bonus Challenge: Refactor in a way that you do not use a single if statement
 */
public class QuizzBetter implements IQuizz {
    List<Player> players = new ArrayList<>();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    List<String> christmasQuestions = new ArrayList<>();
    List<String> newyearQuestions = new ArrayList<>();
    List<String> holidayQuestions = new ArrayList<>();
    List<String> winterMusicQuestions = new ArrayList<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public QuizzBetter() {
        for (int i = 0; i < 50; i++) {
            christmasQuestions.add("Christmas Question " + i);
            newyearQuestions.add(("New Year Question " + i));
            holidayQuestions.add(("Holiday Question " + i));
            winterMusicQuestions.add(createWinterMusicQuestion(i));
        }
    }

    public String createWinterMusicQuestion(int index) {
        return "Winter Music Question " + index;
    }

    public boolean add(String playerName) {

        players.add(new Player(playerName));
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(getCurrentPlayerName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(getCurrentPlayerName() + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                System.out.println(getCurrentPlayerName()
                        + "'s new location is "
                        + places[currentPlayer]);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(getCurrentPlayerName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(getCurrentPlayerName()
                    + "'s new location is "
                    + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if ("Christmax".equals(currentCategory()))
            System.out.println(christmasQuestions.remove(0));
        if ("NewYear".equals(currentCategory()))
            System.out.println(newyearQuestions.remove(0));
        if ("Holiday".equals(currentCategory()))
            System.out.println(holidayQuestions.remove(0));
        if ("WinterMusic".equals(currentCategory()))
            System.out.println(winterMusicQuestions.remove(0));
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Christmax";
        if (places[currentPlayer] == 4) return "Christmax";
        if (places[currentPlayer] == 8) return "Christmax";
        if (places[currentPlayer] == 1) return "NewYear";
        if (places[currentPlayer] == 5) return "NewYear";
        if (places[currentPlayer] == 9) return "NewYear";
        if (places[currentPlayer] == 2) return "Holiday";
        if (places[currentPlayer] == 6) return "Holiday";
        if (places[currentPlayer] == 10) return "Holiday";
        return "WinterMusic";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                System.out.println(getCurrentPlayerName()
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == howManyPlayers()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == howManyPlayers()) currentPlayer = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(getCurrentPlayerName()
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == howManyPlayers()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(getCurrentPlayerName() + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == howManyPlayers()) currentPlayer = 0;
        return true;
    }

    private String getCurrentPlayerName() {
        return players.get(currentPlayer).getName();
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
