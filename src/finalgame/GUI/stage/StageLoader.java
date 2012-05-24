package finalgame.GUI.stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import finalgame.ResourceLoader;
import finalgame.Util;
import finalgame.Graphics.RenderEngine;

public class StageLoader {
	public static Stage load(File map) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				ResourceLoader.getResourceAsStream(map)));
		String name = in.readLine();
		String textureName = in.readLine();
		String texture = "resources\\image\\level\\" + textureName;
		String textureType = Util.getExtension(textureName).toUpperCase();
		Texture tex = TextureLoader.getTexture(textureType,
				ResourceLoader.getResourceAsStream(texture, true));
		Stage stage = new Stage(tex);
		StageMap.addStage(name, stage);
		return stage;
	}
}
