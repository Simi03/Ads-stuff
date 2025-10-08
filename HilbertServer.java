package ch.zhaw.ads;

public class HilbertServer implements CommandExecutor {
    private void drawHilbert(Turtle turtle, int depth, double dist, double angle) {
        if (depth < 0) return;

        turtle.turn(angle);
        drawHilbert(turtle, depth - 1, dist, -angle);
        turtle.move(dist);

        turtle.turn(-angle);
        drawHilbert(turtle, depth - 1, dist, angle);
        turtle.move(dist);

        drawHilbert(turtle, depth - 1, dist, angle);
        turtle.turn(-angle);
        turtle.move(dist);

        drawHilbert(turtle, depth - 1, dist, -angle);
        turtle.turn(angle);
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
