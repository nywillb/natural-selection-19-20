package org.firstinspires.ftc.teamcode.intelligentdesign;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Position;
import org.json.JSONException;

import java.io.IOException;
import java.util.Random;

@TeleOp(name = "⚙️ Intelligent Design Test")
public class IDLogTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        IntelligentDesign id1 = new IntelligentDesign("Log test 1", MatchPhase.AUTONOMOUS, Position.BUILDING_ZONE, Alliance.RED);
        id1.startMatch();
        PopulateLog(id1);

        IntelligentDesign id2 = new IntelligentDesign("Log test 2", MatchPhase.TELEOP, Position.LOADING_ZONE, Alliance.BLUE);
        id2.startMatch();
        PopulateLog(id2);
    }

    private void PopulateLog(IntelligentDesign id) {
        Random gen = new Random();

        for (int i = 0; i < 100; i++) {
            try {
                id.addItem(new IDItem("test", gen.nextInt(100)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            id.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
