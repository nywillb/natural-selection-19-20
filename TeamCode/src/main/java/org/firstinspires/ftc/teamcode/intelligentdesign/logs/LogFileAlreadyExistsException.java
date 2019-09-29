package org.firstinspires.ftc.teamcode.intelligentdesign.logs;

public class LogFileAlreadyExistsException extends Exception {
    public LogFileAlreadyExistsException(String file) {
        super("The file " + file + " already exists.");
    }
}
