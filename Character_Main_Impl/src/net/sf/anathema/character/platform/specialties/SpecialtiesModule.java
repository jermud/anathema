package net.sf.anathema.character.platform.specialties;

import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.framework.additionaltemplate.IAdditionalViewFactory;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.IAdditionalModelFactory;
import net.sf.anathema.character.generic.framework.module.CharacterModule;
import net.sf.anathema.character.generic.framework.module.CharacterModuleAdapter;
import net.sf.anathema.character.presenter.specialty.SpecialtiesModelFactory;
import net.sf.anathema.character.presenter.specialty.SpecialtiesTemplate;
import net.sf.anathema.lib.registry.IRegistry;

@CharacterModule
public class SpecialtiesModule extends CharacterModuleAdapter {

  @Override
  public void addAdditionalTemplateData(ICharacterGenerics characterGenerics) {
    IRegistry<String, IAdditionalModelFactory> additionalModelFactoryRegistry = characterGenerics.getAdditionalModelFactoryRegistry();
    String templateId = SpecialtiesTemplate.ID;
    additionalModelFactoryRegistry.register(templateId, new SpecialtiesModelFactory());
    IRegistry<String, IAdditionalViewFactory> additionalViewFactoryRegistry = characterGenerics.getAdditionalViewFactoryRegistry();
    additionalViewFactoryRegistry.register(templateId, new SpecialtiesViewFactory());
    characterGenerics.getGlobalAdditionalTemplateRegistry().add(new SpecialtiesTemplate());
  }
}