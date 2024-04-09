package org.birthleft.model;

import org.birthleft.exception.FileMissingSegmentException;
import org.birthleft.exception.TrulyUnexpectedException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileModel {
    private File file;
    private Map<Integer, String> content;

    private final String[] segmentNames = new String[]{"Available machines", "Machine features", "Part list", "Part operations"};

    private void loadFile(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null!");
        }
        file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("File at path " + path + " does not exist!");
        }
    }

    public FileModel(String path) {
        loadFile(path);
    }

    private boolean isComment(String line) {
        return line.startsWith("#");
    }

    private void removeCommentsFromContent(Map<Integer, String> content) {
        List<Integer> linesToRemove = new ArrayList<>(content.size());
        for (Map.Entry<Integer, String> entry : content.entrySet()) {
            if (isComment(entry.getValue())) {
                linesToRemove.add(entry.getKey());
            }
        }
        for (Integer lineIndex : linesToRemove) {
            content.remove(lineIndex);
        }
    }

    private void removeEmptyNewLinesFromContent(Map<Integer, String> content) {
        List<Integer> linesToRemove = new ArrayList<>(content.size());
        for (Map.Entry<Integer, String> entry : content.entrySet()) {
            if (entry.getValue().isEmpty()) {
                linesToRemove.add(entry.getKey());
            }
        }
        for (Integer lineIndex : linesToRemove) {
            content.remove(lineIndex);
        }
    }

    private void checkForSegments(Map<Integer, String> content) {
        Map<String, Boolean> isSegmentPresent = Arrays.stream(segmentNames)
                .map(segmentName -> new AbstractMap.SimpleEntry<>(segmentName, false))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for (Map.Entry<Integer, String> entry : content.entrySet()) {
            for (String segmentName : segmentNames) {
                if (entry.getValue().contains(segmentName)) {
                    isSegmentPresent.put(segmentName, true);
                }
            }
        }

        String[] missingSegments = isSegmentPresent.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .toArray(String[]::new);
        if (missingSegments.length > 0) {
            throw new FileMissingSegmentException(missingSegments);
        }
    }

    private Map<String, ArrayList<Integer>> splitSegments(Map<Integer, String> content) {
        String currentSegment = null;
        // Important to use TreeMap to keep the order of the segments.
        Map<String, ArrayList<Integer>> segments = new TreeMap<>();
        for (Map.Entry<Integer, String> entry : content.entrySet()) {
            boolean isSegmentHeader = false;
            for (String segmentName : segmentNames) {
                if (entry.getValue().contains(segmentName)) {
                    isSegmentHeader = true;
                    currentSegment = segmentName;
                    segments.put(currentSegment, new ArrayList<>());
                }
            }
            if (currentSegment != null && !isSegmentHeader) {
                segments.get(currentSegment).add(entry.getKey());
            }
        }

        return segments;
    }

    public Map<String, Map<Integer, String>> readFile() {
        content = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            Integer position = 1;
            while ((line = br.readLine()) != null)
                content.put(position++, line);
            br.close();
        } catch (IOException e) {
            throw new TrulyUnexpectedException("Could not read file " + file.getAbsoluteFile() + "!");
        }

        removeCommentsFromContent(content);
        removeEmptyNewLinesFromContent(content);
        checkForSegments(content);

        Map<String, ArrayList<Integer>> segments = splitSegments(content);

        return segments.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue()
                                    .stream()
                                    .collect(Collectors.toMap(lineIndex -> lineIndex,
                                            content::get,
                                            (a, b) -> null, // This should never happen.
                                            TreeMap::new)),
                        (a, b) -> null, // This should never happen.
                        TreeMap::new));
    }
}
