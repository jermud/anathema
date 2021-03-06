package net.sf.anathema.character.view.magic;

import net.sf.anathema.character.generic.framework.magic.view.IMagicLearnProperties;
import net.sf.anathema.character.generic.magic.ICharm;

import javax.swing.Icon;

public interface IComboViewProperties extends IMagicLearnProperties {

  Icon getFinalizeButtonIcon();

  String getFinalizeButtonToolTip();

  String getAvailableComboCharmsLabel();

  String getComboedCharmsLabel();

  Icon getClearButtonIcon();

  String getClearButtonToolTip();

  Icon getCancelEditButtonIcon();

  String getFinalizeButtonEditToolTip();

  String getCancelButtonEditToolTip();

  boolean isRemoveButtonEnabled(ICharm charm);
}