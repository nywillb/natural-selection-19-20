package org.firstinspires.ftc.teamcode.pyppyn.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Position;

@Autonomous(name="\uD83D\uDD35 \uD83C\uDFD7️ Blue Building Zone")
public class BlueBuildingZone extends AutonomousOperation {
    @Override
    public Alliance getAlliance() {
        return Alliance.BLUE;
    }

    @Override
    public Position getPosition() {
        return Position.BUILDING_ZONE;
    }
}
