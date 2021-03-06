package net.sf.anathema.character.presenter.overview;

import net.sf.anathema.character.generic.caste.ICasteType;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.GlobalCharacterChangeAdapter;
import net.sf.anathema.character.generic.template.ICharacterTemplate;
import net.sf.anathema.character.library.overview.IAdditionalAlotmentView;
import net.sf.anathema.character.library.overview.IOverviewCategory;
import net.sf.anathema.character.model.ICharacter;
import net.sf.anathema.character.model.creation.IBonusPointManagement;
import net.sf.anathema.character.view.overview.CategorizedOverview;
import net.sf.anathema.lib.gui.Presenter;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.workflow.labelledvalue.ILabelledAlotmentView;
import net.sf.anathema.lib.workflow.labelledvalue.IValueView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreationOverviewPresenter implements Presenter {

  private final IResources resources;
  private final CategorizedOverview view;
  private final ICharacter character;
  private final IBonusPointManagement management;
  private final ICharacterTemplate template;
  private final List<IOverviewSubPresenter> presenters = new ArrayList<>();
  private final Map<String, IOverviewCategory> categoriesById = new LinkedHashMap<>();

  public CreationOverviewPresenter(IResources resources, ICharacter character, CategorizedOverview overviewView, IBonusPointManagement management) {
    this.management = management;
    this.resources = resources;
    this.character = character;
    this.template = character.getCharacterTemplate();
    character.getCharacterContext().getCharacterListening().addChangeListener(new GlobalCharacterChangeAdapter() {
      @Override
      public void characterChanged() {
        updateOverview();
      }
    });
    this.view = overviewView;
  }

  @Override
  public void initPresentation() {
    this.management.recalculate();
    IOverviewModel[] allModels = management.getAllModels();
    initConcept();
    initCategories(allModels);
    for (IOverviewModel model : allModels) {
      model.accept(new IOverviewModelVisitor() {
        @Override
        public void visitStringValueModel(IValueModel<String> visitedModel) {
          // TODO : Implement
        }

        @Override
        public void visitIntegerValueModel(IValueModel<Integer> visitedModel) {
          IValueView<Integer> valueView = categoriesById.get(visitedModel.getCategoryId()).addIntegerValueView(getLabelString(visitedModel), 2);
          presenters.add(new ValueSubPresenter(visitedModel, valueView));
        }

        @Override
        public void visitAlotmentModel(ISpendingModel visitedModel) {
          ILabelledAlotmentView valueView = categoriesById.get(visitedModel.getCategoryId()).addAlotmentView(getLabelString(visitedModel), 2);
          presenters.add(new AlotmentSubPresenter(visitedModel, valueView));
        }

        @Override
        public void visitAdditionalAlotmentModel(IAdditionalSpendingModel visitedModel) {
          if (visitedModel.isExtensionRequired()) {
            IAdditionalAlotmentView valueView = categoriesById.get(visitedModel.getCategoryId())
                                                              .addAdditionalAlotmentView(getLabelString(visitedModel),
                                                                      visitedModel.getRequiredSize());
            presenters.add(new AdditionalAlotmentSubPresenter(visitedModel, valueView));
          } else {
            visitAlotmentModel(visitedModel);
          }
        }
      });
    }
    updateOverview();
  }

  private void initCategories(IOverviewModel[] allModels) {
    for (IOverviewModel model : allModels) {
      String categoryId = model.getCategoryId();
      IOverviewCategory category = categoriesById.get(categoryId);
      if (category == null) {
        category = view.addOverviewCategory(getString("Overview.Creation.Category." + categoryId)); 
        categoriesById.put(categoryId, category);
      }
    }
  }

  private void updateOverview() {
    this.management.recalculate();
    for (IOverviewSubPresenter presenter : presenters) {
      presenter.update();
    }
  }

  private String getLabelString(IOverviewModel visitedModel) {
    return getString("Overview.Creation." + visitedModel.getCategoryId() + "." + visitedModel.getId());  
  }

  private String getString(String string) {
    return resources.getString(string);
  }

  private void initConcept() {
    IOverviewCategory category = view.addOverviewCategory(getString("Overview.Creation.Category.Concept")); 
    if (!template.getCasteCollection().isEmpty()) {
      IValueView<String> casteView = category.addStringValueView(getString(template.getPresentationProperties().getCasteLabelResource()));
      IValueModel<String> casteModel = new IValueModel<String>() {
        @Override
        public String getValue() {
          return getCasteValueResourceKey();
        }

        @Override
        public String getId() {
          return getString(template.getPresentationProperties().getCasteLabelResource());
        }

        @Override
        public void accept(IOverviewModelVisitor visitor) {
          visitor.visitStringValueModel(this);
        }

        @Override
        public String getCategoryId() {
          return "Concept"; 
        }
      };
      presenters.add(new StringSubPresenter(casteModel, casteView, resources));
    }
    final String resourcekey = "Overview.Creation.Concept.Motivation.Label";
    IValueView<String> willpowerView = category.addStringValueView(getString(resourcekey));
    IValueModel<String> willpowerModel = new IValueModel<String>() {
      @Override
      public String getValue() {
        return getWillpowerRegainingConceptValue();
      }

      @Override
      public String getId() {
        return resourcekey;
      }

      @Override
      public void accept(IOverviewModelVisitor visitor) {
        visitor.visitStringValueModel(this);
      }

      @Override
      public String getCategoryId() {
        return "Concept"; 
      }
    };
    presenters.add(new StringSubPresenter(willpowerModel, willpowerView, resources));
  }

  private String getWillpowerRegainingConceptValue() {
    if (character.getCharacterConcept().getWillpowerRegainingConcept().getDescription().isEmpty()) {
      return "";
    }
    return "Overview.Creation.Concept.Motivation.Selected"; 
  }

  private String getCasteValueResourceKey() {
    ICasteType casteType = character.getCharacterConcept().getCaste().getType();
    if (casteType.equals(ICasteType.NULL_CASTE_TYPE)) {
      return "";
    }
    return "Caste." + casteType.getId(); 
  }
}