# standalone programs

SinglePlayerGame.class: SinglePlayerGame.java Game.class HumanGUIController.class AIDummyController.class Deck.class
	javac SinglePlayerGame.java

SinglePlayerTextGame.class: SinglePlayerTextGame.java Game.class HumanTextController.class AIDummyController.class Deck.class
	javac SinglePlayerTextGame.java

AIDummyGame.class: AIDummyGame.java Game.class AIDummyController.class Deck.class
	javac AIDummyGame.java

PlayerTester.class: PlayerTester.java Player.class Deck.class
	javac PlayerTester.java

DeckTester.class: DeckTester.java Deck.class
	javac DeckTester.java

# dependency classes

Game.class: Game.java Controller.class
	javac Game.java

HumanGUIController.class: HumanGUIController.java Controller.class FishGUI.class
	javac HumanGUIController.java

FishGUI.class: FishGUI.java
	javac FishGUI.java

HumanTextController.class: HumanTextController.java Controller.class
	javac HumanTextController.java

AIDummyController.class: AIDummyController.java Controller.class
	javac AIDummyController.java

Controller.class: Controller.java Declaration.class Player.class
	javac Controller.java

Declaration.class: Declaration.java Question.class
	javac Declaration.java

Question.class: Question.java Card.class
	javac Question.java

Player.class: Player.java Card.class
	javac Player.java

Deck.class: Deck.java Card.class
	javac Deck.java

Card.class: Card.java
	javac Card.java

run:
	java SinglePlayerGame
