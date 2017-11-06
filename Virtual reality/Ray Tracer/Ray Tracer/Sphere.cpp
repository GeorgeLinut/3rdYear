#include "Sphere.hpp"

using namespace rt;

inline double sqr(double a)
{
    return a * a;
}

Intersection Sphere::getIntersection(const Line& line, double minDist, double maxDist) {
    
    double x1 = line.dx().x();
    double y1 = line.dx().y();
    double z1 = line.dx().z();
    double x0 = line.x0().x();
    double y0 = line.x0().y();
    double z0 = line.x0().z();
    double xc = this->_center.x();
    double yc = this->_center.y();
    double zc = this->_center.z();
    double r = this->_radius;

   
    double a = sqr(x0 - x1) + sqr(y0 - y1) + sqr(z0 - z1);
    double c = sqr(x0 - xc) + sqr(y0 - yc) + sqr(z0 - zc) - sqr(r);
    double b = sqr(x1 - xc) + sqr(y1 - yc) + sqr(z1 - zc) - a - c - sqr(r);
    
    double delta2 = sqr(b) - 4 * a * c;

    if (delta2 < 0)
    {
        return Intersection(false, this, &line, 0);
    }

    double delta = sqrt(delta2);
    double t = -(delta + b) / (2 * a);
   
    return Intersection(true, this, &line, t);
}


const Vector Sphere::normal(const Vector& vec) const {
    Vector n = vec - _center;
    n.normalize();
    return n;
}
