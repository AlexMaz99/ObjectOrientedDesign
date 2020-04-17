# Laboratorium 3 - Wzorce Projektowe
## Aleksandra Mazur, Grzegorz Poręba


### 4.1. Builder
### 1. Interfejs MazeBuilder
Stworzono interfejs MazeBuilder, służący do tworzenia labiryntów.

```java
package pl.agh.edu.dp.labirynth;

public interface MazeBuilder {

    void addRoom(Room room);
    void createDoor(Room r1, Room r2) throws Exception;
    void createCommonWall(Direction firstRoomDir, Room r1, Room r2) throws Exception;
}
```
### 2. Modyfikacja MazeGame
Po utworzeniu powyższego interfejsu zmodyfikowano funkcję składową tak, aby przyjmowała jako parametr obiekt tej klasy.

```java
package pl.agh.edu.dp.labirynth;

public class MazeGame {
    public Maze createMaze(MazeBuilder builder) throws Exception {
        ...
    }
}
```

### 3. Interpretacja
Obecne zmiany pozwoliły na oddzielenie tworzenia obiektów do innej klasy. Dzięki temu ten sam proces konstrukcji może prowadzić do powstania różnych reprezentacji. Unikniemy powtarzania tego samego kodu kilka razy.

### 4. Klasa StandardMazeBuilder
Do klasy Direction dodano metodę zwracającą przeciwny kierunek.
```java
package pl.agh.edu.dp.labirynth;

public enum Direction {
    North, East, South, West;

    public Direction getOppositeSide() {
        int enumI = (this.ordinal() + 2) % Direction.values().length;
        return Direction.values()[enumI];
    }
}
```

Stworzono klasę StandardMazeBuilder będącą implementacją interfejsu MazeBuilder. Posiada ona zmienną currentMaze, w której jest zapisywany obecny stan labiryntu oraz poniższe metody.

| Metoda | Typ | Znaczenie |
|---------|-----|-----------|
|addRoom| void | dodaje pokój do labiryntu i tworzy 4 ściany|
|createDoor   | void| tworzy drzwi między dwoma pokojami|
| createCommonWall | void | łączy dwa pokoje za pomocą jednej ściany wg. kierunku ściany pierwszego pokoju |
| commonWall | Direction | zwraca wspólną ścianę pomiędzy dwoma pomieszczeniami |
| getCurrentMaze | Maze | zwraca obecny stan labiryntu |


```java
package pl.agh.edu.dp.labirynth;

public class StandardMazeBuilder implements MazeBuilder {

    private Maze currentMaze;

    public StandardMazeBuilder() {
        this.currentMaze = new Maze();
    }

    @Override
    public void addRoom(Room room) {
        room.setSide(Direction.North, new Wall());
        room.setSide(Direction.East, new Wall());
        room.setSide(Direction.South, new Wall());
        room.setSide(Direction.West, new Wall());
        currentMaze.addRoom(room);
    }

    @Override
    public void createDoor(Room r1, Room r2) throws Exception {

        Direction firstRoomDir = commonWall(r1, r2);

        Door door = new Door(r1, r2);

        r1.setSide(firstRoomDir, door);
        r2.setSide(firstRoomDir.getOppositeSide(), door);
    }

    @Override
    public void createCommonWall(Direction firstRoomDir, Room r1, Room r2) throws Exception {
        MapSite side = r1.getSide(firstRoomDir);
        if (side == null)
            throw new Exception("Side doesnt exist");
        r2.setSide(firstRoomDir.getOppositeSide(), side);

    }

    private Direction commonWall(Room r1, Room r2) throws Exception {
        for (Direction dir1 : Direction.values()) {
            if (r1.getSide(dir1).equals(r2.getSide(dir1.getOppositeSide()))) {
                return dir1;
            }
        }
        throw new Exception("Rooms doesnt have common wall");
    }

    public Maze getCurrentMaze() {
        return currentMaze;
    }
}
```
### 5. Utworzenie labiryntu
Zmodyfikowano metodę createMaze, tak aby przyjmowała obiekt klasy StandardMazeBuilder, a następnie utworzono przy jej pomocy labirynt z dwoma pokojami.

```java
package pl.agh.edu.dp.labirynth;

import static pl.agh.edu.dp.labirynth.Direction.*;

public class MazeGame {
    public Maze createMaze(StandardMazeBuilder smb) throws Exception {

        Room r1 = new Room(1);
        Room r2 = new Room(2);
        smb.addRoom(r1);
        smb.addRoom(r2);

        smb.createCommonWall(South, r1, r2);
        smb.createDoor(r1, r2);

        return smb.getCurrentMaze();
    }
}
```

```java
package pl.agh.edu.dp.main;

import pl.agh.edu.dp.labirynth.*;

public class Main {

    public static void main(String[] args) throws Exception {

        MazeGame mazeGame = new MazeGame();
        StandardMazeBuilder builder = new StandardMazeBuilder();
        Maze maze = mazeGame.createMaze(builder);

        System.out.println(maze.getRoomNumbers());
    }
}
```

![](res/2020-04-17-20-39-51.png)
Jak widać powyżej liczba utworzonych pokoi została zwrócona poprawnie.

### 6. Klasa CountingMazeBuilder
Stworzono kolejną klasę implementującą interfejs MazeBuilder, która zlicza utworzone komponenty różnych obiektów.

| Metoda | Typ | Znaczenie |
|---------|-----|-----------|
|addRoom| void | dodaje 1 pokój do liczby pokoi i 4 ściany do łącznej liczby ścian|
|createDoor   | void| usuwa jedną ścianę z liczby ścian i dodaje jedne drzwi do liczby drzwi|
| createCommonWall | void | usuwa jedną ścianę z łącznej liczby ścian |
| getCounts | int | zwraca łączną sumę pokoi, drzwi i ścian |
| getRoomsNr | int | zwraca łączną liczbę pokoi |
| getDoorsNr | int | zwraca łączną liczbę drzwi |
| getWallNr | int | zwraca łączną liczbę ścian |

Trzy ostatnie metody wygenerowano używając biblioteki lombok. W dalszej części zadania również z niej korzystano.

```java
package pl.agh.edu.dp.labirynth;

import lombok.Getter;

@Getter
public class CountingMazeBuilder implements MazeBuilder {

    private int roomsNr;
    private int doorsNr;
    private int wallNr;

    public CountingMazeBuilder() {
        this.roomsNr = 0;
        this.doorsNr = 0;
        this.wallNr = 0;
    }
    
    @Override
    public void addRoom(Room room) {
        roomsNr ++;
        wallNr += 4;
    }

    @Override
    public void createDoor(Room r1, Room r2) throws Exception {
        wallNr --;
        doorsNr ++;
    }

    @Override
    public void createCommonWall(Direction firstRoomDir, Room r1, Room r2) throws Exception {
        wallNr --;
    }

    public int getCounts(){
        return roomsNr + wallNr + doorsNr;
    }

}
```

Przetestowano działanie utworzonej klasy zmieniając tymczasowo kod w metodzie createMaze, tak aby przyjmowała obiekt klasy CountingMazeBuilder.

```java
package pl.agh.edu.dp.labirynth;

import static pl.agh.edu.dp.labirynth.Direction.*;

public class MazeGame {
    public void createMaze(CountingMazeBuilder cmb) throws Exception {

        Room r1 = new Room(1);
        Room r2 = new Room(2);
        cmb.addRoom(r1);
        cmb.addRoom(r2);

        cmb.createCommonWall(South, r1, r2);
        cmb.createDoor(r1, r2);

        System.out.println(cmb.getCounts());
    }
}
```

![](res/2020-04-17-21-02-34.png)

Jak widać powyżej, suma wszystkich pokoi, drzwi i ścian została obliczona poprawnie (2 pokoje + 6 ścian + 1 drzwi).
