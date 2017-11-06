#include <cmath>
#include <iostream>
#include <string>

#include "Vector.hpp"
#include "Line.hpp"
#include "Geometry.hpp"
#include "Sphere.hpp"
#include "Image.hpp"
#include "Color.hpp"
#include "Intersection.hpp"
#include "Material.hpp"

#include "Scene.hpp"

using namespace std;
using namespace rt;

double power(double number, int power)
{
    double p = 1;

    for (int i = 1; i < power; i++)
    {
        p *= number;
    }

    return p;
}

double imageToViewPlane(int n, int imgSize, double viewPlaneSize) {
    double u = (double)n*viewPlaneSize / (double)imgSize;
    u -= viewPlaneSize / 2;
    return u;
}

const Intersection findFirstIntersection(const Line& ray,
    double minDist, double maxDist) {
    Intersection intersection;

    for (int i = 0; i < geometryCount; i++) {
        Intersection in = scene[i]->getIntersection(ray, minDist, maxDist);
        if (in.valid()) {
            if (!intersection.valid()) {
                intersection = in;
            }
            else if (in.t() < intersection.t()) {
                intersection = in;
            }
        }
    }
    return intersection;
}

Color applyLight(Color& color, const Material& material, const Light* light, 
    const Intersection& intersection, const Sphere* sphere, const Vector& viewPoint)
{
    color = color + material.ambient() * light->ambient();

    Vector C = viewPoint;
    Vector V = intersection.vec();
    Vector L = light->position();
    Vector N = sphere->normal(V);
    Vector E = C - V;
    Vector T = L - V;

    N.normalize();
    T.normalize();
    E.normalize();

    Vector R = 2 * N * (N * T) - T;
    R.normalize();

    if (N * T > 0)
    {
        color = color + light->diffuse() * material.diffuse() * (N * T);
    }

    if (E * R > 0)
    {
        color = color + light->specular() * material.specular() * power((E * R), material.shininess());
    }

    return color;
}

int main() {
    Vector viewPoint(0, 0, 0);
    Vector viewDirection(0, 0, 1);
    Vector viewUp(0, -1, 0);

    double frontPlaneDist = 0;
    double backPlaneDist = 1000;

    double viewPlaneDist = 512;
    double viewPlaneWidth = 1024;
    double viewPlaneHeight = 768;

    int imageWidth = 1024;
    int imageHeight = 768;

    Vector viewParallel = viewUp ^ viewDirection;

    viewDirection.normalize();
    viewUp.normalize();
    viewParallel.normalize();

    Image image(imageWidth, imageHeight);

	for (int i = 0; i < imageWidth; i++)
        for (int j = 0; j < imageHeight; j++)
        {
            Vector x0;
            Vector x1;

            x0 = viewPoint;
            x1 = viewPoint + viewDirection * viewPlaneDist;
            x1 += imageToViewPlane(j, imageHeight, viewPlaneHeight) * viewUp;
            x1 += imageToViewPlane(i, imageWidth, viewPlaneWidth) * viewParallel;

            Line line(x0, x1, true);

            Intersection intersection = findFirstIntersection(line, frontPlaneDist, backPlaneDist);

            if (intersection.valid())
            {
                const Geometry* geometry = intersection.geometry();
                const Sphere* sphere = (const Sphere*)geometry;
                const Material& material = geometry->material();
                Color color;

                for (auto light : lights)
                {
                    applyLight(color, material, light, intersection, sphere, viewPoint);
                }

                image.setPixel(i, j, color);
            }
            else
            {
                image.setPixel(i, j, Color(0, 0, 0));
            }
        }

    image.store("scene.png");

    for (int i = 0; i < geometryCount; i++) {
        delete scene[i];
    }

    return 0;
}
