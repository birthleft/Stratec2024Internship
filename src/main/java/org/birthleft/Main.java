package org.birthleft;

import org.birthleft.controller.JobShopController;
import org.birthleft.model.FileModel;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var file = new FileModel("C:\\Users\\octav\\IDEAProjects\\Stratec2024Internship\\src\\main\\resources\\Input_One.txt");
        var data = file.readFile();
        var controller = new JobShopController(data);
        controller.processSegments(data);
    }
}