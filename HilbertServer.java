package ch.zhaw.ads;

public class HilbertServer implements CommandExecutor {

    private void drawHilbert(Turtle turtle, int depth, double dist, double angle) {
        if (depth == 0) {

        }
        turtle.turn(-angle);
        // draw recursive
        turtle.move(dist);
        turtle.turn(angle);
        // draw recursive
        turtle.move(dist);
        // draw recursive
        turtle.turn(angle);
        turtle.move(dist);
        // draw recursive
        turtle.turn(-angle);
    }


    @Override
    public String execute(String command) throws Exception {
        int depth = Integer.parseInt(command);
        double dist = 0.8 / (Math.pow(2,depth+1)-1);
        Turtle turtle = new Turtle(0.1, 0.1);

        drawHilbert(turtle, depth, dist, -90);
        return turtle.getTrace();
    }
}
