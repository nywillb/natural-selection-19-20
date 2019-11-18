package org.firstinspires.ftc.teamcode.pyppyn.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Position;

@Autonomous(name="\uD83D\uDD34 \uD83D\uDC77 Red Loading Zone")
public class RedLoadingZone extends AutonomousOperation {
    @Override
    public Alliance getAlliance() {
        return Alliance.RED;
    }

    @Override
    public Position getPosition() {
        return Position.LOADING_ZONE;
    }
}
