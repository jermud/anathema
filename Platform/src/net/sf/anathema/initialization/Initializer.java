package net.sf.anathema.initialization;

import net.sf.anathema.framework.IApplicationModel;
import net.sf.anathema.framework.configuration.IInitializationPreferences;
import net.sf.anathema.framework.exception.CentralExceptionHandler;
import net.sf.anathema.framework.module.IItemTypeConfiguration;
import net.sf.anathema.framework.resources.AnathemaResources;
import net.sf.anathema.framework.view.ApplicationView;
import net.sf.anathema.initialization.reflections.AggregatedResourceLoader;
import net.sf.anathema.initialization.reflections.CustomDataResourceLoader;
import net.sf.anathema.initialization.reflections.DefaultAnathemaReflections;
import net.sf.anathema.initialization.reflections.ReflectionObjectFactory;
import net.sf.anathema.initialization.reflections.ResourceLoader;
import net.sf.anathema.initialization.repository.RepositoryLocationResolver;
import net.sf.anathema.lib.exception.CentralExceptionHandling;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.resources.ResourceFile;

import java.util.Collection;
import java.util.Set;

public abstract class Initializer {

  private final IInitializationPreferences initializationPreferences;
  private final ItemTypeConfigurationCollection itemTypeCollection;
  private final AnathemaExtensionCollection extensionCollection;
  private final DefaultAnathemaReflections reflections;
  private final Instantiater objectFactory;

  public Initializer(IInitializationPreferences initializationPreferences) throws InitializationException {
    this.reflections = new DefaultAnathemaReflections();
    this.objectFactory = new ReflectionObjectFactory(reflections);
    this.itemTypeCollection = new ItemTypeConfigurationCollection(objectFactory);
    this.extensionCollection = new AnathemaExtensionCollection(objectFactory);
    this.initializationPreferences = initializationPreferences;
  }

  protected InitializedModelAndView initializeModelViewAndPresentation() throws InitializationException {
    ResourceLoader loader = createResourceLoaderForInternalAndCustomResources();
    AnathemaResources resources = initResources(loader);
    showVersion(resources);
    configureExceptionHandling(resources);
    IApplicationModel anathemaModel = initModel(resources, loader);
    ApplicationFrameView view = initView(resources, anathemaModel, objectFactory);
    initPresentation(resources, anathemaModel, view);
    return new InitializedModelAndView(view, anathemaModel);
  }

  private void initPresentation(AnathemaResources resources, IApplicationModel anathemaModel, ApplicationView view) {
    Collection<IItemTypeConfiguration> itemTypes = itemTypeCollection.getItemTypes();
    AnathemaPresenter presenter = new AnathemaPresenter(anathemaModel, view, resources, itemTypes, objectFactory);
    presenter.initPresentation();
  }

  private void configureExceptionHandling(AnathemaResources resources) {
    CentralExceptionHandling.setHandler(new CentralExceptionHandler(resources));
  }

  private IApplicationModel initModel(IResources resources, ResourceLoader loader) throws InitializationException {
    displayMessage("Creating Model...");
    Collection<IItemTypeConfiguration> itemTypes = itemTypeCollection.getItemTypes();
    AnathemaModelInitializer modelInitializer = new AnathemaModelInitializer(initializationPreferences, itemTypes, extensionCollection);
    return modelInitializer.initializeModel(resources, reflections, loader);
  }

  private AnathemaResources initResources(ResourceLoader loader) {
    displayMessage("Loading Resources...");
    AnathemaResources resources = new AnathemaResources();
    Set<ResourceFile> resourcesInPaths = loader.getResourcesMatching(".*\\.properties");
    for (ResourceFile resource : resourcesInPaths) {
      resources.addResourceBundle(resource);
    }
    return resources;
  }

  private ResourceLoader createResourceLoaderForInternalAndCustomResources() {
    RepositoryLocationResolver resolver = new RepositoryLocationResolver(initializationPreferences);
    CustomDataResourceLoader customLoader = new CustomDataResourceLoader(resolver);
    return new AggregatedResourceLoader(reflections, customLoader);
  }

  protected IInitializationPreferences getPreferences() {
    return initializationPreferences;
  }

  protected abstract void showVersion(IResources resources);

  protected abstract void displayMessage(String message);

  protected abstract ApplicationFrameView initView(IResources resources, IApplicationModel anathemaModel, Instantiater objectFactory);
}