package ch.zhaw.ads;


public class SnowflakeServer implements CommandExecutor {

    public void drawSnowflake(Turtle turtle, int stufe, double distance) {
        if(stufe == 0) {
            turtle.move(distance);
        } else {
            stufe--;
            distance = distance/3;
            drawSnowflake(turtle, stufe, distance);
            turtle.turn(60);
            drawSnowflake(turtle, stufe, distance);
            turtle.turn(-120);
            drawSnowflake(turtle, stufe, distance);
            turtle.turn(60);
            drawSnowflake(turtle, stufe, distance);
        }
    }

    @Override
    public String execute(String command) throws Exception {
        Turtle turtle = new Turtle();
        for (int i = 0; i < 3; i++) {
            drawSnowflake(turtle, Integer.parseInt(command), 0.7);
            turtle.turn(-120);
        }

        return turtle.getTrace();
    }
}
