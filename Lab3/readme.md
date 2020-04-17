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

### 4.2 Fabryka abstrakcyjna
Po zapoznaniu się z treścią kolejnych poleceń, stworzono klasę Vector2d, obrazującą współrzędne obiektu na mapie.

| Metoda | Typ | Znaczenie |
|---------|-----|-----------|
|nextPos| Vector2d | zwraca pozycję, na którą powinien przemieścić się obiekt po udaniu się w danym kierunku |
|lowerRight | Vector2d | zwraca nowy wektor o maksymalnych współrzędnych |

```java
package pl.agh.edu.dp.labirynth.Utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d nextPos(Direction dir) {
        switch (dir) {
            case South:
                return new Vector2d(this.x, this.y - 1);
            case East:
                return new Vector2d(this.x + 1, this.y);
            case West:
                return new Vector2d(this.x - 1, this.y);
            case North:
                return new Vector2d(this.x, this.y + 1);
        }
        throw new NullPointerException("Pos doesnt exist");
    }


    public Vector2d lowerRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }
}
```

Następnie do klasy Room dodano zmienną Vector2d odwzorowującą pozycję w labiryncie.

```java
package pl.agh.edu.dp.labirynth.MazeElements.Rooms;

import lombok.Getter;
import pl.agh.edu.dp.labirynth.MazeElements.MapSite;
import pl.agh.edu.dp.labirynth.Utils.Direction;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

import java.util.EnumMap;
import java.util.Map;

public class Room extends MapSite {
    @Getter
    private Vector2d position;
    @Getter
    private Map<Direction, MapSite> sides;


    public Room(Vector2d position) {
        this.sides = new EnumMap<>(Direction.class);
        this.position = position;
    }

    public MapSite getSide(Direction direction) {
        return this.sides.get(direction);
    }

    public void setSide(Direction direction, MapSite ms) {
        this.sides.put(direction, ms);
    }

    @Override
    public void Enter() {
        System.out.println("You entered normal room");
    }
}
```

### 1. Klasa MazeFactory
Stworzono klasę MazeFactory, służącą do tworzenia elementów labiryntu, o następujących metodach:

| Metoda | Typ | Znaczenie |
|---------|-----|-----------|
|createRoom| Room | tworzy pokój na określonej pozycji|
|createDoor   | Door| tworzy drzwi między dwoma pokojami|
| createWall | Wall | tworzy ścianę |

```java
package pl.agh.edu.dp.labirynth.Factory;

import pl.agh.edu.dp.labirynth.MazeElements.Doors.Door;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class MazeFactory {

    public Door createDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }

    public Room createRoom(Vector2d pos) {
        return new Room(pos);
    }

    public Wall createWall() {
        return new Wall();
    }
}
```

### 2. Funkcja createMaze
Przeprowadzono kolejną modyfikację funkcji createMaze tak, aby jako parametr przyjmowała obiekt MazeFactory.

```java
package pl.agh.edu.dp.labirynth;

import static pl.agh.edu.dp.labirynth.Direction.*;

public class MazeGame {
    public Maze createMaze(CountingMazeBuilder builder, MazeFactory factory) throws Exception {

        Room r1 = factory.createRoom(1);
        Room r2 = factory.createRoom(2);

        builder.addRoom(r1);
        builder.addRoom(r2);

        builder.createCommonWall(South, r1, r2);
        builder.createDoor(r1, r2);

        return builder.getCurrentMaze();
    }
}
```

### 3. Klasa EnchantedMazeFactory
Stworzono klasy reprezentujące magiczne pokoje, drzwi i ściany, dziedziczące odpowiednio po klasach Room, Door, Wall.

Klasa EnchantedRoom:
```java
package pl.agh.edu.dp.labirynth.MazeElements.Rooms;

import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class EnchantedRoom extends Room {

    public EnchantedRoom(Vector2d position) {
        super(position);
    }

    @Override
    public void Enter() {
        System.out.println("You entered enchanted room");
    }

}
```

Klasa EnchantedDoor:
```java
package pl.agh.edu.dp.labirynth.MazeElements.Doors;

import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;

public class EnchantedDoor extends Door {
    public EnchantedDoor(Room r1, Room r2) {
        super(r1, r2);
    }

    @Override
    public void Enter() throws Exception {
        System.out.println("You opened enchanted door");
    }
}
```

Klasa EnchantedWall:
```java
package pl.agh.edu.dp.labirynth.MazeElements.Walls;

public class EnchantedWall extends Wall {

    @Override
    public void Enter(){
        System.out.println("You hit Enchanted wall");

    }
}
```

Następnie stworzono klasę EnchantedMazeFactory (fabryka magicznych labiryntów), która dziedziczy z MazeFactory.

```java
package pl.agh.edu.dp.labirynth.Factory;

import pl.agh.edu.dp.labirynth.Factory.MazeFactory;
import pl.agh.edu.dp.labirynth.MazeElements.Doors.Door;
import pl.agh.edu.dp.labirynth.MazeElements.Doors.EnchantedDoor;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.EnchantedRoom;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.EnchantedWall;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class EnchantedMazeFactory extends MazeFactory {

    @Override
    public Door createDoor(Room r1, Room r2) {
        return new EnchantedDoor(r1, r2);
    }

    @Override
    public Room createRoom(Vector2d pos) {
        return new EnchantedRoom(pos);
    }

    @Override
    public Wall createWall() {
        return new EnchantedWall();
    }
}
```

### 4. Klasa BombedMazeFactory
Stworzono klasy reprezentujące pokoje, drzwi i ściany, dziedziczące odpowiednio po klasach Room, Door, Wall.

Klasa BombedRoom:
```java
package pl.agh.edu.dp.labirynth.MazeElements.Rooms;

import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class BombedRoom extends Room {

    public BombedRoom(Vector2d position) {
        super(position);
    }

    @Override
    public void Enter(){
        System.out.println("You entered bombed room");
    }
}
```

Klasa BombedDoor:
```java
package pl.agh.edu.dp.labirynth.MazeElements.Doors;

import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;

public class BombedDoor extends Door {
    public BombedDoor(Room r1, Room r2) {
        super(r1, r2);
    }

    @Override
    public void Enter() throws Exception {
        System.out.println("You opened bombed door");
    }
}
```

Klasa BombedWall:
```java
package pl.agh.edu.dp.labirynth.MazeElements.Walls;

public class BombedWall extends Wall {

    @Override
    public void Enter(){
        System.out.println("You hit bombed wall");

    }
}
```

Następnie stworzono klasę BombedMazeFactory, która dziedziczy z MazeFactory.

```java
package pl.agh.edu.dp.labirynth.Factory;


import pl.agh.edu.dp.labirynth.MazeElements.Doors.BombedDoor;
import pl.agh.edu.dp.labirynth.MazeElements.Doors.Door;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.BombedRoom;
import pl.agh.edu.dp.labirynth.MazeElements.Rooms.Room;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.BombedWall;
import pl.agh.edu.dp.labirynth.MazeElements.Walls.Wall;
import pl.agh.edu.dp.labirynth.Utils.Vector2d;

public class BombedMazeFactory extends MazeFactory{

    @Override
    public Door createDoor(Room r1, Room r2) {
        return new BombedDoor(r1, r2);
    }

    @Override
    public Room createRoom(Vector2d pos) {
        return new BombedRoom(pos);
    }

    @Override
    public Wall createWall() {
        return new BombedWall();
    }
}
```