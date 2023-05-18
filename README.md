# AmethystDB 🌌🔮

AmethystDB is a stand-alone class that is able to put 'records' (Objects) in a JSON like array.
Records are index-stored and the class does not change the index.


## Dependencies
```
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-core</artifactId>
  <version>2.14.2</version>
</dependency>
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.14.2</version>
 </dependency>
```

## Example usage (in org.example)

```java

public class Human{
  private String name;
  private Integer health;
  
  public Human(String name, Integer health){
    this.name = name;
    this.health = health;
  }
  
  public String getName(){
    return this.name;
  }
  
  public Integer getHealth(){
    return this.health;
  }
  
}

public class Main{

  public static void main(String[] args){
    AmethystDB ADB = new AmethystDB("org.example.Human");
    ADB.addRecord( new Human("Jack", 90);
    ADB.showBase();
  }

}

```
        
