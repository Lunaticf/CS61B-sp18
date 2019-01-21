import java.lang.Math;

public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        double dis = calcDistance(p);
        return G * mass * p.mass / (dis * dis);
    }

    public double calcForceExertedByX(Planet p) {
        return calcForceExertedBy(p) * (p.xxPos - this.xxPos) / calcDistance(p);
    }

    public double calcForceExertedByY(Planet p) {
        return calcForceExertedBy(p) * (p.yyPos - this.yyPos) / calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double res = 0.0;
        for (Planet planet : planets) {
            /** if not the same planet */
            if (!planet.equals(this)) {
                res += calcForceExertedByX(planet);
            }
        }
        return res;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double res = 0.0;
        for (Planet planet : planets) {
            /** if not the same planet */
            if (!planet.equals(this)) {
                res += calcForceExertedByY(planet);
            }
        }
        return res;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX/mass;
        double aY = fY/mass;

        xxVel += dt * aX;
        yyVel += dt * aY;
        
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
    }


}