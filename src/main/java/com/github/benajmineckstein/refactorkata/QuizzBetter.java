package com.github.benajmineckstein.refactorkata;

import java.util.ArrayList;
import java.util.List;


class Player{
    private final String name;

    private int place;

    private int purse;

    private boolean penaltyBox = false;

    public Player(String playerName, int place, int purse) {
        this.name = playerName;
        this.place = place;
        this.purse = purse;
    }

    public int move(int roll) {
        this.place += roll;
        this.place = this.place % 12;
        return this.place;
    }

    public boolean isPenaltyBox() {
        return penaltyBox;
    }

    public int getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public int getPurse() {
        return purse;
    }

    public void incrPurse(){
        this.purse += 1;
    }

    public void moveInPenaltyBox() {
        this.penaltyBox = true;
    }
}

class Players {

    int currentPlayerPos = 0;
    List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName, 0, 0));

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int getCurrentPlayerPos() {
        return currentPlayerPos;
    }

    public int howManyPlayers() {
        return players.size();
    }

    private void resetPlayerPos() {
        this.currentPlayerPos = 0;
    }


    public String getCurrentPlayerName() {
        return getCurrentPlayer().getName();
    }

    public Player getCurrentPlayer(){
        return getPlayers().get(getCurrentPlayerPos());
    }

    public boolean didPlayerWin() {
        return !(getCurrentPlayer().getPurse() == 6);
    }

    public void moveCurrentPlayer(int roll){
        getCurrentPlayer().move(roll);
    }

    public void chooseNextPlayer() {
        this.currentPlayerPos++;
        if (getCurrentPlayerPos() == howManyPlayers()) {
            resetPlayerPos();
        }
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

    Players players = new Players();

    List<String> christmasQuestions = new ArrayList<>();
    List<String> newyearQuestions = new ArrayList<>();
    List<String> holidayQuestions = new ArrayList<>();
    List<String> winterMusicQuestions = new ArrayList<>();

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
       return players.add(playerName);
    }

    public void roll(int roll) {

        System.out.println(players.getCurrentPlayerName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (players.getCurrentPlayer().isPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.getCurrentPlayerName() + " is getting out of the penalty box");
                players.moveCurrentPlayer(roll);

                System.out.println(players.getCurrentPlayerName()
                        + "'s new location is "
                        + players.getCurrentPlayer().getPlace());
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {

                System.out.println(players.getCurrentPlayerName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            players.moveCurrentPlayer(roll);

            System.out.println(players.getCurrentPlayerName()
                    + "'s new location is "
                    + players.getCurrentPlayer().getPlace());
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if ("Christmas".equals(currentCategory()))
            System.out.println(christmasQuestions.remove(0));
        if ("NewYear".equals(currentCategory()))
            System.out.println(newyearQuestions.remove(0));
        if ("Holiday".equals(currentCategory()))
            System.out.println(holidayQuestions.remove(0));
        if ("WinterMusic".equals(currentCategory()))
            System.out.println(winterMusicQuestions.remove(0));
    }


    private String currentCategory() {
        switch (players.getCurrentPlayer().getPlace() % 4) {
            case 0: return "Christmas";
            case 1: return "NewYear";
            case 2: return "Holiday";
            default: return "WinterMusic";
        }
    }

    public boolean wasCorrectlyAnswered() {
        if (players.getCurrentPlayer().isPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                players.getCurrentPlayer().incrPurse();

                System.out.println(players.getCurrentPlayerName()
                        + " now has "
                        + players.getCurrentPlayer().getPurse()
                        + " Gold Coins.");

                boolean winner = players.didPlayerWin();
                players.chooseNextPlayer();

                return winner;
            } else {
                players.chooseNextPlayer();

                return true;
            }


        } else {

            System.out.println("Answer was correct!!!!");
            players.getCurrentPlayer().incrPurse();

            System.out.println(players.getCurrentPlayerName()
                    + " now has "
                    + players.getCurrentPlayer().getPurse()
                    + " Gold Coins.");

            boolean winner = players.didPlayerWin();
            players.chooseNextPlayer();

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");

        System.out.println(players.getCurrentPlayerName() + " was sent to the penalty box");
        players.getCurrentPlayer().moveInPenaltyBox();

        players.chooseNextPlayer();

        return true;
    }

}
