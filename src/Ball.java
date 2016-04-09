import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The Ball class has a center Point, a radius, a color, and a velocity.
 * The class has method to draw the ball, and move the ball according to its velocity.
 * @author Matan Ben Noach Nir Ben Shalom
 * @version 1.0 9 April 2016
 */
public class Ball implements Sprite{
    private Point center; // The center point of the ball.
    private int radius; // The radius of the ball.
    private Color color; // The color of the blue.
    private Velocity v; // The velocity of the ball.
    private GameEnvironment gameEnv; // The game environment of the ball.

    /**
     * Constructor to create the ball.
     * @param p a Point which will be the center of the ball.
     * @param r the radius of the ball.
     * @param color the color of the ball.
     * @param gameEnv the game environment of the ball.
     */
    public Ball(Point p, int r, java.awt.Color color, GameEnvironment gameEnv) {
        this.center = p;
        this.radius = r;
        this.color = color;
        this.gameEnv = gameEnv;
    }

    /**
     * Constructor to create the ball.
     * @param x the x center of the ball.
     * @param y the y center of the ball.
     * @param r the radius of the ball.
     * @param color the color of the ball.
     * @param gameEnv the game environment of the ball.
     */
    public Ball(int x, int y, int r, Color color, GameEnvironment gameEnv) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.gameEnv = gameEnv;
    }

    /**
     * Constructor to create the ball.
     * @param p a Point which will be the center of the ball.
     * @param r the radius of the ball.
     * @param color the color of the ball.
     * @param v velocity of the ball.
     * @param gameEnv the game environment of the ball.
     */
    public Ball(Point p, int r, java.awt.Color color, Velocity v, GameEnvironment gameEnv) {
        this.center = p;
        this.radius = r;
        this.color = color;
        this.v = v;
        this.gameEnv = gameEnv;
    }

    /**
     * Constructor to create the ball.
     * @param x the x center of the ball.
     * @param y the y center of the ball.
     * @param r the radius of the ball.
     * @param color the color of the ball.
     * @param v velocity of the ball.
     * @param gameEnv the game environment of the ball.
     */
    public Ball(int x, int y, int r, Color color, Velocity v, GameEnvironment gameEnv) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.v = v;
        this.gameEnv = gameEnv;
    }

    /**
     * getX method returns the x coordinate of the center of the ball.
     * @return the x coordinate of the center of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * getY method returns the y coordinate of the center of the ball.
     * @return the y coordinate of the center of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * getSize method returns the radius of the ball.
     * @return the radius of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * getColor method returns the color of the ball.
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * drawOn method draws the ball on a given surface.
     * @param surface the surface to draw the balls on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(getX(), getY(), radius);
        surface.setColor(Color.black);
        surface.drawCircle(getX(), getY(), radius);
    }

    /**
     * setVelocity sets the balls velocity.
     * @param newV the velocity to set.
     */
    public void setVelocity(Velocity newV) {
        this.v = newV;
    }

    /**
     * setVelocity sets the balls velocity.
     * @param dx the velocity of the x plane.
     * @param dy the velocity of the y plane.
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * getVelocity gets the balls velocity.
     * @return the balls velocity.
     */
    public Velocity getVelocity() {
        return v;
    }

    /**
     * moveOneStep Changes the center of the ball according to the balls velocity.
     */
    public void moveOneStep() {
        // Get the trajectory.
        Line traj = new Line(this.center, this.v.applyToPoint(this.center));
        // Calculate the collision point if such exists.
        CollisionInfo myInfo = gameEnv.getClosestCollision(traj);
        if (myInfo.collisionPoint() != null) {
            // Calculate and apply the brake speed so the ball's center point wont go over the border.
            Velocity tempV = new Velocity(myInfo.collisionPoint().getX() - this.getX() -
                    (int) Math.signum(v.getDx()), myInfo.collisionPoint().getY() - this.getY()
                    - (int) Math.signum(v.getDy()));
            this.center = tempV.applyToPoint(this.center);
            // Adjust the speed after collision
            Velocity newV = (myInfo.collisionObject()).hit(myInfo.collisionPoint(),v);
            setVelocity(newV);
        }
        else {
            this.center = v.applyToPoint(this.center);
        }
    }

    /**
     * timePassed calls the moveOneStep method in order to move the ball.
     */
    public void timePassed(){
        moveOneStep();
    }

    /**
     * addToGame is in charge of adding the paddle as a sprite to the game's sprites list.
     * @param g is the game object we created.
     */
    public void addToGame (Game g){
        g.addSprite(this);
    }
}
