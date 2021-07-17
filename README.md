# DnumFramework
The Dnum Framework is a simple framework made by Maploop which contains a lot of libraries for spigot 1.8.8 plugins.
This framework contians a lot of nice things that you can use without having to code them! Some of them were taken from
other libraries, there is already credit for those people in the classes!

## How to use:
You will first need to clone the repository, how to do that is pretty easy using IntelliJ IDEA's built in
version control system. Follow these steps:

### 1. Click `Use this template`.

### 2. Create a brand new repository for your project.

### 3. Create a new project in IntelliJ and select `Project from Version Control`.

### 4. Click the big green button named Code just above the src of the repository and copy the link you see. (Doesn't matter which type you use, it could be HTTPS, SSH, or GitHub CLI.)

### 5. After you copied it, you need to open your IntelliJ IDEA and paste the link into the textbox you see on Screen.

### 6. Name the project whatever you want your plugin to be named.

### 7. You're done! Now you can refactor the class names how ever much you want!

# Examples and tutorials
There are already examples available in the plugin itself, you can click on the classes to check them out.

But here's some incase you need to:

### Creating commands:
```java
// NOTE: Command class must be named like "Command_<name>" and the name after the Command_ prefix must be your command name.
// You can change this in the AbstractCommand class.

// In the usage, <command> will be replaced by your command's name.
@CommandParameters(description = "My incredible command!", permission = "dnum.command.mycommand", usage = "/<command> <args>", aliases = {"alias1", "alias2"}, inGameOnly = true)
public class Command_commandName extends AbstractCommand {
  public void run(CommandSource sender, String[] args) {
    Player player = sender.getPlayer();
    // Method "send()" is a default method which sends a message to the cmd sender.
    send("&a&lThis is an example!");
  }
}
```

### Registering your command:
```java
// In main class:
private void loadCommands() {
  cl.register(new Command_commandName());
}
// NOTE: You do not need to put the command in your plugin.yml, because it is automatically put into the
// server's command map when loadCommands is run.
// To make this even easier, you can use reflections to get all subclasses in the commands package and
// register them all at once!
```

### Creating GUIs:
```java
public class myGUI extends GUI {
  public myGUI(PlayerMenuUtility playerMenuUtility) {
    super(playerMenuUtility);
  } 
  
  // Title of your inventory.
  @Override
  public String getTitle() {
    return "MyTitle";
  }
  
  // Size of your inventory.
  // NOTE: Must in from 9 to 54.
  @Override
  public int getSize() {
    return 54;
  }
  
  // This is what happens when someone clicks an item
  // in THIS specific inventory.
  @Override
  public void handleMenu(InventoryClickEvent event) {
    // Event is not cancelled by default.
    // You need to cancel it here:
    event.setCancelled(true);
    
    Player player = (Player) event.getWhoClicked();
    if(event.getSlot() == 13) {
      player.sendMessage("§aClicked on slot §e13§a!");
    }
  }
  
  // This will determain where the items in your inventory will be placed.
  // Read examples:
  @Override
  public void setItems() {
    // This returns the player looking at the menu:
    Player player = playerMenuUtility.getOwner();
  
    // This will fill your whole inventory with black stained glass pane.
    setFilter();
    // IF you want to change the material you can use:
    FILLER_GLASS = makeItem(Material.STAINED_GLASS_PANE, "", 1, 0);
    
    // How to use makeItem method:
    // 1st parameter: Material, second parameter: Displayname, third param: Amount, fourth param: Durability, fifth param (optional): item lore, (can be used like "§aLine1\\nLine2"
    inventory.setItem(13, makeItem(Material.POTATO, "§d13", 1, 0, "§7Item in slot 13!\\n§aClick!"));
  }
}
```

### Opening GUIs:
```java
// Pretty simple:
// Player must be the player you want to open it to.
new myGUI(new PlayerMenuUtility(player)).open();
```

### Enjoy using the framework I guess :)
