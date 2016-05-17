Main.class: Main.java SinglePlayerGame.java Game.java HumanTextController.java AIDummyController.java Controller.java Declaration.java Question.java Player.java Deck.java Card.java
	javac Main.java

PlayerTester.class: PlayerTester.java Player.java Deck.java Card.java
	javac PlayerTester.java

DeckTester.class: DeckTester.java Deck.java Card.java
	javac DeckTester.java

run:
	java Main
