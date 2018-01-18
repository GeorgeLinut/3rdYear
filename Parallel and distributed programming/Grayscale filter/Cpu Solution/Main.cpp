#include "..\Common\Image.h"
#include "..\Common\Filter.h"

#include <algorithm>
#include <exception>
#include <future>
#include <iostream>

const size_t NrThreads = 8;

inline uint8_t interval(int32_t value)
{
    if (value > 255) return 255;
    if (value < 0) return 0;
    return (uint8_t)value;
}

int main()
{
    batman::Image image;
    batman::Image result;

    std::string initialFile = "Polar_Bear_Svalbard_Norwegian_Arctic.png";
    std::string resultFile = "Result.png";

    try
    {
        image.LoadFromFile(initialFile);
        std::cout << "Loaded image " << initialFile << std::endl;

        image.Border(border);

        result.Resize(image.Height(), image.Width());

        __int64 start = std::chrono::duration_cast<std::chrono::milliseconds>(
            std::chrono::system_clock::now().time_since_epoch()
            ).count();

        std::vector<std::future<void>> futures;

        for (size_t as = 0; as < NrThreads; as++)
        {
            futures.push_back(std::async([&image, &result, as]()
            {
                for (
                    size_t index = as * image.Size() / NrThreads;
                    index < (as + 1) * image.Size() / NrThreads;
                    index++
                    )
                {
                    int16_t red = 0, green = 0, blue = 0;
                    size_t h = index / image.Width();
                    size_t w = index % image.Width();

                    for (size_t i = 0; i < filterSize; i++)
                        for (size_t j = 0; j < filterSize; j++)
                        {
                            red += image.Pixel(h + i, w + j).Red() * filter[i][j];
                            green += image.Pixel(h + i, w + j).Green() * filter[i][j];
                            blue += image.Pixel(h + i, w + j).Blue() * filter[i][j];
                        }

                    result.Pixel(h, w).Red() = interval(red);
                    result.Pixel(h, w).Green() = interval(green);
                    result.Pixel(h, w).Blue() = interval(blue);
                }
            }));
        }

        for (size_t f = 0; f < futures.size(); f++)
        {
            futures[f].wait();
        }

        __int64 stop = std::chrono::duration_cast<std::chrono::milliseconds>(
            std::chrono::system_clock::now().time_since_epoch()
            ).count();

        std::cout << "Finished aplying filter on " << NrThreads << " threads in "
            << stop - start << " miliseconds" << std::endl;

        image.Unborder();

        //image.StoreToFile("Initial.png");
        result.StoreToFile(resultFile);
        std::cout << "Stored result in image " << resultFile << std::endl;
    }
    catch (std::exception& ex)
    {
        std::cerr << ex.what() << std::endl;
        return -1;
    }
}
