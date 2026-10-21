package frc.robot;

public class constants {

    public static final class ClimbConstants {
        public static final int LIFT_MAIN = 16;             //PWM
        public static final int LIFT_CUR_LMT = 50;   //const

        public static final double LIFT_MOTOR_VOLTAGE_COMP = 10;
        public static final double LIFT_SPEED_DOWN = 0.5;
        public static final double LIFT_SPEED_UP = -0.5;
        public static final double LIFT_HOLD_DOWN = 0;
        public static final double LIFT_HOLD_UP = 0;
      }
   
      

      public static final class CannonConstants {
        public static final double OPEN_VOLTAGE_DUTY = 1;
        public static final double CLOSE_VOLTAGE_DUTY = -1;

        public static final double PULSE_TIME = 1.0 / 8;
        public static final double HOLD_TIME = 1.0 / 8 r;

        public static final int LEFT_CANNON_CHANNEL = 7;
        public static final int RIGHT_CANNON_CHANNEL = 9;
        public static final int MIDDLE_CANNON_CHANNEL = 8;
      }


      public static final class PivotConstants {
        public static final int Pivot_MAIN = 15;             //PWM
        public static final int Pivot_CUR_LMT = 50;   //const

        public static final double Pivot_MOTOR_VOLTAGE_COMP = 10;
        public static final double Pivot_SPEED_DOWN = 0.25;
        public static final double Pivot_SPEED_UP = -0.25;
        public static final double Pivot_HOLD_DOWN = 0;
        public static final double Pivot_HOLD_UP = 0.1;
      }
} ;