# ⚔️ Battle Arena – Java Turn-Based RPG

A turn-based battle game built using core Object-Oriented Programming concepts in Java, with a JavaFX-based GUI. The player controls a Hero and fights against an AI-driven Enemy using different actions like attack, heal, and special abilities.

---

## 🚀 Features

- Turn-based combat system (Hero vs Enemy)
- Multiple actions:
  - Attack
  - Heal
  - Special Attack (mana-based)
- AI-driven enemy decision logic
- Health and mana management system
- Real-time battle log
- Interactive GUI using JavaFX
- Exception handling for invalid actions
- Multithreaded game execution

---

## 🧠 Concepts Demonstrated

- Object-Oriented Programming (OOP)
- Interfaces and Abstraction
- Inheritance and Polymorphism
- Exception Handling (Custom Exceptions)
- Multithreading (BlockingQueue, daemon threads)
- JavaFX GUI Development
- Modular design using packages

---

## 📂 Project Structure

combatgame/
│
├── actions/
│   ├── Action.java
│   ├── AttackAction.java
│   ├── HealAction.java
│   └── SpecialAttackAction.java
│
├── characters/
│   ├── GameCharacter.java
│   ├── Hero.java
│   └── Enemy.java
│
├── engine/
│   └── BattleEngine.java
│
├── exceptions/
│   └── InvalidActionException.java
│
├── main/
│   └── Game.java
│
├── style.css
└── pom.xml

---

## ⚙️ Tech Stack

- Language: Java  
- GUI: JavaFX  
- Build Tool: Maven  

---

## 🎮 How It Works

- The game runs on a separate battle thread.
- Player inputs are captured via GUI buttons.
- Actions are passed safely using a BlockingQueue.
- The engine processes turns until one character reaches 0 HP.
- All events are logged dynamically in the UI.

---

## 🧩 Key Design Highlights

### Action Interface
Defines a contract for all actions, enabling flexibility and extensibility.

### Abstract GameCharacter
Centralizes shared logic like HP, mana, and damage handling.

### Hero vs Enemy
- Hero: Controlled via user input (thread-safe queue)
- Enemy: AI-based decision-making

### Custom Exception Handling
Invalid actions (e.g., low mana, no heals) are handled gracefully without crashing the game.

---

## 🖥️ Sample Gameplay

BATTLE START

Player attacks Enemy for 12 damage  
Enemy uses SPECIAL ATTACK for 24 damage  

Player heals for 25 HP  

...

Player wins!

---

## ▶️ How to Run

### Prerequisites
- Java 11+
- Maven installed

### Steps

git clone https://github.com/Kavya-2k6/battle-arena.git  
cd combatgame  
mvn clean install  
mvn javafx:run  

---

## 📌 Future Improvements

- Add multiple enemy types
- Introduce inventory system
- Add sound effects and animations
- Save/load game state
- Difficulty levels
