package net.sf.anathema.character.linguistics.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.ConfigurableCharacterChangeListener;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ICharacterModelContext;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.library.removableentry.model.AbstractRemovableEntryModel;
import net.sf.anathema.character.linguistics.presenter.ILinguisticsModel;
import net.sf.anathema.lib.control.IChangeListener;
import net.sf.anathema.lib.lang.StringUtilities;
import net.sf.anathema.lib.util.Identified;
import net.sf.anathema.lib.util.Identifier;
import org.jmock.example.announcer.Announcer;

import java.util.Arrays;

public class LinguisticsModel extends AbstractRemovableEntryModel<Identified> implements ILinguisticsModel {

  private static final int barbarianLanguagesPerPoint= 4;
  private final Identified[] languages = new Identified[] { new Identifier("HighRealm"), //$NON-NLS-1$
      new Identifier("LowRealm"), new Identifier("OldRealm"), new Identifier("Riverspeak"), new Identifier("Skytongue"), new Identifier("Flametongue"), new Identifier("Seatongue"), new Identifier("Foresttongue"), new Identifier("GuildCant"), new Identifier("ClawSpeak"), new Identifier("HighHolySpeech"), new Identifier("Pelagial") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$

  private Identified selection;
  private int languagePointsAllowed;
  private final ICharacterModelContext context;
  private final Announcer<IChangeListener> pointControl = Announcer.to(IChangeListener.class);

  public LinguisticsModel(ICharacterModelContext context) {
    this.context = context;
    ConfigurableCharacterChangeListener listener = new ConfigurableCharacterChangeListener() {
      @Override
      public void configuredChangeOccured() {
        updateLanguagePointAllowance();
      }
    };
    listener.addTraitTypes(AbilityType.Linguistics);
    context.getCharacterListening().addChangeListener(listener);
    updateLanguagePointAllowance();
  }

  private void updateLanguagePointAllowance() {
	int currentPoints = languagePointsAllowed;
    languagePointsAllowed = context.getTraitCollection().getTrait(AbilityType.Linguistics).getCurrentValue() + 1;
    if (currentPoints != languagePointsAllowed)
    	pointControl.announce().changeOccurred();
  }

  @Override
  public Identified[] getPredefinedLanguages() {
    return languages;
  }

  @Override
  protected Identified createEntry() {
    Identified selectionCopy = selection;
    this.selection = null;
    fireEntryChanged();
    return selectionCopy;
  }

  @Override
  public boolean isEntryAllowed() {
    return selection != null && !getEntries().contains(selection);
  }

  @Override
  public boolean isPredefinedLanguage(Object object) {
    return net.sf.anathema.lib.lang.ArrayUtilities.containsValue(languages, object);
  }

  @Override
  public void selectBarbarianLanguage(String customName) {
    if (StringUtilities.isNullOrTrimmedEmpty(customName)) {
      this.selection = null;
      fireEntryChanged();
    }
    selectLanguage(new Identifier(customName));
  }

  @Override
  public void selectLanguage(final Identified language) {
    Preconditions.checkNotNull(language);
    Identified foundLanguage = Iterables.find(getEntries(), new Predicate<Identified>() {
      @Override
      public boolean apply(Identified selectedLanguage) {
        return Objects.equal(language, selectedLanguage);
      }
    }, null);
    if (foundLanguage != null) {
      return;
    }
    this.selection = language;
    fireEntryChanged();
  }

  @Override
  public Identified getPredefinedLanguageById(final String id) {
    return Iterables.find(Arrays.asList(languages), new Predicate<Identified>(){
      @Override
      public boolean apply(Identified definedLanuage) {
        return Objects.equal(id, definedLanuage.getId());
      }
    },null);
  }

  @Override
  public void addCharacterChangedListener(IChangeListener listener) {
    pointControl.addListener(listener);
  }

  @Override
  public int getBarbarianLanguageCount() {
    int count = 0;
    for (Identified language : getEntries()) {
      if (!isPredefinedLanguage(language)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int getLanguagePointsAllowed() {
	updateLanguagePointAllowance();
    return languagePointsAllowed;
  }

  @Override
  public int getLanguagePointsSpent() {
    int spent = getPredefinedLanguageCount();
    spent += Math.ceil((double) getBarbarianLanguageCount() / barbarianLanguagesPerPoint);
    return spent;
  }

  @Override
  public int getPredefinedLanguageCount() {
    int count = 0;
    for (Identified language : getEntries()) {
      if (isPredefinedLanguage(language)) {
        count++;
      }
    }
    return count;
  }
}