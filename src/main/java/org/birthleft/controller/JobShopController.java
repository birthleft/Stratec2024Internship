package org.birthleft.controller;

import org.birthleft.exception.FileInvalidSegmentException;
import org.birthleft.mapper.StringToIntegerMapper;
import org.birthleft.model.MachineModel;
import org.birthleft.validator.CapacityValidator;
import org.birthleft.validator.CooldownTimeValidator;

import javax.crypto.Mac;
import java.util.*;

public class JobShopController {
    private Map<Integer, MachineModel> machines;
    private Map<Integer, MachineModel> parts;

    public JobShopController(Map<String, Map<Integer, String>> data) {
        this.machines = new HashMap<>();
        this.parts = new HashMap<>();
        processSegments(data);
    }

    public void processSegments(Map<String, Map<Integer, String>> data) {
        for (Map.Entry<String, Map<Integer, String>> entry : data.entrySet()) {
            switch (entry.getKey()) {
                case "Available machines":
                    processAvailableMachines(entry.getValue());
                    break;
                case "Machine features":
                    processMachineFeatures(entry.getValue());
                    break;
            }
        }
    }

    private void processAvailableMachines(Map<Integer, String> segmentContent) {
        if (segmentContent.isEmpty()) {
            throw new FileInvalidSegmentException(-1, "Invalid available machines! No available machines found!");
        }
        for (Map.Entry<Integer, String> entry : segmentContent.entrySet()) {
            String[] parts = entry.getValue().split("\\.");
            if (parts.length != 2) {
                throw new FileInvalidSegmentException(entry.getKey(), "Invalid available machines! Invalid machine entry!");
            }
            try {
                Integer machineId = Integer.parseInt(parts[0]);
                String machineName = parts[1].trim();
                machines.put(machineId, new MachineModel(machineId, machineName, -1, -1));
            } catch (NumberFormatException e) {
                throw new FileInvalidSegmentException(entry.getKey(), "Invalid available machines! Invalid machine id!");
            }
        }
    }

    private void processMachineFeatures(Map<Integer, String> segmentContent) {
        if (segmentContent.isEmpty()) {
            throw new FileInvalidSegmentException(-1, "Invalid machine features! No machine features found!");
        }
        if (segmentContent.size() % 2 != 0) {
            throw new FileInvalidSegmentException(-1, "Invalid machine features! There should be an even number of lines!");
        }
        if (segmentContent.size() / 2 != machines.size()) {
            throw new FileInvalidSegmentException(-1, "Invalid machine features! There should be a feature for each machine!");
        }

        Iterator<Map.Entry<Integer, String>> segmentLines = segmentContent.entrySet().iterator();
        while (segmentLines.hasNext()) {
            Map.Entry<Integer, String> firstLine = segmentLines.next();
            Map.Entry<Integer, String> secondLine = segmentLines.next();

            String[] firstParts = firstLine.getValue().split("-");
            if (firstParts.length != 2) {
                throw new FileInvalidSegmentException(firstLine.getKey(), "Invalid machine features! Invalid feature entry!");
            }
            try {
                Integer machineId = Integer.parseInt(firstParts[0].trim().substring(0, firstParts[0].trim().length() - 1));
                if (!machines.containsKey(machineId)) {
                    throw new FileInvalidSegmentException(firstLine.getKey(), "Invalid machine features! Machine not found!");
                }
                MachineModel foundMachine = machines.get(machineId);

                String[] capacityFeature = firstParts[1].split(":");
                if (capacityFeature.length != 2) {
                    throw new FileInvalidSegmentException(firstLine.getKey(), "Invalid machine features! Invalid capacity feature!");
                }
                if (!capacityFeature[0].trim().equals("Capacity")) {
                    throw new FileInvalidSegmentException(firstLine.getKey(), "Invalid machine features! Invalid capacity feature!");
                }

                // This is where the human-readability becomes more of a hindrance than a help.
                // Based on the two input files, the capacity feature is always the first one.
                // The capacity feature doesn't use a numeric value either, but a string value.
                // The way to overcome this is by a map of string values to integer values.
                // The capacity feature's main format is "Capacity: X part/parts at a time", where X is a string that could be converted to an integer.
                // There is also a "secondary" state, in which the Capacity is defined as "no limit", which means that the capacity is 0.

                foundMachine.setCapacity(CapacityValidator.compute(capacityFeature[1].trim(), firstLine.getKey()));

                String[] secondParts = secondLine.getValue().split("-");
                if (secondParts.length != 2) {
                    throw new FileInvalidSegmentException(secondLine.getKey(), "Invalid machine features! Invalid feature entry!");
                }
                String[] cooldownFeature = secondParts[1].split(":");
                if (cooldownFeature.length != 2) {
                    throw new FileInvalidSegmentException(firstLine.getKey(), "Invalid machine features! Invalid capacity feature!");
                }
                if (!cooldownFeature[0].trim().equals("Cooldown time")) {
                    throw new FileInvalidSegmentException(secondLine.getKey(), "Invalid machine features! Invalid cooldown time feature!");
                }

                // For cooldown time, things are a bit different.
                // The format is "Cooldown time: X seconds after each part.", where X is a numeric value.
                // The "secondary" state is "none", which means that the cooldown time is 0.
                // We can parse the numeric value directly.

                foundMachine.setCooldownTime(CooldownTimeValidator.compute(cooldownFeature[1].trim(), secondLine.getKey()));
            } catch (NumberFormatException e) {
                throw new FileInvalidSegmentException(firstLine.getKey(), "Invalid machine features! Invalid machine id!");
            }
        }
        var lol = 0;
    }
}
