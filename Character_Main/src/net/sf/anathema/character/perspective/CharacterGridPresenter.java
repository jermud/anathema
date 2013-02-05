package net.sf.anathema.character.perspective;

import com.google.common.collect.Lists;
import net.sf.anathema.character.perspective.model.model.CharacterIdentifier;
import net.sf.anathema.character.perspective.model.model.ItemSystemModel;
import net.sf.anathema.framework.view.PrintNameFile;

import java.util.List;

public class CharacterGridPresenter {

  private final ItemSystemModel model;
  private final CharacterGridView view;
  private final Selector<CharacterIdentifier> characterSelector;

  public CharacterGridPresenter(ItemSystemModel model, CharacterGridView view, CharacterStackPresenter stackPresenter) {
    this.model = model;
    this.view = view;
    this.characterSelector = new ShowOnSelect(stackPresenter);
  }

  public void initPresentation() {
    List<PrintNameFile> printNameFiles = model.collectCharacterPrintNameFiles();
    List<CharacterButtonDto> dtoList = Lists.transform(printNameFiles, new ToCharacterButtonDto());
    view.addButtons(dtoList, characterSelector);
  }
}