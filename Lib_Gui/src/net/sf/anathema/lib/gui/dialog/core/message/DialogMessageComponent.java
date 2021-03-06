package net.sf.anathema.lib.gui.dialog.core.message;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import net.sf.anathema.lib.gui.dialog.core.IDialogConstants;
import net.sf.anathema.lib.gui.dialog.widgets.AutoWrappingLabel;
import net.sf.anathema.lib.gui.message.AbstractMessageTypeUi;
import net.sf.anathema.lib.gui.message.MessageTypeUi;
import net.sf.anathema.lib.message.IBasicMessage;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import static net.sf.anathema.lib.gui.layout.LayoutUtils.fillWithoutInsets;

public class DialogMessageComponent extends JPanel {

  private final AutoWrappingLabel messageLabel;
  private final JLabel iconLabel;

  public DialogMessageComponent(boolean withIcon) {
    super(new BorderLayout());

    setBackground(IDialogConstants.HEADER_BACKGROUND_COLOR);

    iconLabel = new JLabel();
    messageLabel = new AutoWrappingLabel("!Dialog.message!", 330); //$NON-NLS-1$
    messageLabel.setBackground(IDialogConstants.HEADER_BACKGROUND_COLOR);
    messageLabel.setForeground(IDialogConstants.HEADER_TEXT_COLOR);
    messageLabel.setFont(IDialogConstants.MESSAGE_LABEL_FONT);

    JPanel iconPanel = new JPanel(new MigLayout(fillWithoutInsets()));
    iconPanel.add(iconLabel, new CC().alignY("top").gapAfter("3"));
    iconPanel.setBackground(IDialogConstants.HEADER_BACKGROUND_COLOR);
    if (withIcon) {
      add(iconPanel, BorderLayout.WEST);
    }
    else {
      add(Box.createHorizontalStrut(10), BorderLayout.WEST);
    }
    add(messageLabel.getContent(), BorderLayout.CENTER);
  }

  public void setMessage(IBasicMessage message) {
    if (message == null) {
      return;
    }
    iconLabel.setIcon(MessageTypeUi.getInstance().getIcon(message.getType()));
    messageLabel.setForeground(AbstractMessageTypeUi.getColor(message.getType()));
    messageLabel.setText(message.getText());
  }
}