package com.github.benajmineckstein.refactorkata;

public interface IQuizz {

	boolean add(String playerName);

	void roll(int roll);

	boolean wasCorrectlyAnswered();

	boolean wrongAnswer();

}