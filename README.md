# 🏕️ 99 Nights in the Forest
<img width="689" height="364" alt="image" src="https://github.com/user-attachments/assets/073c6b9d-3af3-48ee-8cdd-3d0de50de4fb" />


*A minimalist Java survival & escape console game *

---

## 🎯 Concept

**99 Nights in the Forest** is a **single-class, text-based survival/escape game** written entirely in Java.
You play as a lone survivor trapped deep in a mysterious forest. Your goal is simple: **survive 99 nights**… or **craft a rescue signal** before your health runs out.

Each night you must decide what to do — **forage**, **build a fire**, **sleep**, **explore**, or **craft a signal** — balancing your **health**, **energy**, **fire strength**, **morale**, and **food**.

Bad luck, hunger, cold, and strange events lurk in the dark. Every decision matters.

---

## 🧩 Story Premise (Room Escape Adaptation)

You wake up disoriented in a forest clearing with no memory of how you got there.
Your only clue: a **campfire**, a **rusty axe**, and a **note** that says *“Survive until the forest releases you.”*

Each night is like a **room** in a larger escape puzzle —
you must uncover how to stay alive, find resources, and eventually **signal for rescue**.

Will you endure the 99 nights… or discover the secret way out?

---

## 🕹️ How to Play

### 1️⃣ Run the Game

Compile and run using any Java 8+ environment:

```bash
javac ra2/NinetyNineNights.java
java ra2.NinetyNineNights
```

### 2️⃣ Choose Actions

Each “night,” choose what to do by typing a number:

| Action              | Key | Effect                                          |
| ------------------- | --- | ----------------------------------------------- |
| **Forage for food** | `1` | Search for food (risk injury or find resources) |
| **Build fire**      | `2` | Strengthen fire to survive cold nights          |
| **Sleep**           | `3` | Recover energy and some health                  |
| **Explore**         | `4` | Discover new places or face danger              |
| **Craft signal**    | `5` | Try to get rescued (rare chance of success)     |

---

## ❤️ Survival Stats

| Stat              | Description                    | Tips                            |
| ----------------- | ------------------------------ | ------------------------------- |
| **Health**        | If it reaches 0, you die.      | Keep fire strong and eat often. |
| **Energy**        | Needed for most actions.       | Sleep to recover.               |
| **Fire Strength** | Prevents cold damage at night. | Build it often.                 |
| **Morale**        | Affects luck in events.        | Avoid too many bad nights.      |
| **Food**          | Keeps you alive each night.    | Forage or find caches.          |

---

## 🌲 Random Night Events

Each night, unexpected events can happen —
storms, wild animals, eerie chants, cultists, or even a traveler who might help.
The forest’s danger grows with each passing day.

---

## 🏁 Win & Lose Conditions

* **Victory:**

  * Survive all 99 nights, **or**
  * Successfully craft a **rescue signal**.

* **Defeat:**

  * Health ≤ 0
  * You starve, freeze, or fall victim to the forest’s secrets.

---

## 🧠 Design Notes

* **Single-class Java 8 structure** using only static methods — ideal for learning procedural design before OOP.
* Demonstrates:

  * `switch-case` logic
  * `Random` for probability-driven outcomes
  * Game state management with variables
  * Basic console UI and pacing (`Thread.sleep`)

---

## 🧩 Room Escape Version Ideas

If adapting this for a **Room Escape** theme:

* Replace **nights** with **rooms** or **stages** (e.g., *“Room 1: The Campfire”*).
* Each “action” becomes an escape puzzle (e.g., “Build fire” → “Solve a wiring puzzle”).
* “Craft signal” becomes the **final escape mechanism** once all clues are found.
* Add a scoring system or inventory (keys, maps, artifacts).

---

## 📜 License

MIT License — free to modify, share, and adapt.
Just keep the author credit:



---

## 🌌 Final Message

> *“The forest watches silently.
> Each night you survive is another step toward freedom.”*
