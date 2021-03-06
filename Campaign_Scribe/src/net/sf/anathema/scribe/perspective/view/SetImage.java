package net.sf.anathema.scribe.perspective.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.anathema.platform.fx.ResourceLoader;
import net.sf.anathema.scribe.perspective.NodeHolder;

import java.io.InputStream;

public class SetImage implements Runnable {
  private final NodeHolder<ImageView> imageView;
  private final String relativePath;

  public SetImage(NodeHolder<ImageView> imageView, String relativePath) {
    this.imageView = imageView;
    this.relativePath = relativePath;
  }

  @Override
  public void run() {
    imageView.getNode().setImage(createImage(relativePath));
  }

  private Image createImage(String pathToImage) {
    ResourceLoader resourceLoader = new ResourceLoader();
    InputStream imageStream = resourceLoader.loadResource(pathToImage);
    return new Image(imageStream, 30, 30, true, true);
  }
}
