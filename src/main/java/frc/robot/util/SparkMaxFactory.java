package frc.robot.util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

public class SparkMaxFactory {
    // Periodic Status Frames: http://www.revrobotics.com/sparkmax-users-manual/#section-3-3-2-1
    
    private SparkMaxFactory() {}

    public static final double voltageCompensation = 11;

    public static CANSparkMax create(int port) {
        var spark = new CANSparkMax(port, MotorType.kBrushless);

        spark.clearFaults();
        spark.enableVoltageCompensation(voltageCompensation);
        spark.getEncoder().setPosition(0);

        return spark;
    }

    public static CANSparkMax createMaster(int port) {
        var master = create(port);

        master.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 5);
        master.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
        master.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 20);

        return master;
    }

    public static CANSparkMax createFollower(CANSparkMax master, int port) {
        var follower = create(port);

        follower.follow(master, false);

        follower.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 500);
        follower.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 500);
        follower.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 500);

        return follower;
    }
}