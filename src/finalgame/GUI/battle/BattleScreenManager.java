package finalgame.GUI.battle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.reflections.Reflections;

import finalgame.Game;
import finalgame.ResourceLoader;
import finalgame.Graphics.RenderEngine;
import finalgame.Graphics.RenderObj;

@SuppressWarnings("unused")
public class BattleScreenManager {
	private static ArrayList<String> battleClasses;
	private static Map<String, Class<? extends BattleScreen>> battleScreens;
	private static boolean init = false;
	
	static {
		System.out.println("Battle Screen init");
		battleClasses = new ArrayList<String>();
		battleScreens = new LinkedHashMap<String, Class<? extends BattleScreen>>();
		loadBattleClasses();
		init = true;
	}

	private static void loadBattleClasses() {
		if(init) return;
		Reflections reflections = new Reflections("finalgame",
				Game.class.getClassLoader());
		Set<Class<? extends BattleScreen>> classes = reflections
				.getSubTypesOf(BattleScreen.class);
		for (Class clazz : classes) {
			try {
				String clazzName = clazz.getSimpleName().toLowerCase();
				Texture tex = TextureLoader.getTexture("PNG", ResourceLoader
						.getResourceAsStream("resources\\topaz\\battlescreen\\"
								+ clazzName + ".png", true));
				RenderEngine.registerSprite(clazzName + ".bg", tex);
				battleScreens.put(clazzName, clazz);
				battleClasses.add(clazzName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static RenderObj getNewBattleScene(String name) {
		name = name.toLowerCase();
		try {
			return (RenderObj) battleScreens.get(name).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}
}
