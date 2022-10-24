package com.bully;
import java.util.List;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;

public final class ProcessClass {

    private ProcessClass() {}

    public static Process exec(Class klass, List<String> args , int process_id , int processesNumber) throws IOException,
            InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = klass.getName();

        List<String> command = new LinkedList<String>();
        command.add(javaBin);
        command.add("-cp");
        command.add(classpath);
        command.add(className);
        command.add(Integer.toString(process_id)) ;
        command.add(Integer.toString(processesNumber)) ;
        if (args != null) {
            command.addAll(args);
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.inheritIO().start();
        Main.processes.add(process);
        process.waitFor();
        return process;
    }

}
