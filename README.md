# Once upon a time, there was an ugly implementation of the Trivia Game (Quizz.java).

Have a look at QuizzTest to see how that class was used.

One guy once noticed that as long as the same input is 
provided to the system, it will print the same output at the console. 

So that guy then had to idea to Copy-Paste the old implementation 
(to BetterQuizz.java), and make a test that using a LOT of random 
inputs would call both the old system and the NEW system with the same input.
Many-many times. And the test would then just verify that the output 
remained the same despite your refactorings.

This is called the "Golden Master Method". You need to use this method 
because the code is just to overkill complicated to write tests for at first. 

Your job is now to refactor BetterQuizz.java, continuously running GoldenMasterTest

Goal: apply OOP, SRP, DRY, and other good software practices! 
https://en.wikipedia.org/wiki/SOLID 

## Single-responsibility principle
A class or method should only have a single responsibility. That will keep methods short and classes precise.

## Open–closed principle
Software entities should be open for extension, but closed for modification.

## Liskov substitution principle
Objects in a program should be replaceable with instances of their subtypes without altering the correctness of that program." See also design by contract.

## Interface segregation principle
"Many client-specific interfaces are better than one general-purpose interface.

## Dependency inversion principle
One should depend upon abstractions, not concretions.

Inspiration and original source code can be found here: 
https://github.com/victorrentea/kata-trivia-java 