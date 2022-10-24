package com.bully;
import java.util.Arrays;
import java.io.IOException;

public class Carrier {
    public static void main(String[] args) throws IOException {

        isolateProcess(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    public static void isolateProcess(int id, int processesNumber) throws IOException {
        new ConnectionPoint(id, processesNumber).startProcess();
    }
}