package net.sf.anathema.character.generic.dummy;

import net.sf.anathema.character.generic.caste.ICasteType;
import net.sf.anathema.lib.util.Identifier;

public class DummyCasteType extends Identifier implements ICasteType {

  public DummyCasteType() {
    this("DummyCaste"); //$NON-NLS-1$
  }

  public DummyCasteType(String id) {
    super(id);
  }
}