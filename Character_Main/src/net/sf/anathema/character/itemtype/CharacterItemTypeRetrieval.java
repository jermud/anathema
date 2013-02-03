package net.sf.anathema.character.itemtype;

import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.item.IItemType;

public class CharacterItemTypeRetrieval {
  public static final String CHARACTER_ITEM_TYPE_ID = "ExaltedCharacter";

  public static IItemType retrieveCharacterItemType(IAnathemaModel model) {
    return model.getItemTypeRegistry().getById(CHARACTER_ITEM_TYPE_ID);
  }
}
