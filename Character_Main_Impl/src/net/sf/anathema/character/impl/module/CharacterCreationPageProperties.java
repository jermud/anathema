package net.sf.anathema.character.impl.module;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import net.disy.commons.core.message.BasicMessage;
import net.disy.commons.core.message.IBasicMessage;
import net.disy.commons.core.message.MessageType;
import net.sf.anathema.character.generic.framework.resources.CharacterTemplateResourceProvider;
import net.sf.anathema.character.generic.type.CharacterType;
import net.sf.anathema.character.view.repository.ITemplateTypeAggregation;
import net.sf.anathema.framework.view.IdentificateSelectCellRenderer;
import net.sf.anathema.lib.resources.IResources;

public class CharacterCreationPageProperties {

  private final IResources resources;
  private CharacterTemplateResourceProvider iconProvider;

  public CharacterCreationPageProperties(IResources resources) {
    this.resources = resources;
    this.iconProvider = new CharacterTemplateResourceProvider(resources);
  }

  public String getDescription() {
    return resources.getString("CharacterDialog.Description"); //$NON-NLS-1$
  }

  public IBasicMessage getConfirmMessage() {
    return new BasicMessage(resources.getString("CharacterDialog.Message.Confirm"), MessageType.INFORMATION); //$NON-NLS-1$
  }

  public IBasicMessage getSelectCharacterTypeMessage() {
    return new BasicMessage(resources.getString("CharacterDialog.Message.SelectType"), MessageType.ERROR); //$NON-NLS-1$
  }

  public IBasicMessage getNoTemplatesAvailableMessage() {
    return new BasicMessage(resources.getString("CharacterDialog.Message.NoTemplates"), MessageType.ERROR); //$NON-NLS-1$
  }

  public String getTypeString(CharacterType type) {
    return resources.getString("CharacterGenerator.NewCharacter." //$NON-NLS-1$
        + type.getId()
        + ".Name"); //$NON-NLS-1$
  }

  public Icon getTypeIcon(CharacterType type) {
    return iconProvider.getSmallTypeIcon(type);
  }

  public String getRulesetLabel() {
    return resources.getString("CharacterDialog.Ruleset.Select.Label"); //$NON-NLS-1$
  }

  public ListCellRenderer getRulesetRenderer() {
    return new IdentificateSelectCellRenderer("Ruleset.", resources); //$NON-NLS-1$
  }

  public ListCellRenderer getTemplateRenderer() {
    return new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(
          JList list,
          Object value,
          int index,
          boolean isSelected,
          boolean cellHasFocus) {
        String printname = resources.getString(((ITemplateTypeAggregation) value).getPresentationProperties()
            .getNewActionResource());
        return super.getListCellRendererComponent(list, printname, index, isSelected, cellHasFocus);
      }
    };
  }
}