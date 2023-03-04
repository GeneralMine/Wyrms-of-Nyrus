package com.vetpetmon.wyrmsofnyrus.locallib.util;

import net.minecraft.util.math.MathHelper;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

// Won't you believe this nonsense? Minecraft doesn't make its own vector objects!
// This means I have to fix it.
// Java already has a Vector3d class, HOWEVER, we want more functions than what Java already provides.
// Such things Java fails to include in the built-in Vector3d includes, but is not limited to:
// - Inverse
// - toString

// TODO: Put this into SynLib v2.0
public class Vect3D {
    public double x,y,z;
    public Vect3D(Vect3D vector){
        this.x= vector.x;this.y= vector.y;this.z= vector.z;
    }
    public Vect3D(double xIn, double yIn, double zIn) {
        this.x= xIn;this.y= yIn;this.z= zIn;
    }

    /**
     * Creates a new 3D Vector object. Similar to how positions work in Unity.
     * Most developers won't need to use this. However, if you are working with
     * AI task functions, 3D Vectors can ease up common development tasks.
     * @param xIn X-Coordinate
     * @param yIn Y-Coordinate
     * @param zIn Z-Coordinate
     * @return A 3D Vector object with XYZ-coordinates.
     */
    public static Vect3D createVector(double xIn, double yIn, double zIn) {
        return new Vect3D(xIn, yIn, zIn);
    }
    /**
     * Modifies an existing 3D Vector object.
     * @param xIn X-Coordinate
     * @param yIn Y-Coordinate
     * @param zIn Z-Coordinate
     * @return A modified 3D Vector object.
     */
    public Vect3D setVector(double xIn, double yIn, double zIn){
        this.x= xIn;
        this.y= yIn;
        this.z= zIn;
        return this;
    }

    // COMPUTATION FUNCTIONS
    /**
     * Unless if the vector is a null vector, it attempts to reduce the magnitude of the inputted vector to 1.0, while preserving its direction.
     * @return Normalized Vect3D
     */
    public double length() {
        return MathHelper.sqrt(Math.pow(this.x,2) + Math.pow(this.y,2) + Math.pow(this.z,2));
    }


    /**
     * Unless if the vector is a null vector, it attempts to reduce the magnitude of the inputted vector to 1.0, while preserving its direction.
     * @return Normalized Vect3D
     */
    public Vect3D normalize() {
        double norm = MathHelper.sqrt(Math.pow(this.x,2) + Math.pow(this.y,2) + Math.pow(this.z,2));
        return norm < 1.0E-4D ? createVector(0.0D, 0.0D, 0.0D) : createVector(this.x / norm, this.y / norm, this.z / norm);
    }

    /**
     * Takes this Vector and another vector and returns a scalar product. Order of the vectors do not matter.
     * Quake uses this. Perfect for scaling vectors!
     * @param vectIn Second Vector to multiply with
     * @return Scalar of both vectors.
     */
    public double dot(Vect3D vectIn) {return (this.x * vectIn.x) + (this.y * vectIn.y) + (this.z * vectIn.z);}

    // MATHEMATICAL CALCULATIONS & FUNCTIONS

    /**
     * Reverses the xyz values of the current 3D Vector object. If an inputted value was already negative,
     * for example, it will become positive while other values turn negative.
     * @return Inverted Vec3D object
     */
    public Vect3D inverse(){
        return new Vect3D(-x, -y, -z);
    }

    // MISC. FUNCTIONS
    /**
     * Prints the values of the 3D Vector object. Essential for logging.
     * @return String with (X, Y, Z) value
     */
    public String toString() {return "xyz(" + this.x + ", " + this.y + ", " + this.z + ")";}

    /**
     * For whatever reason you may have, use this to safely cast a Vect3D into Java's Vector3d (double values)
     * @return Vector3d Object
     */
    public Vector3d convertToBuiltIn3d() {
        return new Vector3d(this.x,this.y,this.z);
    }

    /**
     * For whatever reason you may have, use this to safely cast a Vect3D into Java's Vector3f (float values)
     * @return Vector3f Object
     */
    public Vector3f convertToBuiltIn3f() {
        return new Vector3f(convertToBuiltIn3d());
    }
}
