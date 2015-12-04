package assignment06.entities;

import assignment06.renderEngine.RawModel;
import assignment06.textures.ModelTexture;

/**
 * Created by bryan on 25.11.2015.
 */
public class TexturedEntity extends Entity{
    private ModelTexture texture;

    public TexturedEntity(RawModel model, ModelTexture texture) {
        super(model);
        this.texture = texture;
    }

    public ModelTexture getTexture() {
        return texture;
    }

}
