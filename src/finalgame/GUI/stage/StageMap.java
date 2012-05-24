package finalgame.GUI.stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import finalgame.Game;
import finalgame.ResourceLoader;
import finalgame.Engine.moves.Move;
import finalgame.Graphics.Location;

public class StageMap {
	private static Map<String, Stage> stages;
	private static boolean init = false; 
	
	static {
		System.out.println("Stages init");
		stages = new LinkedHashMap<String, Stage>();
		loadStages();
		init = true;
	}
	
	private static void loadStages() {
		if(init) return;
		Reflections reflections = new Reflections("finalgame",
				Game.class.getClassLoader());
		File folder = ResourceLoader.getResourceFile("resources\\maps", true);
		File[] maps = folder.listFiles();
		if (maps == null)
			return;
		for (File map : maps) {
			String fname = map.getName();
			int mid = fname.indexOf('.');
			if(mid < 0 || !fname.substring(mid + 1).equals("map")) continue;
			try {
				StageLoader.load(map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addStage(String name, Stage stage) {
		stages.put(name, stage);
	}

	public static Stage getStage(String name) {
		return stages.get(name);
	}
}
