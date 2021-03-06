package net.sf.anathema.character.equipment.impl.module;

import net.sf.anathema.character.equipment.creation.presenter.stats.properties.EquipmentUI;
import net.sf.anathema.character.equipment.impl.item.model.gson.GsonEquipmentDatabase;
import net.sf.anathema.character.equipment.item.model.EquipmentStatisticsType;
import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.module.IDatabaseActionProperties;
import net.sf.anathema.initialization.repository.IDataFileProvider;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.Icon;

public class EquipmentDatabaseActionProperties implements IDatabaseActionProperties {

  private static final String EQUIPMENT_DATABASE_ITEM_ID = "EquipmentDatabase.Item"; //$NON-NLS-1$

  private final IResources resources;
  private final IApplicationModel anathemaModel;

  public EquipmentDatabaseActionProperties(IResources resources, IApplicationModel anathemaModel) {
    this.resources = resources;
    this.anathemaModel = anathemaModel;
  }

  @Override
  public String getItemTypeId() {
    return EquipmentDatabaseItemTypeConfiguration.EQUIPMENT_DATABASE_ITEM_TYPE_ID;
  }

  @Override
  public String getActionName() {
    return resources.getString("EquipmentDatabase.NewAction.Name"); //$NON-NLS-1$
  }

  @Override
  public Icon getActionIcon() {
    return new EquipmentUI(resources).getIcon(EquipmentStatisticsType.CloseCombat);
  }

  @Override
  public String getToolTipText() {
    return resources.getString("EquipmentDatabase.NewAction.Tooltip"); //$NON-NLS-1$
  }

  @Override
  public String getProgressTaskTitle() {
    return resources.getString("EquipmentDatabase.Start.Progress.Presentation"); //$NON-NLS-1$
  }

  @Override
  public String getItemId() {
    return EQUIPMENT_DATABASE_ITEM_ID;
  }

  @Override
  public GsonEquipmentDatabase createItemData(IDataFileProvider provider) {
    return GsonEquipmentDatabase.CreateFrom(anathemaModel);
  }
}
