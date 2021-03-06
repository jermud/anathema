package net.sf.anathema.framework.view.menu;

import net.sf.anathema.framework.view.MenuBar;

import javax.swing.JMenuBar;

public class MainMenuBar implements MenuBar {

  private final JMenuBar menuBar = new JMenuBar();
  private final IMenuBarView mainMenu;
  private final IMenuBarView helpMenu;

  public MainMenuBar(String mainMenuName, String helpMenuName) {
    char mainMenuMnemonic = mainMenuName.charAt(0);
    mainMenu = new Menu(mainMenuName, mainMenuMnemonic);
    char helpMenuMnemonic = 'H';
    for (int index = 0; index < helpMenuName.length(); index++) {
      char possibleMnemonic = helpMenuName.charAt(index);
      if (possibleMnemonic != mainMenuMnemonic) {
        helpMenuMnemonic = possibleMnemonic;
        break;
      }
    }
    helpMenu = new Menu(helpMenuName, helpMenuMnemonic);
    menuBar.add(mainMenu.getComponent());
    menuBar.add(helpMenu.getComponent());
  }

  @Override
  public IMenu getMainMenu() {
    return mainMenu;
  }

  @Override
  public IMenu getHelpMenu() {
    return helpMenu;
  }

  public JMenuBar getMenuBar() {
    return menuBar;
  }
}