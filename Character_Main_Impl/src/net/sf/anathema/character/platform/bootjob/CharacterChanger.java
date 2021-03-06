package net.sf.anathema.character.platform.bootjob;

import com.google.common.collect.Lists;
import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.item.IItemType;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static net.sf.anathema.character.itemtype.CharacterItemTypeRetrieval.CHARACTER_ITEM_TYPE_ID;

public abstract class CharacterChanger {
  private final IApplicationModel model;

  public CharacterChanger(IApplicationModel model) {
    this.model = model;
  }

  protected String getCharacterAsString(File file) {
    try {
      return FileUtils.readFileToString(file);
    } catch (IOException e) {
      throw new RuntimeException("Could not clean up repository before launch: Failed to read character", e);
    }
  }

  protected void writeStringAsCharacter(String xmlString, File character) {
    try {
      FileUtils.writeStringToFile(character, xmlString);
    } catch (IOException e) {
      throw new RuntimeException("Could not clean up repository before launch: Failed to modify character.", e);
    }
  }

  public void actOnAllCharacters() {
    for (File character : getCharacters()) {
      actWithCharacter(character);
    }
  }

  private List<File> getCharacters() {
    IItemType character = model.getItemTypeRegistry().getById(CHARACTER_ITEM_TYPE_ID);
    File itemTypeFolder = model.getRepository().getRepositoryFileResolver().getFolder(character.getRepositoryConfiguration());
    if (!itemTypeFolder.exists()) {
      return Collections.emptyList();
    }
    return Lists.newArrayList(itemTypeFolder.listFiles());
  }

  protected abstract void actWithCharacter(File character);
}