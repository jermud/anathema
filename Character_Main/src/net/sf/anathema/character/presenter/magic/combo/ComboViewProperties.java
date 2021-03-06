package net.sf.anathema.character.presenter.magic.combo;

import net.sf.anathema.character.generic.framework.magic.stringbuilder.CharmInfoStringBuilder;
import net.sf.anathema.character.generic.framework.magic.stringbuilder.ICharmInfoStringBuilder;
import net.sf.anathema.character.generic.framework.magic.view.AbstractMagicLearnProperties;
import net.sf.anathema.character.generic.framework.resources.CharacterUI;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.description.MagicDescriptionProvider;
import net.sf.anathema.character.model.charm.IComboConfiguration;
import net.sf.anathema.character.view.magic.IComboViewProperties;
import net.sf.anathema.framework.presenter.resources.BasicUi;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.Icon;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

public final class ComboViewProperties extends AbstractMagicLearnProperties implements IComboViewProperties {
  private final IComboConfiguration comboConfiguration;
  private final ICharmInfoStringBuilder charmInfoStringProvider;

  ComboViewProperties(IResources resources, IComboConfiguration comboConfiguration, MagicDescriptionProvider magicDescriptionProvider) {
    super(resources);
    this.charmInfoStringProvider = new CharmInfoStringBuilder(getResources(), magicDescriptionProvider);
    this.comboConfiguration = comboConfiguration;
  }

  @Override
  public Icon getFinalizeButtonIcon() {
    return new CharacterUI(getResources()).getFinalizeIcon();
  }

  @Override
  public String getAvailableComboCharmsLabel() {
    return getResources().getString("CardView.CharmConfiguration.ComboCreation.AvailableLabel"); //$NON-NLS-1$
  }

  @Override
  public String getComboedCharmsLabel() {
    return getResources().getString("CardView.CharmConfiguration.ComboCreation.SelectedLabel"); //$NON-NLS-1$
  }

  @Override
  public boolean isMagicSelectionAvailable(Object object) {
    if (object == null) {
      return false;
    }
    return comboConfiguration.isComboLegal((ICharm) object);
  }

  @Override
  public boolean isRemoveButtonEnabled(ICharm charm) {
    return charm != null;
  }

  @Override
  public ListCellRenderer getLearnedMagicRenderer() {
    return new AlwaysLegalCharmRenderer(getResources(), charmInfoStringProvider);
  }

  @Override
  public ListCellRenderer getAvailableMagicRenderer() {
    return new ComboLegalityCharmRenderer(getResources(), charmInfoStringProvider, comboConfiguration);
  }

  @Override
  public int getAvailableListSelectionMode() {
    return ListSelectionModel.SINGLE_SELECTION;
  }

  @Override
  public Icon getClearButtonIcon() {
    return new BasicUi(getResources()).getClearIcon();
  }

  @Override
  public String getFinalizeButtonToolTip() {
    return getResources().getString("CardView.CharmConfiguration.ComboCreation.FinalizeToolTip"); //$NON-NLS-1$
  }

  @Override
  public String getClearButtonToolTip() {
    return getResources().getString("CardView.CharmConfiguration.ComboCreation.ClearToolTip");
  } //$NON-NLS-1$

  @Override
  public String getAddButtonToolTip() {
    return getResources().getString("CardView.CharmConfiguration.ComboCreation.AddToolTip"); //$NON-NLS-1$
  }

  @Override
  public String getRemoveButtonToolTip() {
    return getResources().getString("CardView.CharmConfiguration.ComboCreation.RemoveToolTip"); //$NON-NLS-1$  
  }

  @Override
  public Icon getCancelEditButtonIcon() {
    return new CharacterUI(getResources()).getCancelComboEditIcon();
  }

  @Override
  public String getFinalizeButtonEditToolTip() {
    return getResources().getString("CardView.CharmConfiguration.ComboCreation.FinalizeEditToolTip"); //$NON-NLS-1$  }
  }

  @Override
  public String getCancelButtonEditToolTip() {
    return getResources().getString("CardView.CharmConfiguration.ComboCreation.ClearEditToolTip"); //$NON-NLS-1$
  }
}