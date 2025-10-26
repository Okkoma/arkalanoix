package com.okkoma.arkalanoix;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LevelLoader {
	
    public static class LevelsWrapper {
    	// on a besoin d'un wrapper à cause de la structure de LevelData (le tableau int[][] pattern pose pb autrement sans wrapper)
        private List<LevelData> levels;
        public List<LevelData> getLevels() { return levels; }
        public void setLevels(List<LevelData> levels) { this.levels = levels; }
    }

    public static Map<Integer, LevelData> loadLevels() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = LevelLoader.class.getResourceAsStream("/levels.json");
        LevelsWrapper wrapper = mapper.readValue(inputStream, LevelsWrapper.class);

        // retourne une Map avec key = levelid et value = LevelData
        // la partie level->level est une fonction Lambda : avec en entrée "level" = LevelData du stream, et retourne ce LevelData.
        return wrapper.getLevels().stream()
                .collect(Collectors.toMap(LevelData::getId, level -> level));
    }
}