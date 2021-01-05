package com.github.benajmineckstein.refactorkata;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Old implementation. leave as is to verify that nothing is broken.
 */
public class Quizz implements IQuizz {
   ArrayList players = new ArrayList();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   LinkedList christmasQuestions = new LinkedList();
   LinkedList newyearQuestions = new LinkedList();
   LinkedList holidayQuestions = new LinkedList();
   LinkedList winterMusicQuestions = new LinkedList();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public Quizz() {
      for (int i = 0; i < 50; i++) {
         christmasQuestions.addLast("Christmas Question " + i);
         newyearQuestions.addLast(("New Year Question " + i));
         holidayQuestions.addLast(("Holiday Question " + i));
         winterMusicQuestions.addLast(createWinterMusicQuestion(i));
      }
   }

   public String createWinterMusicQuestion(int index) {
      return "Winter Music Question " + index;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {


      players.add(playerName);
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
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places[currentPlayer] = places[currentPlayer] + roll;
         if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places[currentPlayer]);
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void askQuestion() {
      if (currentCategory() == "Christmas")
         System.out.println(christmasQuestions.removeFirst());
      if (currentCategory() == "NewYear")
         System.out.println(newyearQuestions.removeFirst());
      if (currentCategory() == "Holiday")
         System.out.println(holidayQuestions.removeFirst());
      if (currentCategory() == "WinterMusic")
         System.out.println(winterMusicQuestions.removeFirst());
   }


   private String currentCategory() {
      if (places[currentPlayer] == 0) return "Christmas";
      if (places[currentPlayer] == 4) return "Christmas";
      if (places[currentPlayer] == 8) return "Christmas";
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
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + purses[currentPlayer]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
